package am.hovall.common.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PresSellerRequest {

    private long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private double rating;
    private boolean isActive;
}
