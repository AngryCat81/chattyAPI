package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
    @Getter
    @Setter
    @AllArgsConstructor

    @NoArgsConstructor@Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class UpdateUserRequest {

        private String name;
        private String surname;
        private String phone;
        private String email;
        private String gender;
        private String birthDate;
        private String avatarUrl;
        private String backgroundUrl;
        private boolean blocked;

    }


