package com.gammax.ci.gammax.core;

import java.io.File;
import java.util.List;

import com.creditdatamw.zerocell.Reader;
import com.gammax.ci.gammax.testbase.ExcelData;

public final class ExcelReader {
	private ExcelReader() {}
	
	private static List<ExcelData> data = null;
	
	public static List<ExcelData> getData(){
		return data;
	}
	
	static {
		data = Reader.of(ExcelData.class)
				.from(new File(System.getProperty("user.dir")+"/src/main/resources/JobPositionData.xlsx"))
				.sheet("Data")
				.skipHeaderRow(true)
				.list();
	}
}
