package com.restfulbooker.health;

import com.restfulbooker.config.RequestSpecs;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HealthCheckTest {
    @Test
    public void pingReturnsCreated() {
        given()
                .spec(RequestSpecs.baseSpec())
        .when()
                .get("/ping")
        .then()
                .statusCode(201)
                .body(equalTo("Created"));
    }
}
