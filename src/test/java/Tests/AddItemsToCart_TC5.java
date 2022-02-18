package Tests;

import Pages.AddItemToCars;
import Pages.HomePage;
import Pages.Login;
import Utils.Common;
import Utils.Functions;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;

public class AddItemsToCart_TC5 extends Functions {
    WebDriver driver;
    public static ExtentReports extent;
    ExtentTest test;

    @BeforeTest
    public void setup()
    {
        driver = myDriver();

        test = Common.settingUp(driver, "ValidateMultipleSearchedProducts_TC2", "Login",  "this logs into the website");
        extent = Common.extent;
    }

    @Test
    public void AddItemToCart()
    {
        String searchItem = "Printed Chiffon Dress";

        Login.login(driver,test);
        HomePage.searchProduct(driver,searchItem, test);
        AddItemToCars.addItemToCart(driver,test);

    }
    @AfterTest
    public void cleanUp()
    {
        extent.flush();
        driver.close();
        driver.quit();
    }
}
