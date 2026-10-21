package com.restfulbooker.booking;

import com.restfulbooker.config.ApiConfig;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

public class BookingReadTest {
    @Test
    public void getBookingReturnsBookingIds() {
        given()
                .baseUri(ApiConfig.baseUrl())
        .when()
                .get("/booking")
        .then()
                .statusCode(200)
                .contentType("application/json")
                .body("$", instanceOf(List.class))
                .body("bookingid", everyItem(notNullValue()));
    }
}
