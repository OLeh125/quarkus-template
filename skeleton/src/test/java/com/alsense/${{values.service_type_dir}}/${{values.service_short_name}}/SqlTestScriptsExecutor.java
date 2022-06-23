package ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}};

import ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.exception.notfound.NotFoundException;
import io.quarkus.hibernate.orm.PersistenceUnit;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.gradle.internal.impldep.javax.inject.Inject;

@ApplicationScoped
public class SqlTestScriptsExecutor {

    @Inject
    @Default
    EntityManager entityManager;

    @Transactional
    public void executeStatement(List<String> fileNames) throws IOException {
        List<String> sqlScripts = new ArrayList<>();
        for (String fileName: fileNames) {
            InputStream inputStream = WholeDsiApiApplicationTests.class.getResourceAsStream(fileName);
            if (inputStream == null){
                throw new NotFoundException(String.format("File %s is not found", fileName));
            }
            String script = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            sqlScripts.add(script);
        }
        sqlScripts.forEach(s -> entityManager.createNativeQuery(s).executeUpdate());
    }

}
