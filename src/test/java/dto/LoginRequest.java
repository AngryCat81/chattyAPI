package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequest {
    private String email;
    private String password;
}
