package ${{values.service_root_folder}}.exception;

public class InvalidValueException extends RuntimeException {

    public InvalidValueException(String message) {
        super(message);
    }

}
