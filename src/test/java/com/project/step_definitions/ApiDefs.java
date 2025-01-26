package com.project.step_definitions;

import com.project.pages_web.CarDetailPage;
import com.project.pages_web.SearchResultPage;
import com.project.utilities.BrowserUtils;
import com.project.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import com.project.pages_api.ApiPage;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;


public class ApiDefs {

    ApiPage apiPage = new ApiPage();
    WebDriver driver = Driver.get();
    String uiProvision;

    @And("verify provision value same BE and UI")
    public void verifyProvisionValueSameBEAndUI() {
        String resDetailProvision=driver.findElement(By.xpath("//*[@cms-key='filter_provision']/..")).getText();
        Assert.assertEquals(SearchResultPage.extractNumbers(resDetailProvision),uiProvision);
    }

    @Then("Price sended from BE should be same Car detail price")
    public void priceSendedFromBEShouldBeSameCarDetailPrice() {
        int totalPriceAllText = Integer.parseInt(new CarDetailPage().totalPrice.getText().substring(1).replace(".","").replace(",",""));

        int BEtotalprice= Integer.parseInt(apiPage.devToolsRequestCapture.getResponseValue("pricing.display.total.amount.amount"));
        int BEfeeprice= Integer.parseInt(apiPage.devToolsRequestCapture.getResponseValue("pricing.display.fee.amount.amount"));

        System.out.println("totalPriceAllText = " + totalPriceAllText);

        System.out.println("BEtotalprice = " + BEtotalprice);
        System.out.println("BEfeeprice = " + BEfeeprice);

        Assert.assertEquals(totalPriceAllText,BEtotalprice);
        Assert.assertEquals(BEfeeprice,BEtotalprice);

    }

    @Then("get payload information and compare point information")
    public void getPayloadInformationAndComparePointInformation() {
        apiPage.getAllPayloadAndVerifyForPoint();
    }

    @Then("get payload for order api on car detail")
    public void getPayloadForOrderApiOnCarDetail() {
        apiPage.getAllPayloadInfoForOrderOnCarDetail();
    }

    @Then("get payload for item")
    public void getPayloadForItem() {
        apiPage.getPayloadKeyValue("code");
        apiPage.getPayloadKeyValue("quantity");
        apiPage.getPayloadKeyValue("searchID");
    }

    @Then("check payment info")
    public void checkPaymentInfo() {
        apiPage.getAllPayloadInfoForPayment();
    }

    @And("check bin Number response")
    public void checkBinNumber() {
        apiPage.getResponseInfoForPayment();
    }

    @Then("Tax identifier should be {string}")
    public void taxIdentifierShouldBe(String identifier) {
        BrowserUtils.waitFor(2);
        apiPage.checkTaxIdentifier(identifier);
    }

    @Then("check provision fee BE and UI")
    public void checkProvisionFeeBEAndUI() {

        String value=apiPage.devToolsRequestCapture.getResponseValue("details.car.rules.0.details.deposit.amount");
        System.out.println("value = " + value);

        String uiprovisionText=driver.findElement(By.xpath("(//div[@class='flex-1 whitespace-nowrap'])[1]")).getText();
        uiProvision= SearchResultPage.extractNumbers(uiprovisionText);
        System.out.println("uiProvision = " + uiProvision);
        Assert.assertEquals(value,uiProvision);

    }

    @And("Start devtools Listeners {string} method for response {string}")
    public void startDevtoolsListenersMethodForResponse(String method, String url) {
        apiPage.devToolsRequestCapture = apiPage.new DevToolsRequestCapture((ChromeDriver)driver);
        apiPage.devToolsRequestCapture.captureNetworkActivity(url, method);
    }
}
