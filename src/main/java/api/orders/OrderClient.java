package api.orders;

import api.Client;
import api.ingredients.AllIngredientsDataList;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static api.Constants.GET_INGREDIENTS_URL;
import static api.Constants.ORDERS_URL;
import static io.restassured.RestAssured.given;


public class OrderClient extends Client {

    @Step("Get a list of ingredients")
    public AllIngredientsDataList getIngredients() {
        return given().log().all()
                .spec(getSpec())
                .when()
                .get(GET_INGREDIENTS_URL)
                .body().as(AllIngredientsDataList.class);
    }
    @Step("Creating new order by token with ingredients")
    public ValidatableResponse createNewOrderByToken(String accessToken, DefaultOrder defaultOrder) {
        return given().log().all()
                .spec(getSpec())
                .auth().oauth2(accessToken)
                .body(defaultOrder)
                .when()
                .post(ORDERS_URL)
                .then();
    }
    @Step("Creating new order without token with ingredients")
    public ValidatableResponse createNewOrderWithoutToken(DefaultOrder defaultOrder) {
        return given().log().all()
                .spec(getSpec())
                .body(defaultOrder)
                .when()
                .post(ORDERS_URL)
                .then();
    }
    @Step("Creating new order by token without ingredients")
    public ValidatableResponse createNewOrderByTokenWithoutIngredients(String accessToken) {
        return given().log().all()
                .spec(getSpec())
                .auth().oauth2(accessToken)
                .when()
                .post(ORDERS_URL)
                .then();
    }
    @Step("Creating new order without token without ingredients")
    public ValidatableResponse createNewOrderWithoutTokenAndIngredients() {
        return given().log().all()
                .spec(getSpec())
                .when()
                .post(ORDERS_URL)
                .then();
    }
    @Step("Get orders of user")
    public ValidatableResponse getUserOrdersByToken(String accessToken) {
        return given().log().all()
                .spec(getSpec())
                .auth().oauth2(accessToken)
                .when()
                .get(ORDERS_URL)
                .then();
    }
    @Step("Get orders of user without token")
    public ValidatableResponse getUserOrdersWithoutToken() {
        return given().log().all()
                .spec(getSpec())
                .when()
                .get(ORDERS_URL)
                .then();
    }
}