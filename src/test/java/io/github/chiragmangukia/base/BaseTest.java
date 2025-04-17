package io.github.chiragmangukia.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;

public class BaseTest extends Base {

    @Parameters({"browser", "platform"})
    @BeforeMethod
    public void testSetup(String browser, String platform) throws MalformedURLException {
        setup(browser, platform);
    }

    @AfterMethod
    public void tearDownSetup() {
        tearDown();
    }

}
