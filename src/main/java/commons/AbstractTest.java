package commons;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class AbstractTest {
	// Declare Log
	protected final Log log;

	// Constructor
	protected AbstractTest() {
		log = LogFactory.getLog(getClass());
	}

	protected static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();

// Hàm dùng chung cho tầng testcase(Test Class)
	protected WebDriver getBrowserDriver(String browserName,String url) {
		Browser browser = Browser.valueOf(browserName.toUpperCase());
		if (browser == Browser.FIREFOX) {
			// System.setProperty("webdriver.gecko.driver", ".\\browserdrivers\\geckodriver.exe");
			WebDriverManager.firefoxdriver().setup();
			setDriver(new FirefoxDriver());
		} else if (browser == Browser.CHROME) {
			// System.setProperty("webdriver.chrome.driver", ".\\browserdrivers\\chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			setDriver(new ChromeDriver());
		} else if (browser == Browser.EDGE) {
			// System.setProperty("webdriver.edge.driver", ".\\browserdrivers\\msedgedriver.exe");
			WebDriverManager.edgedriver().setup();
			setDriver(new EdgeDriver());
		} else if (browser == Browser.COCCOC) {
			// System.setProperty("webdriver.chrome.driver", ".\\browserdrivers\\chromedriver83.exe");
			WebDriverManager.chromedriver().driverVersion("83.0.4103.39").setup();
			ChromeOptions options = new ChromeOptions();

			options.setBinary("C:\\Users\\Admin\\AppData\\Local\\CocCoc\\Browser\\Application\\browser.exe");
			setDriver(new ChromeDriver(options));
		} else {
			throw new RuntimeException("Please input your browser name !");
		}
		System.out.println("Driver at Abstract Test = " + getDriver().toString());
		//getDriver().get(browserName);
		getDriver().get(url);
		// getDriver().get("http://live.demoguru99.com/index.php/backendlogin");
		// getDriver().get("http://blueimp.github.io/jQuery-File-Upload/");
		getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return getDriver();

	}

	protected void removeDriver() {
		getDriver().quit();
		threadLocalDriver.remove();
	}

	private WebDriver getDriver() {
		return threadLocalDriver.get();
	}

	private void setDriver(WebDriver driver) {
		threadLocalDriver.set(driver);
	}

	protected int randomNumber() {
		Random rand = new Random();
		return rand.nextInt(99999);
	}

	private boolean checkTrue(boolean condition) {
		boolean pass = true;
		try {
			if (condition == true) {
				log.info(" -------------------------- PASSED -------------------------- ");
			} else {
				log.info(" -------------------------- FAILED -------------------------- ");
			}
			Assert.assertTrue(condition);
		} catch (Throwable e) {
			pass = false;

			// Add lỗi vào ReportNG
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyTrue(boolean condition) {
		return checkTrue(condition);
	}

	private boolean checkFailed(boolean condition) {
		boolean pass = true;
		try {
			if (condition == false) {
				log.info(" -------------------------- PASSED -------------------------- ");
			} else {
				log.info(" -------------------------- FAILED -------------------------- ");
			}
			Assert.assertFalse(condition);
		} catch (Throwable e) {
			pass = false;
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyFalse(boolean condition) {
		return checkFailed(condition);
	}

	private boolean checkEquals(Object actual, Object expected) {
		boolean pass = true;
		try {
			Assert.assertEquals(actual, expected);
			log.info(" -------------------------- PASSED -------------------------- ");
		} catch (Throwable e) {
			pass = false;
			log.info(" -------------------------- FAILED -------------------------- ");
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyEquals(Object actual, Object expected) {
		return checkEquals(actual, expected);
	}

	protected void closeBrowserAndDriver(WebDriver driver) {
		try {
			// get ra tên của OS và convert qua chữ thường
			String osName = System.getProperty("os.name").toLowerCase();
// Khai báo 1 biến command line để thực thi
			String cmd = "";
			if (driver != null) {
				driver.quit();
			}

			if (driver.toString().toLowerCase().contains("chrome")) {
				if (osName.toLowerCase().contains("mac")) {
					cmd = "pkill chromedriver";
				} else if (osName.toLowerCase().contains("windows")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq chromedriver*\"";
				}
			} else if (driver.toString().toLowerCase().contains("internetexplorer")) {
				if (osName.toLowerCase().contains("window")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq IEDriverServer*\"";
				}
			} else if (driver.toString().toLowerCase().contains("firefox")) {
				if (osName.toLowerCase().contains("mac")) {
					cmd = "pkill geckodriver";
				} else if (osName.toLowerCase().contains("windows")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq geckodriver*\"";
				}
			}

			Process process = Runtime.getRuntime().exec(cmd);
			process.waitFor();

			log.info("---------- QUIT BROWSER SUCCESS ----------");
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}
	protected String getCurrentDay() {
		DateTime nowUTC = new DateTime();
		int day = nowUTC.getDayOfMonth();
		if (day < 10) {
			String dayValue = "0" + day;
			return dayValue;
		}
		return String.valueOf(day);
	}

	protected String getCurrentMonth() {
		DateTime now = new DateTime();
		int month = now.getMonthOfYear();
		if (month < 10) {
			String monthValue = "0" + month;
			return monthValue;
		}
		return String.valueOf(month);
	}

	protected String getCurrentYear() {
		DateTime now = new DateTime();
		return String.valueOf(now.getYear());
	}

	protected String getWordpressToday() {
		return getCurrentDay() + "/" + getCurrentMonth() + "/" + getCurrentYear();
	}
	protected String getBankGuruToday() {
		return getCurrentYear() + "-" + getCurrentMonth() + "-" + getCurrentDay();
	}
}
