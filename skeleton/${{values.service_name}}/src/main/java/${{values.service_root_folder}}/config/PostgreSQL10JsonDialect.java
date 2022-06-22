package ${{values.service_root_folder}}.config;

import ${{values.service_root_folder}}.db.CustomJsonBinaryType;
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