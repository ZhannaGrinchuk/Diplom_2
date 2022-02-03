package com.example;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private static Faker faker = new Faker();
    private String email;
    private String password;
    private String name;


    public User() {

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User getRandom() {
        final String email = faker.internet().safeEmailAddress();
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new User(email, password, name);

    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public static User getWithEmailAndName() {
        return new User().setEmail(faker.internet().safeEmailAddress()).setName(RandomStringUtils.randomAlphabetic(10));
    }

    public static User getWithPasswordAndName() {
        return new User().setPassword(RandomStringUtils.randomAlphabetic(10)).setName(RandomStringUtils.randomAlphabetic(10));
    }

    public static User getWithEmailAndPassword() {
        return new User().setEmail(faker.internet().safeEmailAddress()).setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    public static User getWithRandomEmail() {
        return new User().setEmail(faker.internet().safeEmailAddress());
    }

    public static User getWithRandomPassword() {
        return new User().setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    public static User getWithRandomName() {
        return new User().setName(RandomStringUtils.randomAlphabetic(10));
    }

}
