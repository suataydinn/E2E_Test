package com.project.step_definitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import com.project.pages_api.ApiPage;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class ApiDefs {


    ApiPage apiPage = new ApiPage();

    private JSONObject bookingBody;
    private Response response;
    private String authToken;
    private int bookingId;

    @Given("I have booking details for a new customer")
    public void i_have_booking_details() {
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2024-01-01");
        bookingDates.put("checkout", "2024-01-05");

        bookingBody = new JSONObject();
        bookingBody.put("firstname", "Suat");
        bookingBody.put("lastname", "Aydin");
        bookingBody.put("totalprice", 111);
        bookingBody.put("depositpaid", true);
        bookingBody.put("bookingdates", bookingDates);
        bookingBody.put("additionalneeds", "Breakfast");
    }

    @When("I send POST request to create booking")
    public void i_send_post_request_to_create_booking() {
        response = apiPage.createBooking(bookingBody);
    }

    @Then("the booking should be created successfully")
    public void the_booking_should_be_created_successfully() {
        response.then().statusCode(200);
        bookingId = response.path("bookingid");
    }

    @And("the response should contain the booking details")
    public void response_should_contain_booking_details() {
        response.then()
                .body("booking.firstname", equalTo("Suat"))
                .body("booking.lastname", equalTo("Aydin"));
        response.print();
    }

    @Given("I have authentication token")
    public void i_have_authentication_token() {
        authToken = apiPage.getAuthToken("admin", "password123");
    }

    @Given("a booking exists in the system")
    public void a_booking_exists_in_the_system() {
        bookingId = apiPage.getBookingIdsFromResponse().get(0);
    }

    @When("I send GET request to retrieve the booking")
    public void i_send_get_request_to_retrieve_booking() {
        response = apiPage.getBooking(bookingId);
    }

    @Then("I should get the booking details successfully")
    public void i_should_get_booking_details_successfully() {
        response.then()
                .statusCode(200)
                .body("firstname", notNullValue())
                .body("lastname", notNullValue());
    }

    @Given("I have updated booking details")
    public void i_have_updated_booking_details() {
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2024-02-01");
        bookingDates.put("checkout", "2024-02-05");

        bookingBody = new JSONObject();
        bookingBody.put("firstname", "SuatUpdate");
        bookingBody.put("lastname", "AydinUpdate");
        bookingBody.put("totalprice", 222);
        bookingBody.put("depositpaid", true);
        bookingBody.put("bookingdates", bookingDates);
        bookingBody.put("additionalneeds", "Lunch");
    }

    @When("I send PUT request to update the booking")
    public void i_send_put_request_to_update_booking() {
        response = apiPage.updateBooking(bookingId, bookingBody, authToken);
    }

    @Then("the booking should be updated successfully")
    public void the_booking_should_be_updated_successfully() {
        System.out.println("response.prettyPrint() = " + response.prettyPrint());
        System.out.println("response.print() = " + response.print());
        response.then()
                .statusCode(200)
                .body("firstname", equalTo("SuatUpdate"))
                .body("lastname", equalTo("AydinUpdate"))
                .body("totalprice", equalTo(222))
                .body("depositpaid", equalTo(true))
                .body("firstname", equalTo("SuatUpdate"))
                .body("firstname", equalTo("SuatUpdate"))
                .body("bookingdates.checkin", equalTo("2024-02-01"))
                .body("bookingdates.checkout", equalTo("2024-02-05"))
                .body("additionalneeds", equalTo("Lunch"));
    }

    @Given("I have partial booking details to update")
    public void i_have_partial_booking_details() {
        bookingBody = new JSONObject();
        bookingBody.put("firstname", "SuatPartialUpdate");
        bookingBody.put("totalprice", 150);
    }

    @When("I send PATCH request to update the booking")
    public void i_send_patch_request_to_update_booking() {
        response = apiPage.partialUpdateBooking(bookingId, bookingBody, authToken);
    }

    @Then("the booking should be partially updated successfully")
    public void the_booking_should_be_partially_updated_successfully() {
        response.then()
                .statusCode(200)
                .body("firstname", equalTo("SuatPartialUpdate"))
                .body("totalprice", equalTo(150));
    }

    @When("send DELETE request to remove the booking")
    public void send_delete_request_to_remove_booking() {
        response = apiPage.deleteBooking(bookingId, authToken);
    }

    @When("GET request to retrieve all booking IDs")
    public void get_request_for_booking_ids() {
        response = apiPage.getAllBookingIds();
    }

    @Then("I should receive a list of booking IDs")
    public void i_should_receive_booking_ids() {
        response.then()
                .statusCode(200)
                .body("$", not(empty()));
        System.out.println("response.path(\"bookingid[0]\") = " + response.path("bookingid[0]"));

        response.print();
    }

    @When("GET request to ping the API")
    public void get_request_for_health_check() {

        response = apiPage.healthCheck();
    }

    @Then("verify status code {int}")
    public void verifyStatusCode(int statusCode) {
        response.then().statusCode(statusCode);

    }


    @Given("get booking ID index {int}")
    public void getBookingIDIndex(int index) {
        bookingId = apiPage.getBookingIdsFromResponse().get(index);

    }
}
