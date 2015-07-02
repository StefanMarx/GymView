package com.mc.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.mc.entities.Training;
import com.mc.entities.ViewHistory;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class ExtractPageContent {

	/** The original PDF that will be parsed. */
	public static final String PREFACE = "/Users/stefan/work/techno/GymView/KIESER TRAINING DATA.pdf";
	/** The resulting text file. */
	public static final String RESULT = "/Users/stefan/work/techno/GymView/KIESER TRAINING DATA.txt";

	@Resource(lookup = "java:/MySQLDS")
	private static DataSource dataSource;

	private static MysqlDataSource myDatasource; 
	/**
	 * Parses a PDF to a plain text file.
	 * 
	 * @param pdf
	 *            the original PDF
	 * @param txt
	 *            the resulting text
	 * @throws IOException
	 */
	public void parsePdf(String pdf, String txt) throws IOException {
		String[] machNames = new String[12];

		Connection con = null;
		// Setting up the DataSource object
					com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
					ds.setServerName("localhost");
					ds.setPortNumber(3306);
					ds.setDatabaseName("GymView");
					ds.setUser("jboss");
					ds.setPassword("password");
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PdfReader reader = new PdfReader(pdf);
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		PrintWriter out = new PrintWriter(new FileOutputStream(txt));
		TextExtractionStrategy strategy;
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
			final String content = strategy.getResultantText();
			out.println(content);
			// System.out.println(content);
			StringTokenizer tok = new StringTokenizer(content, "\n");
			while (tok.hasMoreElements()) {
				String line = (String) tok.nextElement();
				if (line.startsWith("Datum")) {
					System.out.println(line);
					StringTokenizer nametok = new StringTokenizer(line.substring(5), " ");
					int cnt = 0;
					while (nametok.hasMoreElements()) {
						String name = (String) nametok.nextElement();
						// System.out.println(name);
						machNames[cnt++] = name;
					}
					for (int j = 0; j < machNames.length; j++) {
						System.out.println(machNames[j]);
					}
				}
				if (line.charAt(2) == '-') {
					System.out.println(line);
					String theDate = line.substring(0, 10).replace('-', '.');
					System.out.println("New date: " + theDate);
					StringTokenizer traintok = new StringTokenizer(line.substring(10), " ");
					int cnt = 0;
					while (traintok.hasMoreElements()) {
						String lbs = (String) traintok.nextElement();
						System.out.println("weight: " + lbs);
						String slash = (String) traintok.nextElement();
						System.out.println("slash: " + slash);
						String secs = (String) traintok.nextElement();
						System.out.println("secs: " + secs);

						Training oneTrain = Training.makeOneA4Train(theDate, machNames[cnt++],
								new Integer(lbs).intValue(), new Integer(secs).intValue());
						// ViewHistory.storeTraining(oneTrain, ds);
						ViewHistory myHist = new ViewHistory();
						myHist.storeTraining(oneTrain, dataSource);
					}

				}
			}
		}
		out.flush();
		out.close();
		reader.close();
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *            no arguments needed
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		new ExtractPageContent().parsePdf(PREFACE, RESULT);
	}
	
	public static MysqlDataSource createDS(){
		MysqlDataSource dataSource = new MysqlDataSource();
	    dataSource.setDatabaseName("gymview");
	    dataSource.setUser("jboss");
	    dataSource.setPassword("password");
	    dataSource.setServerName("kinemo");
	    return dataSource;
	}
}