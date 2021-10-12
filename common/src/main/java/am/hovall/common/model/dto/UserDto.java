package am.hovall.common.model.dto;

import am.hovall.common.model.Role;
import am.hovall.common.model.entities.Company;
import am.hovall.common.model.entities.Region;
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
