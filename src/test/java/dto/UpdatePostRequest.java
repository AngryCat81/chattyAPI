package dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;



@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UpdatePostRequest {
    private  String title;
    private  String description;
    private  String    body;
    private  String imageUrl;
    private  String publishDate;
    private boolean draft;
}
