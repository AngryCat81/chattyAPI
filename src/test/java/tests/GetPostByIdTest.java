package tests;

import dto.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static tests.BaseTest.*;

public class GetPostByIdTest extends FakeUserData {
    @Test
    public void getPostById() {

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
        String idResponse = response.body().jsonPath().getString("id");
        String getPostByIdEndPoint = "/posts/" + idResponse;
        GetAllPostsResponse responseId = getRequestWithToken(getPostByIdEndPoint, 200, accessUserToken)
                .body().jsonPath().
                getObject("", GetAllPostsResponse.class);
        assertEquals(idResponse, responseId.getId());


    }

    @Test
    public void getPostByInvalidId() {

        String emailUser = "sobachka@kl.lo";
        String passwordUser = "sobachka231";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        Response responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody);
        String accessUserToken = responseBody.body().jsonPath().getString("accessToken");

        String getPostByIdEndPoint = "/posts/f76dbbb8-3d10-4770-b886-080b69ab61e";
        UserErrorResponse response = getRequestWithToken(getPostByIdEndPoint, 404, accessUserToken)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);
        assertEquals("Post not found!", response.getMessage());


    }

}
