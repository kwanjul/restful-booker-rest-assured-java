package com.restfulbooker.booking;

import com.restfulbooker.config.RequestSpecs;
import com.restfulbooker.data.BookingData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

public class BookingReadTest {

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
    public void getBookingReturnsBookingIds() {
        given()
                .spec(RequestSpecs.baseSpec())
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
        Map<String, Object> booking = BookingData.defaultBooking();

        @SuppressWarnings("unchecked")
        Map<String, String> bookingDates = (Map<String, String>) booking.get("bookingdates");

        bookingId = BookingClient.createBooking(booking);

        given()
                .spec(RequestSpecs.baseSpec())
        .when()
                .get("/booking/{id}", bookingId)
        .then()
                .statusCode(200)
                .contentType("application/json")
                .body("firstname", equalTo(booking.get("firstname")))
                .body("lastname", equalTo(booking.get("lastname")))
                .body("totalprice", equalTo(booking.get("totalprice")))
                .body("depositpaid", equalTo(booking.get("depositpaid")))
                .body("bookingdates.checkin", equalTo(bookingDates.get("checkin")))
                .body("bookingdates.checkout", equalTo(bookingDates.get("checkout")))
                .body("additionalneeds", equalTo(booking.get("additionalneeds")));
    }
}
