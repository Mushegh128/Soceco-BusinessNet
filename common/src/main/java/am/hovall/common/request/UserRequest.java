package am.hovall.common.request;

import am.hovall.common.entity.Region;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
public class UserRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    private String passportId;
    private boolean isMailVerified;
    private boolean isContractVerified;
    @NotNull
    private Region region;
    @NotNull
    private CompanyRequest companyRequest;
}
