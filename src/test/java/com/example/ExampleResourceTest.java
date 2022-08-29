package com.example;

import com.example.todoapp.control.TaskService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class ExampleResourceTest {

    @Inject
    TaskService taskService;

    @Test
    public void testHelloEndpoint() {
        taskService.init();
        List<Object> list = given()
                .when()
                .headers(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .get("/tasks")
                .then()
                .using()
                .assertThat()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath().getList("$");
        Assertions.assertEquals(2, list.size());
    }

}