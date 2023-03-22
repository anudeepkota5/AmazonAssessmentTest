package com.gammax.ci.gammax.core;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;


public class ExtentManager {
	static final Logger log = LogManager.getLogger(ExtentManager.class);
	public static ExtentReports extentReports;
	public static Properties prop;

	public ExtentManager(ExtentReports extent) {
		this.extentReports = extent;
	}

	public static ExtentReports getInstance(String propertyPath, String reportOutPutPath, String reportConfigXmlPath ) {
		prop = new Properties();
		try {
			FileInputStream fs = new FileInputStream(System.getProperty(propertyPath));
			prop.load(fs);
			fs.close();
		} catch (Exception e) {
			log.error("Exception occurred during getting the instance of Extend Report " + e);
		}

		if (extentReports == null) {
			Date d = new Date();
			String fileName = d.toString().replace(":", "_").replace(" ", "_") + ".html";
			String outPutFilePath = reportOutPutPath + fileName;
			extentReports = new ExtentReports(outPutFilePath, true, DisplayOrder.NEWEST_FIRST);
		}
		extentReports.loadConfig(new File(reportConfigXmlPath));
		return extentReports;
	}

	public static ExtentReports getInstanceWithDefaultConfig(String reportPath){
		try {
			if (extentReports == null) {
				Date d = new Date();
				String fileName = d.toString().replace(":", "_").replace(" ", "_") + ".html";
				Path path = Paths.get(reportPath);
				Files.createDirectories(path);
				extentReports = new ExtentReports(reportPath + fileName, true, DisplayOrder.NEWEST_FIRST);
			}
			String defaultConfigFilePath = Paths.get(System.getProperty("user.dir")) + File.separator + "ReportsConfig.xml";
			extentReports.loadConfig(new File(defaultConfigFilePath));
		} catch (IOException e) {
			log.error("cannot generate the path " + e);
		}
		return extentReports;
	}


}
