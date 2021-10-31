package am.hovall.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserAuthResponse {
    @NotBlank
    private String token;
    @NotNull
    private UserResponse userResponse;

}
