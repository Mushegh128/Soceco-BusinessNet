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
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long barcode;
    private String title;
    private String description;
    private double price;
    private double weight;
    private double cashback;
    private boolean isSynchronized;
    private String smallPicUrl;
    private String picUrl;
    private LocalDateTime createdDateTime;
    private boolean isActive;
    @ManyToOne
    private MadeInCountry maidInCountry;
    @ManyToOne
    private ProductCategory productCategory;
    @ManyToOne
    private Brand brand;
}
