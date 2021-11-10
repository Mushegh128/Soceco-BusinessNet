package am.hovall.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PresSellerResponse {

    private long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private double rating;
    private boolean isActive;
}
