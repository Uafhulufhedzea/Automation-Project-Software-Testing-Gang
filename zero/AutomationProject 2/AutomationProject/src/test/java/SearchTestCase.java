import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.io.File;
import java.io.IOException;





public class SearchTestCase {
    ExtentReports extent = new ExtentReports();
    ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentReports.html");

    WebDriver driver;

    @BeforeTest
    public void setUp() {
      spark.config().setTheme(Theme.DARK);
      spark.config().setDocumentTitle("My Report");
      extent.attachReporter(spark);
      WebDriverManager.chromedriver().setup();
      driver = new ChromeDriver();




    }
    @AfterTest
    public void tearDown() {
      extent.flush();
      driver.quit();


    }
    @Test
    public void TestCase1() {

        ExtentTest test = extent.createTest("Verify page titles").assignAuthor("Rambuda").
                assignCategory("Functional Testing").assignDevice("Windows");
        test.info("Capturing the page title");
        driver.get("https://www.saucedemo.com/");
        String pageTitle = driver.getTitle();
        if (pageTitle.equals("Swag Labs")) {
            test.info("Capture page title as : " + pageTitle);
            test.log(Status.PASS,"Page title is verified : Title Captured " + pageTitle);

        } else {
            test.log(Status.FAIL,"Page title is not matched with expected results : " + pageTitle);

        }


    }

   @Test
    public void TestCase5() throws IOException {
        ExtentTest test = extent.createTest("Verify the Continue button").assignAuthor("Rambuda").
                assignCategory("Functional test case").assignDevice("Windows");
        test.info("Verify if Continue button is enabled");

        try {
            driver.findElement(By.xpath("//*[@id=\"continue\"]")).isEnabled();
            test.pass("The Continue button is enabled");
        } catch (Exception e) {
            test.fail("The Continue button is not enabled " + e.getMessage());
            //test.addScreenCaptureFromPath(capturescreenshot(driver));
        }
    }

    @Test
    public void TestCase2() throws IOException{

        ExtentTest test = extent.createTest("Verify the Login button").assignAuthor("Rambuda").
                assignCategory("Functional test case").assignDevice("Windows");
        test.info("Verify if Login button is enabled");

        try {
            driver.findElement(By.xpath("//*[@id=\"login-button\"]")).isEnabled();
            test.pass("The Login Button is Enabled");
        } catch (Exception e) {
            test.fail("The Login Button is not enabled" +e.getMessage());
        }
    }

    @Test
    public void TestCase3() throws IOException{

        ExtentTest test = extent.createTest("Verify Item 1 in products page").assignAuthor("Rambuda").
                assignCategory("Functional test case").assignDevice("Windows");
        test.info("Verify if item 1 in products page is displayed");

        try {
            driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]")).isEnabled();
            test.pass("Item 1 of the products page is displayed");
        } catch (Exception e) {
            test.fail("Item 1 of the products page is not displayed" +e.getMessage());
            //test.addScreenCaptureFromPath(capturescreenshot(driver));
        }
    }
    @Test
    public void TestCase4() throws IOException {
        ExtentTest test = extent.createTest("Verify the Checkout button is enabled").assignAuthor("Rambuda").
                assignCategory("Functional test case").assignDevice("Windows");
        test.info("Verify if Checkout button is enabled");

        try {
            driver.findElement(By.xpath("//*[@id=\"checkout\"]")).isEnabled();
            test.pass("The Checkout button is enabled");
        } catch (Exception e) {
            test.fail("The checkout button is not enabled" + e.getMessage());
            test.addScreenCaptureFromPath(capturescreenshot(driver));
        }
    }



        @Test
        public void TestCase6() throws IOException {
            ExtentTest test = extent.createTest("Verify First Name field").assignAuthor("Rambuda").
                    assignCategory("Functional test case").assignDevice("Windows");
            test.info("Verify if First name field is displayed");

            try {
                driver.findElement(By.xpath("//*[@id=\"first-name\"]")).isDisplayed();
                test.pass("First name field is displayed");
            } catch (Exception e) {
                test.fail("First name field is not displayed" + e.getMessage());
                test.addScreenCaptureFromPath(capturescreenshot(driver));
            }
        }

    @Test
    public void TestCase7() throws IOException {
        ExtentTest test = extent.createTest("Verify the Cancel Button").assignAuthor("Rambuda").
                assignCategory("Functional test case").assignDevice("Windows");
        test.info("Verify if Cancel button is displayed");

        try {
            driver.findElement(By.xpath("//*[@id=\"cancel\"]")).isDisplayed();
            test.pass("The Cancel button is displayed");
        } catch (Exception e) {
            test.fail("The Cancel button is not displayed" + e.getMessage());
            test.addScreenCaptureFromPath(capturescreenshot(driver));
        }
    }

    @Test
    public void TestCase8() throws IOException {
        ExtentTest test = extent.createTest("Verify the Back To Home Button").assignAuthor("Rambuda").
                assignCategory("Functional test case").assignDevice("Windows");
        test.info("Verify if Back To Home button is displayed");

        try {
            driver.findElement(By.xpath("//*[@id=\"back-to-products\"]")).isEnabled();
            test.pass("The Back To Home Button is enabled");
        } catch (Exception e) {
            test.fail("The Back To Home button is not displayed" + e.getMessage());
            test.addScreenCaptureFromPath(capturescreenshot(driver));
        }
    }

    public static String capturescreenshot(WebDriver driver) throws IOException{

        File srcfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destinationFilePath = new File("src/../images/screenshot" + System.currentTimeMillis() + ".png");
        String absolutepathlocation = destinationFilePath.getAbsolutePath();
        FileUtils.copyFile(srcfile, destinationFilePath);
        return absolutepathlocation;


    }

    }







