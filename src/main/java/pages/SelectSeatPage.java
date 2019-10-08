package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.Select;

/**
 * @author preethi
 *
 *         POM class for select seats for the tour page
 */
public class SelectSeatPage {

	WebDriver driver;

	@FindBy(xpath = "//input[@type='checkbox']")
	List<WebElement> priceCheckbox;

	@FindBy(css = "a[class*='seat-plan__submit__button']")
	WebElement addToBasketButton;

	public int TimeoutValue = 10;

	public SelectSeatPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TimeoutValue), this);
	}

	public int getpriceCheckboxesCount() {
		return priceCheckbox.size();
	}

	public boolean isAddToBasketDisabled() {
		return (addToBasketButton.getAttribute("disabled")!=null);
	}

	public void clickAddToBasket() {
		addToBasketButton.click();
	}
}
