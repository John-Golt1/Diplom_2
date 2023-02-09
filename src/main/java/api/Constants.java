package api;

public class Constants {
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site"; // URL сайта stellaburgers
    public static final String CREATE_NEW_USER_URL = "/api/auth/register";
    public static final String LOGIN_USER_URL = "/api/auth/login";
    public static final String USER_DATA_URL = "/api/auth/user";
    public static final String GET_INGREDIENTS_URL = "/api/ingredients";
    public static final String ORDERS_URL = "/api/orders";
    public static final String ERROR_MESSAGE_OF_EXISTING_USER = "User already exists";
    public static final String ERROR_MESSAGE_OF_MISSING_FIELD = "Email, password and name are required fields";
    public static final String ERROR_MESSAGE_OF_INCORRECT_FIELD = "email or password are incorrect";
    public static final String ERROR_MESSAGE_OF_AUTHORISATION = "You should be authorised";
    public static final String ERROR_MESSAGE_OF_MISSING_INGREDIENTS = "Ingredient ids must be provided";
}
