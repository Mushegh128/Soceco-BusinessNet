package am.hovall.common.model.dto;

import am.hovall.common.model.entities.CompanyType;
import am.hovall.common.model.entities.Discount;
import am.hovall.common.model.entities.PresSeller;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
