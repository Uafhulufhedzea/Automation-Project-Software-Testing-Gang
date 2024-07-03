package Reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

public class ExtentReport {




    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeTest
    public void setUp() {
        // Initialize ExtentReports and attach the Spark reporter
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);

        // Configuration settings
        spark.config().setDocumentTitle("Automation Test Results");
        spark.config().setReportName("My Report");
    }

    @BeforeMethod
    public void setUpTest() {
        // Set up WebDriver
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        driver = new ChromeDriver();
        driver=new FirefoxDriver();


    }

    @Test
    public void testLogin() {
        // Start test within ExtentReports
        test = extent.createTest("Login Test", "Verify user login functionality");

        // Perform login
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Validate login success
        String pageTitle = driver.getTitle();
        if (pageTitle.equals("Swag Labs")) {
            test.info("Page title captured: " + pageTitle);
            test.pass("Login successful - Page title verified");
        } else {
            test.fail("Login failed - Page title not as expected: " + pageTitle);
        }
    }
    @Test(priority = 2)
    public void testAddToCart() {
        // Start test within ExtentReports
        test = extent.createTest("Add to Cart Test", "Verify adding items to the cart");

        // Add products to cart
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-onesie")).click();
        driver.findElement(By.id("shopping_cart_container")).click();

        // Validate products added to cart
        String cartTitle = driver.findElement(By.className("title")).getText();
        if (cartTitle.equals("YOUR CART")) {
            test.info("Shopping cart title captured: " + cartTitle);
            test.pass("Products successfully added to cart - Cart title verified");
        } else {
            test.fail("Failed to add products to cart - Cart title not as expected: " + cartTitle);
        }
    }

    @Test(priority = 3)
    public void testCheckout() {
        // Start test within ExtentReports
        test = extent.createTest("Checkout Test", "Verify checkout process");

        // Proceed to checkout
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();

        // Validate checkout process
        String checkoutTitle = driver.findElement(By.className("title")).getText();
        if (checkoutTitle.equals("CHECKOUT: OVERVIEW")) {
            test.info("Checkout page title captured: " + checkoutTitle);
            test.pass("Checkout process successful - Checkout title verified");
        } else {
            test.fail("Checkout process failed - Checkout title not as expected: " + checkoutTitle);
        }
    }

    @AfterMethod
    public void tearDownTest() {
        // Close browser and terminate WebDriver
        driver.quit();
    }

    @AfterTest
    public void tearDown() {
        // Flush ExtentReports to save the report
        extent.flush();
    }
}
