package twitsec.userservice.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import twitsec.userservice.controller.exception.NotAuthorizedException;
import twitsec.userservice.service.exception.TokenInvalidException;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HttpControllerAdvice {

    private static final HttpStatus UNAUTHORIZED = HttpStatus.UNAUTHORIZED;

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<HttpStatus> notAuthorizedException(final NotAuthorizedException e) {
        log.error(String.format("NotAuthorizedException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(UNAUTHORIZED).body(UNAUTHORIZED);
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<HttpStatus> tokenInvalidException(final TokenInvalidException e) {
        log.error(String.format("TokenInvalidException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(UNAUTHORIZED).body(UNAUTHORIZED);
    }
}
