package Pages;

import Utils.Functions;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class Login extends Functions {

    static String username = "qwerdf@fgt.com";
    static String password = "qwerty";
    static String signinButtonXpath ="//a[@class='login']";
    static String emailAddressId = "email";
    static String passwdAddressId = "passwd";
    static String signin ="SubmitLogin";
    static String myAccountOptions = "//div[@id='center_column']//div[@class='row addresses-lists']";

    public  static void login(WebDriver driver, ExtentTest test )
    {
        try
        {
            test.log(Status.PASS,"page loaded successfully");
            clickElementByXpath(driver, signinButtonXpath);
            enterTextById(driver, emailAddressId,username);
            enterTextById(driver, passwdAddressId,password);

            clickElementById(driver, signin);
            waitForElementToExistByXpath(driver, myAccountOptions);
            test.log(Status.PASS,"login successful");
        }
        catch(Exception exception)
        {
            test.log(Status.FAIL,"failed to login ");
            System.out.println(exception);
        }

    }
}
