package com.User;

import com.example.User;
import com.example.UserClient;
import com.example.UserCredentials;
import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Description;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChangeUserDataTest {

    private UserClient userClient;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }


    @Test
    @Description("Изменение информации о пользователе. Авторизованный пользователь. Изменение поля Email")
    public void changeUserDataFieldEmailAuthUser() {

        user = User.getRandom();
        userClient.create(user);

        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        String accessToken = login.extract().path("accessToken");
        ValidatableResponse changeUserData = userClient.changeUserData(accessToken, User.getWithRandomEmail());

        int statusCode = changeUserData.extract().statusCode();
        boolean isUserDataChanging = changeUserData.extract().path("success");

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertTrue("User data is incorrect", isUserDataChanging);
    }


    @Test
    @Description("Изменение информации о пользователе. Авторизованный пользователь. Изменение поля Password")
    public void changeUserDataFieldPasswordAuthUser() {

        user = User.getRandom();
        userClient.create(user);

        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        String accessToken = login.extract().path("accessToken");
        ValidatableResponse changeUserData = userClient.changeUserData(accessToken, User.getWithRandomPassword());

        int statusCode = changeUserData.extract().statusCode();
        boolean isChangingUserData = changeUserData.extract().path("success");

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertTrue("User data is incorrect", isChangingUserData);
    }


    @Test
    @Description("Изменение информации о пользователе. Авторизованный пользователь. Изменение поля Name")
    public void changeUserDataFieldNameAuthUser() {

        user = User.getRandom();
        userClient.create(user);

        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        String accessToken = login.extract().path("accessToken");
        ValidatableResponse changeUserData = userClient.changeUserData(accessToken, User.getWithRandomName());

        int statusCode = changeUserData.extract().statusCode();
        boolean isChangingUserData = changeUserData.extract().path("success");

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertTrue("User data is incorrect", isChangingUserData);
    }


    @Test
    @Description("Изменение информации о пользователе. Неавторизованный пользователь. Изменение поля Email")
    public void changeUserDataFieldEmailNonAuthUser() {

        user = User.getRandom();
        ValidatableResponse create = userClient.create(user);
        String accessToken = create.extract().path("accessToken");
        ValidatableResponse changeUserData = userClient.changeUserData(accessToken, User.getWithRandomEmail());

        int statusCode = changeUserData.extract().statusCode();
        boolean isUserDataChanging = changeUserData.extract().path("success");

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertTrue("User data is incorrect", isUserDataChanging);
    }


    @Test
    @Description("Изменение информации о пользователе. Неавторизованный пользователь. Изменение поля Password")
    public void changeUserDataFieldPasswordNonAuthUser() {

        user = User.getRandom();
        ValidatableResponse create = userClient.create(user);
        String accessToken = create.extract().path("accessToken");
        ValidatableResponse changeUserData = userClient.changeUserData(accessToken, User.getWithRandomPassword());

        int statusCode = changeUserData.extract().statusCode();
        boolean isChangingUserData = changeUserData.extract().path("success");

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertTrue("User data is incorrect", isChangingUserData);
    }


    @Test
    @Description("Изменение информации о пользователе. Неавторизованный пользователь. Изменение поля Name")
    public void changeUserDataFieldNameNonAuthUser() {

        user = User.getRandom();
        ValidatableResponse create = userClient.create(user);
        String accessToken = create.extract().path("accessToken");
        ValidatableResponse changeUserData = userClient.changeUserData(accessToken, User.getWithRandomName());

        int statusCode = changeUserData.extract().statusCode();
        boolean isChangingUserData = changeUserData.extract().path("success");

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertTrue("User data is incorrect", isChangingUserData);
    }


    @Test
    @Description("Изменение информации о пользователе. Неавторизованный пользователь. Система возвращает ошибку")
    public void changeUserDataAndGetErrorNonAuthUser() {

        user = User.getRandom();
        userClient.create(user);

        ValidatableResponse changeUserData = userClient.changeUserData("", User.getWithRandomName());

        int statusCode = changeUserData.extract().statusCode();
        boolean isChangingUserData = changeUserData.extract().path("success");

        assertThat("Status code is incorrect", statusCode, equalTo(401));
        assertFalse(isChangingUserData);
    }

}
