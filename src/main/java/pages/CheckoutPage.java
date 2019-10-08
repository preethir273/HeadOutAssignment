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
 * POM class for the checkout page for selected tickets
 */

public class CheckoutPage {

	WebDriver driver;
	WebDriverWait wait;

	@FindBy(xpath = "//h1[text()='Checkout']")
	WebElement checkoutPageHeading;

	@FindBy(xpath = "//*[contains(text(),'I agree')]")
	WebElement termsNConds;

	@FindBy(xpath = "//*[contains(text(),'Credit or Debit Card')]")
	WebElement cardRadioButton;

	@FindBy(css = "input[type='text'][name='name']")
	WebElement fullName;

	@FindBy(css = "input[type='email'][name='email']")
	WebElement email;

	@FindBy(css = "input[type='tel'][name='tel']")
	WebElement telephone;

	@FindBy(css = "input[type='tel'][name='card']")
	WebElement cardNumber;

	@FindBy(css = "input[type='tel'][name='expiry']")
	WebElement expiryDate;

	@FindBy(css = "input[type='tel'][name='cvv']")
	WebElement cvv;

	@FindBy(xpath = "//button[./*[text()='Complete booking']]")
	WebElement completeBookingButton;

	By paymentErrorModal = By
			.xpath("//div[contains(@class,'modal__body content--danger')]/h1[text()='Payment not processed']");

	public int TimeoutValue = 12;

	public CheckoutPage(WebDriver driver) {

		this.driver = driver;
		wait = new WebDriverWait(driver, 20);
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TimeoutValue), this);
	}

	public boolean isCheckoutPageDisplayed() {

		wait.until(ExpectedConditions.visibilityOf(checkoutPageHeading));
		return checkoutPageHeading.isDisplayed();
	}

	public void acceptTermsNConditions() {
		termsNConds.click();
	}

	public void selectCardPayment() {
		cardRadioButton.click();
	}

	public void enterFullName(String inputText) {
		fullName.sendKeys(inputText);
	}

	public void enterEmail(String inputText) {
		email.sendKeys(inputText);
	}

	public void enterTelephone(String inputText) {
		telephone.sendKeys(inputText);
	}

	public void enterCardNumber(String inputText) {
		cardNumber.sendKeys(inputText);
	}

	public void enterExpiryDate(String inputText) {
		expiryDate.sendKeys(inputText);
	}

	public void enterCvv(String inputText) {
		cvv.sendKeys(inputText);
	}

	public void clickCompleteBooking() {
		completeBookingButton.click();
	}

	public boolean isPaymentErrorDisplayed() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(paymentErrorModal));
		return driver.findElements(paymentErrorModal).size() > 0;
	}
}
