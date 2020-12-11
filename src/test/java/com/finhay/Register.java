package com.finhay;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.AbstractTest;
import pageObjects.finhay.PageGeneratorManager;
import pageObjects.finhay.RegisterPageObject;
import pageObjects.finhay.VerifyPhonePageObject;


public class Register extends AbstractTest {
	@Parameters({"browser","url"})
	@BeforeClass
	public void beforeClass(String browserName,String url) {
		driver = getBrowserDriver(browserName, url);
		 registerPage = PageGeneratorManager.getRegisterPage(driver);
	}
 @Test
public void Register_01_No_Input_Email_Field() {
registerPage.inputToPhoneNumberField("0345845811");
registerPage.inputToFullNameField("Kim Oanh");
registerPage.inputToPasswordField("12345678");
verifyTrue(registerPage.isDisableButtonRegister());
}
 
 @Test
public void Register_02_Input_Invalid_Email_Filed() {
	registerPage.inputToEmailField("oanhkim123");
	registerPage.inputToPhoneNumberField("0345845811");
	registerPage.inputToFullNameField("Kim Oanh");
	registerPage.inputToPasswordField("12345678");
	registerPage.clickButtonRegister();
	verifyTrue(registerPage.isDisableButtonRegister());
	verifyTrue(registerPage.isDisplayErorMessageInvalidEmail());
 
}
 @Test
 public void Register_03_Input_PhoneNumber_Filed_Least_8_Char() {
 	registerPage.inputToEmailField("oanhkim123@gmail.com");
 	registerPage.inputToPhoneNumberField("0345845811");
 	registerPage.inputToFullNameField("Kim Oanh");
 	registerPage.inputToPasswordField("123456");
 	verifyTrue(registerPage.isDisableButtonRegister());
 	  
 }
 @Test
 public void Register_04_Sucessful() {
 	registerPage.inputToEmailField("oanhkim3010199961211@gmail.com");
 	registerPage.inputToPhoneNumberField("0344877444");
 	registerPage.inputToFullNameField("Kim Oanh");
 	registerPage.inputToPasswordField("12345678");
 	 verifyPhonePage = PageGeneratorManager.getVerifyPhonePage(driver);
 	verifyPhonePage = registerPage.clickButtonRegister();
       verifyTrue(verifyPhonePage.isDislayVerifyPhone());
 	  
 }
 
 
		@AfterClass
		  public void afterClass() {
			 
			closeBrowserAndDriver(driver);
		 
	}
	WebDriver driver;
RegisterPageObject registerPage;
VerifyPhonePageObject verifyPhonePage;
	
}
