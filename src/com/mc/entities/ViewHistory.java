package com.mc.entities;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
// import java.time.Month;
import java.util.Date;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.mail.internet.MimeBodyPart;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.mc.mail.ReadingMail;

/**
 * Servlet
 */
@WebServlet("/GymView")
public class ViewHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:/MySQLDS")
	private DataSource dataSource;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date myDate;

	final String mailId = "stefan.marx@marx-consulting.com";
	private int userIdFromMail = 0;

	public int getUserIdFromMail() {
		return userIdFromMail;
	}

	public void setUserIdFromMail(int userIdFromMail) {
		this.userIdFromMail = userIdFromMail;
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewHistory() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * final PrintWriter out = response.getWriter();
		 * out.println("<!DOCTYPE html>"); out.println("<html>");
		 * out.println("<body>"); out.println("<h1>Registrierung</h1>");
		 * out.println("Datum: " + new Date()); out.println("</body>");
		 * out.println("</html>"); out.flush();
		 * 
		 * if (!insertUserData()) { log("User loggon *DIDN'T* work at " + new
		 * Date()); System.exit(-1); }
		 */
		logUserAccess();

		int generatedkey = 118;

		if (generatedkey != 0) {
			doA4Display(response, generatedkey);
		}
	}

	private void logUserAccess() {
		try (java.sql.Connection sqlConn = dataSource.getConnection();) {
			long time = System.currentTimeMillis();
			java.sql.Timestamp timestamp = new java.sql.Timestamp(time);

			java.sql.PreparedStatement preparedStatement = sqlConn
					.prepareStatement("insert into logons values (?,?,?)");

			preparedStatement.setInt(1, 2);
			preparedStatement.setTimestamp(2, timestamp);
			preparedStatement.setTimestamp(3, null);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log("Bin in do Post");
	}

	protected void doA4Display(HttpServletResponse response, int insertPK)
			throws ServletException, IOException {

		Training[] attachedLines = getAttachmentsFromDb(insertPK);

		final Training[] allA4Trains = attachedLines;

		log("All " + allA4Trains.length + " are at hand.");
		TimeSeries s1 = new TimeSeries("Bewegte Gewichte");
		TimeSeries s2 = new TimeSeries("Zeit");
		OutputStream outstream = response.getOutputStream();

		for (int i = 0; i < allA4Trains.length; i++) {
			Training oneTraining = allA4Trains[i];
			if (oneTraining != null) {
				s1.add(tokenizeDateString(allA4Trains[i].getTrainDaten()
						.toString()), allA4Trains[i].getTrainWeight());
				s2.add(tokenizeDateString(allA4Trains[i].getTrainDaten()
						.toString()), allA4Trains[i].getTrainSecs());
			}
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(s2);
		dataset.addSeries(s1);

		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Trainingsergebnisse Kieser-Studio Köln-Nord (" + new Date()
						+ ")", // title
				"Datum", // y-axis label
				"Gewicht [lbs] / Zeit [secs]", // x-axis label

				dataset, // data
				true, // create legend?
				true, // generate tooltips?
				false // generate URLs?
				);
		response.setContentType("image/png");
		ChartUtilities.writeChartAsPNG(outstream, chart, 600, 450);

	}

	private Training[] getAttachmentsFromDb(int insertPK) {
		Training[] theTrainings = new Training[Training.ALL_TRAININGS];
		String[] theMaschineNames = new String[13];

		log("Going to fetch attachments ... " + new Date());
		try (java.sql.Connection sqlConn = dataSource.getConnection();) {
			java.sql.PreparedStatement preparedStatement = sqlConn
					.prepareStatement("select org_attachment from attachments where cnt_no = ?");
			preparedStatement.setInt(1, insertPK);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String buff;
				buff = resultSet.getString(1);
				int lineCnt = 0;
				StringTokenizer ast = new StringTokenizer(buff, "\r\n");

				// one line
				while (ast.hasMoreTokens()) {

					String line = ast.nextToken();
					Training oneTraining = null;
					if (lineCnt == 0) {
						theMaschineNames = determineMachineAmount(line);
					}
					if (lineCnt == 1) {
					}

					if (lineCnt > 1) {
						StringTokenizer lst = new StringTokenizer(line, ";");
						int tCnt = 0;
						String date = null;
						String weight = null;
						String sec = null;

						// treat one line with all the data
						int rows = 0;
						while (lst.hasMoreTokens()) {
							String actToken = lst.nextToken();
							// log("At token count " + tCnt + " and tok= " +
							// actToken );
							if (tCnt == 0) {
								date = actToken;
								tCnt++;
								continue;
							}
							if (tCnt % 2 == 1) {
								weight = actToken;
								if (weight == null || weight.length() == 0) {
									weight = "0";
								}
							}
							if (tCnt % 2 == 0) {
								sec = actToken;
								if (sec == null || sec.length() == 0) {
									sec = "0";
								}
								// System.out.println("_one Training " + theMaschineNames[rows] + " weight "+ weight + " secs " + sec );
								oneTraining = Training.makeOneA4Train(date,
										theMaschineNames[rows],
										new Integer(weight).intValue(),
										new Integer(sec).intValue());
								theTrainings[lineCnt - 2] = oneTraining;
								storeTraining(oneTraining, dataSource);
								rows++;
							}

							tCnt++;
						}
					}
					lineCnt++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theTrainings;
	}

	private void storeTraining(Training oneTraining, DataSource dataSource2) {
		/* 
		   TRAINIG_ID   | int(11)     | NO   | PRI | NULL    | auto_increment |
		 | USER_ID      | int(11)     | NO   |     | NULL    |                |
		 | TRAIN_DATE   | date        | YES  |     | NULL    |                |
		 | MACHINE_NAME | varchar(16) | YES  |     | NULL    |                |
		 | WEIGHT       | float       | YES  |     | NULL    |                |
		 | SECONDS      | int(11)     | YES  |     | NULL    |                |
		 */
	
		try (java.sql.Connection sqlConn = dataSource.getConnection();) {
			
			java.sql.PreparedStatement updateTrainings = sqlConn
					.prepareStatement(
							"insert into trainings (user_id, train_date, machine_name, weight, seconds) values (?,?,?,?,?)");
			
			updateTrainings.setInt(1, getUserIdFromMail());
	
			updateTrainings.setString(2, oneTraining.getTrainDaten().toString());
			
			updateTrainings.setString(3,oneTraining.getMachineName());
			updateTrainings.setInt(4,oneTraining.getTrainWeight());
			updateTrainings.setInt(5,oneTraining.getTrainSecs());

			updateTrainings.executeUpdate();


		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	private String[] determineMachineAmount(String line) {
		final String machineNames = line.substring(6);
		StringTokenizer strtok = new StringTokenizer(machineNames, ";;");
		String[] allNames = new String[Training.ALL_NAMES];
		int cnt = 0;
		while (strtok.hasMoreElements()) {
			String machName = (String) strtok.nextElement();
			// System.out.println("Found machine " + machName);
			allNames[cnt++] = machName;
		}
		return allNames;
	}

	private Day tokenizeDateString(String string) {
		StringTokenizer tok = new StringTokenizer(string, "-");
		int i = 0;
		int year = 0;
		int mon = 0;
		int day = 0;
		while (tok.hasMoreElements()) {
			switch (i) {
			case 0:
				year = Integer.parseInt((String) tok.nextElement());
				break;
			case 1:
				mon = Integer.parseInt((String) tok.nextElement());
				break;
			case 2:
				day = Integer.parseInt((String) tok.nextElement());
				break;
			default:
				break;
			}
			i++;
		}
		// log("Year " + year + " mon " + mon + " day " + day);
		Day jfDay = new Day(day, mon, year);
		return jfDay;
	}
}
