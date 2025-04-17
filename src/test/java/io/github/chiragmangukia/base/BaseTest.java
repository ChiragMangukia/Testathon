package io.github.chiragmangukia.base;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;

public class BaseTest extends Base {

    @Parameters({"browser"})
    @BeforeMethod
    public void testSetup(@Optional("chrome") String browser) throws MalformedURLException {
        setup(browser, System.getenv("HYPEREXECUTE_PLATFORM"));
    }

    @AfterMethod
    public void tearDownSetup() {
        String sessionId = ((RemoteWebDriver)driver).getSessionId().toString();
        tearDown();
        downloadMetadata(sessionId);
    }

}
