package tests;
import dto.*;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.post;
import static org.junit.jupiter.api.Assertions.*;
import static tests.BaseTest.*;


public class GetPostListTest {
    @Test
    public void getPostsListWithAdminRole() {
        String emailUser = "katja@th.lv";
        String passwordUser = "katja1981";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        String accessToken = responseBody.getAccessToken();
        List<GetAllPostsResponse> posts = getRequestWithToken("/posts", 200, accessToken)
                .body().jsonPath().
                getList("", GetAllPostsResponse.class);
        for (GetAllPostsResponse post : posts) {
            assertFalse(post.getId().isEmpty());
            assertFalse(post.getTitle().isEmpty());
            assertFalse(post.getDescription().isEmpty());
        }
        }
        @Test
        public void getPostsListWithUserRole() {
            String emailUser = "sobachka@kl.lo";
            String passwordUser = "sobachka231";
            LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
            LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
                    .body().jsonPath().
                    getObject("", LoginResponse.class);
            String accessToken= responseBody.getAccessToken();
            List<GetAllPostsResponse> posts = getRequestWithToken("/posts", 200, accessToken)
                    .body().jsonPath().
                    getList("", GetAllPostsResponse.class);
            for (GetAllPostsResponse post : posts) {
                assertFalse(post.getId().isEmpty());
                assertFalse(post.getTitle().isEmpty());
                assertFalse(post.getDescription().isEmpty());
            }


        }
        @Test
    public void getPostsListWithRequiredLimit() {
        String emailUser = "katja@th.lv";
        String passwordUser = "katja1981";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        String accessToken = responseBody.getAccessToken();
        List<GetAllPostsResponse> posts = getRequestWithToken("/posts", 200, accessToken)
                .body().jsonPath().
                getList("", GetAllPostsResponse.class);

        assertEquals(10, posts.size());
    }


    @Test
    public void getPostsListWithInvalidEndpoint() {
        String emailUser = "katja@th.lv";
        String passwordUser = "katja1981";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        String accessToken = responseBody.getAccessToken();
        Response posts = getRequestWithToken("/post", 404, accessToken);



    }

    @Test//
    public void getPostsListWithInvalidHttpMethod() {
        String emailUser = "katja@th.lv";
        String passwordUser = "katja1981";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        LoginResponse responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody)
                .body().jsonPath().
                getObject("", LoginResponse.class);
        String accessToken = responseBody.getAccessToken();
     getRequestWithInvalitHttpMethod("/posts", 400, accessToken);



    }


}



