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
@WebServlet("/ViewUserDetails")
public class ViewUserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:/MySQLDS")
	private DataSource dataSource;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date myDate;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewUserDetails() {
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

		StringBuffer tab = makeUserTab(1);
		final PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<body>");
		out.println(tab.toString());
		out.println("</html>");
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log("Bin in do Post");
	}

	private StringBuffer makeUserTab(int user_id) {

		StringBuffer utab = new StringBuffer();
		log("Going to fetch user data ... " + new Date());
		try (java.sql.Connection sqlConn = dataSource.getConnection();) {
			java.sql.PreparedStatement preparedStatement = sqlConn
					.prepareStatement("select USER_ID, MAIL, FIRSTNAME, LASTNAME, STREET, STREETNO, ZIP, CITY, CELLPHONE, BIRTH_DATE, ENTER_DATE from users where user_id = "
							+ user_id);

			ResultSet resultSet = preparedStatement.executeQuery();
			utab.append( " <p>Details zum User dieses Kontos: </p> ");
			utab.append("<table border=\"1\"><caption>user details</caption> <thead><tr> <th>ID</th> <th>Email</th> <th>first name</th><th>last name</th><th>street</th><th>street no.</th> <th>zip</th> <th>city</th> <th>cellphone</th> <th>birth date</th> <th>enter date</th> </tr></thead><tbody>");
			while (resultSet.next()) {
				utab.append(" <tr> <td>");
				utab.append(resultSet.getString(1) + " ");
				utab.append("</td> <td>");
				utab.append(resultSet.getString(2) + " ");
				utab.append("</td> <td>");
				utab.append(resultSet.getString(3) + " ");
				utab.append("</td> <td>");
				utab.append(resultSet.getString(4) + " ");
				utab.append("</td> <td>");
				utab.append(resultSet.getString(5) + " ");
				utab.append("</td> <td>");
				utab.append(resultSet.getString(6) + " ");
				utab.append("</td> <td>");
				utab.append(resultSet.getString(7) + " ");
				utab.append("</td> <td>");
				utab.append(resultSet.getString(8) + " ");
				utab.append("</td> <td>");
				utab.append(resultSet.getString(9) + " ");
				utab.append("</td> <td>");
				utab.append(resultSet.getString(10) + " ");
				utab.append("</td> <td>");
				utab.append(resultSet.getString(11) + " ");
				utab.append("</td> </tr>");
				
				
			utab.append("</tbody> </table>");
			utab.append( "<p> </p>");
			utab.append("<form action=\"http://localhost:8080/GymView.war/ViewTable\" method=\"get\" autocomplete=\"off\"> "+		
					  "<button type=\"submit\" name=\"action\" value=\"1\">Trainingsübersicht</button> "+
					"</form>");
			utab.append( "<p> </p>");
			utab.append("<form action=\"http://localhost:8080/GymView.war/GymView\" method=\"get\" autocomplete=\"off\"> "+		
					  "<button type=\"submit\" name=\"action\" value=\"1\">Einzeltraining</button> "+
					"</form>");
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return utab;
	}
}
