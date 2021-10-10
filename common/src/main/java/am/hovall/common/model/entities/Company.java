package am.hovall.common.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long barcode;
    private String name;
    private String address;
    private long registerNumber;
    private String bankingAccount;
    private String phoneNumber;
    private String logoUrl;
    private double level;
    private double rating;
    private boolean isSynchronized;
    @ManyToOne
    private Discount discount;
    private LocalDateTime createdDateTime;
    @ManyToOne
    private CompanyType companyType;
    @ManyToOne
    private PresSeller presSeller;


}
