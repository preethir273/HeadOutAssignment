package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

/**
 * @author preethi 
 * 
 * POM class for the tour detail page 
 */

public class TourPage {
	WebDriver driver;

	@FindBy(css = ".pg-head__top .pg-head__content .header-top-navigation__link")
	List <WebElement> eventLinkInTop;

	@FindBy(css = "#event-title")
	WebElement showTitleInBody;
	
	@FindBy(xpath = "//*[contains(@id,'BookButton')]")
	WebElement bookTicketsButton;

	public int TimeoutValue = 10;

	/**
	 * initialize driver
	 * 
	 * @param webdriver
	 *            instance
	 */
	public TourPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TimeoutValue), this);
	}

	public String getEventTextInTop() {
		return eventLinkInTop.get(eventLinkInTop.size()-1).getText();
	}
	
	public String getEventTextInBody() {
		return showTitleInBody.getText();
	}
	
	public void clickBookTicketsButton() {
		bookTicketsButton.click();
	}
}
