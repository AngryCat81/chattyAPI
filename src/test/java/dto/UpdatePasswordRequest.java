package dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor

@NoArgsConstructor@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UpdatePasswordRequest {

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

}
