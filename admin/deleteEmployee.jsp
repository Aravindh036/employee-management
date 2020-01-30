<%@ page import="java.sql.*,java.util.Properties"%>
<%@ page import="employee.dblayer.DatabaseActions"%>
<%@ page import="employee.model.Employee"%>
<%@ page import="java.lang.Exception, com.duosecurity.duoweb.DuoWeb"%>

<html>
	<head>
		<title>Dashboard</title>
		 <link href='main.css' rel='stylesheet'/>
		<link href='https://fonts.googleapis.com/css?family=Poppins&display=swap' rel='stylesheet'>
	</head>
	<body>
	<nav class='nav-container'><h4>Employee Management</h4></nav>
		<div class='topic'><span>List of Employee Details</span></div>
			<div class="emp-update">
				<form class='emp-delete' action='delete' method='POST'>
					<input type='number' placeholder='Employee ID' name='id'/> 
					<input type='submit' value='Delete'/>
				</form>
			</div>
		<%
			String authenticatedUser="";
			String duoIntegrationKey = "DIP8GKFBFD0SXIBA8FE8";
			String duoSecretKey = "RmGwUK5j8PG2tLmzAjkThaSkBcoJOVQ6f6VbR7k8";
			String duoApplicationKey = "QpWTYCufqU4npxKJtFNmRwA9JID9AZGBO1S4U1Iw";
			try{
				if((session.getAttribute("login").toString()=="done")&&(session.getAttribute("key").toString()!="")){
			
					authenticatedUser = DuoWeb.verifyResponse(duoIntegrationKey,duoSecretKey,duoApplicationKey,session.getAttribute("key").toString());
					System.out.println(authenticatedUser);
					if(!(request.getRemoteUser()).equals(authenticatedUser)){
						response.sendRedirect(request.getContextPath()+ "/two-factor-auth.jsp");
					}
				}
				
				else{
					response.sendRedirect(request.getContextPath()+ "/two-factor-auth.jsp");
				}
			}
			catch(Exception e){
				session.setAttribute("login","done");
				session.setAttribute("key","");
				response.sendRedirect(request.getContextPath()+ "/two-factor-auth.jsp");
			}
		%>
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