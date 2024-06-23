import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShoppingCart {
    public static ThreadLocal<String> Shopping_Cart_Item_Name = new ThreadLocal<String>();
    public static ThreadLocal<String> Shopping_Cart_Item_Price = new ThreadLocal<String>();
    public static ThreadLocal<String> Shopping_Cart_Item_Quantity = new ThreadLocal<String>();
    public WebDriver driver = WebDriverManager.getDriver();

    public String Product_Page_Item_Name = ProductPage.get_Product_Page_Item_Name();
    public String Product_Page_Item_Price = ProductPage.get_Product_Page_Item_Name();
    public String Product_Page_Item_Quantity = ProductPage.get_Product_Page_Item_Name();

    public static String get_Shopping_Cart_Item_Name() {
        return Shopping_Cart_Item_Name.get();
    }

    public static void set_Shopping_Cart_Item_Name(String string) {
        Shopping_Cart_Item_Name.set(string);
    }

    public static String get_Shopping_Cart_Item_Price() {
        return Shopping_Cart_Item_Price.get();
    }

    public static void set_Shopping_Cart_Item_Price(String string) {
        Shopping_Cart_Item_Price.set(string);
    }

    public static String get_Shopping_Cart_Item_Quantity() {
        return Shopping_Cart_Item_Quantity.get();
    }

    public static void set_Shopping_Cart_Item_Quantity(String string) {
        Shopping_Cart_Item_Quantity.set(string);
    }

    @Test(priority = 14)
    void Getting_Shopping_Cart_Item_Details() {
        driver = WebDriverManager.getDriver();
        set_Shopping_Cart_Item_Name(driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/div[3]/div[1]/div[1]/div/ul/li/div/div/div/div[1]/div/div[2]/div[1]/h3/a")).getText());
        String text_to_be_cleaned = driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/div[3]/div[1]/div[1]/div/ul/li/div/div/div/div[1]/div/div[3]/div/div[2]/div/div/div[2]/div/span/span/span")).getText();
        Pattern pattern = Pattern.compile("[()US\\s]");
        Matcher matcher = pattern.matcher(text_to_be_cleaned);

        // Replace unwanted symbols with an empty string
        set_Shopping_Cart_Item_Price(matcher.replaceAll(""));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement Quantity_normal_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='mainContent']/div/div[3]/div[1]/div[1]/div/ul/li/div/div/div/div[1]/div/div[3]/div/div[1]/div/div/span/span/span[2]")));
            if (Quantity_normal_element.isDisplayed()) {
                String text_to_be_cleaned_2 = Quantity_normal_element.getText();
                Pattern pattern_2 = Pattern.compile("[()Qty\\s]");
                Matcher matcher_2 = pattern.matcher(text_to_be_cleaned_2);

                // Replace unwanted symbols with an empty string
                set_Shopping_Cart_Item_Quantity(matcher_2.replaceAll(""));

            }
        } catch (TimeoutException e) {
            System.out.println("Quantity normal element not found.(TimeoutException)");
        } catch (NoSuchElementException e) {
            System.out.println("Quantity normal element not found.(NoSuchElement)");
        }

        try {
            WebElement quantityDropdown = driver.findElement(By.cssSelector("[id*='dropdown-575404027']"));
            Select quantitySelect = new Select(quantityDropdown);
            List<WebElement> options = quantitySelect.getOptions();
            int selectedIndex = -1;

            for (int i = 0; i < options.size(); i++) {
                String option = options.get(i).getText();
                if (option.equals(ProductPage.get_Product_Page_Item_Quantity())) {

                    selectedIndex = i;
                    String quantity = options.get(i).getText();
                    set_Shopping_Cart_Item_Quantity(quantity);
                    break;
                }
            }
            if (get_Shopping_Cart_Item_Quantity() == null) {
                System.out.println("Quantity limit per buyer has reached.Please Check the quantity limit");
            }
            if (selectedIndex != -1) {
                quantitySelect.selectByIndex(selectedIndex);
            } else {
                System.out.println("No enabled quantity options available.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Quantity dropdown not found.(NoSuchElement)");
        }


    }

    @Test(priority = 15)
    void Assertion_of_Item_Title() {
        String actualTitle = get_Shopping_Cart_Item_Name();
        String expectedTitle = ProductPage.get_Product_Page_Item_Name();
        Assert.assertEquals(actualTitle, expectedTitle, "Item title is not matched");
    }

    @Test(priority = 16)
    void Assertion_of_Item_Price() {
        String actualPrice = get_Shopping_Cart_Item_Price();
        String expectedPrice = ProductPage.get_Product_Page_Item_Price();
        if (ProductPage.get_Product_Page_Item_Quantity().equals("1")) {
            Assert.assertEquals(actualPrice, expectedPrice, "Item price is not matched");
        } else {
            System.out.println("Quantity is larger than 1,Therefore price can be changed with discounts");
        }
    }

    @Test(priority = 17)
    void Assertion_of_Item_Quantity() {
        String actualQuantity = get_Shopping_Cart_Item_Quantity();
        String expectedQuantity = ProductPage.get_Product_Page_Item_Quantity();
        Assert.assertEquals(actualQuantity, expectedQuantity, "Item quantity is not matched");
        System.out.println("Shoping Cart Details");
        System.out.println("...............................................................");
        System.out.println("Title:- " + get_Shopping_Cart_Item_Name());
        System.out.println("Price:- " + get_Shopping_Cart_Item_Price());
        System.out.println("Quantity:- " + get_Shopping_Cart_Item_Quantity());
        System.out.println("...............................................................");
    }

    @Test(priority = 18)
    void Go_to_Checkout() {
        WebElement Go_to_Checkout_Button = driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/div[3]/div[2]/div/div/div[2]/button"));
        Go_to_Checkout_Button.click();
    }

    @Test(priority = 19)
    void Continue_as_guest() {
        Duration duration = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver, duration);
        WebElement Continue_as_guest_Button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"gxo-btn\"]")));
        Continue_as_guest_Button.click();
    }
}
