package exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(value = { APIRequestException.class })
    public ResponseEntity<Object> handleAPIRequestException(APIRequestException e) {
        APIException apiException = new APIException(e.getMessage(), e.getStatus(), ZonedDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(apiException, e.getStatus());

    }
}