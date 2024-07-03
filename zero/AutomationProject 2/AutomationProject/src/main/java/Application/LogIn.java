package Application;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class LogIn {
    WebDriver driver;

    public LogIn(WebDriver driver) {
        this.driver = driver;
    }

    public void username(String user) {
        driver.findElement(By.xpath("//*[@id=\"user-name\"]")).sendKeys(user);
    }

    public void password(String pass) {
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(pass);
    }

    public void loginButton() {
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
    }

    public void clearUsername() {
        driver.findElement(By.xpath("//*[@id=\"user-name\"]")).clear();
    }

    public void clearPassword() {
        driver.findElement(By.xpath("//*[@id=\"password\"]")).clear();
    }


    public void logout() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"react-burger-menu-btn\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"logout_sidebar_link\"]")).click();
    }


    public void screenshot() throws IOException {
        try {
            // Find the WebElement
            WebElement HD = driver.findElement(By.xpath("//*[@id='root']"));

            // Take screenshot of the WebElement
            File source = HD.getScreenshotAs(OutputType.FILE);

            // Specify destination file
            File destination = new File("src/main/java/utilities/Login.png");

            // Copy screenshot to destination using Java NIO
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Screenshot captured and saved successfully.");
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
            throw e; // Re-throwing the exception to the calling method
        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void captureScreenshot() {
        try {
            screenshot(); // Call your screenshot method
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            // Handle or log the exception as needed
        }
    }
    @AfterTest
   public void tearDown() {
       driver.quit();
   }
}
