package testSuits;

import Objects.AmazonPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ActualTest {

    WebDriver driver;
    AmazonPage ap;

    @BeforeTest
    public void beforeTest(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.in/");
    }

    @Test(priority = 1)
    public void validateRelatedProducts() throws InterruptedException {
        ap = new AmazonPage(driver);
        ap.SelectByValue("Electronics");
        ap.enterTextToSearch("IPhone 13");
        ap.getListOfSuggestions();
        ap.printListOfSuggestionsAndValidate();
        Thread.sleep(2000);
    }

    @Test(priority = 2)
    public void validateNewTab(){
        ap = new AmazonPage(driver);
        ap.clearSearchField();
        ap.SelectByValue("Electronics");
        ap.enterTextToSearch("iPhone 13 128 GB");
        ap.getListOfSuggestions();
        ap.printListOfSuggestionsAndClick("iPhone 13 128 GB");
        ap.clickOnSearchResultOP();
        String parentWindowTitle = ap.getParentWindowTitle();
        String childWindowTitle = ap.navigateToNewTab();
        Assert.assertNotEquals(parentWindowTitle, childWindowTitle);
    }

    @Test(priority = 3)
    public void verifyQuickLookAndVerifyTheProduct(){
        ap = new AmazonPage(driver);
        ap.navigateToAppleStore();
        ap.clickOnAppleWatchSEDropdown();
        ap.selectTheAppleWatch();
        ap.hoverOnElement();
        Assert.assertTrue(ap.isDisplayed());
        Assert.assertTrue(ap.getText().contains("Apple Watch SE") && ap.getText().contains("GPS + Cellular"));
    }

    @AfterTest
    public void afterTest(){
        driver.quit();
    }

}
