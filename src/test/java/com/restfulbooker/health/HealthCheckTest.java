package com.restfulbooker.health;

import com.restfulbooker.config.ApiConfig;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HealthCheckTest {
    @Test
    public void pingReturnsCreated() {
        given()
                .baseUri(ApiConfig.baseUrl())
        .when()
                .get("/ping")
        .then()
                .statusCode(201)
                .body(equalTo("Created"));
    }
}
