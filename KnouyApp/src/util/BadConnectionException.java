package util;

public class BadConnectionException  extends Exception {
    public BadConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadConnectionException(String message) {
        super(message);
    }

    public BadConnectionException(Throwable cause) {
        super(cause);
    }

    public BadConnectionException() {
        super();
    }

}
