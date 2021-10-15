package am.hovall.common.dto;

import am.hovall.common.entity.Company;
import am.hovall.common.entity.Region;

public class UserRegisterDto {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String passportId;
    private boolean isMailVerified;
    private boolean isContractVerified;
    private Region region;
    private Company company;

}
