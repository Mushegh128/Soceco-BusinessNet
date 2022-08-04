package am.hovall.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompanyWithoutUserException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Company can not be without user";
    }
}
