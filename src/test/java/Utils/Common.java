package Utils;

import Pages.HomePage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.reactivex.rxjava3.core.Single.using;

public class Common extends Functions {

    public static ExtentReports extent;
    public static ExtentSparkReporter htmlReporter;
    public static ExtentTest test;


    public static ExtentTest settingUp(WebDriver driver, String reportName, String testName, String testDescription)
    {
        openUrl(driver, HomePage.Url);
        extent = new ExtentReports();
        String date = LocalDateTime.now().toString().replace(':','-');
        String destination = System.getProperty("user.dir");
        htmlReporter = new ExtentSparkReporter(destination +"\\Reports\\" + reportName+"-"+date + ".html");
        extent.attachReporter(htmlReporter);
        test = extent.createTest(testName,testDescription);
        return test;
    }
    //Creating a method getScreenshot and passing two parameters
    //driver and screenshotName
    public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
        //below line is just to append the date format with the screenshot name to avoid duplicate names
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        //after execution, you could see a folder "FailedTestsScreenshots" under src folder
        String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+screenshotName+dateName+".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        //Returns the captured file path
        return destination;
    }
}
