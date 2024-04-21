package tests;

import dto.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tests.BaseTest.*;

public class FeedbackTest extends FakeUserData{
    @Test

    public void successfullySentFeedback(){
        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email(fakeEmail)
                .password(fakePassword)
                .confirmPassword(confirmPassword)
                .role(userRole)
                .build();
        LoginResponse user = postRequest("/auth/register", 201, reqBodyBuilder)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        String emailUser = "sobachka@kl.lo";
        String passwordUser = "sobachka231";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        String accessToken= responseBody.getAccessToken();
        CreatedUserResponse getUserResponse = getRequestWithToken("/me", 200, accessToken)
                .body().jsonPath().
                getObject("", CreatedUserResponse.class);
        String name= getUserResponse.getName();
        String email= getUserResponse.getEmail();
        FeedbackRequest reqFeedbackBodyBuilder = FeedbackRequest.builder()
                .name(name)
                .email(email)
                .content("Thank you for a such wonderful site")
                .build();
        FeedbackRequest response=postRequestWithAccessToken("/feedback",201,reqFeedbackBodyBuilder,accessToken)
                 .body().jsonPath().
                getObject("",FeedbackRequest.class);

        assertEquals("Thank you for a such wonderful site",response.getContent());
    }

    @Test//

    public void feedbackWithEmptyName(){
        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email(fakeEmail)
                .password(fakePassword)
                .confirmPassword(confirmPassword)
                .role(userRole)
                .build();
        LoginResponse user = postRequest("/auth/register", 201, reqBodyBuilder)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        String emailUser = "sobachka@kl.lo";
        String passwordUser = "sobachka231";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        String accessToken= responseBody.getAccessToken();
        CreatedUserResponse getUserResponse = getRequestWithToken("/me", 200, accessToken)
                .body().jsonPath().
                getObject("", CreatedUserResponse.class);
        String email= getUserResponse.getEmail();
        FeedbackRequest reqFeedbackBodyBuilder = FeedbackRequest.builder()

                .email(email)
                .content(fakeTitle)
                .build();
        UserErrorResponse response=postRequestWithAccessToken("/feedback",400,reqFeedbackBodyBuilder,accessToken)
                .body().jsonPath().
                getObject("",UserErrorResponse.class);

        assertEquals("Name can not be empty!",response.getName().get(0));

    }

    @Test//

    public void feedbackWithEmptyContent(){
        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email(fakeEmail)
                .password(fakePassword)
                .confirmPassword(confirmPassword)
                .role(userRole)
                .build();
        LoginResponse user = postRequest("/auth/register", 201, reqBodyBuilder)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        String emailUser = "sobachka@kl.lo";
        String passwordUser = "sobachka231";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        String accessToken= responseBody.getAccessToken();
        CreatedUserResponse getUserResponse = getRequestWithToken("/me", 200, accessToken)
                .body().jsonPath().
                getObject("", CreatedUserResponse.class);
        String email= getUserResponse.getEmail();
        String name= getUserResponse.getName();
        FeedbackRequest reqFeedbackBodyBuilder = FeedbackRequest.builder()
                .name(name)
                .email(email)
                .build();
        UserErrorResponse response=postRequestWithAccessToken("/feedback",400,reqFeedbackBodyBuilder,accessToken)
                .body().jsonPath().
                getObject("",UserErrorResponse.class);

        assertEquals("Content can not be empty!",response.getContent().get(0));

    }
}
