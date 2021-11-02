package am.hovall.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnVerifiedContractException extends RuntimeException{
    @Override
    public String getMessage() {
        return "access to transactions is prohibited if there is no agreement on the contract";
    }
}
