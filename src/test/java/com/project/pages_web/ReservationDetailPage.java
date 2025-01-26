package com.project.pages_web;

import com.project.pages_api.ApiPage;
import com.project.utilities.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



public class ReservationDetailPage extends MainPage {
    WebDriver driver = Driver.get();

    @FindBy(xpath = "//div[@cms-key='reservation_title_badge_left']")
    public WebElement reservationMessage;

    @FindBy(xpath = "//div[@cms-key='reservation_title_badge_right']")
    public WebElement reservationMessage2;

    @FindBy(xpath = "//span[@id='rich-rezNo']")
    public WebElement reservationNo;

    @FindBy(css = "#rich-rezNo")
    public WebElement onlyReservationNumber;

    @FindBy(xpath = "//button[@cms-key='bottom_fixed_button_text']")
    public WebElement signUpButton;


    public ReservationDetailPage() {
        PageFactory.initElements(driver, this);
    }

    public static String paymentType() {//This method required for send sms step and reservation
        String stagingToken = "TOKEN";
        RestAssured.baseURI = "https://api.360y.co/api/v1/payment-api";
        Response response = RestAssured.given().headers("Authorization",
                        "Bearer " + stagingToken,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .when().post("/active");
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), 200);
        JsonPath jsonPath = response.jsonPath();
        String activePayment = jsonPath.getString("active");
        System.out.println("activePayment = " + activePayment);
        return activePayment;
    }


}
