package am.hovall.common.dto;

import am.hovall.common.entity.CompanyType;
import am.hovall.common.entity.Discount;
import am.hovall.common.entity.PresSeller;
import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class CompanyDto {

    private long id;
    private String name;
    private String address;
    private String logoUrl;
    private double level;
    private double rating;
    private Discount discount;
    private CompanyType companyType;
    private PresSeller presSeller;

}
