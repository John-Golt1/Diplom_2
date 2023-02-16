package api.orders;

import api.User;
import api.UserGenerator;
import api.ingredients.AllIngredientsDataList;
import api.user.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateOrderTest {
    private final UserGenerator userGenerator = new UserGenerator();
    private String accessToken;
    private UserClient userClient;
    private OrderClient orderClient;
    private User user;
    private AllIngredientsDataList allIngredientsDataList;
    private DefaultOrder defaultOrder;
    private List<String> ingredients;
    private OrderAssertions orderAssertions;

    @Before
    @Step("Precondition step for creating order tests")
    public void setUp() {
        userClient = new UserClient();
        user = userGenerator.getNewRandomUser();
        ValidatableResponse responseCreateUser = userClient.createUser(user);
        accessToken = responseCreateUser.extract().path("accessToken").toString().substring(7);
        orderClient = new OrderClient();
        allIngredientsDataList = orderClient.getIngredients();
        ingredients = new ArrayList<>();
        ingredients.add(allIngredientsDataList.getData().get(0).getId());
        ingredients.add(allIngredientsDataList.getData().get(1).getId());
        defaultOrder = new DefaultOrder(ingredients);
        orderAssertions = new OrderAssertions();
    }
    @Test
    @DisplayName("Create new order by authorized user")
    @Description("Test for successfully creation order with 200 ОК status code and accessToken")
    public void createNewOrderByToken() {
        ValidatableResponse responseNewOrderByTokenWithIngredients = orderClient.createNewOrderByToken(accessToken, defaultOrder);
        orderAssertions.creatingOrderSuccessfully(responseNewOrderByTokenWithIngredients);
    }
    @Test
    @DisplayName("Create new order by unauthorized user")
    @Description("Test for successfully creation order with 200 OK status code and message")
    public void createNewOrderWithoutToken() {
        ValidatableResponse responseWithIngredientsWithoutToken = orderClient.createNewOrderWithoutToken(defaultOrder);
        orderAssertions.creatingOrderSuccessfully(responseWithIngredientsWithoutToken);
    }
    @Test
    @DisplayName("Create new order by authorized user without ingredients")
    @Description("Test for unsuccessfully creation order with 400 status code and message")
    public void createNewOrderByTokenWithoutIngredients() {
        ValidatableResponse responseOrderByTokenWithoutIngredients = orderClient.createNewOrderByTokenWithoutIngredients(accessToken);
        orderAssertions.creatingOrderUnsuccessfullyWithoutIngredients(responseOrderByTokenWithoutIngredients);
    }
    @Test
    @DisplayName("Create new order by authorized user with wrong ingredients")
    @Description("Test for unsuccessfully creation order with 500 status code")
    public void createNewOrderByTokenWithWrongIngredients() {
        defaultOrder.setIngredients(List.of("84ytwv4yw6bv8497un985f794", "4i78tyv475y68473vt"));
        ValidatableResponse responseOrderByTokenWrongIngredient = orderClient.createNewOrderByToken(accessToken, defaultOrder);
        orderAssertions.creatingOrderUnsuccessfullyWithWrongIngredients(responseOrderByTokenWrongIngredient);
    }
    @Test
    @DisplayName("Create new order by unauthorized user without ingredients")
    @Description("Test for unsuccessfully creation order with 400 status code and message")
    public void createNewOrderWithoutTokenWithoutIngredients() {
        ValidatableResponse responseWithNothing = orderClient.createNewOrderWithoutTokenAndIngredients();
        orderAssertions.creatingOrderUnsuccessfullyWithoutIngredients(responseWithNothing);
    }
    @After
    @Step("Delete test user")
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }
}
