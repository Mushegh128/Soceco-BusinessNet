package am.hovall.common.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double size;
    private boolean isSynchronized;
    private LocalDateTime createdDateTime;
    @ManyToOne
    private User fromUser;
    @ManyToOne
    private Order order;
}
