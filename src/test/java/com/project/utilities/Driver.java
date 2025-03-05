package com.project.utilities;

import com.project.step_definitions.Hooks;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.edge.EdgeDriver;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class Driver {
    private Driver() {}

    private static final InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();

    public static WebDriver get() {
        if (Hooks.publicScenario == null) {
            throw new IllegalStateException("No scenario tag found in Hooks.");
        }

        if (driverPool.get() == null) {
            String browser;
            if (Hooks.publicScenario.equals("@browserWeb")) {
                browser = System.getProperty("browserWeb", ConfigurationReader.get("browserWeb"));
            } else if (Hooks.publicScenario.equals("@browserMobile")) {
                browser = System.getProperty("browserMobile", ConfigurationReader.get("browserMobile"));
            } else {
                throw new IllegalStateException("Unknown browser type: " + Hooks.publicScenario);
            }

            switch (browser) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-notifications");
                    driverPool.set(new ChromeDriver(chromeOptions));
                    break;

                case "chrome-headless":
                    ChromeOptions headlessChromeOptions = new ChromeOptions();
                    headlessChromeOptions.addArguments("--headless=new");
                    headlessChromeOptions.addArguments("--disable-gpu");
                    headlessChromeOptions.addArguments("--disable-notifications");
                    headlessChromeOptions.addArguments("--remote-allow-origins=*");
                    driverPool.set(new ChromeDriver(headlessChromeOptions));
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                    driverPool.set(new FirefoxDriver(firefoxOptions));
                    break;

                case "firefox-headless":
                    FirefoxOptions headlessFirefoxOptions = new FirefoxOptions();
                    headlessFirefoxOptions.addArguments("--headless=new");
                    headlessFirefoxOptions.addArguments("--disable-gpu");
                    driverPool.set(new FirefoxDriver(headlessFirefoxOptions));
                    break;

                case "edge":
                    driverPool.set(new EdgeDriver());
                    break;

                case "remote_chrome":
                    ChromeOptions remoteOptions = new ChromeOptions();
                    remoteOptions.setCapability("platform", Platform.ANY);
                    try {
                        driverPool.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), remoteOptions));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;

                case "mobile":
                    ChromeOptions mobileOptions = new ChromeOptions();
                    mobileOptions.addArguments("user-agent='Mozilla/5.0 (Linux; Android 13; Mi 14) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Mobile Safari/537.36'");
                    mobileOptions.addArguments("--remote-allow-origins=*");
                    mobileOptions.addArguments("--disable-notifications");
                    driverPool.set(new ChromeDriver(mobileOptions));

                    Map<String, Object> mobileMetrics = new HashMap<>();
                    mobileMetrics.put("width", 375);
                    mobileMetrics.put("height", 700);
                    mobileMetrics.put("deviceScaleFactor", 0);
                    mobileMetrics.put("mobile", true);
                    ((ChromeDriver) driverPool.get()).executeCdpCommand("Emulation.setDeviceMetricsOverride", mobileMetrics);
                    break;

                case "mobile-headless":
                    ChromeOptions mobileHeadlessOptions = new ChromeOptions();
                    mobileHeadlessOptions.addArguments("user-agent='Mozilla/5.0 (Linux; Android 13.0; Mi 14.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.6167.143 Mobile Safari/537.36'");
                    mobileHeadlessOptions.addArguments("--headless=new");
                    mobileHeadlessOptions.addArguments("--disable-notifications");
                    driverPool.set(new ChromeDriver(mobileHeadlessOptions));

                    Map<String, Object> mobileHeadlessMetrics = new HashMap<>();
                    mobileHeadlessMetrics.put("width", 385);
                    mobileHeadlessMetrics.put("height", 700);
                    mobileHeadlessMetrics.put("deviceScaleFactor", 0);
                    mobileHeadlessMetrics.put("mobile", true);
                    ((ChromeDriver) driverPool.get()).executeCdpCommand("Emulation.setDeviceMetricsOverride", mobileHeadlessMetrics);
                    break;

                default:
                    throw new IllegalStateException("Unsupported browser: " + browser);
            }
        }

        return driverPool.get();
    }

    public static void closeDriver() {
        if (driverPool.get() != null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }
}
