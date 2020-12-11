package commons;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPage {

	protected final Log log;

	protected AbstractPage() {
		log = LogFactory.getLog(getClass());
	}

	public void openPageUrl(WebDriver driver, String pageUrl) {
		driver.get(pageUrl);

	}

	public void setImplicitWait(WebDriver driver, long timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}

	// 2 - get tille
	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	// 3 - get current URL
	public String getPageUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	// 4 - get page source
	public String getPageSource(WebDriver driver) {
		return driver.getPageSource();
	}

	// 5 - Back to page
	public void backToPage(WebDriver driver) {
		driver.navigate().back();
	}

	// 6 - refesh current page
	public void refeshCurrentPage(WebDriver driver) {
		driver.navigate().refresh();
	}

	// 7 - forward to page
	public void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}

	public void waitAlertPresence(WebDriver driver) {
		try {
			explicitWait = new WebDriverWait(driver, longTimeOut);
			explicitWait.until(ExpectedConditions.alertIsPresent());
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	// 8- accept Alert
	public void acceptAlert(WebDriver driver) {
		waitAlertPresence(driver);
		alert = driver.switchTo().alert();
		alert.accept();
	}

	// 9- cancel Alert
	public void cancelAlert(WebDriver driver) {
		waitAlertPresence(driver);
		alert = driver.switchTo().alert();
		alert.dismiss();
	}

	// 10 - cancel Alert
	public String getAlertText(WebDriver driver) {
		waitAlertPresence(driver);
		alert = driver.switchTo().alert();
		return alert.getText();
	}

	// 11 - sendkey to Alert
	public void sendkeyToAlert(WebDriver driver, String text) {
		waitAlertPresence(driver);
		alert = driver.switchTo().alert();
		alert.sendKeys(text);
		// gá»�i hÃ m
		// senkeyToAlert(driver, "Automation");
	}

	// Window
	public void switchToWindowByID(WebDriver driver, String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindow : allWindows) {
			if (!runWindow.equals(parentID)) {
				driver.switchTo().window(runWindow);
				break;
			}
		}
	}

	public void switchToWindowByTitle(WebDriver driver, String title) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			driver.switchTo().window(runWindows);
			String currentWin = driver.getTitle();
			if (currentWin.equals(title)) {
				break;
			}
		}
	}

	public void closeAllWindowsWithoutParent(WebDriver driver, String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			if (!runWindows.equals(parentID)) {
				driver.switchTo().window(runWindows);
				driver.close();
			}
		}
		driver.switchTo().window(parentID);
	}

	// II Web element
	// 1 - Find element
	public WebElement find(WebDriver driver, String xpathValue) {
		return driver.findElement(byXpath(xpathValue));
	}

	// 1.1 - Finds element
	public List<WebElement> finds(WebDriver driver, String xpathValue) {
		return driver.findElements(byXpath(xpathValue));
	}

	public By byXpath(String xpathValue) {
		return By.xpath(xpathValue);
	}

	// 2 - click
	public void clickToElement(WebDriver driver, String xpathValue) {
		try {
			find(driver, xpathValue).click();
		} catch (Exception e) {
			log.debug("Element is not clickable:" + e.getMessage());
		}

	}

	public String getDynamicLocator(String xpathValue, String... values) {
		xpathValue = String.format(xpathValue, (Object[]) values);
		return xpathValue;
	}

	public void clickToElement(WebDriver driver, String xpathValue, String values) {
		try {
			find(driver, getDynamicLocator(xpathValue, values)).click();
		} catch (Exception e) {
			log.debug("Element is not clickable:" + e.getMessage());
		}

	}

	// 3 - sendkey
	public void sendkeyToElement(WebDriver driver, String xpathValue, String value) {
		element = find(driver, xpathValue);
		element.clear();
		element.sendKeys(value);

	}

	public void sendkeyToElement(WebDriver driver, String xpathValue, String value, String... values) {
		element = find(driver, getDynamicLocator(xpathValue, values));
		element.clear();
		element.sendKeys(value);

	}

	public void selectItemInDropdown(WebDriver driver, String xpathValue, String itemValue) {
		select = new Select(find(driver, xpathValue));
		select.selectByVisibleText(itemValue);
	}

	public String getSelectedItemInDropdown(WebDriver driver, String xpathValue, String itemValue) {
		select = new Select(find(driver, xpathValue));
		return select.getFirstSelectedOption().getText();
	}

	public Boolean isDropdownMultiple(WebDriver driver, String xpathValue) {
		select = new Select(find(driver, xpathValue));
		return select.isMultiple();
	}

	// Select item trong dropdown custom
	public void selectItemInCustomDropdown(WebDriver driver, String parentLocator, String childItemLocator,
			String expectedItem) {

		find(driver, parentLocator).click();
		sleepInSecond(1);

		explicitWait = new WebDriverWait(driver, longTimeOut);
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childItemLocator)));

		elements = finds(driver, childItemLocator);

		for (WebElement item : elements) {

			if (item.getText().equals(expectedItem)) {

				jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(1);

				item.click();
				sleepInSecond(1);
				break;
			}
		}
	}

	public void sleepInSecond(long timeout) {
		try {
			Thread.sleep(timeout * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getElementAttribute(WebDriver driver, String xpathValue, String attributeName) {
		return find(driver, xpathValue).getAttribute(attributeName);
	}

	public String getElementText(WebDriver driver, String xpathValue) {
		return find(driver, xpathValue).getText();
	}

	public String getElementText(WebDriver driver, String xpathValue, String... values) {
		return find(driver, getDynamicLocator(xpathValue, values)).getText().trim();
	}

	public int countElementNumber(WebDriver driver, String xpathValue) {
		return finds(driver, xpathValue).size();
	}

	public int countElementNumber(WebDriver driver, String xpathValue, String... values) {
		return finds(driver, getDynamicLocator(xpathValue, values)).size();
	}

	public void checkToCheckbox(WebDriver driver, String xpathValue) {
		element = find(driver, xpathValue);
		if (!element.isSelected()) {
			clickToElementByJS(driver, xpathValue);
		}
	}

	public void checkToCheckbox(WebDriver driver, String xpathValue, String... values) {
		element = find(driver, getDynamicLocator(xpathValue, values));
		if (!element.isSelected()) {
			clickToElementByJS(driver, getDynamicLocator(xpathValue, values));
		}
	}

	public void uncheckToCheckbox(WebDriver driver, String xpathValue) {
		element = find(driver, xpathValue);
		if (element.isSelected()) {
			clickToElementByJS(driver, xpathValue);
		}
	}

	public void uncheckToCheckbox(WebDriver driver, String xpathValue, String... values) {
		element = find(driver, getDynamicLocator(xpathValue, values));
		if (element.isSelected()) {
			clickToElementByJS(driver, getDynamicLocator(xpathValue, values));
		}
	}

	public boolean isElementDisplayed(WebDriver driver, String xpathValue) {
		try {
			return find(driver, xpathValue).isDisplayed();
		} catch (Exception e) {
			log.debug("Element is not displayed with error:" + e.getMessage());
			return false;
		}

	}

	public boolean isElementDisplayed(WebDriver driver, String xpathValue, String... values) {
		try {
			return find(driver, getDynamicLocator(xpathValue, values)).isDisplayed();
		} catch (Exception e) {
			log.debug("Element is not displayed with error:" + e.getMessage());
			return false;
		}

	}

	public boolean isElementEnable(WebDriver driver, String xpathValue) {
		return find(driver, xpathValue).isEnabled();
	}

	public boolean isElementSelected(WebDriver driver, String xpathValue) {
		return find(driver, xpathValue).isSelected();
	}

	public void switchToFrameorIframe(WebDriver driver, String xpathValue) {
		try {
			driver.switchTo().frame(find(driver, xpathValue));
		} catch (Exception e) {
			log.debug("No frame/iframe:" + e.getMessage());
		}

	}

	public void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	public void hoverToElement(WebDriver driver, String xpathValue) {
		action = new Actions(driver);
		action.moveToElement(find(driver, xpathValue)).perform();
	}

	public void sendkeyBoardToElement(WebDriver driver, String xpathValue, Keys key) {
		action = new Actions(driver);
		action.sendKeys(find(driver, xpathValue), key);
	}

	public void highlightElement(WebDriver driver, String xpathValue) {
		element = find(driver, xpathValue);
		String originalStyle = element.getAttribute("style");
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				"border: 5px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				originalStyle);

	}

	public void clickToElementByJS(WebDriver driver, String xpathValue) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", find(driver, xpathValue));
	}

	public void clickToElementByJS(WebDriver driver, String xpathValue, String... values) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", find(driver, getDynamicLocator(xpathValue, values)));
	}

	public void scrollToElement(WebDriver driver, String xpathValue) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", find(driver, xpathValue));
		sleepInSecond(1);
	}

	public void scrollToElement(WebDriver driver, String xpathValue, String... values) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);",
				find(driver, getDynamicLocator(xpathValue, values)));
	}

	public void scrollToAboveTop(WebDriver driver) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scroll(0,0)");
	}

	public void scrollToBottom(WebDriver driver) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.bdy.scrollHeight)");
	}

	public void sendkeyToElementByJS(WebDriver driver, String xpathValue, String value) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", find(driver, xpathValue));
	}

	public void removeAttributeInDOM(WebDriver driver, String xpathValue, String attributeRemove) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", find(driver, xpathValue));
	}

	public void removeAttributeInDOM(WebDriver driver, String xpathValue, String attributeRemove, String... values) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');",
				find(driver, getDynamicLocator(xpathValue, values)));
	}

	/*
	 * public boolean isImageLoaded(WebDriver driver, String xpathValue) { element =
	 * find(driver, xpathValue); jsExecutor = (JavascriptExecutor) driver; boolean
	 * status = (boolean) jsExecutor.
	 * executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0"
	 * , element); if (status) { return true; } else { return false; } }
	 * 
	 * public boolean isImageLoaded(WebDriver driver, String
	 * xpathValue,String...values) { element = find(driver,
	 * getDynamicLocator(xpathValue, values)); jsExecutor = (JavascriptExecutor)
	 * driver; boolean status = (boolean) jsExecutor.
	 * executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0"
	 * , element); if (status) { return true; } else { return false; } }
	 */
	public boolean waitForJStoLoad(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
		jsExecutor = (JavascriptExecutor) driver;
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}

		};
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};
		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}

	public void waitElementVisible(WebDriver driver, String xpathValue) {
		try {
			explicitWait = new WebDriverWait(driver, longTimeOut);
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(byXpath(xpathValue)));
		} catch (Exception e) {
			log.debug("Wait for element visible with error:" + e.getMessage());
		}

	}

	public void waitElementVisible(WebDriver driver, String xpathValue, String... values) {
		try {
			explicitWait = new WebDriverWait(driver, longTimeOut);
			explicitWait.until(
					ExpectedConditions.visibilityOfElementLocated(byXpath(getDynamicLocator(xpathValue, values))));
		} catch (Exception e) {
			log.debug("Wait for element visible with error:" + e.getMessage());
		}

	}

	public void waitElementInvisible(WebDriver driver, String xpathValue) {
		explicitWait = new WebDriverWait(driver, longTimeOut);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(byXpath(xpathValue)));
	}

	public void waitElementClickable(WebDriver driver, String xpathValue) {
		try {
			explicitWait = new WebDriverWait(driver, longTimeOut);
			explicitWait.until(ExpectedConditions.elementToBeClickable(byXpath(xpathValue)));
		} catch (Exception e) {
			log.debug("Wait for element clickable with error: " + e.getMessage());
		}

	}

	public void waitElementClickable(WebDriver driver, String xpathValue, String... values) {
		try {
			explicitWait = new WebDriverWait(driver, longTimeOut);
			explicitWait.until(ExpectedConditions.elementToBeClickable(byXpath(getDynamicLocator(xpathValue, values))));
		} catch (Exception e) {
			log.debug("Wait for element clickable with error: " + e.getMessage());

		}

	}

	
	public void overrideGlobalTimeout(WebDriver driver, long timeInSecond) {
		driver.manage().timeouts().implicitlyWait(timeInSecond, TimeUnit.SECONDS);
	}

	public boolean isElementUndisplayed(WebDriver driver, String locator) {
		System.out.println("Start time = " + new Date().toString());
		overrideGlobalTimeout(driver, shortTimeOut);
		elements = finds(driver, locator);
		overrideGlobalTimeout(driver, longTimeOut);
		if (elements.size() == 0) {
			System.out.println("Element not in DOM");
			System.out.println("End time = " + new Date().toString());

			return true;
		} else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
			System.out.println("Element in DOM but not visible/displayed");
			System.out.println("End time = " + new Date().toString());

			return true;
		} else {
			System.out.println("Element in DOM and visible");

			return false;
		}

	}

	public boolean isElementUndisplayed(WebDriver driver, String locator, String... value) {
		System.out.println("Start time = " + new Date().toString());
		overrideGlobalTimeout(driver, shortTimeOut);
		elements = finds(driver, getDynamicLocator(locator, value));
		overrideGlobalTimeout(driver, longTimeOut);
		if (elements.size() == 0) {
			System.out.println("Element not in DOM");
			System.out.println("End time = " + new Date().toString());

			return true;
		} else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
			System.out.println("Element in DOM but not visible/displayed");
			System.out.println("End time = " + new Date().toString());

			return true;
		} else {
			System.out.println("Element in DOM and visible");

			return false;
		}
	}

	private long longTimeOut = 30;
	private long shortTimeOut = 5;
	private Actions action;
	private Alert alert;
	private Select select;
	private WebElement element;
	private List<WebElement> elements;
	private WebDriverWait explicitWait;
	private JavascriptExecutor jsExecutor;
	private String osName = System.getProperty("os.name");
}
