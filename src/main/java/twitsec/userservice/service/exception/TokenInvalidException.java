package twitsec.userservice.service.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException(final String message) {super(message);}
}