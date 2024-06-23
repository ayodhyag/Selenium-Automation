import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NavigateToEbay {
    @Test(priority = 1)
    @Parameters({"url"})
    void navigation(String url){
        WebDriver driver = WebDriverManager.getDriver();
        driver.get(url);
        driver.manage().window().maximize();
    }
    @Test(priority = 2)
    void logoVerificationTest(){
        WebDriver driver = WebDriverManager.getDriver();
        WebElement logo = driver.findElement(By.xpath("//*[@id=\"gh-logo\"]"));
        Assert.assertTrue(logo.isDisplayed(),"Logo is not displayed on the page.");
    }
    @Test(priority = 3)
    void titleVerificationTest(){
        WebDriver driver = WebDriverManager.getDriver();
        String actualTitle = driver.getTitle();
        String expectedTitle = "Electronics, Cars, Fashion, Collectibles & More | eBay";
        Assert.assertEquals(actualTitle,expectedTitle,"Title is not matched");
    }


}
