package com.qacart.testcases;

import com.qacart.apis.UserAPI;
import com.qacart.data.ErrorMessages;
import com.qacart.models.Error;
import com.qacart.models.User;
import com.qacart.steps.UserSteps;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class UsersTest {


    @Test
    public void ShouldBeAbleToRegister() {



        User user = UserSteps.generateUser();
        Response response = UserAPI.register(user);

        User returnedUser = response.as(User.class);

        assertThat(response.statusCode(), equalTo(201));
        assertThat(returnedUser.getFirstName(), equalTo(user.getFirstName()));

    }


    @Test
    public void ShouldNotBeAbleToRegisterWithExistingEmail() {

        User user = UserSteps.getRegisteredUser();

        Response response = UserAPI.register(user);

        Error returnedError = response.body().as(Error.class);

        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.EMAIL_ALREADY_EXISTS));

    }


    @Test
    public void ShouldBeAbleToLogin() {

        User user = UserSteps.getRegisteredUser();
        User loginUser = new User(user.getEmail(), user.getPassword());

        Response response = UserAPI.login(loginUser);

        User returnedUser = response.as(User.class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedUser.getFirstName(), equalTo(user.getFirstName()));
        assertThat(returnedUser.getAccessToken(), not(equalTo(null)));


    }


    @Test
    public void ShouldNotBeAbleToLoginIfThePasswordIsNotCorrect() {


        User user = UserSteps.getRegisteredUser();
        User loginUser = new User(user.getEmail(), "wrongPassword");

        Response response = UserAPI.login(loginUser);

        Error returnedError = response.body().as(Error.class);

        assertThat(response.statusCode(), equalTo(401));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.EMAIL_OR_PASSWORD_NOT_CORRECT));

    }
}
