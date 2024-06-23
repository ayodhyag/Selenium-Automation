import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class CheckOut extends WebDriverManager {
    @Test(priority = 20)
    void titleVerificationTest(){
        WebDriver driver = WebDriverManager.getDriver();
        String actualTitle = driver.getTitle();
        String expectedTitle = "Mobile Phone in Cell Phones & Accessories for sale | eBay";
        Assert.assertEquals(actualTitle,expectedTitle,"Title is not matched");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
    }

    @AfterTest
    void Close_the_Browser(){
        WebDriver driver = WebDriverManager.getDriver();
        driver.quit();
    }
}
