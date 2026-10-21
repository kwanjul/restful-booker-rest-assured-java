package com.restfulbooker.booking;

import com.restfulbooker.config.ApiConfig;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class BookingCreateTest {
    @Test
    public void createBookingReturnsBookingIdAndBookingDetails() {
        Map<String, Object> booking = Map.of(
                "firstname", "Jane",
                "lastname", "Doe",
                "totalprice", 150,
                "depositpaid", true,
                "bookingdates", Map.of(
                        "checkin", "2026-10-20",
                        "checkout", "2026-10-22"),
                "additionalneeds", "Breakfast");

        given()
                .baseUri(ApiConfig.baseUrl())
                .contentType("application/json")
                .body(booking)
        .when()
                .post("/booking")
        .then()
                .statusCode(200)
                .contentType("application/json")
                .body("bookingid", greaterThan(0))
                .body("booking.firstname", equalTo("Jane"))
                .body("booking.lastname", equalTo("Doe"))
                .body("booking.totalprice", equalTo(150))
                .body("booking.depositpaid", equalTo(true))
                .body("booking.bookingdates.checkin", equalTo("2026-10-20"))
                .body("booking.bookingdates.checkout", equalTo("2026-10-22"))
                .body("booking.additionalneeds", equalTo("Breakfast"));
    }
}
