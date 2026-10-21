package com.restfulbooker.booking;

import com.restfulbooker.config.RequestSpecs;
import com.restfulbooker.data.BookingData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class BookingCreateTest {

    private Integer bookingId = null;

    @AfterMethod(alwaysRun = true)
    public void cleanup() {
        try {
            if (bookingId != null) {
                BookingClient.deleteBooking(bookingId);
            }
        } finally {
            bookingId = null;
        }
    }

    @Test
    public void createBookingReturnsBookingIdAndBookingDetails() {
        Map<String, Object> booking = BookingData.defaultBooking();

        @SuppressWarnings("unchecked")
        Map<String, String> bookingDates = (Map<String, String>) booking.get("bookingdates");

        bookingId = given()
                .spec(RequestSpecs.baseSpec())
                .body(booking)
        .when()
                .post("/booking")
        .then()
                .statusCode(200)
                .contentType("application/json")
                .body("bookingid", greaterThan(0))
                .body("booking.firstname", equalTo(booking.get("firstname")))
                .body("booking.lastname", equalTo(booking.get("lastname")))
                .body("booking.totalprice", equalTo(booking.get("totalprice")))
                .body("booking.depositpaid", equalTo(booking.get("depositpaid")))
                .body("booking.bookingdates.checkin", equalTo(bookingDates.get("checkin")))
                .body("booking.bookingdates.checkout", equalTo(bookingDates.get("checkout")))
                .body("booking.additionalneeds", equalTo(booking.get("additionalneeds")))
                .extract()
                .path("bookingid");
    }
}
