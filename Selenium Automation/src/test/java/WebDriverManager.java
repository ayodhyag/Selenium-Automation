import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class WebDriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static ChromeOptions chrome_options;
    public static EdgeOptions edge_options;
    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    @BeforeTest
    @Parameters({"browser"})
    public static void choose_browser(String browser){
        if(browser.equalsIgnoreCase("Chrome")){
            chrome_options = new ChromeOptions();
            chrome_options.addArguments("--remote-allow-origins=*");
            System.setProperty("web-driver.Chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
            setDriver(new ChromeDriver(chrome_options));
        }

        if(browser.equalsIgnoreCase("Edge")){
            edge_options = new EdgeOptions();
            edge_options.addArguments("--remote-allow-origins=*");
            System.setProperty("web-driver.Edge.driver", System.getProperty("user.dir") + "/src/test/resources/msedgedriver.exe");
            setDriver(new EdgeDriver(edge_options));
        }

    }
}
