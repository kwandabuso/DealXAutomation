package Pages;

import Utils.Functions;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class AddItemToCars extends Functions {

    static String addToCart = "(//a[@title='Add to cart'])[1]";
    static String addToCartPopUp = "//div[@class='layer_cart_product_info']";
    static String proceedToCheckout = "//a[@title='Proceed to checkout']";
    static String cartItemImg = "//td[@class='cart_product']//img";
    static String increaseQuantity = "//a[contains(@id,'cart_quantity_up_')]";
    static String proceedToCheckout2 = "//p[@class='cart_navigation clearfix']//a[@title='Proceed to checkout']";
    static String price = "//td[@class='cart_unit']//span[@class='price']//span[@class='price']";
    static String quantity = "//td[@class='cart_quantity text-center']/input[@type='hidden']";
    private static String totalPrice = "//td[@class='cart_total']//span";

    public static void addItemToCart(WebDriver driver,  ExtentTest test) {
        try
        {
            clickElementByXpath(driver, addToCart);
            test.log(Status.PASS,"added Item to cart");
            waitForElementToExistByXpath(driver, addToCartPopUp);
            clickElementByXpath(driver, proceedToCheckout);
            test.log(Status.PASS,"proceed to checkout clicked");
            clickElementByXpath(driver, increaseQuantity);
            waitForElementToExistByXpath(driver, cartItemImg);

            double total = calculateTotalCart(driver, test);
            validateTotalCart(total, driver, test);
            test.log(Status.PASS,"Cart items displayed");
            clickElementByXpath(driver, proceedToCheckout2);

            test.log(Status.PASS,"proceeded to checkout");
        }
        catch(Exception exception)
        {
            test.log(Status.FAIL,"failed to add item to cart ");
            System.out.println(exception);
        }
    }

    public static double calculateTotalCart(WebDriver driver, ExtentTest test) {
        try {
            double cartPrice = Double.parseDouble(getTextByXpath(driver, price).substring(1));
            int cartQuantity = Integer.parseInt(getAttributeByXpath(driver, quantity));

            return cartPrice * cartQuantity;
        }
        catch(Exception exception)
        {
            test.log(Status.FAIL,"failed to calculate cart total");
            System.out.println(exception);
            return 0;
        }

    }

    public static void validateTotalCart(double calculatedTotal, WebDriver driver, ExtentTest test) {
        try
        {
            double cartTotal = Double.parseDouble(getTextByXpath(driver, totalPrice).substring(1));

            Assert.assertEquals(cartTotal, calculatedTotal);
        }
        catch(Exception exception)
        {
            test.log(Status.FAIL,"failed to validate cart total");
            System.out.println(exception);

        }
    }
}
