package ${{values.service_root_folder}}.controller.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {

    @Positive
    private Integer pageSize;
    @Positive
    private Integer pageNumber = 1;

    public static Pagination empty() {
        return new Pagination(null, null);
    }

    @JsonIgnore
    public boolean isEmpty() {
        return pageSize == null;
    }
}
