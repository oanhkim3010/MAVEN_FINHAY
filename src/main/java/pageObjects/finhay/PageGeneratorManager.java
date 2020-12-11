package pageObjects.finhay;

import org.openqa.selenium.WebDriver;


public class PageGeneratorManager {
	public static RegisterPageObject getRegisterPage(WebDriver driver) {
		return new RegisterPageObject(driver);
	}

	public static LoginPageObject getLoginPage(WebDriver driver) {
		return new LoginPageObject(driver);
	}
	public static VerifyPhonePageObject getVerifyPhonePage(WebDriver driver) {
		return new VerifyPhonePageObject(driver);
	}
	
}
