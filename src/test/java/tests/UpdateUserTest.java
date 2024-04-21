package tests;

import dto.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static tests.BaseTest.*;

public class UpdateUserTest extends FakeUserData {

    CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
            .email(fakeEmail)
            .password(fakePassword)
            .confirmPassword(confirmPassword)
            .role(userRole)
            .build();

    LoginRequest requestBody = new LoginRequest(emailAdmin, passwordAdmin);
    LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
            .body().jsonPath().
            getObject("", LoginResponse.class);
    String accessToken = responseBody.getAccessToken();
    @Test
    public void userProfileSuccessfulUpdate() {

        Response registeredUserResponse = postRequest("/auth/register", 200, reqBodyBuilder);
        String createdUserToken = registeredUserResponse.body().jsonPath().getString("accessToken");
        Response getUserResponse = getRequestWithToken("/me", 200, createdUserToken);
        String registeredUserID = getUserResponse.body().jsonPath().getString("id");
        String registeredUserName = getUserResponse.body().jsonPath().getString("name");
        String registeredUserSurname = getUserResponse.body().jsonPath().getString("surname");
        String registeredUserPhone = getUserResponse.body().jsonPath().getString("phone");
        String updateEndPoint = "/users/" + registeredUserID;
        UpdateUserRequest requestBodyBuilder = UpdateUserRequest.builder()
                .name(fakeName)
                .surname(fakeSurname)
                .phone("+1234567897")
                .build();
        UpdateUserResponse updatedResponse = putRequest(updateEndPoint, 200, accessToken, requestBodyBuilder)
                .body().jsonPath().
                getObject("", UpdateUserResponse.class);
        assertNotEquals(registeredUserName, updatedResponse.getName());
        assertNotEquals(registeredUserSurname, updatedResponse.getSurname());
        assertNotEquals(registeredUserPhone, updatedResponse.getPhone());
    }
    @Test
    public void userUpdateWithInvalidHttpMethod() {
        Response registeredUserResponse = postRequest("/auth/register", 200, reqBodyBuilder);
        String createdUserToken = registeredUserResponse.body().jsonPath().getString("accessToken");
        Response getUserResponse = getRequestWithToken("/me", 200, createdUserToken);
        String registeredUserID = getUserResponse.body().jsonPath().getString("id");
        String updateEndPoint = "/users/" + registeredUserID;
        UpdateUserRequest requestBodyBuilder = UpdateUserRequest.builder()
                .name(fakeName)
                .build();
        putRequestWithInvalidHttp(updateEndPoint, 405, accessToken, requestBodyBuilder);
    }

    @Test// Bug report zavesti? dolzhen prihoditj status kod 404?, prihodit 405. v swagere zapros ne otpravljaetsja


    public void userProfileUpdateWithoutIdInEndpoint() {
        Response registeredUserResponse = postRequest("/auth/register", 201, reqBodyBuilder);
        String createdUserToken = registeredUserResponse.body().jsonPath().getString("accessToken");

        UpdateUserRequest requestBodyBuilder = UpdateUserRequest.builder()
                .name(fakeName)
                .build();
        Response updatedResponse = putRequest("/users", 404, createdUserToken, requestBodyBuilder);


    }

    @Test
// Bug report zavesti, dolzhen prihoditj status kod 400, prihodit 200.v swagere 400 kod.Email ne menjaetsja, no ne nuzhno li soobshenie
    //chto email nevozmozhno pomenjatj?


    public void userProfileUpdateWithInvalidBodyField() {
        Response registeredUserResponse = postRequest("/auth/register", 201, reqBodyBuilder);
        String createdUserToken = registeredUserResponse.body().jsonPath().getString("accessToken");

        Response getUserResponse = getRequestWithToken("/me", 200, createdUserToken);
        String registeredUserID = getUserResponse.body().jsonPath().getString("id");
        String registeredUserEmail = getUserResponse.body().jsonPath().getString("email");

        String updateEndPoint = "/users/" + registeredUserID;

        UpdateUserRequest requestBodyBuilder = UpdateUserRequest.builder()
                .email("kurochka@fg.lv")
                .build();
        putRequest(updateEndPoint, 400, accessToken, requestBodyBuilder);
//                .body().jsonPath().
//                getObject("", UpdateUserResponse.class);
//        assertEquals(registeredUserEmail, userResponse.getEmail());


    }


}




