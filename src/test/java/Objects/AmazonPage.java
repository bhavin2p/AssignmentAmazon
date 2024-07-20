package Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class AmazonPage {

    WebDriver driver;
    WebDriverWait wait;
    Actions action;

    public AmazonPage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5000));
        action = new Actions(driver);
    }
    By searchDropdown = By.id("searchDropdownBox");
    By searchField = By.id("twotabsearchtextbox");
    By suggestionBox = By.xpath("//div[@id='nav-flyout-searchAjax']//div[@class='s-suggestion s-suggestion-ellipsis-direction']");
    By clickOnSearchedResult = By.xpath("(//a/span[contains(@class, 'text-normal') and contains(text(),'Apple iPhone 13')])[1]");
    By visitAppleStore = By.xpath("//a[@id='bylineInfo']");
    By appleWatchSEDropDown = By.xpath("//span[text()='Apple Watch']/parent::a[@aria-haspopup='true']");
    By clickValueappleWatchSE = By.xpath("//span[contains(text(),'SE (GPS + Cellular)')]/parent::a");
    By appleWatchSearchResult = By.xpath("(//div[@class='EditorialTile__innerContent__n92i8']/a)[1]");
    By quickLook = By.xpath("//button[@data-testid='quick-look-button']");

    public boolean isDisplayed(){
        return driver.findElement(quickLook).isDisplayed();
    }

    public String getText(){
        return driver.findElement(appleWatchSearchResult).getAttribute("title").toString();
    }

    public void selectTheAppleWatch(){
        waitForElementToBeClickable(clickValueappleWatchSE);
        driver.findElement(clickValueappleWatchSE).click();
    }
    public void clickOnAppleWatchSEDropdown(){
        driver.findElement(appleWatchSEDropDown).click();
    }

    public void navigateToAppleStore(){
        driver.findElement(visitAppleStore).click();
    }

    public void hoverOnElement(){
        moveToElement(appleWatchSearchResult);
    }

    public void clickOnSearchResultOP(){
        waitForElementToBeVisible(clickOnSearchedResult);
        driver.findElement(clickOnSearchedResult).click();
    }

    public String getParentWindowTitle(){
        return driver.getTitle();
    }

    public String navigateToNewTab(){
        String parentWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();
        String child = null;
        for(String s : windows){
            if(!parentWindow.equals(s)){
                driver.switchTo().window(s);
                String newTabTitle = driver.getTitle();
                System.out.println("new window Title = " + newTabTitle);
                child = newTabTitle;
                return newTabTitle;
            }
        }
        return child;
    }

    public void moveToElement(By locator){
        action.moveToElement(driver.findElement(locator)).build().perform();
    }

    public void waitForElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public void waitForElementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void SelectByValue(String value){
        Select select = new Select(driver.findElement(searchDropdown));
        select.selectByVisibleText(value);
    }

    public void enterTextToSearch(String value){
        driver.findElement(searchField).sendKeys(value);
    }
    public List<WebElement> getListOfSuggestions(){
        waitForElementToBeVisible(suggestionBox);
        return driver.findElements(suggestionBox);

    }
    public void printListOfSuggestionsAndValidate(){
        List<WebElement> suggestions = getListOfSuggestions();
        for (WebElement e : suggestions) {
            String suggestedElement = e.getAttribute("aria-label").toLowerCase();
            System.out.println(suggestedElement);
            Assert.assertTrue(suggestedElement.contains("iphone"));
        }
    }

    public void clearSearchField(){
        driver.navigate().refresh();
        driver.findElements(searchField).clear();
    }

    public void printListOfSuggestionsAndClick(String valueToClick){
        waitForElementToBeVisible(suggestionBox);
        List<WebElement> suggestions = getListOfSuggestions();
        System.out.println("Size of new list = " +suggestions.size());
        for(WebElement e : suggestions){
            String suggestedElement = e.getAttribute("aria-label").toString().toLowerCase();
            System.out.println(suggestedElement);
            if(suggestedElement.equalsIgnoreCase(valueToClick)){
                System.out.println(suggestedElement);
                e.click();
                break;
            }
        }
    }



}
