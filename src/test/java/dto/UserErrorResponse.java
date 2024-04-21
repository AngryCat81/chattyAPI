package dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserErrorResponse {


    private String httpStatus;
    private String message;
   private ArrayList<String> email;
   private ArrayList<String> password;
    private ArrayList<String> role;
    private ArrayList<String> title;
    private ArrayList<String> description;
    private ArrayList<String> body;
    private ArrayList<String> name;
    private ArrayList<String> content;


}
