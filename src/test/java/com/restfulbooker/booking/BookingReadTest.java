package com.restfulbooker.booking;

import com.restfulbooker.config.ApiConfig;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
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

    @Test
    public void getBookingByIdReturnsBookingDetails() {
        Map<String, Object> booking = Map.of(
                "firstname", "Jane",
                "lastname", "Doe",
                "totalprice", 150,
                "depositpaid", true,
                "bookingdates", Map.of(
                        "checkin", "2026-10-20",
                        "checkout", "2026-10-22"),
                "additionalneeds", "Breakfast");

        int bookingId = given()
                .baseUri(ApiConfig.baseUrl())
                .contentType("application/json")
                .body(booking)
        .when()
                .post("/booking")
        .then()
                .statusCode(200)
                .extract()
                .path("bookingid");

        given()
                .baseUri(ApiConfig.baseUrl())
        .when()
                .get("/booking/{id}", bookingId)
        .then()
                .statusCode(200)
                .contentType("application/json")
                .body("firstname", equalTo("Jane"))
                .body("lastname", equalTo("Doe"))
                .body("totalprice", equalTo(150))
                .body("depositpaid", equalTo(true))
                .body("bookingdates.checkin", equalTo("2026-10-20"))
                .body("bookingdates.checkout", equalTo("2026-10-22"))
                .body("additionalneeds", equalTo("Breakfast"));
    }
}
