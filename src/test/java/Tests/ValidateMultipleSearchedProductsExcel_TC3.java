package Tests;



import Pages.HomePage;
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

public class ValidateMultipleSearchedProductsExcel_TC3 extends Functions{
    WebDriver driver;
    public static ExtentReports extent;
    ExtentTest test;

    @BeforeTest
    public void setup()
    {
        driver = myDriver();

        test = Common.settingUp(driver, "ValidateMultipleSearchedProductsExcel_TC3", "Login",  "this logs into the website");
        extent = Common.extent;
    }

    @Test (dataProvider = "getData")
    public void searchAndValidateItem(String searchItem)
    {
        HomePage.searchProductAndValidateIfFound(driver,searchItem, test);

    }

    @DataProvider
    public Object[][] getData()
    {
        var workingDirectory = System.getProperty("user.dir");//creating a new file instance
        var filename = workingDirectory+"\\TestData\\SearchItems.xlsx";
        return Excel.get(filename);
    }


    @AfterTest
    public void cleanUp()
    {
        extent.flush();
        driver.close();
        driver.quit();
    }
}
