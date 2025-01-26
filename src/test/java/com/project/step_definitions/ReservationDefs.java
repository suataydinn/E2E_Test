package com.project.step_definitions;

import com.project.pages_web.CarDetailPage;
import com.project.pages_web.ReservationPage;
import com.project.utilities.BrowserUtils;
import com.project.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ReservationDefs {


    ReservationPage reservationPage = new ReservationPage();
    WebDriver driver = Driver.get();

    @And("enter payment method")
    public void enterPaymentMethod() {
        reservationPage.paymentCard();
    }

    @And("send sms key")
    public void sendSmsKey() {
    }

    @And("click reservation detail button")
    public void clickReservationDetailButton() {
        BrowserUtils.waitForVisibility(By.cssSelector("#modal3dd>iframe"),10);
        WebElement iframe = driver.findElement(By.cssSelector("#modal3dd>iframe"));
        driver.switchTo().frame(iframe);

        BrowserUtils.waitForClickability(By.xpath("//button[text()='Rezervasyon Detayı']"),18);
        WebElement popupElement = driver.findElement(By.xpath("//button[text()='Rezervasyon Detayı']"));
        popupElement.click();

        driver.switchTo().defaultContent();

        BrowserUtils.waitForVisibility(reservationPage.reservationMessage, 25);
    }

    @Then("the user should be able to make reservation")
    public void theUserShouldBeAbleToMakeReservation() {
    }

    @And("verify match reservation detail and DB")
    public void verifyMatchReservationDetailAndDB() {
    }

    @Then("verify calculate price on reservation detail page")
    public void verifyCalculatePriceOnReservationDetailPage() {
        new CarDetailPage().calculatePrice();
    }
}
