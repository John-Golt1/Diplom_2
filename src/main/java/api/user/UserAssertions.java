package api.user;

import io.restassured.response.ValidatableResponse;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;
import static api.Constants.*;

public class UserAssertions {
    public void creatingUserSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue());
    }
    public void creationUserTheSameData(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(ERROR_MESSAGE_OF_EXISTING_USER));
    }
    public void creationUserFailedField(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(ERROR_MESSAGE_OF_MISSING_FIELD));
    }
    public void loginUserSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue());
    }
    public void loginUserFailedField(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo(ERROR_MESSAGE_OF_INCORRECT_FIELD));
    }
    public void refreshUserSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }
    public void refreshUserUnsuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo(ERROR_MESSAGE_OF_AUTHORISATION));
    }
}
