package com.User;

import com.example.User;
import com.example.UserClient;
import com.example.UserCredentials;
import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Description;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;


public class CreateUserTest {

    private UserClient userClient;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }


    @Test
    @Description("Создание уникального пользователя")
    public void checkUserCanBeCreated() {

        user = User.getRandom();
        ValidatableResponse response = userClient.create(user);

        int statusCode = response.extract().statusCode();
        String accessToken = response.extract().path("accessToken");

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertThat("Access token is null", accessToken, is(not(0)));

    }

    @Test
    @Description("Создание пользователя, который уже зарегистрирован")
    public void checkCreatingUserAlreadyRegistered() {

        user = User.getRandom();
        userClient.create(user);
        userClient.login(UserCredentials.from(user));

        ValidatableResponse createAgain = userClient.create(user);

        int statusCode = createAgain.extract().statusCode();
        boolean isUserCreated = createAgain.extract().path("success");

        assertThat("Status code is incorrect", statusCode, equalTo(403));
        assertFalse("User is created", isUserCreated);

    }


    @Test
    @Description("Создание пользователя без заполнения одного из обязательных полей (без поля Name)")
    public void checkUserCreatingWithoutName() {

        user = User.getWithEmailAndPassword();

        ValidatableResponse response = userClient.create(user);

        int statusCode = response.extract().statusCode();
        boolean isUserCreated = response.extract().path("success");

        assertThat("Status code is incorrect", statusCode, equalTo(403));
        assertFalse("User is created", isUserCreated);

    }


    @Test
    @Description("Создание пользователя без заполнения одного из обязательных полей (без поля Email)")
    public void checkUserCreatingWithoutEmail() {

        user = User.getWithPasswordAndName();

        ValidatableResponse response = userClient.create(user);

        int statusCode = response.extract().statusCode();
        boolean isUserCreated = response.extract().path("success");

        assertThat("Status code is incorrect", statusCode, equalTo(403));
        assertFalse("User is created", isUserCreated);

    }


    @Test
    @Description("Создание пользователя без заполнения одного из обязательных полей (без поля Password)")
    public void checkUserCreatingWithoutPassword() {

        user = User.getWithEmailAndName();

        ValidatableResponse response = userClient.create(user);

        int statusCode = response.extract().statusCode();
        boolean isUserCreated = response.extract().path("success");

        assertThat("Status code is incorrect", statusCode, equalTo(403));
        assertFalse("User is created", isUserCreated);

    }

}
