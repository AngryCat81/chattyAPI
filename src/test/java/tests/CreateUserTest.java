package tests;

import dto.CreateUserRequest;
import dto.LoginRequest;
import dto.LoginResponse;

import dto.UserErrorResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tests.BaseTest.*;


public class CreateUserTest extends FakeUserData {
    @Test
    public void successfulUserCreationRoleUser() {
        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email(fakeEmail)
                .password(fakePassword)
                .confirmPassword(confirmPassword)
                .role(userRole)
                .build();
        LoginResponse user = postRequest("/auth/register", 200, reqBodyBuilder)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        assertFalse(user.getExpiration().isEmpty());
        assertFalse(user.getAccessToken().isEmpty());
        assertFalse(user.getRefreshToken().isEmpty());
    }
    @Test
    public void successfulAdminAccountCreation() {
        String emailUser = "katja@th.lv";
        String passwordUser = "katja1981";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        String accessToken = responseBody.getAccessToken();

        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email(fakeEmail)
                .password(fakePassword)
                .confirmPassword(confirmPassword)
                .role(adminRole)
                .build();
        LoginResponse user = postRequestWithAccessToken("/auth/register", 200, reqBodyBuilder, accessToken)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        assertFalse(user.getExpiration().isEmpty());
        assertFalse(user.getAccessToken().isEmpty());
        assertFalse(user.getRefreshToken().isEmpty());


    }

    @Test


    public void userCreationRoleWithoutEmail() {

        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()

                .password(fakePassword)
                .confirmPassword(fakePassword)
                .role(userRole)
                .build();
        UserErrorResponse errorResponse = postRequest("/auth/register", 400, reqBodyBuilder)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);
        assertEquals("Email cannot be empty", errorResponse.getEmail().get(0));
        ;
    }

    @Test

    public void userCreationRoleUserWithInvalidPasswordValue() {

        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email(fakeEmail)
                .password("kurica")
                .confirmPassword("kurica")
                .role(userRole)
                .build();
        UserErrorResponse errorResponse = postRequest("/auth/register", 400, reqBodyBuilder)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);
        assertEquals("Password must contain at least 8 characters", errorResponse.getPassword().get(0));
        assertEquals("Password must contain letters and numbers", errorResponse.getPassword().get(1));
    }

    @Test

    public void adminCreationWithoutEmail() {

        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()

                .password(fakePassword)
                .confirmPassword(fakePassword)
                .role(adminRole)
                .build();
        UserErrorResponse errorResponse = postRequest("/auth/register", 400, reqBodyBuilder)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);
        assertEquals("Email cannot be empty", errorResponse.getEmail().get(0));

    }

    @Test
    public void accountCreationWithoutRole() {

        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email(fakeEmail)
                .password(fakePassword)
                .confirmPassword(fakePassword)
                .build();
        UserErrorResponse errorResponse = postRequest("/auth/register", 400, reqBodyBuilder)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);
        assertEquals("must not be null", errorResponse.getRole().get(0));
        ;


    }

    @Test
    public void userCreationInvalidEmailWithoutAt() {

        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email("sobaka.lv")
                .password("sobaka231")
                .confirmPassword("sobak231")
                .role(userRole)
                .build();
        UserErrorResponse responseError = postRequest("/auth/register", 400, reqBodyBuilder)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);
        assertEquals("Email is not valid.", responseError.getEmail().get(0));


    }

    @Test
    public void userCreationPasswordOnlyLetters() {

        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email("sobaka@ln.lb")
                .password("sobaka")
                .confirmPassword("sobaka")
                .role(userRole)
                .build();
        UserErrorResponse responseError = postRequest("/auth/register", 400, reqBodyBuilder)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);
        assertEquals("Password must contain at least 8 characters", responseError.getPassword().get(0));
    }

}