package am.hovall.common.request;

import am.hovall.common.entity.Region;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserRequest {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String passportId;
    private boolean isMailVerified;
    private boolean isContractVerified;
    private Region region;
    private CompanyRequest companyRequest;
}
