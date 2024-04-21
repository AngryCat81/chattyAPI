package tests;

import dto.LoginRequest;
import dto.LoginResponse;
import dto.UserErrorResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static tests.BaseTest.*;

public class LoginTest {
    @Test
    public void userLogin() {
        String emailUser = "sobachka@kl.lo";
        String passwordUser = "sobachka231";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        String accessToken= responseBody.getAccessToken();

        assertFalse(accessToken.isEmpty());
        assertFalse(accessToken.isEmpty());
        assertFalse(accessToken.isEmpty());
        LoginResponse responseLogin = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
                .body().jsonPath().
                getObject("", LoginResponse.class);


    }

    @Test
    public void userLoginWithInvalidPassword() {
        String email = "sobachka@kl.lo";
        String password = "sobachka25";
        LoginRequest requestBody = new LoginRequest(email, password);
        UserErrorResponse responseBody = postRequestWithoutAccessToken("/auth/login", 401, requestBody)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);

        assertEquals("UNAUTHORIZED", responseBody.getHttpStatus());
        assertEquals("The password does not match the one saved in the database!", responseBody.getMessage());


    }

    @Test
    public void userLoginWithoutEmailValue() {
        String password = "sobachka25";
       LoginRequest reqBodyBuilder = LoginRequest.builder()
                .password(password)
                .build();
        UserErrorResponse responseBody = postRequestWithoutAccessToken("/auth/login", 400,reqBodyBuilder)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);

        assertEquals("Email cannot be empty", responseBody.getEmail().get(0));

    }
}

