package api.user;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import api.AuthorizationData;
import api.User;
import api.UserGenerator;

public class UserCreateTest {
    private final UserGenerator userGenerator = new UserGenerator();
    String accessToken;
    private UserClient userClient;
    private User user;
    private UserAssertions userAssertions;

    @Before
    @Step("Precondition step for creating user tests")
    public void setUp() {
        userClient = new UserClient();
        user = userGenerator.getNewRandomUser();
        userAssertions = new UserAssertions();
    }
    @Test
    @DisplayName("Create new user")
    @Description("Test for successfully creation with 200 ОК status code and accessToken")
    public void userCanBeCreatedWith200SC() {
        ValidatableResponse responseCreateUser = userClient.createUser(user);
        userAssertions.creatingUserSuccessfully(responseCreateUser);
        AuthorizationData authorizationData = AuthorizationData.from(user);
        ValidatableResponse responseLoginUser = userClient.loginUser(authorizationData);
        accessToken = responseLoginUser.extract().path("accessToken").toString().substring(7);
    }
    @Test
    @DisplayName("Try to create user with existing data")
    @Description("Test to try create user with the same/existing data. Check status code and message.")
    public void userCanNotBeCreatedWithTheSameData() {
        userClient.createUser(user);
        ValidatableResponse responseCreateUser = userClient.createUser(user);
        userAssertions.creationUserTheSameData(responseCreateUser);
    }
    @Test
    @DisplayName("Create user without email field")
    @Description("Test to try create new user without email field. Check status code and message")
    public void userCanNotBeCreatedWithoutEmailField() {
        user.setEmail(null);
        ValidatableResponse responseNullEmail = userClient.createUser(user);
        userAssertions.creationUserFailedField(responseNullEmail);
    }
    @Test
    @DisplayName("Create user without password field")
    @Description("Test to try create new user without password field. Check status code and message")
    public void userCanNotBeCreatedWithoutPasswordField() {
        user.setPassword(null);
        ValidatableResponse responseNullPassword = userClient.createUser(user);
        userAssertions.creationUserFailedField(responseNullPassword);
    }
    @Test
    @DisplayName("Create user without name field")
    @Description("Test to try create new user without name field. Check status code and message")
    public void userCanNotBeCreatedWithoutNameField() {
        user.setName(null);
        ValidatableResponse responseNullName = userClient.createUser(user);
        userAssertions.creationUserFailedField(responseNullName);
    }
    @After
    @Step("Delete test user")
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }
}