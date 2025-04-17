package io.github.chiragmangukia.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class Base {

    public WebDriver driver;

    public static String hubURL = "https://hub.lambdatest.com/wd/hub";

    public static final String USERNAME = System.getenv("LT_USERNAME");
    public static final String ACCESS_KEY = System.getenv("LT_ACCESS_KEY");
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

    public static void downloadMetadata(String sessionId) {
        String username = System.getenv("LT_USERNAME");
        String accessKey = System.getenv("LT_PASSWORD");

        if (username == null || accessKey == null) {
            System.err.println("LT_USERNAME or LT_ACCESS_KEY is not set.");
            return;
        }

        String auth = Base64.getEncoder().encodeToString((username + ":" + accessKey).getBytes());

        String url = "https://api.lambdatest.com/automation/api/v1/sessions/" + sessionId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Basic " + auth)
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String outputDir = "artifacts";
                Files.createDirectories(Paths.get(outputDir));

                Path outputPath = Paths.get(outputDir, "session-" + sessionId + "-metadata.json");
                Files.writeString(outputPath, response.body());

                System.out.println("Metadata downloaded for session: " + sessionId);
            } else {
                System.err.println("Failed to fetch metadata. HTTP " + response.statusCode());
                System.err.println(response.body());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
