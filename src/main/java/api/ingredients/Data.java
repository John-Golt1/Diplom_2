package api.ingredients;


import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("_id")
    private String id;
    private String name;
    private String type;
    private int proteins;
    private int fat;
    private int carbohydrates;
    private int calories;
    private int price;
    private String image;
    private String image_mobile;
    private String image_large;
    @SerializedName("__v")
    private int v;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getProteins() {
        return proteins;
    }
    public void setProteins(int proteins) {
        this.proteins = proteins;
    }
    public int getFat() {
        return fat;
    }
    public void setFat(int fat) {
        this.fat = fat;
    }
    public int getCarbohydrates() {
        return carbohydrates;
    }
    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }
    public int getCalories() {
        return calories;
    }
    public void setCalories(int calories) {
        this.calories = calories;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getImage_mobile() {
        return image_mobile;
    }
    public void setImage_mobile(String image_mobile) {
        this.image_mobile = image_mobile;
    }
    public String getImage_large() {
        return image_large;
    }
    public void setImage_large(String image_large) {
        this.image_large = image_large;
    }
    public int getV() {
        return v;
    }
    public void setV(int v) {
        this.v = v;
    }
}