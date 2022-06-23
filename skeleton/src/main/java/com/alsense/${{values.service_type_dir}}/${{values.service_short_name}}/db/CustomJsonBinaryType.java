package ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.db;

import io.quarkiverse.hibernate.types.json.JsonBinaryType;
import java.util.Properties;

public class CustomJsonBinaryType extends JsonBinaryType {

    public static final CustomJsonBinaryType INSTANCE = new CustomJsonBinaryType();

    @Override
    public void setParameterValues(Properties parameters) {
        if (parameters.isEmpty()) {
            return;
        }
        super.setParameterValues(parameters);

    }

}
