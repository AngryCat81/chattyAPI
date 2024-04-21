package dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)


public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private String expiration;

}
