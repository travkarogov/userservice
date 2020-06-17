package twitsec.userservice.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@Order
public class LastResortControllerAdvice {

    private static final HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpStatus> handleLastResortException(final Exception e) {
        log.error(String.format("LastResortException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<HttpStatus> handleLastResortRuntimeException(final RuntimeException e) {
        log.error(String.format("LastResortRuntimeException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(INTERNAL_SERVER_ERROR);
    }
}
