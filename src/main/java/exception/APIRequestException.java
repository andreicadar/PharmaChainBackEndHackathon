package exception;

import org.springframework.http.HttpStatus;

public class APIRequestException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -7923167392216673799L;
    private final HttpStatus httpStatus;

    public APIRequestException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public APIRequestException(HttpStatus httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;

    }

    public HttpStatus getStatus() {
        return this.httpStatus;
    }
}
