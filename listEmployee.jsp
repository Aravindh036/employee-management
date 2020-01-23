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
	<nav class='nav-container'>
		<h4>Employee Management</h4>
		<form action='search' method='GET'>
			<input type='text' placeholder='name' name='employeeName'/> 
			<input type='submit' value='Search'  class='emp-search'/>
		</form>
	</nav>
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
		<a href='/sampleServlet/'>Go back</a>
	</body>
</html>