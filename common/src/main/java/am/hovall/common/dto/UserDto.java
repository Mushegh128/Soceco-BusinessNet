package am.hovall.common.dto;

import am.hovall.common.entity.Role;
import am.hovall.common.entity.Company;
import am.hovall.common.entity.Region;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class UserDto {
    private long id;
    private String name;
    private String surname;
    private String email;
    private double rating;
    private Region region;
    private Company company;
    private Role role;
}
