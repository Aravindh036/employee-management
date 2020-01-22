<%@ page import="java.sql.*,java.util.Properties"%>
<html>
	<head>
		<title>Dashboard</title>
		 <link href='main.css' rel='stylesheet'/>
		<link href='https://fonts.googleapis.com/css?family=Poppins&display=swap' rel='stylesheet'>
	</head>
	<body>
	<nav class='nav-container'><h4>Employee Management</h4></nav>
		<div class="topic"><span>Update Employee Details</span></div>
		<div class="emp-update"><form action="getDetails.jsp" method="GET"><input type="number" placeholder="Employee ID" name="id"/><input type="submit" value="Search Employee"/></form></div>
		<%
			boolean found = false;
			String tableName="employee";
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
					if(!found){
						out.println("<div class='table'>");
						out.println("<table class='employee'>");
						out.println("<tr><th>Id</th><th>Name</th><th>Number</th><th>Age</th><th>Designation</th><th>Salary</th></tr>");		
					}
					found=true;
					out.println("<tr> <td>"+resultSet.getInt("id")+"</td>   <td>"+resultSet.getString("name")+"</td>   <td>"+resultSet.getString("mobile_number")+"</td>  <td>"+resultSet.getInt("age")+"</td> <td>"+resultSet.getString("designation")+"</td> <td>"+resultSet.getInt("salary")+"</td>  </tr>");
				}
				if(found){
					out.println("</table>");
					out.println("</div>");
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
			if(!found){
			out.println("<div class='search-result'><span>No records found</span></div>");
		}
			out.println("<a href='/sampleServlet/list'>Go back</a>");
		%>
	</body>
</html>