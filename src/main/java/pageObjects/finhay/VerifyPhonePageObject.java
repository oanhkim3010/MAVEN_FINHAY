package pageObjects.finhay;

import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import pageUIs.finhay.RegisterPageUI;
import pageUIs.finhay.VerifyPhonePageUI;

public class VerifyPhonePageObject extends AbstractPage {
	WebDriver driver;

	public  VerifyPhonePageObject(WebDriver driver) {
	
		this.driver = driver;
	} 
	public boolean isDislayVerifyPhone() {
		waitElementVisible(driver, VerifyPhonePageUI.VERIFY_PHONE_TEXT);
		return isElementDisplayed(driver, VerifyPhonePageUI.VERIFY_PHONE_TEXT);
	}
}
