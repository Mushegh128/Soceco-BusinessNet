package am.hovall.common.request;

import lombok.Data;

@Data
public class UserAuthRequest {

    private String email;
    private String password;

}
