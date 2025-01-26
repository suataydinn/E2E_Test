package com.project.pages_web;

import com.project.utilities.BrowserUtils;
import com.project.utilities.ConfigurationReader;
import com.project.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.project.pages_web.ReservationDetailPage.paymentType;

public class ReservationPage {


    @FindBy(xpath = "//div[@cms-key='reservation_title_badge_left']")
    public WebElement reservationMessage;

    @FindBy(css = "#info_cardNumber")
    public WebElement cardNumberBox;



    WebDriver driver = Driver.get();
    public ReservationPage() {
        PageFactory.initElements(driver, this);
    }



    public void paymentCard() {
        BrowserUtils.waitForVisibility(cardNumberBox, 5);

        switch (paymentType()) {
            case "IYZICO":
                cardNumberBox.sendKeys(ConfigurationReader.get("IyzicoCreditCardNumber"));
                break;
            case "MSU":
                cardNumberBox.sendKeys(ConfigurationReader.get("MsuCreditCardNumber"));
                break;
            case "CRAFTGATE":
                cardNumberBox.sendKeys(ConfigurationReader.get("CraftgateCreditCardNumber"));
                break;
        }
    }


}

