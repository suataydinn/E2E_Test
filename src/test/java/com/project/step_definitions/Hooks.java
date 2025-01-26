package com.project.step_definitions;

import com.project.utilities.BrowserUtils;
import com.project.utilities.ConfigurationReader;
import com.project.utilities.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.*;

public class Hooks {
    @Before
    public void setUp(Scenario scenario) {
        String scenarioTag = null;

        for (String tag : scenario.getSourceTagNames()) {
            if (tag.equals("@browserWeb") || tag.equals("@browserMobile")) {
                scenarioTag = tag;
                break;
            }
        }

        if (scenarioTag == null) {
            throw new IllegalStateException("No browser type specified for scenario");
        }

        Driver.setCurrentScenarioTag(scenarioTag);

        WebDriver driver = Driver.get();

        driver.manage().window().setSize(new Dimension(1440, 900));


        driver.get(ConfigurationReader.get("url"));
        BrowserUtils.waitForPageToLoad(30);
        BrowserUtils.scrollToSize(0, -500);

        try {
            WebElement popupClose = driver.findElement(By.cssSelector("div.gs_trigger_discount_popup_close_container"));
            popupClose.click();
        } catch (Exception ignored) {}
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) Driver.get()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "screenshot");
        }

        Driver.closeDriver();
    }

    @Before("@abc")
    public void setUpdb() {
        System.out.println("\tconnecting to database...");
    }

    @After("@abc")
    public void closeDb() {
        System.out.println("\tdisconnecting to database...");
    }
}