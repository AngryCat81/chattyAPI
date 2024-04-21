package tests;
import dto.*;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tests.BaseTest.*;

public class DeletePostTest extends FakeUserData {
    @Test


    public void successfulPostDelete() {

        String emailUser = "sobachka@kl.lo";
        String passwordUser = "sobachka231";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        Response responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody);
        String accessUserToken = responseBody.body().jsonPath().getString("accessToken");
        CreatePostRequest reqBodyBuilder = CreatePostRequest.builder()
                .title(fakeTitle)
                .description(fakeDescription)
                .body(fakeMyThoughts)
                .draft(true)
                .build();
        Response response = postRequestWithAccessToken("/posts", 201, reqBodyBuilder, accessUserToken);
        String idRequest = response.body().jsonPath().getString("id");
        String deleteEndpoint = "/posts/" + idRequest;
        String getPostEndpoint = "/posts/" + idRequest;
       deleteRequest(deleteEndpoint, 204, accessUserToken);
        UserErrorResponse responseId = getRequestWithToken(getPostEndpoint, 404, accessUserToken)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);

        assertEquals("Post not found!", responseId.getMessage());

    }

@Test


    public void notAuthorpostDelete(){

        String emailUser = "sobachka@kl.lo";
        String passwordUser = "sobachka231";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        Response responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody);
    String accessUserToken = responseBody.body().jsonPath().getString("accessToken");

    List<GetAllPostsResponse> posts = getRequestWithToken("/posts?limit=1", 200, accessUserToken)
            .body().jsonPath().
            getList("", GetAllPostsResponse.class);
    String postId = posts.get(0).getId();
    String deleteEndpoint = "/posts/" + postId;
    UserErrorResponse responseId = deleteRequest(deleteEndpoint, 403, accessUserToken)
            .body().jsonPath().
              getObject("", UserErrorResponse.class);
    assertEquals("You are not allowed to delete this post!",responseId.getMessage());








}
}
