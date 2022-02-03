package com.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends RestAssuredClient {

    private static final String USER_PATH = "api/";

    @Step("Создание пользователя")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .log().body()
                .when()
                .post(USER_PATH + "auth/register")
                .then()
                .log().body();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse login(UserCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .log().body()
                .when()
                .post(USER_PATH + "auth/login")
                .then()
                .log().body();
    }

    @Step("Получение данных о пользователе")
    public ValidatableResponse getUserInfo(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .log().body()
                .when()
                .get(USER_PATH + "auth/user")
                .then()
                .log().body();

    }

    @Step("Изменение данных пользователя")
    public ValidatableResponse changeUserData(String accessToken, User user) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(user)
                .log().body()
                .when()
                .patch(USER_PATH + "auth/user")
                .then()
                .log().body();
    }

}
