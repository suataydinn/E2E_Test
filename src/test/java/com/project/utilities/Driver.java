package com.project.utilities;

import com.project.step_definitions.Hooks;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Driver {
    private Driver() {}

    private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();
    private static InheritableThreadLocal<ChromeDriver> driverPool1 = new InheritableThreadLocal<>();

    public static WebDriver get() {
        if (new Hooks().publicScenario.equals("@browserWeb")) {
            if (driverPool.get() == null) {
                String browser = System.getProperty("browserWeb") != null ? browser = System.getProperty("browserWeb") : ConfigurationReader.get("browserWeb");
                switch (browser) {
                    case "chrome":
                        ChromeOptions options = new ChromeOptions();
                        options.addArguments("--disable-web-security");
                     //   options.addArguments("--user-data-dir=/tmp/test-browser");

                        options.addArguments("--remote-allow-origins=*");
                        options.addArguments("--disable-notifications");
                        // Amazon için User-Agent'i ayarla (burada örnek bir Chrome user-agent kullanılıyor)
                        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.6778.140 Safari/537.36");

                        driverPool.set(new ChromeDriver(options));
                        break;

                    case "chrome-headless":
                        ChromeOptions options1 = new ChromeOptions();

                        options1.addArguments("--headless=new");
                        options1.addArguments("--disable-gpu");
                        options1.addArguments("--disable-notifications");
                        options1.addArguments("--remote-allow-origins=*");

                        // Amazon için User-Agent'i ayarla (burada örnek bir Chrome user-agent kullanılıyor)
                        options1.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36");


                        driverPool.set(new ChromeDriver(options1));

                        break;

                    case "firefox":
                        FirefoxOptions options2 = new FirefoxOptions();
                        options2.setProfile(new FirefoxProfile());
                        options2.addPreference("dom.webnotifications.enabled", false);
                        System.setProperty("webdriver.firefox.driver", "path/to/driver/exe");
                        WebDriverManager.firefoxdriver().setup();
                        driverPool.set(new FirefoxDriver(options2));
                        break;

                    case "firefox-headless":
                        FirefoxOptions options3 = new FirefoxOptions();
                        WebDriverManager.firefoxdriver().setup();
                        options3.addArguments("--headless=new");
                        options3.addArguments("--disable-gpu");
                        driverPool.set(new FirefoxDriver(options3));
                        break;

                    case "ie":
                        if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                            throw new WebDriverException("Your OS doesn't support Internet Explorer");
                        WebDriverManager.iedriver().setup();
                        driverPool.set(new InternetExplorerDriver());
                        break;

                    case "edge":
                        if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                            throw new WebDriverException("Your OS doesn't support Edge");
                        WebDriverManager.edgedriver().setup();
                        driverPool.set(new EdgeDriver());
                        break;

                    case "safari":
                        if (!System.getProperty("os.name").toLowerCase().contains("mac"))
                            throw new WebDriverException("Your OS doesn't support Safari");
                        WebDriverManager.getInstance(SafariDriver.class).setup();
                        driverPool.set(new SafariDriver());
                        break;

                    case "remote_chrome":
                        ChromeOptions chromeOptions = new ChromeOptions();
                        chromeOptions.setCapability("platform", Platform.ANY);
                        try {
                            driverPool.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
            return driverPool.get();
        } else if (new Hooks().publicScenario.equals("@browserMobile")) {

            if (driverPool1.get() == null) {
                String browser = System.getProperty("browserMobile") != null ? browser = System.getProperty("browserMobile") : ConfigurationReader.get("browserMobile");
                switch (browser) {
                    case "mobile":
                        WebDriverManager.chromedriver().setup();
                        ChromeOptions options = new ChromeOptions();
                        //options.addArguments("user-agent='Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Mobile Safari/537.36'");
                        options.addArguments("user-agent='Mozilla/5.0 (Linux; Android 13; Mi 14) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Mobile Safari/537.36'");

                        options.addArguments("--remote-allow-origins=*");
                        options.addArguments("--disable-notifications");
                        DesiredCapabilities cp = new DesiredCapabilities();
                        cp.setCapability(ChromeOptions.CAPABILITY, options);
                        options.merge(cp);
                        driverPool1.set(new ChromeDriver(options));
                        //   DevTools devTools = driverPool1.get().getDevTools();
                        //  devTools.createSession();

                        Map<String, Object> deviceMetrics = new HashMap<>();
                        deviceMetrics.put("width", 375);
                        deviceMetrics.put("height", 700);
                        deviceMetrics.put("deviceScaleFactor", 0);
                        deviceMetrics.put("mobile", true);

                        driverPool1.get().executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceMetrics);
                        break;

                    case "mobile-headless":
                        WebDriverManager.chromedriver().setup();
                        WebDriverManager.chromedriver().clearDriverCache();

                        ChromeOptions options1 = new ChromeOptions();
                        // options1.addArguments("user-agent='Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Mobile Safari/537.36'");
                        options1.addArguments("user-agent='Mozilla/5.0 (Linux; Android 13.0; Mi 14.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.6167.143 Mobile Safari/537.36'");

                        options1.addArguments("--remote-allow-origins=*");
                        options1.addArguments("--headless=new");
                        //      options1.addArguments("--enable-gpu");
                        options1.addArguments("--disable-notifications");
                        //       options1.addArguments("--remote-allow-origins=*");
                        DesiredCapabilities cp1 = new DesiredCapabilities();
                        cp1.setCapability(ChromeOptions.CAPABILITY, options1);
                        options1.merge(cp1);
                        driverPool1.set(new ChromeDriver(options1));
                        //   DevTools devTools1 = driverPool1.get().getDevTools();
                        //  devTools1.createSession();

                        Map<String, Object> deviceMetrics1 = new HashMap<>();
                        deviceMetrics1.put("width", 385);
                        deviceMetrics1.put("height", 700);
                        deviceMetrics1.put("deviceScaleFactor", 0);
                        deviceMetrics1.put("mobile", true);

                        driverPool1.get().executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceMetrics1);
                        break;
                }
            }
            return driverPool1.get();

        }


        return null;

    }
    public static void closeDriver() {
        if (Hooks.publicScenario.equals("@browserMobile")) {
            if (driverPool1.get() != null) {
                driverPool1.get().quit();
                driverPool1.remove();
            }
        } else if (Hooks.publicScenario.equals("@browserWeb")) {
            if (driverPool != null) {
                driverPool.get().quit();
                driverPool.remove();
            }
        }
    }
}