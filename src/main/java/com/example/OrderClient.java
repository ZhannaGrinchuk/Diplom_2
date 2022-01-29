package com.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {

    private static final String ORDER_PATH = "api/";

    //создание заказа
    public ValidatableResponse createOrder(String accessToken, Ingredients ingredients) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(ingredients)
                .log().body()
                .when()
                .post(ORDER_PATH + "orders")
                .then()
                .log().body();
    }


    //получение списка заказов конкретного пользователя
    public ValidatableResponse getListOfOrders(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .log().body()
                .when()
                .get(ORDER_PATH + "orders")
                .then()
                .log().body();

    }


    //получение данных об ингредиентах
    public ValidatableResponse getListOfIngredients() {
        return given()
                .spec(getBaseSpec())
                .log().body()
                .when()
                .get(ORDER_PATH + "ingredients")
                .then()
                .log().body();

    }
}
