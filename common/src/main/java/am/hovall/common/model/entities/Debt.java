package am.hovall.common.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "debt")
public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double debtSize;
//    @ManyToMany
//    @JoinTable(
//            name = "debt_debtTemplate",
//            joinColumns = {@JoinColumn(name = "debt_id")},
//            inverseJoinColumns = {@JoinColumn(name = "debt_template_id")})
//    private List<DebtTemplate> debtTemplateList;
}
