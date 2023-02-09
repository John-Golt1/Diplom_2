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

public class GetOrderTest {
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
    @Step("Precondition step for getting order tests")
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
    @DisplayName("Get user's order by authorized user without orders")
    @Description("Test for getting order list with 200 ОК status code and accessToken")
    public void getUserOrdersByTokenWithoutOrders() {
        ValidatableResponse responseGetOrderByToken = orderClient.getUserOrdersByToken(accessToken);
        orderAssertions.getUserOrdersSuccessfully(responseGetOrderByToken);
    }
    @Test
    @DisplayName("Get user's order by authorized user")
    @Description("Test for getting order list with 200 ОК status code and accessToken")
    public void getUserOrdersByTokenWithOrders() {
        orderClient.createNewOrderByToken(accessToken, defaultOrder);
        ValidatableResponse responseGetOrderByTokenWithOrder = orderClient.getUserOrdersByToken(accessToken);
        orderAssertions.getUserOrdersSuccessfullyWithOrder(responseGetOrderByTokenWithOrder);
    }
    @Test
    @DisplayName("Get user's order by unauthorized user")
    @Description("Test for getting order list with 401 status code and message")
    public void getUserOrdersWithoutToken() {
        ValidatableResponse responseGetOrderWithoutToken = orderClient.getUserOrdersWithoutToken();
        orderAssertions.getUserOrdersUnsuccessfully(responseGetOrderWithoutToken);
    }
    @After
    @Step("Delete test user")
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }
}
