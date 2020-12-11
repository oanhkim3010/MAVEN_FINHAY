package com.finhay;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.AbstractTest;
import pageObjects.finhay.LoginPageObject;
import pageObjects.finhay.PageGeneratorManager;

public class Login extends AbstractTest{
	@Parameters({"browser","url"})
	@BeforeClass
	public void beforeClass(String browserName,String url) {
		driver = getBrowserDriver(browserName, url);
		loginPage = PageGeneratorManager.getLoginPage(driver);
	}
	 @Test
	 public void Login_01_Input_Incorrect_Email() {
		 loginPage = PageGeneratorManager.getLoginPage(driver);
		 loginPage.inputToEmailTextbox("oanhkim30101996");
		 loginPage.inputToPasswordTextbox("12345678");
		 loginPage.clickToButtonLogin();
		 verifyTrue(loginPage.isDisplayErrorMessageWrongEmail());
	 }
	 @Test
	 public void Login_02_Input_Password_Least_8_Char() {
		 loginPage.inputToEmailTextbox("oanhkim30101996i");
		 loginPage.inputToPasswordTextbox("12345");
		 verifyTrue(loginPage.isDisableButtonLogin());
	 }
	 @AfterClass
	  public void afterClass() {
		 
		closeBrowserAndDriver(driver);
	 
}
	WebDriver driver;
	LoginPageObject loginPage;
}
