import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;

public class ListAllEmployee extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		DatabaseActions dbactions = new DatabaseActions();
		String tableName = "employee";
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		//String controlString = getServletConfig().getInitParameter("myParam");
		pw.println("<html>");
		pw.println("<link href='main.css' rel='stylesheet'/>");
		pw.println("<link href='https://fonts.googleapis.com/css?family=Poppins&display=swap' rel='stylesheet'>");
		pw.println("<body>");
		pw.println("<nav class='nav-container'><h4>Employee Management</h4></nav>");
		pw.println("<div class='topic'><span>List of Employee Details</span></div>");
		pw.println("<div class='delete-container'><div><form class='emp-delete' action='delete' method='POST'><input type='number' placeholder='Employee ID' name='id'/> <input type='submit' value='Delete'/></form></div>");
		if (dbactions.checkRecords(tableName)) {
			pw.println("<table class='employee'>");
			pw.println("<tr><th>Id</th><th>Name</th><th>Number</th><th>Age</th><th>Designation</th><th>Salary</th></tr>");
			for (Employee emp : dbactions.getRecords(tableName)) {
				pw.println("<tr> <td>"+emp.getId()+"</td>   <td>"+emp.getName()+"</td>   <td>"+emp.getNumber()+"</td>  <td>"+emp.getAge()+"</td> <td>"+emp.getDesignation()+"</td> <td>"+emp.getSalary()+"</td>  </tr>");
			}
			pw.println("</table>");
			pw.println("</div></div>");
		} else {
			System.out.println("No Records found");
		}
		pw.println("</body>");
		pw.println("</html>");
		pw.close();
	}
}
