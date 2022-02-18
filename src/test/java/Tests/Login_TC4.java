package Tests;

import Pages.HomePage;
import Pages.Login;
import Utils.Common;
import Utils.Excel;
import Utils.Functions;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

public class Login_TC4 extends Functions {
    WebDriver driver;
    public static ExtentReports extent;
    ExtentTest test;

    @BeforeTest
    public void setup()
    {
        driver = myDriver();

        test = Common.settingUp(driver, "Login_TC4", "Login",  "this logs into the website");
        extent = Common.extent;
    }

    @Test
    public void LoginToTheSite()
    {
        Login.login(driver, test);
    }


    @AfterTest
    public void cleanUp()
    {
        extent.flush();
        driver.close();
        driver.quit();
    }


}
