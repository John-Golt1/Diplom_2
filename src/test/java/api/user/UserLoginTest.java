package api.user;

import api.AuthorizationData;
import api.User;
import api.UserGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserLoginTest {
    private final UserGenerator userGenerator = new UserGenerator();
    String accessToken;
    private UserClient userClient;
    private AuthorizationData authorizationData;
    private User user;
    private UserAssertions userAssertions;

    @Before
    @Step("Precondition for login tests with creation user")
    public void setUp() {
        userClient = new UserClient();
        user = userGenerator.getNewRandomUser();
        userClient.createUser(user);
        authorizationData = AuthorizationData.from(user);
        userAssertions = new UserAssertions();
    }
    @Test
    @DisplayName("Login user with valid authorization data")
    @Description("Check, token status code and message")
    public void userCanSuccessfullyLogin() {
        ValidatableResponse responseLoginUser = userClient.loginUser(authorizationData);
        userAssertions.loginUserSuccessfully(responseLoginUser);
        accessToken = responseLoginUser.extract().path("accessToken").toString().substring(7);
    }
    @Test
    @DisplayName("Login user without email field")
    @Description("Try to login user without email field. Check status code and message.")
    public void userLoginUnsuccessfullyWithoutEmailField() {
        AuthorizationData authorizationDataWithoutEmailField = new AuthorizationData(null, user.getPassword());
        ValidatableResponse responseEmailErrorMessage = userClient.loginUser(authorizationDataWithoutEmailField);
        userAssertions.loginUserFailedField(responseEmailErrorMessage);
    }
    @Test
    @DisplayName("Login user without password field")
    @Description("Try to login user without password field. Check status code and message.")
    public void userLoginUnsuccessfullyWithoutPasswordField() {
        AuthorizationData authorizationDataWithoutPasswordField = new AuthorizationData(user.getEmail(), null);
        ValidatableResponse responsePasswordErrorMessage = userClient.loginUser(authorizationDataWithoutPasswordField);
        userAssertions.loginUserFailedField(responsePasswordErrorMessage);
    }
    @After
    @Step("Delete test user")
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }
}
