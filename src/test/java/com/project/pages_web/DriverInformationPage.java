package com.project.pages_web;

import com.project.pages_api.ApiPage;
import com.project.utilities.BrowserUtils;
import com.project.utilities.ConfigurationReader;
import com.project.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.util.List;

public class DriverInformationPage extends MainPage{
    WebDriver driver = Driver.get();

    @FindBy(css = "#driver_firstName")
    public WebElement nameBox;

    @FindBy(css = "#driver_lastName")
    public WebElement surnameBox;

    @FindBy(css = "#driver_birthDate")
    public WebElement birthdayBox;

    @FindBy(css = "#driver_identity")
    public WebElement passportNumberBox;

    @FindBy(css = "#driver_phone")
    public WebElement phoneNumberBox;

    @FindBy(css = "#driver_email")
    public WebElement mailBox;

    @FindBy(css = "#billing_firstName")
    public WebElement billingNameBox;

    @FindBy(css = "#billing_taxDivision")
    public WebElement billingTaxDivision;

    @FindBy(css = "#billing_taxIdentifier")
    public WebElement billingTaxIdentifier;

    @FindBy(css = "#billing_zipCode")
    public WebElement zipBox;

    @FindBy(css = "#billing_city")
    public WebElement cityBox;

    @FindBy(css = "#billing_state")
    public WebElement stateBox;

    @FindBy(css = "#billing_line")
    public WebElement fullAddressBox;

    @FindBy(css = "div.capitalize.cursor-pointer")
    public List <WebElement> driverInfoDropdown;

    @FindBy(css = "#driver_isAgreement>input")
    public WebElement kvkkCheckBox;


    public DriverInformationPage() {
        PageFactory.initElements(driver, this);
    }


    public void guestDriverInfo(){
        driverInfo("test", "11111990", "+376");
        validIdentityNumber();
        enterAddress("12345", "İspanya");
    }


    public void guestAbroadDriverInfo(){
        driverInfo("test", "11111990","+376");
        Assert.assertTrue(driver.findElements(By.xpath("//label[@cms-key='label_identity']")).size()==0);
        kvkkCheckBox.click();
        Assert.assertTrue(Driver.get().findElement(By.cssSelector("input.rounded.peer")).isSelected());
        enterAddress("12345", "İspanya");
    }

    public void driverInfo(String name, String birthday , String areaCode){
        BrowserUtils.waitForVisibility(nameBox,10);
        BrowserUtils.waitHalfSecond();
        nameBox.sendKeys(name);
        BrowserUtils.waitHalfSecond();
        surnameBox.sendKeys("surname");
        birthdayBox.sendKeys(birthday);

        BrowserUtils.clickWithJS(areaCodeLocator);
        BrowserUtils.waitHalfSecond();
        areaCodeSearchLocator.sendKeys(areaCode);
        WebElement element=driver.findElement(By.xpath("//*[contains(text(),'"+areaCode+"')]"));
        BrowserUtils.clickWithJS(element);

        phoneNumberBox.sendKeys("541384");
        String email = ConfigurationReader.get("email");
        mailBox.sendKeys(email);
        BrowserUtils.waitHalfSecond();
    }

    public void validIdentityNumber(){
        BrowserUtils.waitHalfSecond();
        passportNumberBox.sendKeys(ConfigurationReader.get("tckn"));
        BrowserUtils.waitHalfSecond();
    }


    public void enterAddress(String zipCode, String country){
        BrowserUtils.clickWithJS(driverInfoDropdown.get(1));
        driver.findElement(By.xpath("//div[text()='"+country+"']")).click();

        cityBox.sendKeys("Istanbul");
        stateBox.sendKeys("Maltepe");
        BrowserUtils.waitForVisibility(zipBox,1);
        zipBox.sendKeys(zipCode);
        BrowserUtils.waitForVisibility(fullAddressBox,1);
        fullAddressBox.sendKeys("İSTANBUL TÜRKİYE Maltepe Test");
    }

    public void enterAddressWithKurumsal(String zipCode, String country){

        BrowserUtils.waitFor(1);
        billingNameBox.sendKeys("Yolcu360 LTD");
        zipBox.sendKeys(zipCode);
        BrowserUtils.clickWithJS(driverInfoDropdown.get(1));
        driver.findElement(By.xpath("//div[text()='"+country+"']")).click();

        cityBox.sendKeys("Madrid");
        stateBox.sendKeys("Albacete");
        BrowserUtils.waitFor(1);
        billingTaxDivision.sendKeys("Erenler Vergi Dairesi");
        billingTaxIdentifier.sendKeys("9876543210");

        BrowserUtils.waitFor(1);
        fullAddressBox.sendKeys("İspanya Madrid Center Test");
    }


}
