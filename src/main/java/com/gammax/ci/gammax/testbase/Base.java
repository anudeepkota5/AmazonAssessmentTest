
package com.gammax.ci.gammax.testbase;


import com.gammax.ci.gammax.core.BrowserDriver;
import com.gammax.ci.gammax.core.Element;
import com.gammax.ci.gammax.core.ExcelReader;
import com.gammax.ci.gammax.core.ExtentManager;
import com.gammax.ci.gammax.core.TestProperties;
import com.gammax.ci.gammax.core.Testuff;
import com.gammax.ci.gammax.testbase.ExcelData;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Base {

	public static WebDriver driver;
	public static String TEST_ID;
	public static String LABID = "";
	public static ExtentReports extentReports;
	public static ExtentTest extentTest;
	public static String DownloadDir;
	public static String USERID;
	public static String PASSWORD;
	public static String BRANCHID;
	public static String NEWLABID;
	public static String PARENTID;
	public static String LABNAME;
	public static String UPLOAD_FILEPATH;
	public static String SUITENAME;
	public static data CONFIG;
	static Logger logger = LogManager.getLogger(Base.class.getName());
	public static Testuff teStuffs = new Testuff();
	Element element = new Element(driver);
	BrowserDriver browserDriver = new BrowserDriver();

	@Parameters("browser")
	@BeforeSuite
	public void beforeSuite(@Optional("chrome") String browserName, ITestContext iTestContext) {
		CONFIG = ConfigFactory.create(data.class);
		String suitName = iTestContext.getSuite().getName();
		SUITENAME = suitName;
		System.out.println("Suite name is: "+ SUITENAME);
		String extendReportPath = System.getProperty("user.dir") + File.separator + "reports" + File.separator
				+ SUITENAME + File.separator;
		extentReports = ExtentManager.getInstanceWithDefaultConfig(extendReportPath);
		LABID = teStuffs.getLabID(returnTodayDateForLab() + "_" + "Automated");
		System.out.println("LabID name is: "+ LABID );
/*		if (teStuffs.getLabCount(returnTodayDateForLab()).equalsIgnoreCase("0")) {
//			if (isLinux() && System.getProperty("ReportingtoTestuff").equalsIgnoreCase("true")
			if (System.getProperty("ReportingtoTestuff").equalsIgnoreCase("true")
					&& !suitName.contains("Failed suite")) {
				System.out.println("creating lab in testStuff");
				LABID = createLabInTestStuff(browserName);
				System.out.println("LabID2 name is: "+ LABID );
			}
		}*/
	}

	@Parameters("browser")
	@BeforeMethod
	public void driverInitialize(@Optional("chrome") String browserName, Method method)
			throws Exception {

		BasicConfigurator.configure();
		extentTest = extentReports.startTest(method.getName());
		System.out.println(System.getProperty("isDocker"));
/*		if (System.getProperty("isDocker").equalsIgnoreCase("true")) {
			driver = runOnDocker(browserName);
		} else {*/
			driver = BrowserDriver.getDriver(browserName);
	//	}
		
		System.out.println("Entering URL..............");
		driver.navigate().to(TestProperties.get("url"));
		takeScreenShot();

		//startTestCase(method.getName());
		//loginToCS();
		//logger.info("Logining in to Claiming System");
		//extentTest.log(LogStatus.PASS, "Logining in to Claiming System");
		//takeScreenShot();
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
			fprofile.setPreference("browser.download.dir", "/admin/seluser/Downloads");
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
				driver = new RemoteWebDriver(new URL("http://localhost:4446/wd/hub"), options);
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
			chromePref.put("download.default_directory", "/admin/seluser/Downloads");

			options.setExperimentalOption("prefs", chromePref);

			options.addArguments("disable-infobars");
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--disable-notifications");
			options.addArguments("enable-automation");
			options.addArguments("--no-sandbox");
			options.addArguments("--start-maximized");

			options.addExtensions(new File(System.getProperty("user.dir")+"/src/test/resources/extension_10_24_1_0.crx"));

			if (System.getProperty("os.name").contains("Windows")) {
				driver = new RemoteWebDriver(new URL("http://localhost:4441/wd/hub"), options);
				System.out.println("Inside Docker chrome");
			} else {
				driver = new RemoteWebDriver(new URL("http://10.62.6.62:4474/wd/hub"), options);
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
			prefs.put("download.default_directory", "/admin/seluser/Downloads");
			options.setExperimentalOption("prefs", prefs);

			if (System.getProperty("os.name").contains("Windows")) {
				driver = new RemoteWebDriver(new URL("http://localhost:4445/wd/hub"), options);
			} else {
				driver = new RemoteWebDriver(new URL("http://10.62.6.62:4475/wd/hub"), options);
			}

		}
		return driver;
	}


	@Parameters("browser")
	@AfterMethod
	public synchronized void afterMethod(@Optional("chrome") String browserName, ITestResult result, Method method) {
		if (result.getStatus() == ITestResult.FAILURE) {
			if (isLinux()) {
				teStuffs.setTestFailureFlag(false);
			} else {
				teStuffs.setTestFailureFlag(false);
			}
			try {
				String str = result.getThrowable().getCause().toString().replace("<", " ").replace(">", " ");
				extentTest.log(LogStatus.FAIL, StringUtils.substringBefore(str, "(Session info:"));
				extentReports.endTest(extentTest);
				extentReports.flush();
			} catch (NullPointerException e) {
				extentTest.log(LogStatus.FAIL, result.getThrowable());
				extentReports.endTest(extentTest);
				extentReports.flush();
			}
			extentTest.log(LogStatus.INFO,
					StringUtils.substringBefore("Testuff has been updated with Fail status", "(Session info:"));
		}
		if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest.log(LogStatus.INFO,
					StringUtils.substringBefore("Testuff has been updated with Pass status", "(Session info:"));
		}

		//if (isLinux() && System.getProperty("ReportingtoTestuff").equalsIgnoreCase("true")) {
	/*	if (System.getProperty("ReportingtoTestuff").equalsIgnoreCase("true")) {

			if (SUITENAME.contains("Failed suite") && result.getStatus() == ITestResult.SUCCESS) {
				System.out.println("inside existing lab");
				LABID = teStuffs.getLabID(returnTodayDateForLab() + "_" + "Automated");
				String Run_id = teStuffs.getRunID(LABID, TEST_ID);
				System.out.println("Reporting from the inside failed test suite");
				teStuffs.reportFailedSuiteTestInTestStuff(Run_id);

			} else if(!SUITENAME.contains("Failed suite")) {
				System.out.println("Reporting from the inside test suite");
				if (teStuffs.getTestCount(TEST_ID, LABID) == 0) {
					teStuffs.reportTestInTestStuff(browserName, TEST_ID, LABID);
				}else if (teStuffs.getTestCount(TEST_ID, LABID) > 0 && !teStuffs.getConfigName(LABID, TEST_ID).equalsIgnoreCase(browserName)){
					teStuffs.reportTestInTestStuff(browserName, TEST_ID, LABID);
				}
			}
		}*/

		driver.quit();
		driver = null;
		extentReports.endTest(extentTest);
		extentReports.flush();
		endTestCase(method.getName());
	}

	@DataProvider
	public static Object[] getData(Method m) {
		List<ExcelData> testdata = ExcelReader.getData()
				.stream()
				.filter(e->e.getTestname().equalsIgnoreCase(m.getName())).collect(Collectors.toList());
		return testdata.toArray();
	}
	
	public static void setLabID(String labid) {
		Base.LABID = labid;
	}

	public static void setTestId(String testId) {
		Base.TEST_ID = testId;
	}

	public static String createLabInTestStuff(String browserName) {

		USERID = TestProperties.get("TestuffUserID");
		PASSWORD = TestProperties.get("TestuffPassword");
		BRANCHID = TestProperties.get("TestuffBranchID");
		PARENTID = TestProperties.get("TestffParentID");
		NEWLABID = "o7cd7xtj2pr3ikoxnaul" + returnUniqueData();
		LABNAME = returnTodayDateForLab() + "_" + "Automated";
		//LABNAME = "2022_02_11" + "_" + "Docker";

		System.out.println(USERID);
		System.out.println(PASSWORD);
		System.out.println(BRANCHID);
		System.out.println(PARENTID);
		System.out.println(NEWLABID);
		System.out.println(LABNAME);
		System.out.println(browserName);
		try {
			if (isLinux()) {
				Testuff.execCurlCommand("sudo " + "curl -u " + USERID + ":" + PASSWORD
						+ " -k -H accept:application/json -H -X POST https://service4.testuff.com/api/v0/lab/ -H Content-Type:application/json -d {\"branch_id\":\""
						+ BRANCHID + "\",\"instructions\":\"\",\"name\":\"" + LABNAME + "\",\"id\":\"" + NEWLABID
						+ "\",\"parent_id\":\"" + PARENTID + "\",\"product_version\":\"\"}");
			}
			if (isWindows()) {
				Testuff.execCurlCommand("curl -u " + USERID + ":" + PASSWORD
						+ " -k -H \"accept:application/json\" -H -X POST https://service4.testuff.com/api/v0/lab/ -H \"Content-Type:application/json\" -d {\\\"branch_id\\\":\\\""
						+ BRANCHID + "\\\",\\\"name\\\":\\\"" + LABNAME + "\\\",\\\"id\\\":\\\"" + NEWLABID
						+ "\\\",\\\"parent_id\\\":\\\"" + PARENTID + "\\\"}");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		System.out.println("The New Lab ID is: "+NEWLABID);
		return NEWLABID;
	}

	public static String returnUniqueData() {
		DateFormat dateFormat = new SimpleDateFormat("MMddyyyyHHmmss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String returnTodayDateForLab() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static void takeScreenShot() {

		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String pathText = System.getProperty("user.dir") + File.separator + "reports" + File.separator + SUITENAME
				+ File.separator;
		Path path = Paths.get(pathText).toAbsolutePath();
		try {
			Files.createDirectories(path);
			pathText += screenshotFile;
			File destinationFile = new File(pathText);
			if (!destinationFile.exists()) {
				destinationFile.canWrite();
			}
			FileHandler.copy(scrFile, destinationFile);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		extentTest.log(LogStatus.INFO, extentTest.addScreenCapture(screenshotFile));
	}

	public void deleteDownloadedFile(String downloadPath, String fileName) {

		File file;
		if (System.getProperty("isDocker").equalsIgnoreCase("true") && isLinux()) {
			String finalpathLinux = TestProperties.get("files") + File.separator + fileName;
			File dirpath = new File(TestProperties.get("files"));
			System.out.println(dirpath);
			File[] files = dirpath.listFiles();
			if (files != null) {
				for (File f : files) {
					f.delete();
				}
			}
			file = new File(finalpathLinux);
			if (file.exists()) {
				file.delete();
			}
		} else if (System.getProperty("isDocker").equalsIgnoreCase("true") && isWindows()) {
			String finalpathWindows = downloadPath + File.separator + fileName;
			File dirpath = new File(downloadPath);
			File[] files = dirpath.listFiles();
			if (files != null) {
				for (File f : files) {
					f.delete();
				}
			}
			file = new File(finalpathWindows);
			if (file.exists()) {
				file.delete();
			}
		} else {
			String finalpath = downloadPath + File.separator + fileName;
			if (finalpath.startsWith("/")) {
				File dirpath = new File(finalpath.substring(1)).getAbsoluteFile();
				File[] files = dirpath.listFiles();
				if (files != null) {
					for (File f : files) {
						f.delete();
					}
				}
				file = new File(finalpath.substring(1)).getAbsoluteFile();
			} else {
				File dirpath = new File(downloadPath);
				File[] files = dirpath.listFiles();
				if (files != null) {
					for (File f : files) {
						f.delete();
					}
				}
				file = new File(downloadPath + File.separator + fileName);
			}
		}
	}

	public boolean isFileDownloaded(String downloadPath, String fileName) throws IOException {
		File file;
		File destinationFile;
		if (System.getProperty("isDocker").equalsIgnoreCase("true") && isLinux()) {
			String finalpathLinux = TestProperties.get("files") + File.separator + fileName;
			String tempPath = BrowserDriver.DownloadDir + File.separator + fileName;

			destinationFile = new File(tempPath);
			System.out.println("Destination File: " + destinationFile);
			file = new File(finalpathLinux);
			System.out.println("File Path: " + file);
			FileHandler.copy(file, destinationFile);
			if (file.exists()) {
				return true;
			}
		} else {
			String finalpath = downloadPath + File.separator + fileName;
			if (finalpath.startsWith("/")) {
				file = new File(finalpath.substring(1)).getAbsoluteFile();
			} else {
				file = new File(downloadPath + File.separator + fileName);
			}
		}

		if (file.exists()) {
			return true;
		}

		return false;
	}

	public void uploadFile(String filePath, String fileName) throws InterruptedException {
		if (System.getProperty("isDocker").equalsIgnoreCase("true") && isLinux()) {
			if (driver instanceof RemoteWebDriver) {
				((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
			}
			String finalpathLinux = TestProperties.get("files") + File.separator + fileName;
			String file_Path = new File(finalpathLinux).getAbsolutePath();
			driver.findElement(By.xpath("//div[@ng-controller='ImportCtrl']//input[@name='entry.File']"))
					.sendKeys(file_Path);
		} else {
			String fullPath = filePath + File.separator + fileName;

			if (fullPath.startsWith("/")) {
				fullPath = fullPath.substring(1);
			}
			String file_Path = new File(fullPath).getAbsolutePath();
			logger.info(file_Path);
			driver.findElement(By.xpath("//div[@ng-controller='ImportCtrl']//input[@name='entry.File']"))
					.sendKeys(file_Path);
		}
		timeinterval(5);
		driver.findElement(By.xpath("//button[text()[contains(., 'Upload')]]")).click();
		timeinterval(20);
		Base.takeScreenShot();
		logger.info("File uploading...");
		Base.extentTest.log(LogStatus.PASS, "File uploading...");
		WebElement confirmLogCheckbox = driver.findElement(By.xpath("//input[@name='entry.ConfirmLog']"));
		browserDriver.scrollDown(driver);
		Assert.assertTrue(element.isDisplayed(confirmLogCheckbox));
		confirmLogCheckbox.click();
		timeinterval(2);
		Base.takeScreenShot();
		logger.info("Confirm button clicked");
		Base.extentTest.log(LogStatus.PASS, "Confirm button clicked");
		WebElement commitButton = driver.findElement(By.xpath("//button[text()[contains(., 'Commit')]]"));
		Assert.assertTrue(element.isDisplayed(commitButton));
		commitButton.click();
		timeinterval(2);
		Base.takeScreenShot();
		logger.info("Commit button clicked");
		Base.extentTest.log(LogStatus.PASS, "Commit button clicked");
		driver.findElement(By.xpath("//button[@aria-label='UnlabledInput' and text()[contains(., 'Close')]]")).click();
		Base.takeScreenShot();
		logger.info("Close button clicked");
		Base.extentTest.log(LogStatus.PASS, "Close button clicked");

	}

	public static String currentDate() {

		Date date = new Date();
		SimpleDateFormat DateFor = new SimpleDateFormat("MM/dd/yyyy");
		String stringDate = DateFor.format(date);
		return stringDate;

	}

	public static String pastDate(int days) {

		Date date = new Date();
		DateFormat dateFor = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, days);
		return dateFor.format(c.getTime());

	}

	public static String getFutureDate(int days) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, days);
		return df.format(c.getTime());
	}

	public static boolean isLinux() {
		String os = System.getProperty("os.name");
		System.out.println(os);
		return os.contains("nix") || os.contains("nux");
	}

	public static boolean isWindows() {
		String os = System.getProperty("os.name");
		return os.contains("Windows");
	}

	public void timeinterval(int i) throws InterruptedException {
		Thread.sleep(1000 * i);
	}

	public String getUploadPath() {
		UPLOAD_FILEPATH = Paths.get(System.getProperty("user.dir"), TestProperties.get("tmpDownload")).toString();
		System.out.println(UPLOAD_FILEPATH);
		return UPLOAD_FILEPATH;
	}

	public void hoverOverElement(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element, 250, 0).build().perform();

	}

	public void mouseHoverJScript(WebElement HoverElement) {
		try {

			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			((JavascriptExecutor) driver).executeScript(mouseOverScript, HoverElement);

		} catch (

				StaleElementReferenceException e) {
			logger.error("Element with " + HoverElement + "is not attached to the page document" + e.getStackTrace());
		} catch (NoSuchElementException e) {
			logger.error("Element " + HoverElement + " was not found in DOM" + e.getStackTrace());
		} catch (Exception e) {
			logger.error("Error occurred while hovering" + e.getStackTrace());
		}
	}

	public static void startTestCase(String sTestCaseName) {

		logger.info("============================================================================================");
		logger.info("Starting Test                 " + sTestCaseName + "                                         ");
		logger.info("============================================================================================");

	}

	public static void endTestCase(String sTestCaseName) {

		logger.info("============================================================================================");
		logger.info("End Test                " + sTestCaseName + "                                               ");
		logger.info("============================================================================================");
	}
}
