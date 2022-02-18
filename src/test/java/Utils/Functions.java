package Utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import javax.swing.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class Functions {

    public static WebDriver driver;

    public static WebDriver myDriver()
    {
        String[] options = {"Chrome","Firefox"};
        /*String browser = (String) JOptionPane.showInputDialog(null, "Pick a browser:",
                "Browsers", JOptionPane.QUESTION_MESSAGE, null, options, options[1]);*/

        String browser = "Chrome";

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

    public static void openUrl(WebDriver driver, String url)
    {
        driver.navigate().to(url);
        WebDriverWait wait = new WebDriverWait(driver, 30);

        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver wdriver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState"
                ).equals("complete");
            }
        });
    }

    public static void clickElementByXpath(WebDriver driver, String element) {

        // Initialize and wait till element(link) became clickable - timeout in 10 seconds
        WebElement firstResult = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(element)));

        firstResult.click();
    }

    public static void clickElementById(WebDriver driver, String element) {
        WebElement webElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id(element)));


        webElement.click();
    }

    public static void enterTextById(WebDriver driver,String element, String Text) {
        WebElement webElement;
        //will wait for 10 seconds if it can't find an element
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        webElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(element)));
        webElement.sendKeys(Text);
    }

    public static void clearTextBoxById(WebDriver driver, String element) {
        WebElement webElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id(element)));

        webElement.clear();
    }

    public static boolean waitForElementToExistByXpath(WebDriver driver, String element) {
        try {
            WebElement webElement = new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
            return webElement.isDisplayed();

        } catch (Exception exception) {
            return false;
        }

    }

    public static String getTextByXpath(WebDriver driver, String element) {
        try {
            WebElement webElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
String test = webElement.getText();
            return webElement.getText();
        } catch (Exception exception) {
            return "";
        }

    }

    public static String getAttributeByXpath(WebDriver driver, String element) {
        try {
            WebElement webElement = driver.findElement(By.xpath(element));

            return webElement.getDomAttribute("value");
        } catch (Exception exception) {
            return "";
        }

    }

    public static boolean isElementDisplayed(WebDriver driver, String element, String searchText)
    {
        try
        {
            WebElement webElement = driver.findElement(By.xpath(element));
            String Ele = webElement.getText();
            if(webElement.getText().equalsIgnoreCase(searchText))
            {
                return true;
            }

        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
        return false;
    }
}
