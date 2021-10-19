package am.hovall.common.response;

import am.hovall.common.entity.CompanyType;
import am.hovall.common.entity.Discount;
import am.hovall.common.entity.PresSeller;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CompanyResponse {
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
