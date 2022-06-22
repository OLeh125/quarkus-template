package ${{values.service_root_folder}}.db;

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
