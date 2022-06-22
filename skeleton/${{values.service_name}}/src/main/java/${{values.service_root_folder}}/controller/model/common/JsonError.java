package ${{values.service_root_folder}}.controller.model.common;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class JsonError {

    @Schema(required = true)
    private ZonedDateTime timestamp;

    @Schema(required = true)
    private List<String> errors;

    @Schema(required = true)
    private String details;
}
