package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.reactivex.rxjava3.core.Single.using;

public class Common extends Functions {

    public static ExtentReports extent;
    public static ExtentSparkReporter htmlReporter;
    static WebDriver driver;
    String Away, Home;
    TeamsEnum teamsEnum;
    String winHandleBefore;
    //ExtentReports extent;
    //ExtentSparkReporter htmlReporter;
    public static ExtentTest test;

    public boolean CheckIfCountryPlays(String country, String League) {

        String element = "//div[@title='" + country + "']//span[@title='" + League + "']";
        //clickElementById(driver, "");
        return waitForElementToExistByXpath(driver, element);

    }

    public boolean IsTeamPlaying(String country, String league, String Team) {
        try {
            Thread.sleep(5000);
            if (CheckIfCountryPlays(country, league)) {
                if (waitForElementToExistByXpath(driver, "//div[text()='" + Team + "']")) {
                    return true;
                }
            }
        } catch (Exception exception) {

        }

        return false;
    }

    public void getTeams(String myTeam) {
        try {
            List<String> Teams = getListByXpath(driver, "//div[text()='" + myTeam + "']//ancestor::div[@title='Click for match detail!']//div[contains(@class, 'event__participant event')]");

            System.out.println();
            Home = Teams.get(0);
            Away = Teams.get(1);
            test.log(Status.INFO, "<font size=5px><strong>Home: " + Home + "  &emsp; &emsp;&emsp; &emsp; Away: " + Away + "</strong></font>");
            System.out.println("<font color=green> <strong>Home: " + Home + "  Away: " + Away + "</Strong></font>");

        } catch (Exception ex) {

        }

    }

    public String checkStandings(String Home, String Away) throws InterruptedException {

        // Perform the actions on new window
        clickElementByXpath(driver, "//a[@href='#standings']");

        //will wait for 10 seconds if it can't find an element
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        String home = getTextByXpath(driver, "//a[text()='" + Home + "'][@class='tableCellParticipant__name']//..//..//..//..//div[@class='tableCellRank']");
        String away = getTextByXpath(driver, "//a[text()='" + Away + "'][@class='tableCellParticipant__name']//..//..//..//..//div[@class='tableCellRank']");
        System.out.println("Home: " + home + "  Away: " + away);
        test.log(Status.INFO, "<font size=3px><strong>STANDINGS</strong></font>");
        test.log(Status.INFO, "Home: " + home + "&emsp; &emsp;&emsp; &emsp; Away: " + away);
        return "Home: " + home + " Away: " + away;

    }

    public String checkStandings() throws InterruptedException {
        String home = "";
        String away = "";
        try {
            // Perform the actions on new window
            clickElementByXpath(driver, "//a[@href='#standings']");
            String[][] standing = new String[1][2];
            //will wait for 10 seconds if it can't find an element
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            home = getTextByXpath(driver, "//a[text()='" + Home + "'][@class='tableCellParticipant__name']//..//..//..//..//div[@class='tableCellRank']");
            away = getTextByXpath(driver, "//a[text()='" + Away + "'][@class='tableCellParticipant__name']//..//..//..//..//div[@class='tableCellRank']");
            System.out.println("Home: " + home + "  Away: " + away);

            test.log(Status.INFO, "<font size=3px><strong>STANDINGS</strong></font>");
            standing[0][0] = home;
            standing[0][1] = away;
            Markup m = MarkupHelper.createTable(standing);

            test.pass(m);

            //test.log(Status.INFO, "Home: " + home + "&emsp; &emsp;&emsp; &emsp; Away: " + away);
        } catch (Exception exception) {

        }

        return "Home: " + home + " Away: " + away;

    }

    public void checkH2H(String element, String homeTeam, ExtentTest test) throws InterruptedException {

        clickElementByXpath(driver, element);


        List<String> homeTeamResults = null;
        List<String> awayTeamResults = null;
        List<String> headToHead = null;

        String awayTeam = getTextByXpath(driver, "//div[@class='duelParticipant__away']//a[@class='participant__participantName participant__overflow']");

        Thread.sleep(10000);
        String homeResults = "//div[contains(text(),'" + Home + "')]//parent::div[@class='h2h__section section ']//span[@class='h2h__icon']//div";

        waitForElementToExistByXpath(driver, homeResults);
        homeTeamResults = getListByTitleXpath(driver, homeResults);

        String awayResults = "//div[contains(text(),'" + Away + "')]//parent::div[@class='h2h__section section ']//span[@class='h2h__icon']//div";

        awayTeamResults = getListByTitleXpath(driver, awayResults);

        //displayLastMatches(homeTeamResults, awayTeamResults, test);

        headToHead = getListByTextXpath(driver, "//div[text()='Head-to-head matches']//parent::div[@class='h2h__section section ']//div[@class='h2h__row']");

        displayLastFiveMatches(homeTeamResults, awayTeamResults, test);

        displayH2HMatches(headToHead, test);
    }

    private void displayLastMatches(List<String> homeTeamResults, List<String> awayTeamResults, ExtentTest test) {
        int i = 0;
        test.log(Status.INFO, "");
        test.log(Status.INFO, "<font size=3px><strong>LAST 5 GAMES</strong></font>");
        String[][] homeLastFive = new String[5][1];
        for (String element : homeTeamResults) {
            String away = "";
            String home = "";
            System.out.println("Home: " + element + " Away: " + awayTeamResults.get(i));


            if (element.equalsIgnoreCase("win")) {
                home = "<font color=green> Home: " + element + " &emsp; &emsp;";
            } else if (element.equalsIgnoreCase("draw")) {
                home = "<font color=orange> Home: " + element + " &emsp; &emsp;";
            } else if (element.equalsIgnoreCase("loss")) {
                home = "<font color=red> Home: " + element + " &emsp; &emsp;";
            }

            //check away history
            if (awayTeamResults.get(i).equalsIgnoreCase("win")) {
                away = "<font color=green> &emsp; &emsp; Away: " + awayTeamResults.get(i) + "</font>";
            } else if (awayTeamResults.get(i).equalsIgnoreCase("draw")) {
                away = "<font color=orange> &emsp; &emsp; Away: " + awayTeamResults.get(i) + "</font>";
            } else if (awayTeamResults.get(i).equalsIgnoreCase("loss")) {
                away = "<font color=red> &emsp; &emsp; Away: " + awayTeamResults.get(i) + "</font>";
            }

            String history = home + away;

            test.log(Status.INFO, history);

            i++;
        }


    }

    private void displayH2HMatches(List<String> homeTeamResults, ExtentTest test) {
        try {
            int j = 0;
            String[][] teamsHeadToHead = new String[homeTeamResults.size()][6];
            test.log(Status.INFO, "<font size=3px><strong>LAST 5  GAMES</strong></font>");
            for (String element : homeTeamResults) {
                String[] game = element.split("\\n");
                for (int i = 0; i < game.length; i++) {
                    teamsHeadToHead[j][i] = game[i];
                }
                j++;

            }
            Markup m = MarkupHelper.createTable(teamsHeadToHead);

            test.pass(m);
        } catch (Exception exception) {

        }


    }

    public void getOddsH2H(ExtentTest test) throws InterruptedException {
        try {
            String[][] odd = new String[1][3];
            clickElementByXpath(driver, "//a[@href='#odds-comparison']");
            List<String> data = new ArrayList<>();
            String odds = "//a[@title='Betway Africa']//ancestor::div[@class='ui-table__row']//a//span";
            String myOdd = "";
            test.log(Status.INFO, "");
            test.log(Status.INFO, "<font size=3px><strong>ODDS</strong></font>");

            data = getListByXpath(driver, odds);
            int i = 0;

            for (String myData : data) {
                odd[0][i] = "<font color=green> " + myData + "</font>";
                myOdd += myData + "&emsp; &emsp;&emsp;";
                i++;
            }

            Markup m = MarkupHelper.createTable(odd);
            test.pass(m);
            test.log(Status.INFO, "-------------------------------------------------------------------------------");
        } catch (Exception exception) {
            test.log(Status.INFO, "No ODDS");
        }
    }

    public void getTeams(String myTeam, ExtentTest test) {


        List<String> Teams = getListByXpath(driver, "//div[text()='" + myTeam + "']//ancestor::div[@title='Click for match detail!']//div[contains(@class, 'event__participant event')]");
        String[][] teams = new String[1][2];

        System.out.println();
        Home = Teams.get(0);
        Away = Teams.get(1);
        test.log(Status.INFO, "<font size=5px><strong>" + Home + "  &emsp; &emsp;&emsp; &emsp; " + Away + "</strong></font>");
        System.out.println("<font color=green> <strong>" + Home + " " + Away + "</Strong></font>");

        teams[0][0] = "<font color=green> <strong>" + Home + "</Strong></font>";
        teams[0][1] = "<font color=green> <strong>" + Away + "</Strong></font>";


    }

    public void getStats(String[] teams, String League, String Country, ExtentTest test) throws InterruptedException {
        createTest();
        // clickElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");
        Thread.sleep(10000);
        //driver.navigate().refresh();
        //  clickElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");

        for (int j = 0; j < 2; j++) {//days

            for (int i = 0; i < teams.length; i++) {//teams


                if (IsTeamPlaying(Country, League, teams[i])) {
                    String date = getTextByXpath(driver, "//div[@class='calendar__datepicker ']");
                    getTeams(teams[i], test);
                    WillNotPlay(teams[i], test);
                    checkStandings();
                    checkH2H("//a[@href='#h2h']", teams[i], test);
                    getOddsH2H(test);
                }
            }
            Thread.sleep(10000);
            ScrollElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");
            clickElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");
        }
        driver.quit();
    }

    public List<String> getResults(List<WebElement> elements) throws InterruptedException {
        List<String> results = new ArrayList<>();
        for (WebElement webElement : elements) {
            Thread.sleep(5000);
            String outcome = webElement.getAttribute("title");
            results.add(outcome);
        }
        return results;
    }

    public void WillNotPlay(String Team, ExtentTest test) throws InterruptedException {

        Thread.sleep(10000);
        List<String> willNotPlayHome = new ArrayList<>();
        List<String> willNotPlayAway = new ArrayList<>();
        willNotPlayHome = getListByXpath(driver, "//div[text()='Will not play']//parent::div//div[@class='lf__participant lf__scratchParticipant ']");
        willNotPlayAway = getListByXpath(driver, "//div[text()='Will not play']//parent::div//div[@class='lf__participant lf__scratchParticipant lf__isReversed']");
        String injuredList = "";
        int homeMissingPlayers;
        int awayMissingPlayers;

        test.log(Status.INFO, "<font size=3px><strong>WILL NOT PLAY</strong></font>");
        String[][] data;

        try {
            if (willNotPlayHome.size() > willNotPlayAway.size()) {
                data = new String[willNotPlayHome.size() + 1][2];
            } else {
                data = new String[willNotPlayAway.size() + 1][2];
            }
        } catch (NullPointerException ex) {
            data = new String[1][2];
        }


        data[0][0] = "HOME";
        data[0][1] = "AWAY";
        try {
            homeMissingPlayers = willNotPlayHome.size();
        } catch (Exception exception) {
            homeMissingPlayers = 0;

        }

        try {
            awayMissingPlayers = willNotPlayAway.size();
        } catch (Exception exception) {
            awayMissingPlayers = 0;
        }

        if (homeMissingPlayers == 0 && awayMissingPlayers > 0) {

            for (String player : willNotPlayAway) {
                //String injured = "emsp; &emsp; &emsp; &emsp; &emsp; &emsp;&emsp; &emsp;" + player;

                String injured = "" + player + "";

                test.log(Status.INFO, injured);

            }

        } else if (awayMissingPlayers == 0 && homeMissingPlayers > 0) {
            int i = 0;

            for (String player : willNotPlayHome) {
                // String injured = "&emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp;" + player;
                String injured = "" + player + "";

                test.log(Status.INFO, injured);
                i++;
            }
        } else if (awayMissingPlayers == 0 && homeMissingPlayers == 0) {
            test.log(Status.INFO, "NO INJURED OR SUSPENDED PLAYERS");
        } else if (awayMissingPlayers < homeMissingPlayers) {
            int i = 1;
            int j = 0;
            int k = 0;
            String awayplayer = "";
            for (String player : willNotPlayHome) {

                if (willNotPlayAway.size() > 0 && willNotPlayAway.size() > k) {
                    awayplayer = willNotPlayAway.get(k);
                } else {
                    awayplayer = "";
                }
                //String injured = player + "&emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp;" + awayplayer;
                String injured = "" + player + "" + awayplayer + "";
                data[i][j] = player;
                data[i][j + 1] = awayplayer;
                //test.log(Status.INFO, injured);
                i++;
                k++;
            }

        } else {
            int i = 1;
            int j = 0;
            int k = 0;

            for (String player : willNotPlayAway) {
                String awayplayer = "";

                if (k < willNotPlayHome.size()) {
                    awayplayer = willNotPlayHome.get(k);
                } else {
                    awayplayer = "";
                }
                data[i][j] = awayplayer;
                data[i][j + 1] = player;
                i++;
                k++;
            }

        }

        Markup m = MarkupHelper.createTable(data);

        test.pass(m);
    }

    public void createTest() {
        driver = DriverFactory.open("chrome");
        driver.get("https://www.soccer24.com/");
        driver.manage().window().maximize();
        closeIfElementExist(driver, "onetrust-accept-btn-handler");
    }

    public void setUp(String reportName, String testname, String testDescription) {
        extent = new ExtentReports();
        htmlReporter = new ExtentSparkReporter("C:\\Users\\busok001\\Documents\\Apps\\reports\\" + reportName + ".html");
        extent.attachReporter(htmlReporter);

    }

    public void setUp(String reportName) {
        extent = new ExtentReports();
        htmlReporter = new ExtentSparkReporter("C:\\Users\\busok001\\Documents\\Apps\\reports\\" + reportName + ".html");
        extent.attachReporter(htmlReporter);
        test = extent.createTest("kwanda");
    }

    public void readAndOpenTeams() throws InterruptedException {
        //getTeams();
        createTest();
        List<List<String>> records = new ArrayList<>();
        String[][] data = new String[1][2];

        String line;
        // int i = 0;
        winHandleBefore = driver.getWindowHandle();
        String date = getTextByXpath(driver, "//div[@class='calendar__datepicker ']").split("\\s+")[1];

        switch (date) {
            case "Mo":
                ScrollElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");
                clickElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");
                ScrollElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");
                clickElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");
                break;

            case "Tu":
                ScrollElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");
                clickElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");
                break;
            case "Fr":
                ScrollElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");
                clickElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");
                break;
        }

        String lottoTeams[][] = readCSVFile();
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < lottoTeams.length; i++) {
                // for (int i = 0; i < 1; i++) {
                String home = lottoTeams[i][0];
                String away = lottoTeams[i][1];
                data[0][0] = "<Font size=3px><Strong>" + home + "</strong></font>";
                data[0][1] = "<Font size=3px><Strong>" + away + "</strong></font>";
                Markup m = MarkupHelper.createTable(data);

                test.pass(m);
                String ele = "//div[text()='" + home + "']/following-sibling::div[text()='" + away + "']";
                boolean isFound = false;
                try {
                    if (away.contains("found")) {
                        isFound = true;
                    }
                } catch (Exception ex) {
                }


                if (!isFound && waitForElementToExistByXpath(driver, "//div[text()='" + home + "']/following-sibling::div[text()='" + away + "']")) {

                    String Mydate = getTextByXpath(driver, "//div[@class='calendar__datepicker ']");

                    WillNotPlay(home, test);
                    checkStandings(home, away);

                    readH2h(home, away);
                    getOddsH2H(test);
                    lottoTeams[i][1] = "found";
                } else {
                    continue;
                }
                System.out.println(away + " - " + home);
            }

            ScrollElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");
            clickElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");
        }

    }

    public Boolean openTeams(String home, String away) throws InterruptedException {
        boolean isFound = false;

        if (waitForElementToExistByXpath(driver, "//div[text()='" + home + "']/following-sibling::div[text()='" + away + "']")) {

            String Mydate = getTextByXpath(driver, "//div[@class='calendar__datepicker ']");

            WillNotPlay(home, test);
            checkStandings(home, away);

            readH2h(home, away);
            getOddsH2H(test);
            isFound = true;

        }
        System.out.println(away + " - " + home);
        return true;
    }

    public void readH2h(String homeTeam, String AwayTeam) {
        clickElementByXpath(driver, "//a[@href='#h2h']");

        // int i = 0;
        List<String> headToHeadHome = new ArrayList<>();
        //List<String> tempHome = new ArrayList<>();
        List<String> headToHeadAway = new ArrayList<>();
        headToHeadHome = getListByXpath(driver, "//div[text() ='Last matches: " + homeTeam + "']//parent::div[@class='h2h__section section ']//div[@class='h2h__row']");

        test.log(Status.INFO, "<font><strong> Home: " + homeTeam + "</strong></font>");
        String[][] data = new String[5][6];
        int j = 0;

        for (String element : headToHeadHome) {
            String[] tempHome = element.split("\\n");
            for (int i = 0; i < tempHome.length; i++) {
                data[j][i] = tempHome[i];
            }
            j++;

        }
        Markup m = MarkupHelper.createTable(data);

        test.pass(m);
        String[][] away = new String[5][6];
        headToHeadAway = getListByXpath(driver, "//div[text() ='Last matches: " + AwayTeam + "']//parent::div[@class='h2h__section section ']//div[@class='h2h__row']");
        test.log(Status.INFO, "<font><strong> Away: " + AwayTeam + "</strong></font>");
        int k = 0;
        for (String element : headToHeadAway) {
            String[] tempHome = element.split("\\n");
            for (int i = 0; i < tempHome.length; i++) {
                away[k][i] = tempHome[i];

            }
            k++;

        }

        Markup mark = MarkupHelper.createTable(away);

        test.pass(mark);

        readTeamH2h();
    }

    public void readTeamH2h() {


        // int i = 0;
        List<String> headToHeadHome = new ArrayList<>();
        headToHeadHome = getListByXpath(driver, "//div[text()='Head-to-head matches']//parent::div//div[@class='h2h__row']");

        test.log(Status.INFO, "<font><strong> Head To head </strong></font>");
        String[][] data = new String[4][5];


        for (String element : headToHeadHome) {
            String[] tempHome = element.split("\\n");
            int j = 0;
            for (int i = 0; i < tempHome.length; i++) {
                data[j][i] = tempHome[i];

            }
            j++;

        }
        Markup m = MarkupHelper.createTable(data);

        test.pass(m);

    }

    public String[][] readCSVFile() {
        String teams[][] = new String[13][2];

        try (BufferedReader br = new BufferedReader(new FileReader("TestData/Teams.csv"))) {
            String line;
            int i = 0;


            String[] values;

            while ((line = br.readLine()) != null) {

                teams[i][0] = line.split("-")[0];
                teams[i][1] = line.split("-")[1];
                i++;
            }

            System.out.println(teams);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return teams;
    }

    public void getTeams() {
        driver = DriverFactory.open("chrome");
        driver.get("https://www.nationallottery.co.za/sportstakefixtures");
        driver.manage().window().maximize();
        List<String> headToHeadAway = new ArrayList<>();
        String teams[][] = new String[13][2];

        headToHeadAway = getListByTextXpath(driver, "//div[contains(text(),'Fixture List')]//parent::div//div[@class='tableBody']//div[@class='tableRow']");
        for (int i = 0; i < headToHeadAway.size(); i++) {
            System.out.println(headToHeadAway.get(i));
            String home = headToHeadAway.get(i).split("\\n")[0];
            String away = headToHeadAway.get(i).split("\\n")[1];

            teams[i][0] = headToHeadAway.get(i).split("\\n")[0];
            teams[i][1] = headToHeadAway.get(i).split("\\n")[1];

            System.out.println(teams);

        }

    }

    public void refactor(String text) {
        String newString = StringUtils.capitalize(text);
    }

    public void getStats(String[] teams, String report) throws InterruptedException {
        createTest();
        // clickElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");

        String testname = "";
        String testDescription = "";
        String reportName = "";
        String[][] foundTeams = new String[100][2];
        testname = "BListTeams";
        testDescription = "Semi World Class";
        reportName = report + getDate().replace(":", "-");
        int k = 0;
        int days = Integer.parseInt(getDaysFromUser());
        setUp(reportName, testname, testDescription);

        for (int j = 0; j < days; j++) {//days

            for (int i = 0; i < teams.length; i++) {//teams
                System.out.println(teams[i]);
                if (IsTeamPlaying(teams[i])) {

                    test = extent.createTest(teams[i], teams[i]);


                    System.out.println(teams[i] + "Found");
                    getTeams(teams[i], test);
                    //check if teams have been checked before

                    if (!CheckTeams(Home, Away, foundTeams)) {

                        openMatchday(teams[i]);
                        waitForElementToExistByXpath(driver, "//div[@class='duelParticipant__startTime']//div");
                        String date = getTextByXpath(driver, "//div[@class='duelParticipant__startTime']//div");
                        test.log(Status.INFO, "<strong>" + date + "</strong>");

                        WillNotPlay(teams[i], test);
                        checkStandings();
                        checkH2H("//a[@href='#h2h']", teams[i], test);
                        getOddsH2H(test);
                        foundTeams[k][0] = Home;
                        foundTeams[k][1] = Away;
                        driver.close();
                        driver.switchTo().window(winHandleBefore);
                        k++;
                    }


                }

            }
            Thread.sleep(10000);

            if (waitForElementToExistByXpath(driver, "//div[@id='scroll-to-top']")) {
                ClickElementByXpathJS(driver, "//div[@id='scroll-to-top']");
            }
            ScrollElementByXpath(driver, "//div[@class='seoTop__content']");
            clickElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");
        }
        extent.flush();
        driver.quit();
    }

    public String getDaysFromUser() {
        String result = "";
        try {
            JFrame frame = new JFrame();
            result = JOptionPane.showInputDialog(frame, "Enter Days:");
            System.out.println(result);
        } catch (Exception exception) {
            System.out.println("error getting user input: " + exception);
        }


        return result;
    }

    public String getDate() {
        String formattedDate = "";
        try {
            LocalDateTime myDateObj = LocalDateTime.now();
            System.out.println("Before formatting: " + myDateObj);
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            formattedDate = myDateObj.format(myFormatObj);
            System.out.println("After formatting: " + formattedDate);
        } catch (Exception ex) {
            System.out.println("[ERROR]CANNOT FIND DATE: " + ex);
        }


        return formattedDate;

    }

    private boolean CheckTeams(String home, String away, String[][] checkedTeams) {
        try {
            for (int i = 0; i < checkedTeams.length; i++) {
                if (checkedTeams[i][0].equalsIgnoreCase(home) && checkedTeams[i][1].equalsIgnoreCase(away)) {
                    return true;
                }

            }
        } catch (Exception exception) {

        }

        return false;
    }

    public void getStats(String[] teams, ExtentTest ele) throws InterruptedException {
        createTest();
        clickElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");

        String testname = "";
        String testDescription = "";
        String reportName = "";
        //driver.navigate().refresh();
        clickElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");

        for (int j = 0; j < 2; j++) {//days

            for (int i = 0; i < teams.length; i++) {//teams
                System.out.println(teams[i]);
                // IsTeamPlaying(teams[i][i], teams[i][i]);
                if (IsTeamPlaying(teams[i]) && !teams[i].equalsIgnoreCase("found")) {
                    testname = "AListTeams";
                    testDescription = "World Class";
                    reportName = "AList";
                    setUp(reportName, testname, testDescription);

                    System.out.println(teams[i] + "Found");
                    getTeams(teams[i], test);
                    openMatchday(teams[i]);
                    WillNotPlay(teams[i], test);
                    checkStandings();
                    teams[i] = "found";
                    checkH2H("//a[@href='#h2h']", teams[i], test);
                    getOddsH2H(test);

                }

            }
            Thread.sleep(10000);
            ScrollElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");
            clickElementByXpath(driver, "//div[@class='calendar__direction calendar__direction--tomorrow']");
        }
        extent.flush();
        driver.quit();
    }

    public void openMatchday(String team) throws InterruptedException {
        ClickElementByXpathJS(driver, "//div[text()='" + team + "']");
        winHandleBefore = driver.getWindowHandle();
        switchWindows(driver);

    }

    public boolean IsTeamPlaying(String Team) {
        try {

            if (waitForElementToExistByXpath(driver, "//div[text()='" + Team + "']")) {
                return true;
            }
        } catch (Exception exception) {

        }

        return false;
    }

    private void displayLastFiveMatches(List<String> homeTeamResults, List<String> awayTeamResults, ExtentTest test) {
        int i = 0;
        test.log(Status.INFO, "");
        test.log(Status.INFO, "<font size=3px><strong>LAST 5 GAMES</strong></font>");
        String[][] homeLastFive = new String[5][2];
        for (String element : homeTeamResults) {
            String away = "";
            String home = "";
            System.out.println("Home: " + element + " Away: " + awayTeamResults.get(i));
            homeLastFive[i][0] = element;
            homeLastFive[i][1] = awayTeamResults.get(i);

            i++;
        }

        Markup m = MarkupHelper.createTable(homeLastFive);

        test.pass(m);
    }

    public ArrayList<String> LottoNumbers(int numbers, int lines, int totalBoardNumbers) {


        setUp("LottoNumbers");
        ArrayList<String> randomNumbers = new ArrayList<>();
        String[][] lotteryNumbers = new String[lines][numbers];
        Random randomGenerator = new Random();

        for (int j = 0; j < lines; j++) {
            int i = 0;
            randomNumbers = new ArrayList<>();
            while (i < numbers) {

                Random r = new Random();
                int low = 1;
                int high = totalBoardNumbers;
                int random = r.nextInt(high - low) + low;
                boolean isfound = false;

                for (int k = 0; k < numbers; k++) {
                    if (lotteryNumbers[j][k] == Integer.toString(random)) {
                        isfound = true;
                    }
                }

                if (!isfound) {
                    randomNumbers.add(Integer.toString(random));
                    lotteryNumbers[j][i] = Integer.toString(random);
                    i++;

                }


            }
        }
        Arrays.stream(lotteryNumbers).sorted();




        Markup m = MarkupHelper.createTable(lotteryNumbers);

        test.pass(m);
        return randomNumbers;
    }
}
