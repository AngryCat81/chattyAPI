package tests;

import dto.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tests.BaseTest.*;

public class GetUserTest extends FakeUserData {
    String emailUser = "katja@th.lv";
    String passwordUser = "katja1981";
    LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
    LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
            .body().jsonPath().
            getObject("", LoginResponse.class);
    String accessToken = responseBody.getAccessToken();


    @Test// padaet potomu chto getBackgroundUrl =0
    public void getUserData() {
        String emailUser = "sobachka@kl.lo";
        String passwordUser = "sobachka231";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        String accessToken = responseBody.getAccessToken();

        CreatedUserResponse getUserResponse = getRequestWithToken("/me", 200, accessToken)
                .body().jsonPath().
                getObject("", CreatedUserResponse.class);

        assertFalse(getUserResponse.getId().isEmpty());
        assertFalse(getUserResponse.getEmail().isEmpty());
        assertFalse(getUserResponse.getGender().isEmpty());
        assertFalse(getUserResponse.getName().isEmpty());
        assertFalse(getUserResponse.getBirthDate().isEmpty());
        assertFalse(getUserResponse.getRole().isEmpty());
        assertFalse(getUserResponse.getPhone().isEmpty());
        assertFalse(getUserResponse.getBackgroundUrl().isEmpty());
        assertFalse(getUserResponse.getAvatarUrl().isEmpty());
        assertFalse(getUserResponse.getSurname().isEmpty());

    }

    @Test
    public void getUsersListWithAdminDataAccess() {
        String emailUser = "katja@th.lv";
        String passwordUser = "katja1981";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        String accessToken = responseBody.getAccessToken();
        List<CreatedUserResponse> users = getRequestWithToken("/users", 200, accessToken)
                .body().jsonPath().
                getList("", CreatedUserResponse.class);
        for (CreatedUserResponse user : users) {
            assertFalse(user.getEmail().isEmpty());
        }
    }
        @Test
        public void getUsersListWithAdminDataAccessWithLimit() {
            String emailAdmin = "katja@th.lv";
            String passwordAdmin = "katja1981";
            LoginRequest requestBody = new LoginRequest(emailAdmin, passwordAdmin);
            LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
                    .body().jsonPath().
                    getObject("", LoginResponse.class);
            String accessToken = responseBody.getAccessToken();
            List<CreatedUserResponse> users = getRequestWithToken("/users?limit=10", 200, accessToken)
                    .body().jsonPath().
                    getList("", CreatedUserResponse.class);
            for (CreatedUserResponse user : users) {
                assertFalse(user.getEmail().isEmpty());
                assertEquals(10,users.size());
            }

    }

    @Test
    public void getUsersListWithUserDataAccess() {
        String emailUser = "sobachka@kl.lo";
        String passwordUser = "sobachka231";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        String accessToken = responseBody.getAccessToken();
        UserErrorResponse errorResponse = getRequestWithToken("/users", 403, accessToken)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);
        assertEquals("FORBIDDEN", errorResponse.getHttpStatus());

        assertEquals("You don't have permission to get users", errorResponse.getMessage());

    }


    @Test
    public void getDeletedUser() {
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

        deleteRequest(deleteEndPoint, 204, accessToken);
        UserErrorResponse deletedUserResponse = getRequestWithToken("/me", 404, createdUserToken)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);
        assertEquals("User not found!", deletedUserResponse.getMessage());


    }
}

