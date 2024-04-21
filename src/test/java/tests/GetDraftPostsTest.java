package tests;

import dto.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tests.BaseTest.*;

public class GetDraftPostsTest {
    @Test

    public void getDraftPosts() {
        String emailUser = "sobachka@kl.lo";
        String passwordUser = "sobachka231";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        Response responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody);
        String accessUserToken = responseBody.body().jsonPath().getString("accessToken");
        List<GetAllPostsResponse> responseDraft = getRequestWithToken("/posts/drafts", 200, accessUserToken)
                .body().jsonPath().getList("", GetAllPostsResponse.class);
        for (GetAllPostsResponse draft : responseDraft) {
            assertTrue(draft.isDraft());
        }
    }

    @Test//

    public void getDraftPostsWithInvalidHttpMethod() {
        String emailUser = "sobachka@kl.lo";
        String passwordUser = "sobachka231";
        LoginRequest requestBody = new LoginRequest(emailUser, passwordUser);
        Response responseBody = postRequestWithoutAccessToken("/auth/login", 200, requestBody);
        String accessUserToken = responseBody.body().jsonPath().getString("accessToken");
        getRequestWithInvalitHttpMethod("/posts/drafts", 405,accessUserToken);


        }



}
