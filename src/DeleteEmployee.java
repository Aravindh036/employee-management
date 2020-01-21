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
@WebServlet(value = "/delete")
public class DeleteEmployee extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String tableName = "employee";
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		pw.println("<html>");
		// pw.println("<head><style>
		// *{margin:0;border:0;box-sizing:border-box;font-family: 'Poppins',
		// sans-serif;} body{width:100%;height:100vh;background-color:#537EFF;}
		// .nav-container{width:100%;padding:20px
		// 40px;display:flex;justify-content:space-between;align-items:center;background-color:#032380;color:#fff}
		// input{padding:3px 10px; border-radius:4px;} </style></head>");
		pw.println("<link href='https://fonts.googleapis.com/css?family=Poppins&display=swap' rel='stylesheet'>");
		pw.println("<body>");
		pw.println("<nav class='nav-container'><h4>Employee Management</h4></nav>");
		String id = req.getParameter("id");
		if ((id != "")) {
			Connection conn = null;
			ResultSet resultSet = null;
			PreparedStatement preparedStatement =null;
			try {
				Properties props = new Properties();
				props.setProperty("user", "postgres");
				props.setProperty("password", "6279and77@$");
				props.setProperty("jaasApplicationName", "employee");
				props.setProperty("jaasLogin", "true");
				conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", props);
			} catch (SQLException e) {
				pw.println("Unable to connect to the database!" + e);
			}
			String check = "Select * from " + tableName + " WHERE id ='"+id+"'";
			
			boolean found = false;
			try {
				preparedStatement = conn.prepareStatement(check);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					found = true;
				}
			} catch (SQLException e) {
				pw.println("Unable to fetch the records from database!"+e);
			}
		
			String SQL_SELECT = "DELETE FROM " + tableName + " WHERE id=?";
			try {
				if(!found){
					pw.println("No Employee found for the provided ID!");
				}
				else{
					preparedStatement = conn.prepareStatement(SQL_SELECT);
					preparedStatement.setInt(1, Integer.parseInt(id));
					preparedStatement.executeUpdate();		
					pw.println("<span>Sucess, Employee Details Deleted</span>");
				}
			} catch (SQLException e) {
				pw.println("Error in deleting the Employee's Details for the ID :" + id);
			}
			finally{
				try{
					preparedStatement.close();
					conn.close();
				}
				catch(SQLException e){
					System.out.println("Unable to close the connection!");
				}
			} 
		}else {
			pw.println("Provide the necessary details!");
		}

		pw.println("<a href='/sampleServlet/listAll'>Delete Another Employee's Details?</a>");
		pw.println("<a href='/sampleServlet/index.html'>Home</a>");
		pw.println("</body>");
		pw.println("</html>");
		pw.close();
	}
}
