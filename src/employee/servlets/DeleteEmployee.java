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

public class DeleteEmployee extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		DatabaseActions dbactions = new DatabaseActions();
		String tableName = "employee";
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		pw.println("<html>");
		pw.println("<link href='main.css' rel='stylesheet'/>");
		pw.println("<link href='https://fonts.googleapis.com/css?family=Poppins&display=swap' rel='stylesheet'>");
		pw.println("<body>");
		pw.println("<nav class='nav-container'><h4>Employee Management</h4></nav>");
		String id = req.getParameter("id");
		if ((id != "")) {
			if(!dbactions.searchColumn(id, tableName, "id")){
				pw.println("<div class='align-center m-t-100'><span>No Employee found for the provided ID!</span></div>");
			}
			else{
				dbactions.deleteRecord(Integer.parseInt(id), tableName);	
				pw.println("<div class='topic'><span>Successfuly deleted the Employee ( ID: "+id+") details</span></div>");
			}
		}else {
			pw.println("<div class='topic'><span>Provide the necessary details!!!</span></div>");
		}
		pw.println("<a href='/sampleServlet/deleteEmployee.jsp'>Delete Another Employee's Details ?</a>");
		pw.println("<a href='/sampleServlet/index.jsp'>Home</a>");
		pw.println("</body>");
		pw.println("</html>");
		pw.close();
	}
}
