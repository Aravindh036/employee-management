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

public class ListEmployee extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		DatabaseActions dbactions = new DatabaseActions();
		String tableName = "employee";
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		pw.println("<html>");
		pw.println("<link href='main.css' rel='stylesheet'/>");
		pw.println("<link href='https://fonts.googleapis.com/css?family=Poppins&display=swap' rel='stylesheet'>");
		pw.println("<body>");
		pw.println("<nav class='nav-container'><h4>Employee Management</h4><form action='search' method='GET'><input type='text' placeholder='name' name='employeeName'/> <input type='submit' value='Search'  class='emp-search'/></form></nav>");
		pw.println("<div class='topic'><span>List of Employee Details</span></div>");
		if (dbactions.checkRecords(tableName)) {
			pw.println("<div class='table'>");
			pw.println("<table class='employee'>");
			pw.println("<tr><th>Id</th><th>Name</th><th>Number</th><th>Age</th><th>Designation</th><th>Salary</th></tr>");
			for (Employee emp : dbactions.getRecords(tableName)) {
				pw.println("<tr> <td>"+emp.getId()+"</td>   <td>"+emp.getName()+"</td>   <td>"+emp.getNumber()+"</td>  <td>"+emp.getAge()+"</td> <td>"+emp.getDesignation()+"</td> <td>"+emp.getSalary()+"</td>  </tr>");
			}
			pw.println("</table>");
			pw.println("</div>");
		} else {
			pw.println("<div class='search-result'><span>No records found</span></div>");
		}
		pw.println("</body>");
		pw.println("</html>");
		pw.close();
	}
}
