package io.github.chiragmangukia.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.github.chiragmangukia.utils.ExtentReport;
import io.github.chiragmangukia.utils.Utilities;
import org.testng.ITestListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

public class TestNGListener implements ITestListener {

    ExtentReport extentReport;
    ExtentReports extent;
    ExtentTest logger;
    Utilities util = new Utilities();

    @Override
    public void onTestStart(ITestResult result) {
        String qualifiedName = result.getMethod().getQualifiedName();
        int last = qualifiedName.lastIndexOf(".");
        int mid = qualifiedName.substring(0, last).lastIndexOf(".");
        String className = qualifiedName.substring(mid + 1, last);

        logger = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
        logger.assignCategory(result.getTestContext().getSuite().getName());
        logger.assignCategory(className);
        logger.getModel().setStartTime(Utilities.getTime(result.getStartMillis()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.log(Status.PASS, MarkupHelper.createLabel("Test passed", ExtentColor.GREEN));
        logger.getModel().setEndTime(Utilities.getTime(result.getEndMillis()));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String file = util.takeScreenshot();
        logger.log(Status.FAIL, MarkupHelper.createLabel("Test Failed", ExtentColor.RED));
        try {
            logger.fail(result.getThrowable().getMessage(), MediaEntityBuilder
                    .createScreenCaptureFromPath(System.getProperty("user.dir") + "/Screenshots/" + file).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.getModel().setEndTime(Utilities.getTime(result.getEndMillis()));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.log(Status.SKIP, MarkupHelper.createLabel("Test skipped", ExtentColor.ORANGE));
        logger.getModel().setEndTime(Utilities.getTime(result.getEndMillis()));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        String file = util.takeScreenshot();
        logger.log(Status.FAIL, MarkupHelper.createLabel("Test failed with timeout", ExtentColor.RED));
        try {
            logger.fail(result.getThrowable().getMessage(), MediaEntityBuilder
                    .createScreenCaptureFromPath(System.getProperty("user.dir") + "/Screenshots/" + file).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart(ITestContext context) {
        extentReport = new ExtentReport();
        extent = extentReport.initReport();
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

}
