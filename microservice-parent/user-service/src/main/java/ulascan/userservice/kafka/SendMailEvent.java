package ulascan.userservice.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendMailEvent {
    private String firstName;
    private String lastName;
    private String email;
    private String verificationCode;
}
