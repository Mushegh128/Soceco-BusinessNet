package am.hovall.common.request;

import am.hovall.common.entity.CompanyType;
import am.hovall.common.entity.Discount;
import am.hovall.common.entity.PresSeller;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
public class CompanyRequest {
    @NotNull
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotNull @Digits(integer = 8, fraction = 0)
    private Long registerNumber;
    private String logoUrl;
    private double level;
    private double rating;
    private Discount discount;
    @NotNull
    private CompanyType companyType;
    private PresSeller presSeller;
}
