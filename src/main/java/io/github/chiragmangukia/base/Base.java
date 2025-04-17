package io.github.chiragmangukia.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Base {

    public WebDriver driver;

    public static String hubURL = "https://hub.lambdatest.com/wd/hub";

    public static final String USERNAME = "chirag.mangukiya";
    public static final String ACCESS_KEY = "LT_RSyla46oQr7ppkGwp6vrRWjpZeYzMF66yUGItURe4swDliC";
    public static final String GRID_URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@hub.lambdatest.com/wd/hub";

    public void setup(String browser, String platform) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", browser);
        caps.setCapability("platformName", platform);
        caps.setCapability("browserVersion", "latest");

        Map<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("user", USERNAME);
        ltOptions.put("accessKey", ACCESS_KEY);
        ltOptions.put("build", "E-Cart-Test");
        ltOptions.put("project", "QT-Testathon");
        ltOptions.put("name", "AddToCartAndVerify");
        ltOptions.put("network", true);
        ltOptions.put("console", true);
        ltOptions.put("visual", true);
        ltOptions.put("video", true);
        caps.setCapability("LT:Options", ltOptions);
        driver = new RemoteWebDriver(new URL(hubURL), caps);
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
