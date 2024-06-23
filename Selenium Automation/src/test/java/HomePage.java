import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class HomePage extends WebDriverManager {
    public static ThreadLocal<String> Selected_Item_Name = new ThreadLocal<String>();

    public static ThreadLocal<String> Selected_Item_Price = new ThreadLocal<String>();

    public static String get_Selected_Item_Name() {
        return Selected_Item_Name.get();
    }
    public static void set_Selected_Item_Name(String string){
        Selected_Item_Name.set(string);
    }
    public static String get_Selected_Item_Price() {
        return Selected_Item_Price.get();
    }
    public static void set_Selected_Item_Price(String string){
        Selected_Item_Price.set(string);
    }
    @Test(priority = 4)
    @Parameters({"product", "category"})
    void select_category(String product ,String category){
        WebDriver driver = WebDriverManager.getDriver();
        driver.findElement(By.id("gh-ac")).sendKeys(product);
        WebElement categoryDropdownElement = driver.findElement(By.xpath("//*[@id=\"gh-cat\"]"));
        Select categorySelect = new Select(categoryDropdownElement);
        categorySelect.selectByVisibleText(category);
        driver.findElement(By.id("gh-btn")).click();
    }
    @Test(priority = 5)
    void getting_no_of_results(){
        WebDriver driver = WebDriverManager.getDriver();
        String result = driver.findElement(By.xpath("//h1[contains(text(),'results for')]")).getText();
        System.out.println(result);
    }
    @Test(priority = 6)
    @Parameters({"no_of_checking_results"})
    void assertion_of_word_mobile_phone(int no_of_checking_results){
        WebDriver driver = WebDriverManager.getDriver();
        int filtered_count = 0;
        boolean first_filterd_item_selected = false;
        for(int i = 0;i< no_of_checking_results; i++){
            String xpathExpression = String.format("//div[@id='srp-river-results']/ul/li[%d]",(i+1));
            String ithProduct = driver.findElement(By.xpath(xpathExpression)).getText();
            if (ithProduct.contains("Mobile Phone")) {
                filtered_count++;
                String[] lines = ithProduct.split("\\r?\\n");
                String title = lines[0];
                String price = lines[3];
                System.out.println("...............................................................");
                System.out.println(title);
                System.out.println(price);

                if(first_filterd_item_selected==false){
                    String itemID = driver.findElement(By.xpath(xpathExpression)).getAttribute("id");
                    String xpathExpression_2 = String.format("//*[@id=\"%s\"]/div/div[1]/div/a/div/img",itemID);
                    WebElement first_filtered_item_image = driver.findElement(By.xpath(xpathExpression_2));
                    first_filtered_item_image.click();

                    set_Selected_Item_Name(title);
                    set_Selected_Item_Price(price);
                    first_filterd_item_selected = true;
                }

            }
        }
        System.out.println("...............................................................");
        Assert.assertTrue(filtered_count > 0, "At least one of the first five search results does not contain 'Mobile Phone'");
        System.out.println(filtered_count+" of first "+no_of_checking_results+" results contain 'Mobile Phone'");
    }
}
