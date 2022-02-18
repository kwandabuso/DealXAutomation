package Tests;



import Pages.HomePage;
import Utils.Common;
import Utils.Functions;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

public class ValidateMultipleSearchedProducts_TC2 extends Functions{
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
    public void searchAndValidateItem() throws InterruptedException {
        String searchCriteria = "Faded Short Sleeve T-shirts,Blouse,Printed Chiffon Dress";
        String [] searchItems = searchCriteria.split(",");
        HomePage.searchProductAndValidateIfFound(driver,searchItems, test);

    }
    @AfterTest
    public void cleanUp()
    {
        extent.flush();
        driver.close();
        driver.quit();
    }
}
