package am.hovall.common.response;

import am.hovall.common.entity.CompanyType;
import am.hovall.common.entity.Discount;
import am.hovall.common.entity.PresSeller;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
public class CompanyResponse {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    private String logoUrl;
    private double level;
    private double rating;
    private Discount discount;
    @NotNull
    private CompanyType companyType;
    private PresSeller presSeller;
}
