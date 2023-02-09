package api.orders;

import java.util.List;

public class DefaultOrder {
    List<String> ingredients;

    public DefaultOrder() {
    }
    public DefaultOrder(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public List<String> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}