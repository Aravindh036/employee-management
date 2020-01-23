package employee.servlets;
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

import employee.dblayer.*;
import employee.model.*;

public class SearchEmployee extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		DatabaseActions dbactions = new DatabaseActions();
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
		for (Employee emp : dbactions.getSearchResults(tableName,req.getParameter("employeeName"))) {
			if (!found) {
				pw.println("<div class='table'>");
				pw.println("<table class='employee'>");
				pw.println("<tr><th>Id</th><th>Name</th><th>Number</th><th>Age</th><th>Designation</th><th>Salary</th></tr>");
			}
			found = true;
			pw.println("<tr> <td>"+emp.getId()+"</td>   <td>"+emp.getName()+"</td>   <td>"+emp.getNumber()+"</td>  <td>"+emp.getAge()+"</td> <td>"+emp.getDesignation()+"</td> <td>"+emp.getSalary()+"</td>  </tr>");
		}
		if (found) {
			pw.println("</table>");
			pw.println("</div>");
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
