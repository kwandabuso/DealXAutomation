package Utils;

import com.aventstack.extentreports.ExtentReports;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;

import javax.swing.*;

public class DriverFactory {
    static WebDriver driver;

    public static WebDriver open()
    {
        String[] options = {"Chrome","Firefox"};
        String browser = (String) JOptionPane.showInputDialog(null, "Pick a browser:",
                "Browsers", JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

        var workingDirectory = System.getProperty("user.dir");//creating a new file instance
        var filename = workingDirectory+"\\Drivers\\";

        if(browser.equalsIgnoreCase("chrome"))
        {
            WebDriverManager.chromedriver().setup();
            return  new ChromeDriver();
        }
        else
        {
            WebDriverManager.firefoxdriver().setup();
            return  new FirefoxDriver();

        }
    }



}
