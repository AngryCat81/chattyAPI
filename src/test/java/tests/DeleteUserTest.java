package tests;

import dto.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static tests.BaseTest.*;

public class DeleteUserTest extends FakeUserData {

    String emailAdmin = "katja@th.lv";
    String passwordAdmin = "katja1981";

    LoginRequest requestBody = new LoginRequest(emailAdmin, passwordAdmin);
    LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
            .body().jsonPath().
            getObject("", LoginResponse.class);
    String accessToken = responseBody.getAccessToken();

    @Test
// ozhidaetsja  status  kod  na udalenie 200, prihodit 204.Bug?

    public void successfulUserAccountDeleting() {
        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email(fakeEmail)
                .password(fakePassword)
                .confirmPassword(confirmPassword)
                .role(userRole)
                .build();
        Response registeredUserResponse = postRequest("/auth/register", 201, reqBodyBuilder);

        String createdUserToken = registeredUserResponse.body().jsonPath().getString("accessToken");

        Response getUserResponse = getRequestWithToken("/me", 200, createdUserToken);

        String registeredUserID = getUserResponse.body().jsonPath().getString("id");

        String deleteEndPoint = "/users/" + registeredUserID;
        deleteRequest(deleteEndPoint, 204, accessToken);
        UserErrorResponse deletedUserResponse = getRequestWithToken("/me", 404, createdUserToken)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);
        assertEquals("User not found!", deletedUserResponse.getMessage());

    }



    @Test
    public void deleteUserWithoutAuthorization() {
        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email(fakeEmail)
                .password(fakePassword)
                .confirmPassword(fakePassword)
                .role(userRole)
                .build();
        Response registeredUserResponse = postRequest("/auth/register", 201, reqBodyBuilder);

        String createdUserToken = registeredUserResponse.body().jsonPath().getString("accessToken");

        Response getUserResponse = getRequestWithToken("/me", 200, createdUserToken);

        String registeredUserID = getUserResponse.body().jsonPath().getString("id");

        String deleteEndPoint = "/users/" + registeredUserID;
        UserErrorResponse response = deleteRequestWithoutAuthorization(deleteEndPoint, 401)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);
        assertEquals("Authentication failed: Full authentication is required to access this resource", response.getMessage());


    }

    @Test
    public void deleteUserWithInvalidHttpMethod() {
        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email(fakeEmail)
                .password(fakePassword)
                .confirmPassword(fakePassword)
                .role(userRole)
                .build();
        Response registeredUserResponse = postRequest("/auth/register", 201, reqBodyBuilder);

        String createdUserToken = registeredUserResponse.body().jsonPath().getString("accessToken");

        Response getUserResponse = getRequestWithToken("/me", 200, createdUserToken);

        String registeredUserID = getUserResponse.body().jsonPath().getString("id");

        String deleteEndPoint = "/users/" + registeredUserID;
        UserErrorResponse response = deleteRequestWithInvalidHttpMethod(deleteEndPoint,400,accessToken)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);
        assertEquals("BAD_REQUEST", response.getHttpStatus());


    }
}
