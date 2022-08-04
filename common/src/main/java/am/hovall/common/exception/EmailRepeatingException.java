package am.hovall.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailRepeatingException extends RuntimeException{
    @Override
    public String getMessage() {
        return "email can not be repeating";
    }
}
