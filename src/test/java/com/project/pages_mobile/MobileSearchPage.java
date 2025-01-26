package com.project.pages_mobile;


import com.project.pages_web.*;
import com.project.utilities.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


public class MobileSearchPage extends MainPage {
    WebDriver driver = Driver.get();
    SearchResultPage searchResultPage=new SearchResultPage();


    public MobileSearchPage() {PageFactory.initElements(driver, this);}



    public void clickMobileFindButton() {
        mobileFindButton.click();
        BrowserUtils.waitForClickability(firstCarForWait,15);
    }

}
