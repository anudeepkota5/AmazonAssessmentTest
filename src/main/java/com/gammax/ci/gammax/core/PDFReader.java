package com.gammax.ci.gammax.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class PDFReader {

	static Logger log = LogManager.getLogger(Testuff.class.getName());
	static String PDF_DOWNLOAD_DIR =  Paths.get(System.getProperty("user.dir"), "/PDFs").toString();

	public String readPDFDataInDefaultPath(String fileName) {
		String pdfFileInText = null;
		String path = Paths.get(PDF_DOWNLOAD_DIR, fileName+".pdf").toString();
		try (PDDocument document = PDDocument.load(new File(path))) {
			document.getClass();
			if (!document.isEncrypted()) {
				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);
				PDFTextStripper tStripper = new PDFTextStripper();
				pdfFileInText = tStripper.getText(document);
			}
		} catch (IOException e) {
            log.error("Input & Output operations exception occurred." + e.getMessage());
            Assert.fail("Exception Occurred");
        }
		Assert.assertNotNull(pdfFileInText, "PDF file is null");
		return pdfFileInText;
	}


    public String readPDFDataFromPath(String filePath) {
	    if (!filePath.endsWith("pdf")) {
	        Assert.fail("Path should be ended with .pdf");
        }
        String pdfFileInText = null;
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            document.getClass();
            if (!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper tStripper = new PDFTextStripper();
                pdfFileInText = tStripper.getText(document);
            }
        } catch (IOException e) {
            log.error("Input & Output operations exception occurred." + e.getMessage());
            Assert.fail("Exception Occurred");
        }
        Assert.assertNotNull(pdfFileInText, "PDF file is null");
        return pdfFileInText;
    }

	public void deletePdfFiles(String path) {
        if (!path.endsWith("pdf")) {
            path = path + "/.pdf";
            log.info("PDF file path is updated to :" + path);
        }
		File file = new File(path);
		if (!file.delete()) {
		    Assert.fail("Failed to delete the PDF file under this path :" +path);
        }
	}

}
