@PersistenceUnit("readOnly")
@PersistenceUnit(PersistenceUnit.DEFAULT)
package ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.db.model;

import io.quarkus.hibernate.orm.PersistenceUnit;