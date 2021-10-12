package am.hovall.common.model.dto;

import am.hovall.common.model.entities.Company;
import am.hovall.common.model.entities.Region;

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
