package twitsec.userservice.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import twitsec.userservice.controller.exception.NoTokenProvidedException;
import twitsec.userservice.controller.exception.NotAuthorizedException;
import twitsec.userservice.service.exception.TokenInvalidException;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HttpControllerAdvice {

    private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private static final HttpStatus UNAUTHORIZED = HttpStatus.UNAUTHORIZED;

    @ExceptionHandler(NoTokenProvidedException.class)
    public ResponseEntity<HttpStatus> noTokenProvidedException(final NoTokenProvidedException e){
        log.error(String.format("NoTokenProvidedException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(BAD_REQUEST).body(BAD_REQUEST);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<HttpStatus> notAuthorizedException(final NotAuthorizedException e){
        log.error(String.format("NotAuthorizedException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(UNAUTHORIZED).body(UNAUTHORIZED);
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<HttpStatus> tokenInvalidException(final TokenInvalidException e){
        log.error(String.format("TokenInvalidException, %s at: %s", e.getMessage(), e.getStackTrace()[0]));
        return ResponseEntity.status(UNAUTHORIZED).body(UNAUTHORIZED);
    }
}
