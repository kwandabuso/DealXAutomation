package Pages;

import Utils.Functions;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class HomePage extends Functions {

    public static String Url = "http://automationpractice.com/";
    static String searchBoxID = "search_query_top";
    static String btnSearch = "//button[@name='submit_search']";
    static String ProductList = "//ul[@class='product_list grid row']";
    static String ProductSearchedItem = "//ul[@class='product_list grid row']//li//a[@class='product-name']";

    public static void searchProduct(WebDriver driver, String searchItem, ExtentTest test) {
        try
        {
            test.log(Status.PASS, "page loaded successfully");
            enterTextById(driver, HomePage.searchBoxID, searchItem);

            clickElementByXpath(driver, HomePage.btnSearch);

            waitForElementToExistByXpath(driver, HomePage.ProductList);
            test.log(Status.PASS, "item searched successfully");
        }
        catch(Exception exception)
        {
            test.log(Status.FAIL,"failed to search for Item ");
            System.out.println(exception);
        }

    }

    public static void searchProductAndValidateIfFound(WebDriver driver, String[] SearchList, ExtentTest test) throws InterruptedException {

        try
        {
            test.log(Status.PASS, "page loaded successfully");
            //checking if the current line item status is contained within our arguments based on the statuses passed
            for (String searchItem : SearchList) {

                waitForElementToExistByXpath(driver, searchBoxID);

                enterTextById(driver, searchBoxID, searchItem);

                clickElementByXpath(driver, btnSearch);

                waitForElementToExistByXpath(driver, ProductList);

                validateIfSearchedProductIsDisplayed(driver, searchItem, test);

                test.log(Status.PASS, searchItem+ " searched successfully");

                clearTextBoxById(driver, searchBoxID);
            }
        }
        catch(Exception exception)
        {
            test.log(Status.FAIL,"failed to search for Item ");
            System.out.println(exception);
        }


    }

    public static void searchProductAndValidateIfFound(WebDriver driver, String searchItem, ExtentTest test) {
        try
        {
            test.log(Status.PASS, "page loaded successfully");

            enterTextById(driver, searchBoxID, searchItem);

            clickElementByXpath(driver, btnSearch);

            waitForElementToExistByXpath(driver, ProductList);

            waitForElementToExistByXpath(driver, ProductSearchedItem);

            validateIfSearchedProductIsDisplayed(driver, searchItem, test);

            test.log(Status.PASS, "item searched successfully");

            clearTextBoxById(driver, searchBoxID);
        }
        catch(Exception exception)
        {
            test.log(Status.FAIL,"failed to search for Item ");
            System.out.println(exception);
        }



    }

    public static void validateIfSearchedProductIsDisplayed(WebDriver driver, String searchItem, ExtentTest test) {
        try
        {
            boolean isProductDisplayed;
            isProductDisplayed = isElementDisplayed(driver, ProductSearchedItem, searchItem);
            Assert.assertEquals(isProductDisplayed, true);
        }
        catch(Exception exception)
        {
            test.log(Status.FAIL,"failed to search for Item ");
            System.out.println(exception);
        }

    }
}
