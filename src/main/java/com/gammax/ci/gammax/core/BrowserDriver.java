package com.gammax.ci.gammax.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * Singleton access to WebDriver, provides single browser instance across all
 * tests/scenario
 */

public class BrowserDriver {

	static final Logger log = LogManager.getLogger(BrowserDriver.class);
	public static WebDriver driver;

	public static String DownloadDir;
//		opt.addExtensions(new File(System.getProperty("user.dir")+"/src/test/resources/extension_10_24_1_0.crx"));
	public static final String WinChromeDriverPath = "src/test/resources/chromedriver.exe";
	public static final String WinGeckoDriverPath = "src/main/resources/geckodriver.exe";
	public static final String EdgeDriverPath = "src/main/resources/msedgedriver.exe";
	public static final String MacChromeDriverPath = "/src/main/resources/chromedriver";
	public static final String LinuxChromeDriverPath = "/usr/bin/chromedriver";
	public static final String LinuxFirefoxDriverPath = "/usr/bin/geckodriver";
	String mainWindow;

	public static void setDriver(WebDriver driver) {
		BrowserDriver.driver = driver;
	}

	public static String getDownloadDir() {
		return DownloadDir;
	}

	public static void setDownloadDir(String downloadDir) {
		BrowserDriver.DownloadDir = downloadDir;
	}

	public static WebDriver runOnDocker(String browser) throws IOException, InterruptedException {

		BasicConfigurator.configure();

		if (TestProperties.get("tmpDownload") == null) {
			DownloadDir = Paths.get(System.getProperty("user.dir"), File.separator + "tmp").toString();
			File directoryPath = new File(DownloadDir);
			System.out.println("Inside run on docker: path to download directory: " + DownloadDir);
			if (!directoryPath.exists()) {
				directoryPath.mkdir();
			}

		} else {
			DownloadDir = Paths.get(System.getProperty("user.dir"), TestProperties.get("tmpDownload")).toString();
			File directoryPath = new File(DownloadDir);
			System.out.println("Inside run on docker: path to download directory: " + DownloadDir);
			if (!directoryPath.exists()) {
				directoryPath.mkdir();
			}
		}

		if (browser.equalsIgnoreCase("firefox")) {

			System.out.println("Initializing Firefox Browser in Docker Container.");
			FirefoxProfile fprofile = new FirefoxProfile();
			fprofile.setPreference("browser.download.dir", "/home/seluser/Downloads");
			fprofile.setPreference("browser.download.folderList",2);
			fprofile.setPreference("browser.download.manager.showWhenStarting",false);
			fprofile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/zip,application/octet-stream,application/x-zip-compressed,multipart/x-zip,application/x-rar-compressed, application/octet-stream,application/msword,application/vnd.ms-word.document.macroEnabled.12,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/rtf,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel,application/vnd.ms-word.document.macroEnabled.12,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/xls,application/msword,text/csv,application/vnd.ms-excel.sheet.binary.macroEnabled.12,text/plain,text/csv/xls/xlsb,application/csv,application/download,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/octet-stream");
			fprofile.setPreference("browser.helperApps.neverAsk.openFile", "application/zip,application/octet-stream,application/x-zip-compressed,multipart/x-zip,application/x-rar-compressed, application/octet-stream,application/msword,application/vnd.ms-word.document.macroEnabled.12,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/rtf,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel,application/vnd.ms-word.document.macroEnabled.12,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/xls,application/msword,text/csv,application/vnd.ms-excel.sheet.binary.macroEnabled.12,text/plain,text/csv/xls/xlsb,application/csv,application/download,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/octet-stream");// MIME type
			fprofile.setPreference("browser.helperApps.alwaysAsk.force", false);
			fprofile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			fprofile.setPreference("pdfjs.disabled", true);
			fprofile.setPreference("browser.download.panel.shown", false);
			FirefoxOptions options = new FirefoxOptions();
			options.setProfile(fprofile);
			
			if (System.getProperty("os.name").contains("Windows")) {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
			} else {
				driver = new RemoteWebDriver(new URL("http://10.62.6.62:4449/wd/hub"), options);
			}

		} else if (browser.equalsIgnoreCase("chrome")) {
			System.out.println("Initializing Chrome Browser in Docker Container.");
			ChromeOptions options = new ChromeOptions();
			HashMap<String, Object> chromePref = new HashMap<>();
			chromePref.put("plugins.always_open_pdf_externally", true);
			chromePref.put("profile.default_content_settings.popups", 0);
			chromePref.put("download.prompt_for_download", false);
			chromePref.put("plugins.plugins_list", new String[] { "Chrome PDF Viewer" });
			chromePref.put("download.directory_upgrade", true);
			chromePref.put("download.extensions_to_open", "applications/pdf");
			chromePref.put("open_pdf_in_system_reader", false);
			chromePref.put("download_restrictions", 0);
			chromePref.put("download.default_directory", "/home/seluser/Downloads");

			options.setExperimentalOption("prefs", chromePref);

			options.addArguments("disable-infobars");
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--disable-notifications");
			options.addArguments("enable-automation");
			options.addArguments("--no-sandbox");
			options.addArguments("--start-maximized");

			if (System.getProperty("os.name").contains("Windows")) {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
			} else {
				driver = new RemoteWebDriver(new URL("http://10.62.6.62:4469/wd/hub"), options);
			}

		} else {
			System.out.println("Initializing Edge Browser in Docker Container.");
			EdgeOptions options = new EdgeOptions();
			options.addArguments("disable-infobars");
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--disable-notifications");
			options.addArguments("enable-automation");
			options.addArguments("--no-sandbox");
			options.addArguments("--start-maximized");
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("download.default_directory", "/home/seluser/Downloads");
			options.setExperimentalOption("prefs", prefs);

			if (System.getProperty("os.name").contains("Windows")) {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
			} else {
				driver = new RemoteWebDriver(new URL("http://10.62.6.62:4470/wd/hub"), options);
			}

		}
		return driver;
	}

	public synchronized static WebDriver getDriver(String browser) {

		BasicConfigurator.configure();

		if (TestProperties.get("tmpDownload") == null) {
			DownloadDir = Paths.get(System.getProperty("user.dir"), File.separator + "tmp").toString();

		} else {
			DownloadDir = Paths.get(System.getProperty("user.dir"), TestProperties.get("tmpDownload")).toString();
		}

		String os = System.getProperty("os.name");
		log.info("Test is starting on " + browser + " in " + os + " System");
		if (os.startsWith("Windows")) {
			if (browser.equals("chrome")) {
				ChromeOptions options = new ChromeOptions();
//				HashMap<String, Object> chromePref = new HashMap<>();
//				chromePref.put("download.default_directory", DownloadDir);
//				options.setExperimentalOption("prefs", chromePref);
//				options.addArguments("--disable-notifications");
//				options.addArguments("disable-infobars");
				options.addArguments("--remote-allow-origins=*","ignore-certificate-errors");
				System.out.println(System.getProperty("user.dir"));
				options.addExtensions(new File(System.getProperty("user.dir")+"/src/test/resources/extension_10_24_1_0.crx"));
				System.setProperty("webdriver.chrome.driver", WinChromeDriverPath);
				driver = new ChromeDriver(options);
				log.info("Chrome Browser is launching");
			} else if (browser.equals("firefox")) {
				FirefoxProfile fprofile = new FirefoxProfile();
				fprofile.setPreference("browser.download.dir", DownloadDir);
				fprofile.setPreference("browser.download.folderList",2);
				fprofile.setPreference("browser.download.manager.showWhenStarting",false);
				fprofile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/zip,application/octet-stream,application/x-zip-compressed,multipart/x-zip,application/x-rar-compressed, application/octet-stream,application/msword,application/vnd.ms-word.document.macroEnabled.12,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/rtf,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel,application/vnd.ms-word.document.macroEnabled.12,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/xls,application/msword,text/csv,application/vnd.ms-excel.sheet.binary.macroEnabled.12,text/plain,text/csv/xls/xlsb,application/csv,application/download,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/octet-stream");
				fprofile.setPreference("browser.helperApps.neverAsk.openFile", "application/zip,application/octet-stream,application/x-zip-compressed,multipart/x-zip,application/x-rar-compressed, application/octet-stream,application/msword,application/vnd.ms-word.document.macroEnabled.12,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/rtf,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel,application/vnd.ms-word.document.macroEnabled.12,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/xls,application/msword,text/csv,application/vnd.ms-excel.sheet.binary.macroEnabled.12,text/plain,text/csv/xls/xlsb,application/csv,application/download,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/octet-stream");// MIME type
				fprofile.setPreference("browser.helperApps.alwaysAsk.force", false);
				fprofile.setPreference("browser.download.manager.showWhenStarting",false);
				fprofile.setPreference("browser.download.manager.alertOnEXEOpen", false);
				fprofile.setPreference("pdfjs.disabled", true); // disable the built-in viewed
				fprofile.setPreference("browser.download.panel.shown", false);
				FirefoxOptions options = new FirefoxOptions();
				options.setProfile(fprofile);
				System.setProperty("webdriver.gecko.driver", WinGeckoDriverPath);
				driver = new FirefoxDriver(options);
				log.info("Firefox Browser is launching");
			} else if (browser.equals("edge")) {
				System.setProperty("webdriver.edge.driver", EdgeDriverPath);
				EdgeOptions options = new EdgeOptions();
				Map<String, Object> prefs = new HashMap<>();
				prefs.put("download.default_directory", DownloadDir);
				options.setExperimentalOption("prefs", prefs);
				driver = new EdgeDriver(options);
				log.info("Edge Browser is launching");
			} else {
				throw new UnsupportedOperationException("Operating System " + os
						+ " is not found! Please update the framework to support the unsupported Operating System.");
			}
		} else if (os.contains("nix") || os.contains("nux")) {
			if (browser.equals("chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless");
    				options.addArguments("--no-sandbox");
    				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--remote-allow-origins=*","ignore-certificate-errors");
				System.out.println(System.getProperty("user.dir"));
				options.addExtensions(new File(System.getProperty("user.dir")+"/src/test/resources/extension_10_24_1_0.crx"));
				driver = new ChromeDriver(options);
				log.info("Chrome Browser is launching");
			} else {
				FirefoxOptions options = new FirefoxOptions();
//				HashMap<String, Object> chromePref = new HashMap<>();
//				chromePref.put("download.default_directory", System.getProperty("user.dir") + DownloadDir);
//				options.setExperimentalOption("prefs", chromePref);
				options.addArguments("--disable-notifications");
				options.addArguments("disable-infobars");
				// Newly added proxy setting
				options.addArguments("--no-proxy-server");
				options.addArguments("--proxy-server='direct://'");
				options.addArguments("--proxy-bypass-list=*");
				options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

				options.addArguments("--no-sandbox");
				options.addArguments("--headless");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--window-size=1920x1080");
				System.setProperty("webdriver.gecko.driver", LinuxFirefoxDriverPath);
				driver = new FirefoxDriver(options);
				log.info("Firefox Browser is launching");
			}
		} else if (os.startsWith("Mac")) {
			if (browser.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+MacChromeDriverPath);

				ChromeOptions options = new ChromeOptions();
//				HashMap<String, Object> chromePref = new HashMap<>();
//				chromePref.put("download.default_directory", System.getProperty("user.dir") + DownloadDir);
//				// DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//				options.setExperimentalOption("prefs", chromePref);
//				options.addArguments("--disable-notifications");
//				options.addArguments("disable-infobars");
//				options.addArguments("--test-type");
//				options.addArguments("--dns-prefetch-disable");
				System.out.println(System.getProperty("user.dir"));
				options.addExtensions(new File("/Users/shyamreddykallu/Desktop/Code/Gammax_Exchange/src/test/resources/extension_10_24_1_0.crx"));
				// options.merge(capabilities);

				driver = new ChromeDriver(options);
				log.info("Chrome Browser is launching on Mac");
			} else {
				throw new UnsupportedOperationException("Operating System " + os
						+ " is not found! Please update the framework to support the unsupported Operating System.");
			}
		} else {
			throw new UnsupportedOperationException("Operating System " + os
					+ " is not found! Please update the framework to support the unsupported Operating System.");
		}

//		Capabilities newCapabilities = ((HasCapabilities) driver).getCapabilities();
//		String browserAfterInint = newCapabilities.getBrowserName();
//		String browserVersion = newCapabilities.getBrowserVersion();

//		log.info("Driver is created. Browser is " + browserAfterInint + ". Browser version is " + browserVersion);
		driver.manage().window().maximize();
		return driver;
	}

	public synchronized static WebDriver getDriver() {
		String browser = TestProperties.get("browser");
		return getDriver(browser);

	}

	public synchronized static void closeBrowser() {
		driver.close();
		driver = null;
	}

//    Driver Scroll Actions
	public void scrollToBottom(WebDriver driver) {

		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void scrollToElement(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void scrollToBottom() {
		this.scrollToBottom(driver);
	}

	public void scrollToLocationOfPage(WebDriver driver, int x, int y) {
		String script = "window.scrollBy(" + x + "," + y + ")";
		((JavascriptExecutor) driver).executeScript(script);
	}

	public void scrollUp(WebDriver driver) {
		this.scrollToLocationOfPage(driver, 0, -250);
		log.info("Scrolling up in Browser.");
	}

	public void scrollDown(WebDriver driver) {
		this.scrollToLocationOfPage(driver, 0, 250);
		log.info("Scrolling down in Browser.");
	}

//    Driver Switch Actions
	public void switchToNewWindow(WebDriver driver) {
		mainWindow = driver.getWindowHandle();
		Set<String> s = driver.getWindowHandles();
		Iterator<String> itr = s.iterator();
		itr.next();
		String w2 = (String) itr.next();
		driver.switchTo().window(w2);
		log.info("Switching to new window.");
	}

	public void switchToNewWindow() {
		this.switchToNewWindow(driver);
		log.info("Switching to main window.");
	}

	public void switchToIframe(WebDriver driver, String name, int second) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(second));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(name));
		log.info("Switching to Iframe.");
	}

	public void switchToIframe(WebDriver driver, String name) {
		this.switchToIframe(driver, name, 40);
		log.info("Switching to Iframe.");
	}

	public void switchToIframe(WebDriver driver, WebElement frameElem, int second) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(second));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElem));
			log.info("Switching to Iframe.");
		} catch (Exception e) {
			log.info("Exception happened while switching to New iFrame", e.getMessage());
			Assert.fail("Fail to Switch to Iframe");
		}
	}

	public void switchToIframe(WebDriver driver, WebElement frameElem) {
		this.switchToIframe(driver, frameElem, 40);
	}

	public void switchToDefaultFrame() {
		this.switchToDefaultFrame(driver);
		log.info("Switching to Default Iframe.");
	}

	public void switchtoDefaultWindow() {
		driver.switchTo().window(mainWindow);
		log.info("Switching to Main Window.");
	}

	public void switchToDefaultFrame(WebDriver driver) {
		driver.switchTo().defaultContent();
		log.info("Switching to Default Iframe.");
	}

//    Browser navigation Actions
	public void navigateForward(WebDriver driver) {
		driver.navigate().forward();
	}

	public void navigateForward() {
		this.navigateForward(driver);
	}

	public void navigateBack(WebDriver driver) {
		driver.navigate().back();
	}

	public void navigateBack() {
		this.navigateBack(driver);
	}

	public void refresh(WebDriver driver) {
		driver.navigate().refresh();
	}

	public void refresh() {
		this.refresh(driver);
	}

//  Browser Alert Actions
	public static void acceptBrowserAlert(WebDriver driver) {
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	public static void declineBrowserAlert(WebDriver driver) {
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}

	public static String getBrowserAlertText(WebDriver driver) {
		String alertText = null;
		try {
			Alert alert = driver.switchTo().alert();
			alertText = alert.getText();
			return alertText;
		} catch (Exception e) {
			log.info("no browser alert showing " + e.getMessage());
			Assert.fail("no browser alert is shown.");
		}
		return alertText;
	}

	public void waitForAlertToSwitch(WebDriver driver, int second) {
		int i = 0;
		while (i++ < second) {
			try {
				driver.switchTo().alert();
				break;
			} catch (NoAlertPresentException e) {
				log.info("no browser alert showing : " + e.getMessage());
				Assert.fail("no alert is shown.");
			}
		}
	}

	public void closeAlert(WebDriver driver) {
		if (isAlertPresent(driver)) {
			return;
		}
		Alert alert = driver.switchTo().alert();
		alert.accept();

	}

	public boolean isAlertPresent(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException ex) {
			return false;
		}
	}

	public boolean isAlertPresent(WebDriver driver, int sec) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(sec));
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException ex) {
			return false;
		}
	}

	public void waitForPageLoaded(WebDriver driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(expectation);
		} catch (Throwable error) {
			log.info("Throwable is occurred. " + error.getMessage());
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}
}
