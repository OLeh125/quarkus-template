package ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.controller.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortingItem {

    private String field;
    private boolean desc;
}
