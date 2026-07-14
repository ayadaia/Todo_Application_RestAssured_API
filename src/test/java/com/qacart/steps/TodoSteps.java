package com.qacart.steps;

import com.github.javafaker.Faker;
import com.qacart.apis.TodoAPI;
import com.qacart.models.Todo;
import io.restassured.response.Response;

public class TodoSteps {

    public static Todo generateTodo(){
        Faker faker = new Faker();
        Boolean isCompleted = false;
        String item = faker.book().title();
        return new Todo(isCompleted,item);


    }

    public static String getTodoID(Todo todo , String token) {
        Response response = TodoAPI.addTodo(todo, token);
        return response.body().path("_id");
    }
}
