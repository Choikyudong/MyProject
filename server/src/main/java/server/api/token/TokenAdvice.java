package server.api.token;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import server.domain.dto.user.response.ErrorMsgResponse;
import server.exception.TokenRefreshException;

import java.util.Date;

@RestControllerAdvice
public class TokenAdvice {

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMsgResponse handleTokenRefreshException(
            TokenRefreshException exception,
            WebRequest webRequest) {
        return new ErrorMsgResponse(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
    }

}
