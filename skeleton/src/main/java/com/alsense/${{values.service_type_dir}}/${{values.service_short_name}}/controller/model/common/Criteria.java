package ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.controller.model.common;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@RegisterForReflection
public class Criteria {

    private Pagination pagination;
    private List<SortingItem> sortOrder;
    private String search;

}
