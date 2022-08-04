package am.hovall.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAuthResponse {
    private String token;
    private UserResponse userResponse;

}
