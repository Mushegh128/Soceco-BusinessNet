package am.hovall.common.response;

import am.hovall.common.entity.Region;
import am.hovall.common.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private long id;
    private String name;
    private String surname;
    private String email;
    private double rating;
    private Region region;
    private CompanyResponse companyResponse;
    private Role role;
}
