package am.hovall.common.model.entities;

import am.hovall.common.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_basket")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Company company;
    private LocalDateTime createdDateTime;
    private LocalDateTime saleDateTime;
    private boolean isVerified;
    private boolean isSynchronized;
    private double orderCost;
    private double debtSize;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "order_product_order",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_order_id")})
    private List<ProductOrder> productOrders;
    @ManyToMany
    @JoinTable(
            name = "order_payment",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "payment_id")})
    private List<Payment> paymentList;

}
