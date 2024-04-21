package tests;


import dto.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


import static tests.BaseTest.*;

public class UpdatePasswordTest extends FakeUserData {

    @Test

    public void successfulPasswordUpdate() {
        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email(fakeEmail)
                .password(fakePassword)
                .confirmPassword(confirmPassword)
                .role(userRole)
                .build();
        String oldPassword = fakePassword;
        Response user = postRequest("/auth/register", 201, reqBodyBuilder);
        String accessUserToken = user.body().jsonPath().getString("accessToken");

        UpdatePasswordRequest updatePasswordBodyBuilder = UpdatePasswordRequest.builder()
                .currentPassword(oldPassword)
                .newPassword(fakePassword)
                .confirmPassword(confirmPassword)
                .build();
        putRequest("user/password/update", 200, accessUserToken, updatePasswordBodyBuilder);


    }

    @Test
    public void passwordContainsOnlyDigits() {
        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email(fakeEmail)
                .password(fakePassword)
                .confirmPassword(confirmPassword)
                .role(userRole)
                .build();
        String oldPassword = fakePassword;
        Response user = postRequest("/auth/register", 201, reqBodyBuilder);
        String accessUserToken = user.body().jsonPath().getString("accessToken");

        UpdatePasswordRequest updatePasswordBodyBuilder = UpdatePasswordRequest.builder()
                .currentPassword(oldPassword)
                .newPassword("11111111")
                .confirmPassword("11111111")
                .build();
        putRequest("user/password/update", 400, accessUserToken, reqBodyBuilder);

    }

    @Test
    public void withoutCurrentPasswordValue() {
        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email(fakeEmail)
                .password(fakePassword)
                .confirmPassword(confirmPassword)
                .role(userRole)
                .build();
        String oldPassword = fakePassword;
        Response user = postRequest("/auth/register", 201, reqBodyBuilder);
        String accessUserToken = user.body().jsonPath().getString("accessToken");

        UpdatePasswordRequest updatePasswordBodyBuilder = UpdatePasswordRequest.builder()

                .newPassword(fakePassword)
                .confirmPassword(confirmPassword)
                .build();
        putRequest("user/password/update", 400, accessUserToken, reqBodyBuilder);
    }
@Test
    public void passwordUpdateConfirmPasswordDoesntMatch() {
        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email(fakeEmail)
                .password(fakePassword)
                .confirmPassword(confirmPassword)
                .role(userRole)
                .build();
        String oldPassword = fakePassword;
        Response user = postRequest("/auth/register", 201, reqBodyBuilder);
        String accessUserToken = user.body().jsonPath().getString("accessToken");

        UpdatePasswordRequest updatePasswordBodyBuilder = UpdatePasswordRequest.builder()
                .currentPassword(oldPassword)
                .newPassword(fakePassword)
                .confirmPassword("jkhjkhjklkljlk123")
                .build();
        putRequest("user/password/update", 400, accessUserToken, updatePasswordBodyBuilder);


    }
    @Test
    public void passwordUpdateWithInvalidCurrentPassword() {
        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .email(fakeEmail)
                .password(fakePassword)
                .confirmPassword(confirmPassword)
                .role(userRole)
                .build();
        String oldPassword = fakePassword;
        Response user = postRequest("/auth/register", 201, reqBodyBuilder);
        String accessUserToken = user.body().jsonPath().getString("accessToken");

        UpdatePasswordRequest updatePasswordBodyBuilder = UpdatePasswordRequest.builder()
                .currentPassword("oldPassword")
                .newPassword(fakePassword)
                .confirmPassword(confirmPassword)
                .build();
        putRequest("user/password/update", 400, accessUserToken, updatePasswordBodyBuilder);


    }
}
