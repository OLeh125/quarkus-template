package ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.controller.v1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;


//TODO move to constants
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class ExampleController {

    @GET
    @Path("/example")
    @Operation(summary = "Get example", description = "example")
//    @APIResponses(value = @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON,
//        schema = @Schema(implementation = ExampleResponse.class))))
    public Response getExample() {
        return Response.ok("{}").build();
    }

}
