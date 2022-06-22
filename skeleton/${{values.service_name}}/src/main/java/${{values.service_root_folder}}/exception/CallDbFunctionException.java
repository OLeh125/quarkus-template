package ${{values.service_root_folder}}.exception;

public class CallDbFunctionException extends RuntimeException {
    public CallDbFunctionException() {
        super();
    }

    public CallDbFunctionException(String s) {
        super(s);
    }

}
