package Utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class Functions {

    public void clickElementByXpath(WebDriver driver, String element) {
        WebElement webElement;
        WebDriverWait wait = new WebDriverWait(driver, 20);
        webElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element)));
        webElement.click();
    }

    public void ScrollElementByXpath(WebDriver driver, String element) {
        try {
            WebElement webElement;
            WebDriverWait wait = new WebDriverWait(driver, 20);
            webElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element)));

            Actions actions = new Actions(driver);
            actions.moveToElement(webElement).perform();
            webElement.sendKeys(Keys.UP);
            webElement.sendKeys(Keys.UP);
            webElement.sendKeys(Keys.UP);
            webElement.sendKeys(Keys.UP);
            webElement.sendKeys(Keys.UP);
            webElement.sendKeys(Keys.UP);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", webElement);
        } catch (Exception ex) {

        }

    }

    public void ClickElementByXpathJS(WebDriver driver, String element) {
        try {
            WebElement webElement;
            WebDriverWait wait = new WebDriverWait(driver, 20);
            webElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", webElement);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
        } catch (Exception ex) {

        }

    }

    public void enterTextByXpath(WebDriver driver, String element, String Text) {
        WebElement webElement;
        WebDriverWait wait = new WebDriverWait(driver, 10);
        webElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element)));
        webElement.sendKeys(Text);
    }

    public void clickElementById(WebDriver driver, String element) {
        WebElement webElement;
        //will wait for 10 seconds if it can't find an element
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //checks for an element for 20 seconds checking every 5 seonds ignoring a no such element
        Wait<WebDriver> fluent = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);

        webElement = fluent.until(ExpectedConditions.elementToBeClickable(By.id(element)));
        // webElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(element)));
        webElement.click();
    }

    public void enterTextById(WebDriver driver, String element, String Text) {
        WebElement webElement;
        //will wait for 10 seconds if it can't find an element
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        webElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(element)));
        webElement.sendKeys(Text);
    }

    public boolean waitForElementToExistByXpath(WebDriver driver, String element) {
        try {
            WebElement webElement;
            //will wait for 10 seconds if it can't find an element
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);//implicit
            WebDriverWait wait = new WebDriverWait(driver, 15);
            webElement = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
            //webElement = driver.findElement(By.xpath(element));
            return webElement.isDisplayed();
        } catch (Exception exception) {
            return false;
        }

    }

    public boolean findElementToTheLeftOf(WebDriver driver, String element1, String element2) {
        try {
            /*WebElement webElement1;
            WebElement webElement2;
            WebDriverWait wait = new WebDriverWait(driver,15);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element1)));
            String id = driver.findElement(By.xpath("li"))
                    .toLeftOf(By.id("pid1"));

            webElement1 = driver.findElement(By.xpath(element1)).toLeftOf(By.xpath(""));
            webElement2 = driver.findElement(with(By.xpath("")).leftof(webElement1));

            return  webElement.isDisplayed();*/
        } catch (Exception exception) {

        }
        return false;
    }

    public String getTextByXpath(WebDriver driver, String element) {
        try {
            //will wait for 10 seconds if it can't find an element
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, 20);
            WebElement webElementElement = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
            /* = driver.findElement(By.xpath(element));*/

            return webElementElement.getText();
        } catch (Exception exception) {
            return "";
        }

    }

    public void closeIfElementExist(WebDriver driver, String element) {
        WebElement webElement;
        WebDriverWait wait = new WebDriverWait(driver, 15);
        webElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(element)));
        webElement.click();
    }

    public void switchWindows(WebDriver driver) throws InterruptedException {
        // Perform the click operation that opens new window
        Thread.sleep(10000);
        // Switch to new window opened
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }

    }

    public List<String> getListByXpath(WebDriver driver, String element) {
        try {
            List<String> data = new ArrayList<>();
            List<WebElement> elements = new ArrayList<>();
            //will wait for 10 seconds if it can't find an element
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, 15);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(element)));//explicit wait

            elements = driver.findElements(By.xpath(element));
            for (WebElement listElement : elements) {
                data.add(listElement.getText());
            }
            return data;
        } catch (Exception exception) {
            return null;
        }
    }

    public List<String> getListByTitleXpath(WebDriver driver, String element) {
        try {
            List<String> data = new ArrayList<>();
            List<WebElement> elements = new ArrayList<>();
            WebDriverWait wait = new WebDriverWait(driver, 15);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(element)));
            elements = driver.findElements(By.xpath(element));
            for (WebElement listElement : elements) {
                data.add(listElement.getAttribute("title"));
            }
            return data;
        } catch (Exception exception) {
            return null;
        }
    }

    public List<String> getListByTextXpath(WebDriver driver, String element) {
        try {
            List<String> data = new ArrayList<>();
            List<WebElement> elements = new ArrayList<>();
            WebDriverWait wait = new WebDriverWait(driver, 15);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(element)));
            elements = driver.findElements(By.xpath(element));
            for (WebElement listElement : elements) {
                data.add(listElement.getText());
            }
            return data;
        } catch (Exception exception) {
            return null;
        }
    }
}
