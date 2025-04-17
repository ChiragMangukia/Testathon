package io.github.chiragmangukia.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport {

    public ExtentReports initReport() {
        String reportName = "TestReport"+ System.currentTimeMillis() + ".html";

        ExtentReports extent = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter("./Reports/" + reportName);
        reporter.config().setReportName("Automation Test Results");
        reporter.config().setDocumentTitle("Test Automation");
        extent.attachReporter(reporter);
        extent.setSystemInfo("System", System.getProperty("os.name"));
        extent.setSystemInfo("Author", "Chirag Mangukia");

        reporter.config().setTheme(Theme.STANDARD);
        return extent;
    }
}
