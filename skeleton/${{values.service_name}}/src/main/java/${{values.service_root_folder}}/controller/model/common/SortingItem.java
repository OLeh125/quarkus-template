package ${{values.service_root_folder}}.controller.model.common;

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
