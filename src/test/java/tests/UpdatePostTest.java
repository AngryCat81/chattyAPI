package tests;
import dto.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tests.BaseTest.*;

public class UpdatePostTest extends FakeUserData {
    @Test

    public void successfulPostUpdate() {

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
        String idRequest = response.body().jsonPath().getString("id");
        String titleRequest = response.body().jsonPath().getString("title");
        String updateEndPoint = "/posts/" + idRequest;

        UpdatePostRequest reqUpdateBuilder = UpdatePostRequest.builder()
                .title("At least Api tests don't have selectors")
                .description(fakeDescription)
                .body(fakeMyThoughts)
                .draft(true)
                .build();

        GetAllPostsResponse updateResponse = putRequest(updateEndPoint, 200, accessUserToken, reqUpdateBuilder)
                .body().jsonPath().
                getObject("", GetAllPostsResponse.class);
        assertNotEquals(titleRequest, updateResponse.getTitle());


    }

    @Test

    public void postUpdateTitleOverMaxLength() {

        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email(fakeEmail)
                .password(fakePassword)
                .confirmPassword(confirmPassword)
                .role(userRole)
                .build();
        Response registeredUserResponse = postRequest("/auth/register", 201, reqBodyBuilder);
        String accessUserToken = registeredUserResponse.body().jsonPath().getString("accessToken");
        CreatePostRequest reqPostBodyBuilder = CreatePostRequest.builder()
                .title("я свобооооооден")
                .description(fakeDescription)
                .body(fakeMyThoughts)
                .draft(true)
                .build();
        Response response = postRequestWithAccessToken("/posts", 201, reqPostBodyBuilder, accessUserToken);
        String idRequest = response.body().jsonPath().getString("id");
        String updateEndPoint = "/posts/" + idRequest;

        UpdatePostRequest reqUpdateBuilder = UpdatePostRequest.builder()
                .title(fakeTitleOverMaxLength)
                .description(fakeDescription)
                .body(fakeMyThoughts)
                .build();
        UserErrorResponse titleResponse = putRequest(updateEndPoint, 400, accessUserToken, reqUpdateBuilder)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);
        assertEquals("Title must contain from 1 to 40 characters", titleResponse.getTitle().get(0));


    }

    @Test//v svagere prohodit 401, tut 404

    public void updateNotAuthorPost() { //DODELATJ TEST , SORTIROVATJ PO imeni, chtobi ne bilo sovpadenij , chtobi v podborke ne vidalo moj post

        String emailUser = "sobachka@kl.lo";
        String passwordUser = "sobachka231";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        Response responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody);
        String accessUserToken = responseBody.body().jsonPath().getString("accessToken");


        List<GetAllPostsResponse> posts = getRequestWithToken("/posts?limit=1", 200, accessUserToken)
                .body().jsonPath().
                getList("", GetAllPostsResponse.class);
        String notAuthorPostId = posts.get(0).getId();

        String updateEndPoint = "/posts/" + notAuthorPostId;
        UpdatePostRequest reqUpdateBuilder = UpdatePostRequest.builder()
                .title("Bamboleyo")
                .description(fakeDescription)
                .body(fakeMyThoughts)
                .build();
        UserErrorResponse titleResponse = putRequest(updateEndPoint, 403, accessUserToken, reqUpdateBuilder)
                .body().jsonPath().
                getObject("", UserErrorResponse.class);

    }
}