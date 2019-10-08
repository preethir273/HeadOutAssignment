package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author preethi
 *
 *         POM class for HomePage
 */
public class HomePage {
	WebDriver driver;
	String searchResultCardXpath = "//*[@class = 'search-results']//*[@class='events-container']//a";

	@FindBy(css = "input[class*='search__input']")
	WebElement searchBar;

	@FindBy(css = "input[class*='search__input']+button[class*='search__submit']")
	WebElement searchButton;

	By searchResultText = By
			.xpath("//*[@class = 'search-results']//*[@class='events-container']//*[@class='card__content']");
	By searchResultLink = By.xpath(searchResultCardXpath);

	public int TimeoutValue = 10;

	public HomePage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TimeoutValue), this);
	}

	public void enterTextAndSearch(String SearchText) {
		searchBar.sendKeys(SearchText);
		searchButton.click();
	}

	public boolean clickSearchResult(String TourName) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultLink));
			if (driver.findElements(searchResultLink).size() > 0) {
				// check for the tour name in result
				String resultTourXpath = searchResultCardXpath + "[.//*[contains(text(),'" + TourName + "')]]";
				if (driver.findElements(By.xpath(resultTourXpath)).size() > 0) {
					driver.findElement(By.xpath(resultTourXpath)).click();
					return true;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

}
