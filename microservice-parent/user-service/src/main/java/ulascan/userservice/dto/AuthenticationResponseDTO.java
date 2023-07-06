package ulascan.userservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO {
    //@JsonProperty("access_token")
    private String accessToken;
    //@JsonProperty("refresh_token")
    private String refreshToken;

    private Long publicId;
    private String firstName;
    private String lastName;
    private String role;
}
