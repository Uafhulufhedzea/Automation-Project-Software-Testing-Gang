package Data;

import Application.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import Utilities.ExcelUtils;

import java.io.IOException;

public class Selenium {
    public WebDriver driver;
    public ExtentReports extent;
    public ExtentTest logger;


    @BeforeTest
    @Parameters({"Browser", "url"})
    void setup(String Browser, String url) throws IOException {




        switch (Browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                driver.manage().window().maximize();
                break;
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                driver.manage().window().maximize();
                break;
        }
        if (url != null && !url.isEmpty()) {
            driver.get(url);
        }
    }

    private void performLoginTest(String url, String username, String password) throws InterruptedException {
        try {
            LogIn LG = new LogIn(driver);
            Product p2 = new Product(driver);
            driver.get(url);

            // Attempt to log in with invalid credentials
            LG.username(username);
            LG.password(password);
            LG.loginButton();
            LG.captureScreenshot();
            Thread.sleep(6000);


            //adding item to cart


            //   p.products();

            //logging out
            //  LogIn LG2 =new LogIn(driver);
            p2.backpack();
            Cart c = new Cart(driver);
            Thread.sleep(4000);
            c.openCart();
            LG.logout();
            Thread.sleep(4000);

            Thread.sleep(4000);
            LG.username(username);
            LG.password(password);

            LG.loginButton();
            Thread.sleep(4000);


            //adding a second item to cart
            p2.onesie();
            p2.captureScreenshot();
            Thread.sleep(4000);
            c.openCart();


            //going to your cart


            //checking out
            CheckOut c2 = new CheckOut(driver);
            // c2=new CheckOut(driver1);


            c2.clickCheckout();
            Thread.sleep(4000);
            c2.enterFirstName("Testing");
            c2.enterLastName("Gang");
            c2.enterPostalCode("216");
            c2.captureScreenshot();


            Thread.sleep(4000);


            //confirmation
            Confirmation o1;
            o1 = new Confirmation(driver);
            // o1=new Confirmation(driver1);
            o1.captureScreenshot();
            o1.clickContinue();
            o1.clickFinish();
            Thread.sleep(4000);


            // Check for error message indicating invalid login
            if (driver.findElement(By.id("error-message-container")).isDisplayed()) {
                // Clear the username and password fields
                LG.clearUsername();
                LG.clearPassword();

                // Assert the fields are cleared
                Assert.assertEquals(driver.findElement(By.id("user-name")).getAttribute("value"), "", "Username field is not cleared.");
                Assert.assertEquals(driver.findElement(By.id("password")).getAttribute("value"), "", "Password field is not cleared.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Parameters({"url"})
    public void invalidUserLoginTest(String url) throws InterruptedException, IOException {
        // Set Excel file path and sheet name for invalid users
        ExcelUtils.setExcelFile("src/test/resources/InvalidUser.xlsx", "Sheet1");

        String username = ExcelUtils.getCellData(1, 0);
        String password = ExcelUtils.getCellData(1, 0);

        performLoginTest(url, username, password);
    }

    @Test
    @Parameters({"url"})
    public void validUserLoginTest(String url) throws InterruptedException, IOException {
        // Set Excel file path and sheet name for valid users
        ExcelUtils.setExcelFile("src/test/resources/ValidUser.xlsx", "Sheet1");

        String username = ExcelUtils.getCellData(1, 0);
        String password = ExcelUtils.getCellData(0, 0);

        performLoginTest(url, username, password);
    }


}
