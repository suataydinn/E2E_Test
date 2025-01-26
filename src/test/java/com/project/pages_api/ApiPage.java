package com.project.pages_api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.utilities.Driver;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v128.network.Network;
import org.openqa.selenium.devtools.v128.network.model.Request;
import org.openqa.selenium.devtools.v128.network.model.RequestId;
import org.openqa.selenium.devtools.v128.network.model.ResponseReceived;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ApiPage {
    private static final Logger logger = LoggerFactory.getLogger(ApiPage.class);
    private WebDriver driver = Driver.get();
    private String requestBody2 = "";
    private String responseBody = "";
    public DevToolsRequestCapture devToolsRequestCapture;

    public class DevToolsRequestCapture {
        private ChromeDriver driver;
        private DevTools devTools;
        private String requestBody;
        private String responseBody;
        private static final Logger logger = LoggerFactory.getLogger(DevToolsRequestCapture.class);

        public DevToolsRequestCapture(ChromeDriver driver) {
            this.driver = driver;
            this.devTools = driver.getDevTools();
        }

        public void captureNetworkActivity(String url, String method) {
            devTools.createSession();
            devTools.send(Network.enable(Optional.of(1000000), Optional.empty(), Optional.empty()));

            devTools.addListener(Network.requestWillBeSent(), request -> {
                Request request1 = request.getRequest();
                if (request1.getMethod().equals(method) && request1.getUrl().contains(url)) {
                    this.requestBody = request.getRequest().getPostData().orElse("");
                    requestBody2 = this.requestBody;
                }
            });

            devTools.addListener(Network.responseReceived(), response -> {
                if (response.getResponse().getUrl().contains(url)) {
                    RequestId requestId = response.getRequestId();
                    this.responseBody = devTools.send(Network.getResponseBody(requestId)).getBody();
                    responseBody = this.responseBody;
                }
            });
        }

        public String getResponseValue(String jsonPath) {
            try {
                if (responseBody == null || responseBody.isEmpty()) {
                    logger.warn("Response body boş");
                    return null;
                }
                JsonElement jsonElement = JsonParser.parseString(responseBody);
                JsonElement result = navigateJsonPath(jsonElement, jsonPath);
                return result != null ? result.getAsString() : null;
            } catch (Exception e) {
                logger.error("Response değeri alınırken hata", e);
                return null;
            } finally {
                if (devTools != null) {
                    try {
                        devTools.close();
                    } catch (Exception e) {
                        logger.error("DevTools kapatılırken hata", e);
                    }
                }
            }
        }

        private JsonElement navigateJsonPath(JsonElement jsonElement, String path) {
            return Arrays.stream(path.split("\\."))
                    .reduce(jsonElement, this::navigateJsonElement, (a, b) -> b);
        }

        private JsonElement navigateJsonElement(JsonElement element, String key) {
            try {
                if (element.isJsonObject()) {
                    return element.getAsJsonObject().get(key);
                } else if (element.isJsonArray()) {
                    int index = Integer.parseInt(key);
                    return element.getAsJsonArray().get(index);
                }
                return null;
            } catch (Exception e) {
                logger.warn("JSON gezinme hatası: {}", e.getMessage());
                return null;
            }
        }
    }

    public String getPayloadKeyValue(String key) {
        try {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(requestBody2, JsonObject.class);
            String value = jsonObject.get(key).getAsString();
            logger.info("{} değeri = {}", key, value);
            return value;
        } catch (Exception e) {
            logger.error("Payload değeri alınırken hata", e);
            return null;
        }
    }


    public void getAllPayloadAndVerifyForPoint() {
        Assert.assertEquals("25", getPayloadKeyValue("age"));
        Assert.assertEquals("TR", getPayloadKeyValue("country"));
        Assert.assertEquals("TRY", getPayloadKeyValue("currency"));
        Assert.assertEquals("tr", getPayloadKeyValue("language"));
        Assert.assertEquals("6836", getPayloadKeyValue("organizationId"));

        String checkInTime = getPayloadKeyValue("checkInDateTime");
        String checkOutTime = getPayloadKeyValue("checkOutDateTime");

        String checkInTimeText = LocalDate.now().plusDays(1).toString();
        String checkOutTimeText = LocalDate.now().plusDays(4).toString();

        Assert.assertEquals(checkInTimeText, checkInTime.substring(0, checkInTime.indexOf("T")));
        Assert.assertEquals(checkOutTimeText, checkOutTime.substring(0, checkOutTime.indexOf("T")));
    }

    public void getAllPayloadInfoForOrderOnCarDetail() {
        Assert.assertEquals("tr", getPayloadKeyValue("language"));
        Assert.assertEquals("6836", getPayloadKeyValue("organizationId"));
        Assert.assertEquals("creditCard", getPayloadKeyValue("paymentType"));
    }

    public void getAllPayloadInfoForPayment() {
        Assert.assertEquals("4256690000000001", getPayloadKeyValue("binNumber"));
        Assert.assertEquals("tr", getPayloadKeyValue("locale"));
        Assert.assertEquals("yolcu360", getPayloadKeyValue("superAppPart"));
    }

    public void getResponseInfoForPayment() {
        Assert.assertEquals("42566900", devToolsRequestCapture.getResponseValue("binNumber"));
        Assert.assertEquals("CREDIT", devToolsRequestCapture.getResponseValue("cardType"));
        Assert.assertEquals("true", devToolsRequestCapture.getResponseValue("shouldForceTo3D"));
        Assert.assertEquals("TRY", devToolsRequestCapture.getResponseValue("supportedCurrencies.0"));
    }

    public void checkTaxIdentifier(String identifier) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(requestBody2, JsonObject.class);

        if (jsonObject.has("taxIdentifier")) {
            String taxIdentifier = jsonObject.get("taxIdentifier").getAsString();
            Assert.assertEquals("tax identifier should be same", taxIdentifier, identifier);
        }
        String responseIdentifier = devToolsRequestCapture.getResponseValue("taxIdentifier");
        Assert.assertEquals("tax identifier should be same", responseIdentifier, identifier);
    }

    public String[][] getReservationDetail(String idText) {
        return new String[][]{};
    }

    public Map<String, String> getPaymentDetail(String id) {
        return new HashMap<>();
    }
}