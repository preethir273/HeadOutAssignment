package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

/**
 * @author preethi 
 * 
 * POM class for the select tour date page 
 */

public class SelectDatePage {
	
	WebDriver driver;

	@FindBy(xpath = "//li[@class='done active']")
	WebElement activeStage;
	
	@FindBy(css = ".booking-calendar__link")
	WebElement dateTimeSlot;
	
	public int TimeoutValue = 10;

	public SelectDatePage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TimeoutValue), this);
	}

	
	public String getActiveStageText() {
		return 	 activeStage.getText();
	}
	
	public void selectFirstDateTime() {
		dateTimeSlot.click();
	}

}
