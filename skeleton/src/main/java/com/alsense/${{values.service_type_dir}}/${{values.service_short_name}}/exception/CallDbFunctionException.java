package ${{"com.alsense." + values.service_type_dir + "." + values.service_short_name}}.exception;

public class CallDbFunctionException extends RuntimeException {
    public CallDbFunctionException() {
        super();
    }

    public CallDbFunctionException(String s) {
        super(s);
    }

}
