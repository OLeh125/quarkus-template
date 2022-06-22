package ${{values.service_root_folder}}.exception.notfound;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String s) {
        super(s);
    }
}
