package ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.db;

import ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.exception.CallDbFunctionException;
import ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.exception.notfound.NotFoundException;
import io.quarkus.hibernate.orm.PersistenceUnit;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.gradle.internal.impldep.javax.inject.Inject;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StringType;

@Slf4j
@ApplicationScoped
@Transactional
public class JdbcFunctionCaller {

    private static final String CALL_FUNCTION_QUERY= "SELECT %s ( %s ) AS res";

    @Inject
    @PersistenceUnit("readOnly")
    EntityManager entityManagerRead;
    @Inject
    @Default
    EntityManager entityManager;

    public String getResponse(String functionName, boolean readOnly, SqlParameter... parameters)
        throws NotFoundException {
        String result;
        try {
            String params = Arrays.stream(parameters)
                .filter(Objects::nonNull)
                .map(p -> ":" + p.getName())
                .collect(Collectors.joining(", "));

            String sql = String.format(CALL_FUNCTION_QUERY, functionName, params);
            NativeQuery query = readOnly
                ? ((Session) this.entityManagerRead.getDelegate()).createSQLQuery(sql)
                : ((Session) this.entityManager.getDelegate()).createSQLQuery(sql);

            declareParameters(query, parameters);
            query.addScalar("res", StringType.INSTANCE);
            result = (String) query.getSingleResult();
        } catch (Exception e) {
            log.error("Error during db function call. ", e);
            throw new CallDbFunctionException("Error during db function call. For more details check logs");
        }
        return Optional.ofNullable(result).orElseThrow(this::getNotFoundException);
    }

    public Long getResponseWithAffectedCount(String functionName, SqlParameter... parameters) {
        return Long.parseLong(getResponse(functionName, false, parameters));
    }

    private void declareParameters(NativeQuery function, SqlParameter... parameters) {
        Arrays.stream(parameters)
            .forEach(p -> function
                .setParameter(p.getName(), p.getValue(), p.getType()));
    }

    private NotFoundException getNotFoundException() {
        return new NotFoundException("No data found for passed values. Function returned null");
    }

}

