package com.mc.entities;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Date;

import javax.annotation.Resource;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet
 */
@WebServlet("/ViewTable")
public class ViewTable extends HttpServlet {
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
	public ViewTable() {
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
        StringBuffer tab = makeHtmlTable(23);
        final PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>"); out.println("<html>");
		out.println("<body>"); 
		out.println(tab.toString());
		out.println("</html>"); 
		out.flush();
	}

	private void logUserAccess() {
		try (java.sql.Connection sqlConn = dataSource.getConnection();) {
			long time = System.currentTimeMillis();
			java.sql.Timestamp timestamp = new java.sql.Timestamp(time);

			java.sql.PreparedStatement preparedStatement = sqlConn
					.prepareStatement("insert into logons values (?,?,?)");

			preparedStatement.setInt(1, 1);
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

	private StringBuffer makeHtmlTable(int insertPK) {
		
		StringBuffer htab = new StringBuffer();
		log("Going to fetch attachments ... " + new Date());
		try (java.sql.Connection sqlConn = dataSource.getConnection();) {
			java.sql.PreparedStatement preparedStatement = sqlConn
					.prepareStatement("select TRAINING_ID, USER_ID, TRAIN_DATE, MACHINE_NAME, WEIGHT, SECONDS from trainings");

			ResultSet resultSet = preparedStatement.executeQuery();
            htab.append("<table border=\"1\"><caption>Trainings Kieser Studio(s)</caption> <thead><tr> <th>Training ID</th> <th>User ID</th> <th>Datum</th><th>Gerät</th><th>Gewicht</th><th>Sekunden</th></tr></thead><tbody>");
			while (resultSet.next()) {
				htab.append(" <tr> <td>");
				htab.append(resultSet.getString(1) + " ");
				htab.append("</td> <td>");
				htab.append(resultSet.getString(2) + " ");
				htab.append("</td> <td>");
				htab.append(resultSet.getString(3) + " ");
				htab.append("</td> <td>");
				htab.append(resultSet.getString(4) + " ");
				htab.append("</td> <td>");
				htab.append(resultSet.getString(5) + " ");
				htab.append("</td> <td>");
				htab.append(resultSet.getString(6) + " ");
				htab.append("</td> </tr>");
			}
			htab.append("</tbody> </table>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return htab;
	}
}
