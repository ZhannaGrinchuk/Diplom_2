package com.example;

import com.github.javafaker.Faker;
import io.restassured.response.ValidatableResponse;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class Ingredients extends RestAssuredClient {

    private static final String INGREDIENTS_PATH = "api/ingredients";
    public ArrayList<Object> ingredients;
    public static Faker faker = new Faker();

    public Ingredients(ArrayList<Object> ingredients) {
        this.ingredients = ingredients;
    }

    public Ingredients() {

    }

    public ArrayList<Object> getIngredients() {
        return ingredients;
    }

    public Ingredients setIngredients(ArrayList<Object> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public static Ingredients getBurger() {


        ValidatableResponse response = given()
                .spec(getBaseSpec())
                .when()
                .get(INGREDIENTS_PATH)
                .then()
                .statusCode(200);


        ArrayList<Object> ingredients = new ArrayList<>();


        int bunIndex = nextInt(0, 2);
        int mainIndex = nextInt(0, 9);
        int sauceIndex = nextInt(0, 4);


        List<Object> bunIngredients = response.extract().jsonPath().getList("data.findAll{it.type == 'bun'}._id");
        List<Object> mainIngredients = response.extract().jsonPath().getList("data.findAll{it.type == 'main'}._id");
        List<Object> sauceIngredients = response.extract().jsonPath().getList("data.findAll{it.type == 'sauce'}._id");


        ingredients.add(bunIngredients.get(bunIndex));
        ingredients.add(mainIngredients.get(mainIndex));
        ingredients.add(sauceIngredients.get(sauceIndex));

        return new Ingredients(ingredients);

    }

    public static Ingredients getIncorrectIngredients() {
        ArrayList<Object> ingredients = new ArrayList<Object>();
        ingredients.add("61c0c5a71d1f8256565656d");
        return new Ingredients(ingredients);
    }

    public static Ingredients getNullIngredients() {
        ArrayList<Object> ingredients = new ArrayList<Object>();
        return new Ingredients(ingredients);
    }
}

