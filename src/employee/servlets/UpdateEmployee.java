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

public class UpdateEmployee extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Employee emp = new Employee();
		String[] columnString = { "Name", "ID", "Age", "Designation", "Salary" };
		DatabaseActions dbactions = new DatabaseActions();
		String tableName = "employee";
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		pw.println("<html>");
		pw.println("<link href='main.css' rel='stylesheet'/>");
		pw.println("<link href='https://fonts.googleapis.com/css?family=Poppins&display=swap' rel='stylesheet'>");
		pw.println("<body>");
		pw.println("<nav class='nav-container'><h4>Employee Management</h4></nav>");
		emp.setId(Integer.parseInt(req.getParameter("id")));
		emp.setName(req.getParameter("name"));
		emp.setNumber(req.getParameter("mobile"));
		emp.setAge(Integer.parseInt(req.getParameter("age")));
		emp.setSalary(Integer.parseInt(req.getParameter("salary")));
		emp.setDesignation(req.getParameter("designation"));
		if ((emp.getName() != "") && (emp.getNumber() != "") && (emp.getAge() != 0) && (emp.getSalary() != 0)
				&& (emp.getDesignation() != "")) {
			dbactions.updateAllField(emp, emp.getId(), tableName);
			pw.println("<div class='topic'><span>Updated Employee(ID : "+emp.getId()+") details successfuly !!</span></div>");
		} else {
			pw.println("Provide the necessary details!");
		}
		pw.println("<a href='/sampleServlet/admin/updateEmployee.jsp'>Update Another Employee's details ?</a>");
		pw.println("<a href='/sampleServlet/admin/index.jsp'>Home</a>");
		pw.println("</body>");
		pw.println("</html>");
		pw.close();
	}
}
