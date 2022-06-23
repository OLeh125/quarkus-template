package ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.db.model;


import io.quarkiverse.hibernate.types.json.JsonBinaryType;
import io.quarkiverse.hibernate.types.json.JsonTypes;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name = "alarm_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = JsonTypes.JSON_BIN, typeClass = JsonBinaryType.class)
public class AlarmCategoryEntity {

    @Id
    @Column(unique = true, nullable = false, updatable = false, insertable = false)
    private long id;

    @Column(unique = true, nullable = false, updatable = false, insertable = false)
    @Type(type = "pg-uuid")
    @NaturalId
    private UUID guid;

    @Column(name = "name", nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", insertable = false)
    private ZonedDateTime createdAt;
}
