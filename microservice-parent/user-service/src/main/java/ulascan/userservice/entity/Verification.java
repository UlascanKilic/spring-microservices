package ulascan.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "verification")
public class Verification {
    @Id
    @GeneratedValue
    private Integer id;

    private String verificationCode;

    private String resetPasswordCode;

    private boolean enabled;

    /*@OneToOne(mappedBy = "verification")
    private User user;*/
}
