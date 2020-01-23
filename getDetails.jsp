<%@ page import="java.sql.*,java.util.Properties"%>
<%@ page import="employee.dblayer.DatabaseActions" %>
<%@ page import="employee.model.Employee"%>
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
			DatabaseActions dbactions = new DatabaseActions();
			int id = Integer.parseInt(request.getParameter("id"));
			String tableName="employee";
			if(dbactions.searchColumn(request.getParameter("id"), tableName, "id"))
			{
				for (Employee emp : dbactions.getColumn(request.getParameter("id"),tableName,"id")) {
			%>
				<form class="add-form"  action="update" method="POST">
					<div class="id">
						<span>Id</span>
						<input type="number" name="id" id="emp-id" value="<%=id%>" readonly>
					</div>
					<div class="name">
						<span>Name</span>
						<input type="text" name="name" id="emp-name" value="<%=emp.getName()%>">
					</div>
					<div class="mobile-number">
						<span>Mobile Number</span>
						<input type="number" name="mobile" id="emp-mobile" value="<%=emp.getNumber()%>">
					</div>
					<div class="age">
						<span>Age</span>
						<input type="number" name="age" id="emp-age" value="<%=emp.getAge()%>">
					</div>
					<div class="designation">
						<span>Designation</span>
						<input type="text" name="designation" id="emp-designation" value="<%=emp.getDesignation()%>">
					</div>
					<div class="salary">
						<span>Salary</span>
						<input type="number" name="salary" id="emp-salary" value="<%=emp.getSalary()%>">
					</div>
					<input type="submit" value="Update Employee">
				</form>
			<%
				}
			}
			else{
			%>
				<div class="align-center m-t-100"><span>No Record found for the Employee ID</span></div>
			<%
			}
		%>
		<a href='/sampleServlet/updateEmployee.jsp'>Go back</a>
	</body>
</html>