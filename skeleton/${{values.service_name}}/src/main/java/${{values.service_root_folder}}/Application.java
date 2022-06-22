package ${{values.service_root_folder}};

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

//TODO Rename application
@QuarkusMain
public class Application {

    public static void main(String... args) {
        Quarkus.run(args);
    }

}
