package ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.db.repository;

import ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.db.JdbcFunctionCaller;
import ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.db.SqlParameter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.SneakyThrows;

@ApplicationScoped
@Transactional
public class Jdbc {

    @Inject
    ObjectMapper mapper;
    @Inject
    JdbcFunctionCaller functionCaller;

    @SneakyThrows(JsonProcessingException.class)
    protected  <T> T mapToResponse(String responseToTransform, Class<T> clazz) {
        return mapper.readValue(responseToTransform, clazz);
    }

    protected String executeDbCall(String functionName, boolean readOnly, SqlParameter... parameters) {
        return functionCaller.getResponse(functionName, readOnly, parameters);
    }

    protected Long executeDbCallWithAffectedCount(String functionName, SqlParameter... parameters) {
        return functionCaller.getResponseWithAffectedCount(functionName, parameters);
    }

}
