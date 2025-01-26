package com.project.pages_mobile;

import com.project.pages_web.*;
import com.project.utilities.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MobileReservationPage extends MainPage {
    WebDriver driver = Driver.get();
    public MobileReservationPage() {
        PageFactory.initElements(driver, this);
    }



    @FindBy(xpath = "//div[@cms-key='payment_pricebox_info']")
    public WebElement paymentInfoBar;

    @FindBy(xpath = "//div[@cms-key='payment_pricebox_text1']/../div[2]")
    public WebElement cardPayment;

    @FindBy(xpath = "//div[@cms-key='text_daily_price']/../div[2]")
    public WebElement dailyPrice;

    @FindBy(xpath = "//div[@cms-key='text_day']")
    public WebElement rentalDay;

    public void verifyGuestMobileReservation() {
            BrowserUtils.waitForVisibility(new ReservationDetailPage().reservationMessage,25);
            Assert.assertTrue(new ReservationDetailPage().reservationMessage.isDisplayed());
            Assert.assertTrue(new ReservationDetailPage().reservationMessage2.isDisplayed());
            BrowserUtils.verifyElementDisplayed(new ReservationDetailPage().signUpButton);
            hamburgerMenu.click();
            BrowserUtils.waitHalfSecond();
        Assert.assertEquals("Üye Ol / Giriş Yap",loginLocatorMobil.getText());
        }


    public void verifyMobilePriceSearchResultToCarDetail(){
        BrowserUtils.waitForVisibility(cardPayment,2);

        String[] searchResultPriceText = SearchResultPage.searchResultPriceText;
        Assert.assertEquals(cardPayment.getText(), searchResultPriceText[0]);
        Assert.assertEquals("Günlük Fiyat : "+ dailyPrice.getText(), searchResultPriceText[1]);
        Assert.assertEquals("("+rentalDay.getText()+")", searchResultPriceText[2]);

    }





}
