package ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.Type;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SqlParameter {

    private String name;
    private Type type;
    private Object value;

}
