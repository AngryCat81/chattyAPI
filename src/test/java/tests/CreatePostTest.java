package tests;

import dto.*;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static tests.BaseTest.*;

public class CreatePostTest extends FakeUserData {
    @Test
    public void successfulPostCreation() {

        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email(fakeEmail)
                .password(fakePassword)
                .confirmPassword(confirmPassword)
                .role(userRole)
                .build();
        Response registeredUserResponse = postRequest("/auth/register", 201, reqBodyBuilder);
        String accessUserToken = registeredUserResponse.body().jsonPath().getString("accessToken");
        CreatePostRequest reqPostBodyBuilder = CreatePostRequest.builder()
                .title(fakeTitle)
                .description(fakeDescription)
                .body(fakeMyThoughts)
                .draft(true)
                .build();
        Response response = postRequestWithAccessToken("/posts", 201, reqPostBodyBuilder, accessUserToken);
        String titleResponse = response.body().jsonPath().getString("title");
        String descriptionResponse = response.body().jsonPath().getString("description");
        String bodyResponse = response.body().jsonPath().getString("body");
        assertEquals(fakeTitle, titleResponse);
        assertEquals(fakeDescription, descriptionResponse);
        assertEquals(fakeMyThoughts, bodyResponse);
    }

    @Test
    public void createPostInAdminRole() {
        String emailAdmin = "katja@th.lv";
        String passwordUserAdmin = "katja1981";
        LoginRequest requestBody = new LoginRequest(emailAdmin, passwordUserAdmin);
        LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        String accessAdminToken = responseBody.getAccessToken();
        CreatePostRequest reqBodyBuilder = CreatePostRequest.builder()
                .title(fakeTitle)
                .description(fakeDescription)
                .body(fakeMyThoughts)
                .draft(true)
                .build();
        Response response = postRequestWithAccessToken("/posts", 403, reqBodyBuilder, accessAdminToken);

    }


    @Test
    public void postCreationWithoutTitle() {

        String emailUser = "sobachka@kl.lo";
        String passwordUser = "sobachka231";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        Response responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody);
        String accessUserToken = responseBody.body().jsonPath().getString("accessToken");
        CreatePostRequest reqBodyBuilder = CreatePostRequest.builder()

                .description(fakeDescription)
                .body(fakeMyThoughts)
                .draft(true)
                .build();
        UserErrorResponse response = postRequestWithAccessToken("/posts", 400, reqBodyBuilder, accessUserToken)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);
        assertEquals("Title can not be empty!", response.getTitle().get(0));


    }

    @Test
    public void postCreationWithEmptyDescription() {

        String emailUser = "sobachka@kl.lo";
        String passwordUser = "sobachka231";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        Response responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody);
        String accessUserToken = responseBody.body().jsonPath().getString("accessToken");
        CreatePostRequest reqBodyBuilder = CreatePostRequest.builder()
                .title(fakeTitle)
                .body(fakeMyThoughts)
                .draft(true)
                .build();
        UserErrorResponse response = postRequestWithAccessToken("/posts", 400, reqBodyBuilder, accessUserToken)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);
        assertEquals("Description can not be empty!", response.getDescription().get(0));


    }

    @Test
    public void postCreationWithEmptyBody() {

        String emailUser = "sobachka@kl.lo";
        String passwordUser = "sobachka231";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        Response responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody);
        String accessUserToken = responseBody.body().jsonPath().getString("accessToken");
        CreatePostRequest reqBodyBuilder = CreatePostRequest.builder()
                .title(fakeTitle)
                .description(fakeDescription)
                .draft(true)
                .build();
        UserErrorResponse response = postRequestWithAccessToken("/posts", 400, reqBodyBuilder, accessUserToken)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);
        assertEquals("Body can not be empty!", response.getBody().get(0));


    }
}
