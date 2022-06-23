package ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}};


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.common.QuarkusTestResource;
import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;

@QuarkusTestResource(TestDbContainer.class)
public class WholeDsiApiApplicationTests {

    @Inject
    protected ObjectMapper objectMapper;
    protected Validator validator;
    @Inject
    protected SqlTestScriptsExecutor sqlTestScriptsExecutor;

    @BeforeEach
    public void init() {
        objectMapper.enable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        objectMapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

}