package ${{values.service_root_folder}}.controller.model.common;

import java.io.File;
import javax.ws.rs.FormParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultipartBody {

    @FormParam("file")
    private File file;

}