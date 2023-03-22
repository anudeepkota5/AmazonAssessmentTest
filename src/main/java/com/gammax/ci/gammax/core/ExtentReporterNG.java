package com.gammax.ci.gammax.core;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class ExtentReporterNG implements IReporter {
	ExtentReports extent;

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		extent = new ExtentReports(outputDirectory + File.separator + "ExtentReportsTestNG.html", true);

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();

				buildTestNodes(context.getPassedTests(), LogStatus.PASS);
				buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
				buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
			}
		}

		extent.flush();
		extent.close();
	}

	void buildTestNodes(IResultMap tests, LogStatus status) {
		ExtentTest test;

		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				test = extent.startTest(result.getMethod().getMethodName());

				for (String group : result.getMethod().getGroups())
					test.assignCategory(group);

				String message = "Test " + status.toString().toLowerCase();

				if (result.getThrowable() != null)
					message = result.getThrowable().getMessage();

				test.log(status, message);

				extent.endTest(test);
			}
		}
	}


//		Helper methods to generate Data for the reporting


	Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}


	static String getCurrentTime() {
		String currenttime = new SimpleDateFormat("mmss").format(Calendar.getInstance().getTime());
		return currenttime;
	}

	static String getCurrentDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}



	String generateRandomChars(int length) {

		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
		}

		return sb.toString();
	}

}