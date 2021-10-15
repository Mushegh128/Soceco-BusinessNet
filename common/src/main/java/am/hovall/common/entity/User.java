package am.hovall.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String passportId;
    private LocalDateTime createdDateTime;
    private double rating;
    private UUID mailVerifyToken;
    private boolean isMailVerified;
    private boolean isContractVerified;
    private boolean isActive;
    private boolean isBlackList;
    private boolean isSynchronized;
    @ManyToOne
    private Region region;
    @ManyToOne
    private Company company;
    @Enumerated(value = EnumType.STRING)
    private Role role;

}

