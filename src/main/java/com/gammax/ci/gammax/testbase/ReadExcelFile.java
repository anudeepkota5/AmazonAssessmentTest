package com.gammax.ci.gammax.testbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gammax.ci.gammax.core.BrowserDriver;
import com.gammax.ci.gammax.core.TestProperties;

public class ReadExcelFile {

	public String readExcel(String fileName, String sheetName, int rowNumber, int columnNumber) throws IOException {

		File file = null;
		//String finalpath = BrowserDriver.DownloadDir + File.separator + fileName;

//		if (finalpath.startsWith("/")) {
//			file = new File(finalpath.substring(1)).getAbsoluteFile();			
//		} else {
//			file = new File(BrowserDriver.DownloadDir + File.separator + fileName);			
//		}

		if (System.getProperty("isDocker").equalsIgnoreCase("true") && Base.isLinux()) {
			String finalpath = TestProperties.get("files") + File.separator + fileName;
			System.out.println("finding file inside docker linux");
			file = new File(finalpath);
		} else if (System.getProperty("isDocker").equalsIgnoreCase("true") && Base.isWindows()) {
			String finalpathWindows = BrowserDriver.DownloadDir + File.separator + fileName;
			System.out.println("finding file inside docker windows");
			file = new File(finalpathWindows);
		} else {
			String finalpath = BrowserDriver.DownloadDir + File.separator + fileName;

			if (finalpath.startsWith("/")) {
				System.out.println("finding file inside linux");
				file = new File(finalpath.substring(1)).getAbsoluteFile();

			} else {
				System.out.println("finding file inside windows");
				file = new File(BrowserDriver.DownloadDir + File.separator + fileName);
			}
		}

		FileInputStream inputStream = new FileInputStream(file);

		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

		Sheet sheet = workbook.getSheet(sheetName);

		// int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		Row row = sheet.getRow(rowNumber);

		String value = row.getCell(columnNumber).getStringCellValue();

		return value;

//		for (int i = 0; i < rowCount + 1; i++) {
//
//			for (int j = 0; j < row.getLastCellNum(); j++) {
//
//				System.out.print(row.getCell(j).getStringCellValue() + "|| ");
//			}
//
//			System.out.println();
//		}

	}
}
