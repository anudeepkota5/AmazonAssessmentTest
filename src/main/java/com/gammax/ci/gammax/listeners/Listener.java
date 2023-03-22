package com.gammax.ci.gammax.listeners;

import com.gammax.ci.gammax.testbase.Base;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * @author
 */
public class Listener extends Base implements ITestListener {

	static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	public void onTestStart(ITestResult result) {
		System.out.println("Start Test: " + result.getMethod().getMethodName());
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("on test success");
		extentTest.log(LogStatus.PASS, result.getMethod().getMethodName() + " test is passed");
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("on test failure");
		extentTest.log(LogStatus.FAIL, result.getMethod().getMethodName() + " test is failed");
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
			// log.error("IOException occurred ", e);
		}
		extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotFile));
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("on test skipped");
		extentTest.log(LogStatus.SKIP, result.getMethod().getMethodName() + " test is skipped");

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(result));
		onTestFailure(result);
	}

	public void onStart(ITestContext context) {
	}

	public void onFinish(ITestContext context) {
	}
}