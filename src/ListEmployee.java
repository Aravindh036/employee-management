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
@WebServlet(value = "/list")
public class ListEmployee extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Employee e = new Employee();
		String tableName = "employee";
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		pw.println("<html>");
		//pw.println("<head><style> *{margin:0;border:0;box-sizing:border-box;font-family: 'Poppins', sans-serif;} body{width:100%;height:100vh;background-color:#537EFF;} .nav-container{width:100%;padding:20px 40px;display:flex;justify-content:space-between;align-items:center;background-color:#032380;color:#fff} input{padding:3px 10px; border-radius:4px;} </style></head>");
		pw.println("<link href='https://fonts.googleapis.com/css?family=Poppins&display=swap' rel='stylesheet'>");
		pw.println("<body>");
		pw.println("<nav class='nav-container'><h4>Employee Management</h4><form action='search' method='GET'><input type='text' placeholder='name' name='employeeName'/> <input type='submit' value='Search'/></form></nav>");
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement statement =null;
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
		String SQL_SELECT = "Select * from " + tableName;
		try {
			statement = conn.prepareStatement(SQL_SELECT);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				pw.println(resultSet.getInt("id"));
				pw.println(resultSet.getString("mobile_number"));
				pw.println(resultSet.getString("name"));
				pw.println(resultSet.getInt("age"));
				pw.println(resultSet.getString("designation"));
				pw.println(resultSet.getInt("salary"));
			}
		} catch (SQLException e) {
			System.out.println("Unable to fetch the records from database!");
		}
		finally{
			try{
				resultSet.close();
				statement.close();
				conn.close();
			}
			catch(SQLException e){
				System.out.println("Unable to close the connection!");
			}
		}
		pw.println("</body>");
		pw.println("</html>");
		pw.close();
	}
}
