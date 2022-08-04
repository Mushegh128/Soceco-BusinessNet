package am.hovall.common.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserAuthRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;

}
