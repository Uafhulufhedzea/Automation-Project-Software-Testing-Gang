import Utilities.ex;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.aventstack.extentreports.ExtentReports;


import static java.sql.DriverManager.getDriver;


public class Listeners extends Selenium implements ITestListener {

   /*
    ExtentTest test;
    ExtentReports extent = ex.getReportObject();
    WebDriver driver;

    @Override
    public void onTestStart(ITestResult result) {
       //ITestListener.super.onTestStart(result);

        test = extent.createTest(result.getMethod().getMethodName());

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        //ITestListener.super.onTestSuccess(result);
        test.log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            // Report the test failure
            test.fail(result.getThrowable());

            // Initialize WebDriver
             driver = getDriver();

            // Capture screenshot
            String filePath = getScreenshot(result.getMethod().getMethodName());

            // Add screenshot to the test report
            test.addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());

            // Optionally, quit the WebDriver instance
            driver.quit();

        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logging framework instead
        }
    }




}

    @Override
    public void onTestSkipped(ITestResult result) {
        //ITestListener.super.onTestSkipped(result);
        //test.log(Status.SKIP, "Test Skipped");
        test.log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        //ITestListener.super.onFinish(context);
        extent.flush();
    }

    */




    private ExtentReports extent;
    private ExtentTest test;


    @Override
    public void onStart(ITestContext context) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportName = "ExtentReport_" + timeStamp + ".html";
        //ex htmlReporter = new ex();
        extent = new ExtentReports();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter ("reports/reports.html");
        extent.attachReporter(sparkReporter);
        test = extent.createTest("Test 1","First tes");
        test.log(Status.FAIL, "Test Case faked:");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Create a new test instance for each test failure


        // Log failure details

        test.log(Status.FAIL, "Test Case Failed: " + result.getThrowable());

        // Capture screenshot and add to report
        WebDriver driver = ((WebDriver)result.getTestContext().getAttribute("driver"));
        if (driver != null) {
            try {
                String screenshotPath = takeScreenshot(driver, result.getMethod().getMethodName());
                test.fail("Screenshot below: ", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String takeScreenshot(WebDriver driver, String methodName) throws IOException {
        // Capture screenshot as bytes
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String screenshotPath = System.getProperty("user.dir") + "/test-output/screenshots/" + methodName + "_" + timeStamp + ".png";

        // Copy screenshot to desired location
        Path targetPath = Paths.get(screenshotPath);
        Files.createDirectories(targetPath.getParent());
        Files.copy(screenshotFile.toPath(), targetPath);

        return screenshotPath;
    }

}