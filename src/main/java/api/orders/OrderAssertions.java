package api.orders;

import io.restassured.response.ValidatableResponse;

import static api.Constants.ERROR_MESSAGE_OF_AUTHORISATION;
import static api.Constants.ERROR_MESSAGE_OF_MISSING_INGREDIENTS;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderAssertions {
    public void creatingOrderSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    public void creatingOrderUnsuccessfullyWithoutIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo(ERROR_MESSAGE_OF_MISSING_INGREDIENTS));
    }

    public void creatingOrderUnsuccessfullyWithWrongIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    public void getUserOrdersSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    public void getUserOrdersSuccessfullyWithOrder(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("orders", notNullValue());
    }

    public void getUserOrdersUnsuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo(ERROR_MESSAGE_OF_AUTHORISATION));
    }
}
