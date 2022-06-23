package ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.config;

import ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.db.CustomJsonBinaryType;
import java.sql.Types;
import org.hibernate.dialect.PostgreSQL10Dialect;

public class PostgreSQL10JsonDialect extends PostgreSQL10Dialect {
  
    public PostgreSQL10JsonDialect() {
        super();
        this.registerHibernateType(
            Types.OTHER, CustomJsonBinaryType.class.getName()
        );
    }
}