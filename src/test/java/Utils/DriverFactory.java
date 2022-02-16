package Utils;

import com.aventstack.extentreports.ExtentReports;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;

public class DriverFactory {
    static WebDriver driver;

    public static WebDriver open(String browser)
    {


        var workingDirectory = System.getProperty("user.dir");//creating a new file instance
        var filename = workingDirectory+"\\Drivers\\";

        if(browser.equalsIgnoreCase("chrome"))
        {
            WebDriverManager.chromedriver().setup();
            //System.setProperty("webdriver.chrome.driver",filename + "chromedriver.exe");
            return  new ChromeDriver();
        }
        else
        {

            System.setProperty("webdriver.gecko.driver",filename+"geckodriver.exe");
            driver.manage().window().maximize();
            return new FirefoxDriver();
        }
    }



}
