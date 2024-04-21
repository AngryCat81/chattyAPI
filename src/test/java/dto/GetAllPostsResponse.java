package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class GetAllPostsResponse {

  private  String id;
    private  String title;
    private  String description;
    private  String    body;
    private User user;
    private  String imageUrl;
    private  String createdAt;
    private  String updatedAt;
    private boolean  draft;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    class User {
        public String id;
        public String name;
        public String surname;
        public String phone;
        public String email;
        public String role;
        public String gender;
        public String birthDate;
        public String avatarUrl;
        public String backgroundUrl;
        }
    }