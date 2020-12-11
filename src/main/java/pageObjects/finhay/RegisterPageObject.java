package pageObjects.finhay;

import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import pageUIs.finhay.RegisterPageUI;

public class RegisterPageObject extends AbstractPage {
	private WebDriver driver;

	public RegisterPageObject(WebDriver driver) {

		this.driver = driver;
	}

	public boolean isDisableButtonRegister() {
		waitElementVisible(driver, RegisterPageUI.REGISTER_BUTTON);
		return isElementUndisplayed(driver, RegisterPageUI.REGISTER_BUTTON);
	}

	public void inputToPhoneNumberField(String phoneValue) {
		waitElementVisible(driver, RegisterPageUI.PHONE_TEXTBOX);
		sendkeyToElement(driver, RegisterPageUI.PHONE_TEXTBOX, phoneValue);
	}

	public void inputToFullNameField(String fullNameValue) {
		waitElementVisible(driver, RegisterPageUI.FULLNAME_TEXTBOX);
		sendkeyToElement(driver, RegisterPageUI.FULLNAME_TEXTBOX, fullNameValue);

	}

	public void inputToPasswordField(String passwordValue) {
		waitElementVisible(driver, RegisterPageUI.PASSWORD_TEXTBOX);
		sendkeyToElement(driver, RegisterPageUI.PASSWORD_TEXTBOX, passwordValue);

	}

	public void inputToEmailField(String emailValue) {
		waitElementVisible(driver, RegisterPageUI.EMAIL_TEXTBOX);
		sendkeyToElement(driver, RegisterPageUI.EMAIL_TEXTBOX, emailValue);

	}

	public boolean isDisplayErorMessageInvalidEmail() {
		waitElementVisible(driver, RegisterPageUI.ERROR_MESSAGE_INVALID_EMAIL);
		return isElementDisplayed(driver, RegisterPageUI.ERROR_MESSAGE_INVALID_EMAIL);
	}

	public VerifyPhonePageObject clickButtonRegister() {
		waitElementVisible(driver, RegisterPageUI.REGISTER_BUTTON);
		clickToElement(driver,  RegisterPageUI.REGISTER_BUTTON);
		return PageGeneratorManager.getVerifyPhonePage(driver);

	}

}
