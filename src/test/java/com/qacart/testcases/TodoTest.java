package com.qacart.testcases;

import com.qacart.apis.TodoAPI;
import com.qacart.models.Error;
import com.qacart.models.Todo;
import com.qacart.steps.TodoSteps;
import com.qacart.steps.UserSteps;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TodoTest {

    @Test
    public void ShouldBeAbleToAddTodo() {

        String token= UserSteps.getUserToken();

          Todo todo = TodoSteps.generateTodo();

    Response response = TodoAPI.addTodo(todo,token);

      Todo returnedTodo = response.as(Todo.class);

      assertThat(response.statusCode(), equalTo(201));
      assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
      assertThat(returnedTodo.getIsCompleted(), equalTo(todo.getIsCompleted()));

    }

    @Test
    public void ShouldNotBeAbleToAddTodoIfIsCompletedIsMissing() {

        String token= UserSteps.getUserToken();

        Todo todo = new Todo("Learn Appium");

        Response response = TodoAPI.addTodo(todo,token);

        Error returnedError = response.body().as(Error.class);

        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo("\"isCompleted\" is required"));


    }

    @Test
    public void ShouldBeAbleToGetTodoWithID() {
        String token= UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodo();
        String todoID= TodoSteps.getTodoID(todo , token);


        Response response = TodoAPI.getTodo(todoID,token);

        Todo returnedTodo = response.as(Todo.class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getIsCompleted(), equalTo(false));


    }

    @Test
    public void ShouldBeAbleToGetAllTodo() {



        String token= UserSteps.getUserToken();

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .header("Authorization","Bearer "+token)
                .when().get("/api/v1/tasks/")
                .then()
                .log().all()
                .extract().response();
    }

    @Test
    public void ShouldBeAbleToDeleteTodoWithID() {
        String token= UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodo();
        String todoID= TodoSteps.getTodoID(todo , token);

      Response response = TodoAPI.deleteTodo(todoID,token);

        Todo returnedTodo = response.as(Todo.class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getIsCompleted(), equalTo(false));

    }
}
