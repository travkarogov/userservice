package twitsec.userservice.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import twitsec.userservice.controller.exception.NotAuthorizedException;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HttpControllerAdvice {

    private static final HttpStatus UNAUTHORIZED = HttpStatus.UNAUTHORIZED;

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<HttpStatus> notAuthorizedException(final NotAuthorizedException e){
        log.error(String.format("NotAuthorizedException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(UNAUTHORIZED).body(UNAUTHORIZED);
    }
}
