package com.project.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Driver {
    private Driver() {}

    private static final ThreadLocal<WebDriver> webDriverPool = new ThreadLocal<>();
    private static final ThreadLocal<WebDriver> mobileDriverPool = new ThreadLocal<>();
    private static final ThreadLocal<String> currentScenarioTag = new ThreadLocal<>();

    public static void setCurrentScenarioTag(String tag) {
        currentScenarioTag.set(tag);
    }

    public static String getCurrentScenarioTag() {
        return currentScenarioTag.get();
    }

    public static WebDriver get() {
        String scenarioTag = getCurrentScenarioTag();

        if (scenarioTag == null) {
            throw new IllegalStateException("Scenario tag not set");
        }

        return scenarioTag.equals("@browserMobile") ? getMobileDriver() : getWebDriver();
    }

    private static WebDriver getWebDriver() {
        if (webDriverPool.get() == null) {
            String browser = System.getProperty("browserWeb") != null
                    ? System.getProperty("browserWeb")
                    : ConfigurationReader.get("browserWeb");

            WebDriver driver = createWebDriver(browser);
            webDriverPool.set(driver);
        }
        return webDriverPool.get();
    }

    private static WebDriver getMobileDriver() {
        if (mobileDriverPool.get() == null) {
            String browser = System.getProperty("browserMobile") != null
                    ? System.getProperty("browserMobile")
                    : ConfigurationReader.get("browserMobile");

            WebDriver driver = createMobileDriver(browser);
            mobileDriverPool.set(driver);
        }
        return mobileDriverPool.get();
    }

    private static WebDriver createWebDriver(String browser) {
        switch (browser) {
            case "chrome":
                return createChromeDriver(false);
            case "chrome-headless":
                return createChromeDriver(true);
            case "firefox":
                return createFirefoxDriver(false);
            case "firefox-headless":
                return createFirefoxDriver(true);
            case "safari":
                return createSafariDriver();
            case "remote_chrome":
                return createRemoteChromeDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    private static WebDriver createMobileDriver(String browser) {
        switch (browser) {
            case "mobile":
                return createMobileChromeDriver(false);
            case "mobile-headless":
                return createMobileChromeDriver(true);
            default:
                throw new IllegalArgumentException("Unsupported mobile browser: " + browser);
        }
    }

    private static ChromeDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
        }

        return new ChromeDriver(options);
    }

    private static ChromeDriver createMobileChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent='Mozilla/5.0 (Linux; Android 13; Mi 14) AppleWebKit/537.36'");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
        }

        ChromeDriver driver = new ChromeDriver(options);

        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", headless ? 385 : 375);
        deviceMetrics.put("height", 700);
        deviceMetrics.put("deviceScaleFactor", 0);
        deviceMetrics.put("mobile", true);

        driver.executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceMetrics);

        return driver;
    }

    private static FirefoxDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        if (headless) {
            options.addArguments("--headless");
        }

        return new FirefoxDriver(options);
    }

    private static SafariDriver createSafariDriver() {
        if (!System.getProperty("os.name").toLowerCase().contains("mac")) {
            throw new WebDriverException("Safari only supports macOS");
        }
        WebDriverManager.getInstance(SafariDriver.class).setup();
        return new SafariDriver();
    }

    private static RemoteWebDriver createRemoteChromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("platform", Platform.ANY);
        try {
            return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Remote WebDriver setup failed", e);
        }
    }

    public static void closeDriver() {
        String scenarioTag = getCurrentScenarioTag();

        if (scenarioTag != null) {
            if (scenarioTag.equals("@browserMobile")) {
                if (mobileDriverPool.get() != null) {
                    mobileDriverPool.get().quit();
                    mobileDriverPool.remove();
                }
            } else {
                if (webDriverPool.get() != null) {
                    webDriverPool.get().quit();
                    webDriverPool.remove();
                }
            }
        }
    }
}