package api.user;

import api.User;
import api.UserGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserRefreshTest {
    private final UserGenerator userGenerator = new UserGenerator();
    String accessToken;
    private UserClient userClient;
    private User user;
    private UserAssertions userAssertions;

    @Before
    @Step("Precondition for refresh user's data tests")
    public void setUp() {
        userClient = new UserClient();
        user = userGenerator.getNewRandomUser();
        ValidatableResponse responseCreateUser = userClient.createUser(user);
        accessToken = responseCreateUser.extract().path("accessToken").toString().substring(7);
        userAssertions = new UserAssertions();
    }
    @Test
    @DisplayName("Update user's name")
    @Description("Test to try update user's name by token. Check name, status code and message")
    public void refreshUserNameSuccessfullyByToken() {
        user.setName("Антон");
        ValidatableResponse responseUpdateNameUser = userClient.refreshUserDataByToken(accessToken, user);
        userAssertions.refreshUserSuccessfully(responseUpdateNameUser);
        responseUpdateNameUser.assertThat().body("user.name", equalTo("Антон"));
    }
    @Test
    @DisplayName("Update user's password")
    @Description("Test to try update user's password by token. Check password, status code and message")
    public void refreshUserPasswordSuccessfullyByToken() {
        user.setPassword("praktikum");
        ValidatableResponse responseUpdatePasswordUser = userClient.refreshUserDataByToken(accessToken, user);
        userAssertions.refreshUserSuccessfully(responseUpdatePasswordUser);
    }
    @Test
    @DisplayName("Update user's email")
    @Description("Test to try update user's email by token. Check email, status code and message")
    public void refreshUserEmailSuccessfullyByToken() {
        user.setEmail("boobooo@yandex.ru");
        ValidatableResponse responseUpdateEmailUser = userClient.refreshUserDataByToken(accessToken, user);
        userAssertions.refreshUserSuccessfully(responseUpdateEmailUser);
        responseUpdateEmailUser.assertThat().body("user.email", equalTo("boobooo@yandex.ru"));
    }
    @Test
    @DisplayName("Update user's name without authorization")
    @Description("Test to try update user's name without token. Check status code and message")
    public void refreshUserNameUnsuccessfullyWithoutToken() {
        user.setName("Антон");
        ValidatableResponse responseUpdateNameUser = userClient.refreshUserDataWithoutToken(user);
        userAssertions.refreshUserUnsuccessfully(responseUpdateNameUser);
    }
    @Test
    @DisplayName("Update user's password without authorization")
    @Description("Test to try update user's password without token. Check status code and message")
    public void refreshUserPasswordUnsuccessfullyWithoutToken() {
        user.setPassword("praktikum");
        ValidatableResponse responseUpdatePasswordUser = userClient.refreshUserDataWithoutToken(user);
        userAssertions.refreshUserUnsuccessfully(responseUpdatePasswordUser);
    }
    @Test
    @DisplayName("Update user's email without authorization")
    @Description("Test to try update user's email without token. Check status code and message")
    public void refreshUserEmailUnsuccessfullyWithoutToken() {
        user.setEmail("booboo@yandex.ru");
        ValidatableResponse responseUpdateEmailUser = userClient.refreshUserDataWithoutToken(user);
        userAssertions.refreshUserUnsuccessfully(responseUpdateEmailUser);
    }
    @After
    @Step("Delete test user")
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }
}