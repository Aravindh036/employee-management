<%@ page import="java.sql.*,java.util.Properties"%>
<html>
	<head>
		<title>Dashboard</title>
		 <link href="main.css" rel="stylesheet"/>
		<link href='https://fonts.googleapis.com/css?family=Poppins&display=swap' rel='stylesheet'>
	</head>
	<body>
	<nav class='nav-container'><h4>Employee Management</h4></nav>
		<div class="topic"><span>Update Employee Details</span></div>
		<%
			int id = Integer.parseInt(request.getParameter("id"));
			String tableName="employee";
			boolean found = false;
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
			String SQL_SELECT = "Select * from " + tableName +" WHERE id = "+id;
			try {
				statement = conn.prepareStatement(SQL_SELECT);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					found = true;
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
			if(found)
			{
			%>
				<form class="add-form"  action="update" method="POST">
					<div class="id">
						<span>Id</span>
						<input type="number" name="id" id="emp-id" value="<%=id%>" readonly>
					</div>
					<div class="name">
						<span>Name</span>
						<input type="text" name="name" id="emp-name">
					</div>
					<div class="mobile-number">
						<span>Mobile Number</span>
						<input type="number" name="mobile" id="emp-mobile">
					</div>
					<div class="age">
						<span>Age</span>
						<input type="number" name="age" id="emp-age">
					</div>
					<div class="designation">
						<span>Designation</span>
						<input type="text" name="designation" id="emp-designation">
					</div>
					<div class="salary">
						<span>Salary</span>
						<input type="number" name="salary" id="emp-salary">
					</div>
					<input type="submit" value="Update Employee">
				</form>
			<%
			}
			else{
			%>
				<span>No Record found for the Employee ID</span>
			<%
			}
		%>
	</body>
</html>