package ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.exception.notfound;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String s) {
        super(s);
    }
}
