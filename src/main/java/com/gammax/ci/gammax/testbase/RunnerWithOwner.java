package com.gammax.ci.gammax.testbase;

import java.io.File;
import java.util.List;

import com.creditdatamw.zerocell.Reader;

public class RunnerWithOwner {

	public static void main(String[] args) {
		
		List<ExcelData> data = Reader.of(ExcelData.class)
				.from(new File(System.getProperty("user.dir")+"/src/main/resources/JobPositionData.xlsx"))
				.sheet("Data")
				.skipHeaderRow(true)
				.list();
		
		data.forEach(System.out::println);
	}

}
