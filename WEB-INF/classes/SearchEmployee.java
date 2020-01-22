import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.*;
import javax.servlet.http.*;

public class SearchEmployee extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		boolean found = false;
		String tableName = "employee";
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		pw.println("<html>");
		pw.println("<link href='main.css' rel='stylesheet'/>");
		pw.println("<link href='https://fonts.googleapis.com/css?family=Poppins&display=swap' rel='stylesheet'>");
		pw.println("<body>");
		pw.println("<nav class='nav-container'><h4>Employee Management</h4></nav>");
		pw.println("<div class='topic'><span>Search Results</span></div>");

		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		String name = req.getParameter("employeeName");
		try {
			Properties props = new Properties();
			props.setProperty("user", "postgres");
			props.setProperty("password", "6279and77@$");
			props.setProperty("jaasApplicationName", "employee");
			props.setProperty("jaasLogin", "true");
			conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", props);
		} catch (SQLException e) {
			System.out.println("Unable to connect to the database!" + e);
		}
		String SQL_SELECT = "Select * from " + tableName + " WHERE name LIKE '%" + name + "%'";
		try {
			statement = conn.prepareStatement(SQL_SELECT);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				if (!found) {
					pw.println("<div class='table'>");
					pw.println("<table class='employee'>");
					pw.println(
							"<tr><th>Id</th><th>Name</th><th>Number</th><th>Age</th><th>Designation</th><th>Salary</th></tr>");
				}
				found = true;
				pw.println("<tr> <td>" + resultSet.getInt("id") + "</td>   <td>" + resultSet.getString("name")
						+ "</td>   <td>" + resultSet.getString("mobile_number") + "</td>  <td>"
						+ resultSet.getInt("age") + "</td> <td>" + resultSet.getString("designation") + "</td> <td>"
						+ resultSet.getInt("salary") + "</td>  </tr>");
			}
			if (found) {
				pw.println("</table>");
				pw.println("</div>");
			}
		} catch (SQLException e) {
			System.out.println("Unable to fetch the records from database!");
		} finally {
			try {
				resultSet.close();
				statement.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("Unable to close the connection!");
			}
		}
		if (!found) {
			pw.println("<div class='search-result'><span>No records found</span></div>");
		}
		pw.println("<a href='/sampleServlet/list'>Go back</a>");
		pw.println("</body>");
		pw.println("</html>");
		pw.close();
	}
}
