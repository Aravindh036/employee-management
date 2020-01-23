<%@ page import="java.sql.*,java.util.Properties"%>
<%@ page import="employee.dblayer.DatabaseActions"%>
<%@ page import="employee.model.Employee"%>
<html>
	<head>
		<title>Dashboard</title>
		 <link href='main.css' rel='stylesheet'/>
		<link href='https://fonts.googleapis.com/css?family=Poppins&display=swap' rel='stylesheet'>
	</head>
	<body>
	<nav class='nav-container'><h4>Employee Management</h4></nav>
		<div class="topic"><span>Update Employee Details</span></div>
		<div class="emp-update">
			<form action="getDetails.jsp" method="GET">
				<input type="number" placeholder="Employee ID" name="id"/>
				<input type="submit" value="Search Employee"/>
			</form>
		</div>
		<%
			String tableName = "employee";
			DatabaseActions dbactions = new DatabaseActions();
			if (dbactions.checkRecords(tableName)) {
				%>
		<div class='table'>
			<table class='employee'>
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>Number</th>
					<th>Age</th>
					<th>Designation</th>
					<th>Salary</th>
				</tr>
		<%
		for (Employee emp : dbactions.getRecords(tableName)) {
			out.println("<tr> <td>"+emp.getId()+"</td>   <td>"+emp.getName()+"</td>   <td>"+emp.getNumber()+"</td>  <td>"+emp.getAge()+"</td> <td>"+emp.getDesignation()+"</td> <td>"+emp.getSalary()+"</td>  </tr>");
		}
		%>
			</table>
		</div>
		<%
	} else {
		%>
		<div class='search-result'>
			<span>No records found</span>
		</div>
		<%
		}
		%>
		<a href='/sampleServlet/list'>Go back</a>
	</body>
</html>