package am.hovall.common.response;

import am.hovall.common.entity.Region;
import am.hovall.common.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    @NotNull
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @Email
    private String email;
    private double rating;
    @NotNull
    private Region region;
    @NotNull
    private CompanyResponse companyResponse;
    @NotNull
    private Role role;
}
