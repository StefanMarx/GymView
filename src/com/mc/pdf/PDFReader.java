package com.mc.pdf;

import java.io.IOException;

//iText imports
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class PDFReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			PdfReader reader = new PdfReader("/Users/stefan/work/techno/GymView/KIESER TRAINING DATA.pdf");
			System.out.println("This PDF has" + reader.getNumberOfPages() + " pages.");
			String page = PdfTextExtractor.getTextFromPage(reader, 1);
			System.out.println("Page Content:\n\n" + page + "\n\n");
			System.out.println("Is this document tampered:" + reader.isTampered());
			System.out.println("Is this document encrypted:" + reader.isEncrypted());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
