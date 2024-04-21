package tests;
import dto.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tests.BaseTest.*;

public class GetPostByUserIdTest extends FakeUserData {
    @Test

    //kak dostuchatjsja  ne do Post id, a do user id????
    public void successfulPostGetting() {
        String emailUser = "sobachka@kl.lo";
        String passwordUser = "sobachka231";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        String accessToken = responseBody.getAccessToken();
        Response responseID = getRequestWithToken("/me", 200, accessToken);
        String userID = responseID.body().jsonPath().getString("id");

        String endpoint = "/users/" + userID + "/posts";
        List<GetAllPostsResponse> userPosts = getRequestWithToken(endpoint, 200, accessToken)
                .body().jsonPath().
                getList("", GetAllPostsResponse.class);
        for (GetAllPostsResponse post : userPosts) {
            // assertEquals(userID, post.getUser().getId());


        }
    }
@Test//prihodit 500 status kod



    public void getPostWithNotExistingUserId() {
        String emailUser = "sobachka@kl.lo";
        String passwordUser = "sobachka231";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        String accessToken = responseBody.getAccessToken();

        String endpoint = "/users/" + "kolkin345" + "/posts";
        List<GetAllPostsResponse> userPosts = getRequestWithToken(endpoint, 404, accessToken)
                .body().jsonPath().getList("", GetAllPostsResponse.class);
    }
}
