package com.project.pages_web;

import com.project.utilities.BrowserUtils;
import com.project.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.List;


public abstract class BasePage {

    @FindBy(xpath = "//div[@data-test-id='nonPremium-logo']")
    public WebElement nonPremiumLogo;

    WebDriver driver = Driver.get();


    public void navigateToModule(String tab, String subTab, String module) {

        String tabLocator = "//span[text()='" + tab + "']";
        String subTabLocator = "//a[text()='" + subTab + "']";
        String moduleLocator = "//*[contains(@class, 'ChildMenuItems')]//a[text()='" + module + "']";


        WebElement moduleElement;

        try {
            BrowserUtils.waitFor(1);
            BrowserUtils.hover(driver.findElement(By.xpath(tabLocator)));//for stale element
            BrowserUtils.waitFor(1);
            BrowserUtils.hover(driver.findElement(By.xpath(subTabLocator)));
            BrowserUtils.waitFor(1);
        } catch (Exception e) {
            BrowserUtils.hover(nonPremiumLogo);
            BrowserUtils.waitFor(1);
            BrowserUtils.hover(driver.findElement(By.xpath(tabLocator)));
            BrowserUtils.waitFor(1);
            BrowserUtils.hover(driver.findElement(By.xpath(subTabLocator)));
            BrowserUtils.waitFor(1);
        }


        try {
            BrowserUtils.waitForPresenceOfElement(By.xpath(moduleLocator), Duration.ofSeconds(5));
            BrowserUtils.waitForVisibility(By.xpath(moduleLocator), 5);
            moduleElement = Driver.get().findElement(By.xpath(moduleLocator));
            BrowserUtils.scrollToElement(moduleElement);
            moduleElement.click();
        } catch (Exception e) {
            System.out.println("3");
            BrowserUtils.clickWithTimeOut(Driver.get().findElement(By.xpath(moduleLocator)),  5);

        }

        Assert.assertTrue("module contains url", driver.getCurrentUrl().contains(module.toLowerCase()));
    }

}

