package test;

import java.util.Collections;
import java.awt.*;
import java.awt.event.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.CheckoutPage;
import pages.TourPage;
import pages.HomePage;
import pages.SelectDatePage;
import pages.SelectSeatPage;

public class BookMammaMiaTest {

	WebDriver driver;
	HomePage homePage;
	TourPage eventPage;
	SelectDatePage selectDatePage;
	SelectSeatPage selectSeatPage;
	CheckoutPage checkoutPage;
	String TestURL, TourName, CardHolderName, Email, Telephone, CardNumber, ExpiryDate, Cvv;

	@BeforeTest
	@Parameters({ "url", "tourName", "cardHolderName", "email", "telephone", "cardNumber", "expiryDate", "cvv" })
	public void setup(String url, String tourName, String cardHolderName, String email, String telephone,
			String cardNumber, String expiryDate, String cvv) {

		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-fullscreen");
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		driver = new ChromeDriver(options);
		TourName = tourName;
		CardHolderName = cardHolderName;
		Email = email;
		Telephone = telephone;
		CardNumber = cardNumber;
		ExpiryDate = expiryDate;
		Cvv = cvv;
		TestURL = url;
		homePage = new HomePage(driver);
		eventPage = new TourPage(driver);
		selectDatePage = new SelectDatePage(driver);
		selectSeatPage = new SelectSeatPage(driver);
		checkoutPage = new CheckoutPage(driver);
	}

	/**
	 * launch the page and search for the tour and click on the result if available
	 * 
	 */

	@Test(priority = 1)
	public void searchEvent() {
		driver.get(TestURL);
		// search for the event
		homePage.enterTextAndSearch(TourName);

		Assert.assertTrue(homePage.clickSearchResult(TourName), "Tour is not available");

	}

	/**
	 * verify tour page is opened and redirect to selecct date page and select first
	 * time slot available
	 * 
	 * @throws InterruptedException
	 */

	@Test(priority = 2, dependsOnMethods = { "searchEvent" })
	public void selectTourTime() throws InterruptedException {

		// verify tour page is opened
		Assert.assertEquals(eventPage.getEventTextInTop(), TourName,
				"Tour name is not displayed in the navgation link");
		Assert.assertTrue(eventPage.getEventTextInBody().contains(TourName), "Tour name is not displayed in the page");

		eventPage.clickBookTicketsButton();
		// verify redirect to select date page
		Assert.assertEquals(selectDatePage.getActiveStageText(), "Select date");
		// select first available time slot
		selectDatePage.selectFirstDateTime();

		Thread.sleep(5000);
		// verify redirect to select seats page
		Assert.assertEquals(selectDatePage.getActiveStageText(), "Select seats");

	}

	/**
	 * hover on seats and select seats and redirect to checkout page
	 * 
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	@Test(priority = 3, dependsOnMethods = { "selectTourTime" })
	public void selectSeats() throws AWTException, InterruptedException {
		// check multiple price range seats are available
		Assert.assertTrue(selectSeatPage.getpriceCheckboxesCount() > 1);
		Assert.assertTrue(selectSeatPage.isAddToBasketDisabled());

		WebElement canvasElement = driver.findElement(By.tagName("canvas"));
		int xStart = canvasElement.getLocation().getX();
		int yStart = canvasElement.getLocation().getY();
		int xWidth = canvasElement.getSize().getWidth();
		int yHeight = canvasElement.getSize().getHeight();
		Robot r = new Robot();
		int selectedSeatsCount = 0;
		// 187 to start mouse move on seats below stalls, 18 distance between two seats
		// and rows
		for (int y = 187 + yStart; y < yHeight + 187 && selectedSeatsCount < 4; y += 19) {
			for (int x = xStart; x < xWidth && selectedSeatsCount < 4; x += 19) {
				r.mouseMove(x, y);
				// Thread.sleep(100);
				if (canvasElement.getCssValue("cursor").equals("pointer")) {
					// click on available seats
					r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
					r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
					Thread.sleep(1000);
					// increment count
					selectedSeatsCount++;
					// verify in the selected seats - to do
				}
			}
		}

		Thread.sleep(4000);

		Assert.assertFalse(selectSeatPage.isAddToBasketDisabled(), "no seats are selected");
		// click on add to basket
		selectSeatPage.clickAddToBasket();
		// verify redirection to checkout page
		Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), "Not redirected to Checkout page");

	}

	/**
	 * enter details user and card detail in checkout page and checks for payment
	 * processing error
	 */

	@Test(priority = 4, dependsOnMethods = { "selectSeats" })
	public void checkout() {
		checkoutPage.acceptTermsNConditions();
		checkoutPage.selectCardPayment();

		checkoutPage.enterFullName(CardHolderName);
		checkoutPage.enterEmail(Email);
		checkoutPage.enterTelephone(Telephone);
		checkoutPage.enterCardNumber(CardNumber);
		checkoutPage.enterExpiryDate(ExpiryDate);
		checkoutPage.enterCvv(Cvv);

		checkoutPage.clickCompleteBooking();

		Assert.assertTrue(checkoutPage.isPaymentErrorDisplayed());

	}

	/**
	 * closes the browser tabs
	 */
	@AfterTest
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
