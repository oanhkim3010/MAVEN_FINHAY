package pageObjects.finhay;

import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import pageUIs.finhay.LoginPageUI;

public class LoginPageObject extends AbstractPage {
	private WebDriver driver;
	public LoginPageObject(WebDriver driver) {
		
		this.driver = driver;
	}
	public void inputToEmailTextbox(String emailValue) {
		waitElementVisible(driver, LoginPageUI.EMAIL_OR_PHONE_TEXTBOX);
		sendkeyToElement(driver,  LoginPageUI.EMAIL_OR_PHONE_TEXTBOX, emailValue);
		
	}
	public void inputToPasswordTextbox(String passwordValue) {
		waitElementVisible(driver, LoginPageUI.PASSWORD);
		sendkeyToElement(driver,  LoginPageUI.PASSWORD, passwordValue);
		
	}
	public void clickToButtonLogin() {
		waitElementVisible(driver, LoginPageUI.LOGIN_BUTTON);
		clickToElement(driver, LoginPageUI.LOGIN_BUTTON);
		
	}
	public boolean isDisplayErrorMessageWrongEmail() {
		waitElementVisible(driver, LoginPageUI.ERROR_MESSAGE_WRONG_EMAIL);
		return isElementDisplayed(driver, LoginPageUI.ERROR_MESSAGE_WRONG_EMAIL);
	}
	public boolean isDisableButtonLogin() {
		waitElementVisible(driver, LoginPageUI.LOGIN_BUTTON);
		return isElementUndisplayed(driver, LoginPageUI.LOGIN_BUTTON);
	}

}
