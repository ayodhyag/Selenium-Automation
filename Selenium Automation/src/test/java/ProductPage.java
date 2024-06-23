import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductPage {
    public static ThreadLocal<String> Product_Page_Item_Name = new ThreadLocal<String>();
    public static ThreadLocal<String> Product_Page_Item_Price = new ThreadLocal<String>();
    public static ThreadLocal<String> Product_Page_Item_Quantity = new ThreadLocal<String>();


    public String Selected_Item_Name = HomePage.get_Selected_Item_Name();
    public String Selected_Item_Price = HomePage.get_Selected_Item_Price();

    public static String get_Product_Page_Item_Name() {
        return Product_Page_Item_Name.get();
    }
    public static void set_Product_Page_Item_Name(String string){
        Product_Page_Item_Name.set(string);
    }
    public static String get_Product_Page_Item_Price() {
        return Product_Page_Item_Price.get();
    }
    public static void set_Product_Page_Item_Price(String string){
        Product_Page_Item_Price.set(string);
    }
    public static String get_Product_Page_Item_Quantity() {
        return Product_Page_Item_Quantity.get();
    }
    public static void set_Product_Page_Item_Quantity(String string){
        Product_Page_Item_Quantity.set(string);
    }

    @Test(priority = 7)
    public void Retrieve_and_Display_Item_Details(){
        WebDriver driver = WebDriverManager.getDriver();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        String string1 = driver.findElement(By.xpath("/html/body/div[2]/main/div[1]/div[1]/div[4]/div/div/div[2]/div/div[1]/div[1]/h1/span")).getText();
        System.out.println(string1);
        set_Product_Page_Item_Name(string1);
        String text_to_be_cleaned = driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div[1]/div[3]/div/div/div[2]/span[2]/span")).getText();
        Pattern pattern = Pattern.compile("[()US\\s]");
        Matcher matcher = pattern.matcher(text_to_be_cleaned);

        // Replace unwanted symbols with an empty string
        set_Product_Page_Item_Price(matcher.replaceAll(""));
        System.out.println("Product Page Details");
        System.out.println("...............................................................");
        System.out.println("Title:- "+get_Product_Page_Item_Name());
        System.out.println("Price:- "+get_Product_Page_Item_Price());
        System.out.println("...............................................................");
    }

    @Test(priority = 8)
    void Assertion_of_Item_Title(){
        String actualTitle = get_Product_Page_Item_Name();
        String expectedTitle = HomePage.get_Selected_Item_Name();
        Assert.assertEquals(actualTitle,expectedTitle,"Item title is not matched");
    }

    @Test(priority = 9)
    void Assertion_of_Item_Price(){
        String actualPrice = get_Product_Page_Item_Price();
        String expectedPrice = HomePage.get_Selected_Item_Price();
        Assert.assertEquals(actualPrice,expectedPrice,"Item price is not matched");
    }

    @Test(priority = 10)
    @Parameters({"color"})
    void Set_Color(String color){

        try {
            WebDriver driver = WebDriverManager.getDriver();
            WebElement colorDropdown = driver.findElement(By.id("x-msku__select-box-1000"));
            Select colorSelect = new Select(colorDropdown);

            // Get all options
            List<WebElement> options = colorSelect.getOptions();

            // Find the index of the first enabled option
            int selectedIndex = -1;
            String selectedColor = null;
            for (int i = 1; i < options.size(); i++) {
                String option = options.get(i).getText();
                if (option.equals(color)) {
                    selectedIndex = i;
                    selectedColor = option;
                    break;
                }
            }
            if(selectedIndex == -1) {
                System.out.println("Desired Colour option is not available.Selecting an existing color.");
                for (int i = 1; i < options.size(); i++) {
                    if (options.get(i).isEnabled()) {
                        selectedIndex = i;
                        selectedColor = options.get(i).getText();
                        break; // Exit the loop after finding the first enabled option
                    }
                }
            }
            // Select the first enabled option if found
            if (selectedIndex != -1) {
                colorSelect.selectByIndex(selectedIndex);
                System.out.println("Selected Color = "+selectedColor);
            } else {
                System.out.println("No enabled color options available.");
            }

        } catch (NoSuchElementException e) {
            System.out.println("No color options available.");
        }
    }

    @Test(priority = 11)
    @Parameters({"quantity"})
    void Set_Quantity(String quantity){
        WebDriver driver = WebDriverManager.getDriver();
        WebElement quantityTextBox = driver.findElement(By.id("qtyTextBox"));
        quantityTextBox.clear();
        quantityTextBox.sendKeys(quantity);
        set_Product_Page_Item_Quantity(quantity);
    }
    @Test(priority = 12)
    void Add_Item_to_Cart(){
        WebDriver driver = WebDriverManager.getDriver();
        WebElement Add_Cart_Button = driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/div[8]/ul/li[2]/div/a/span/span"));
        Add_Cart_Button.click();
    }
    @Test(priority = 13)
    void Click_On_Cart_Icon(){
        WebDriver driver = WebDriverManager.getDriver();
        WebElement Cart_Icon_Button = driver.findElement(By.xpath("//*[@id=\"gh-cart-n\"]"));
        Cart_Icon_Button.click();
    }


}