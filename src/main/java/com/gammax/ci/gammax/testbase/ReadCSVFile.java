package com.gammax.ci.gammax.testbase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.gammax.ci.gammax.core.BrowserDriver;
import com.gammax.ci.gammax.core.TestProperties;
import com.opencsv.*;

public class ReadCSVFile {

	public String[] ReadCsv(int rowNum, String fileName) throws IOException {

		File file = null;
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

		// This will load csv file
		CSVReader reader = new CSVReader(new FileReader(file));

		// this will load content into list
		List<String[]> li = reader.readAll();

		// LOGGER.info("Total rows which we have is " + li.size());
		for (int j = 0; j < li.size(); j++) {
			if (j == rowNum) {
				// create Iterator reference
				Iterator<String[]> i1 = li.listIterator(j);

				// Iterate all values
				while (i1.hasNext()) {

					String[] str = i1.next();

					for (int i = 0; i < str.length; i++) {

					}

					return str;
				}
			}
		}
		return null;
	}

}
