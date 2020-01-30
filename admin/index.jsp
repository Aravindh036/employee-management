<%@ page import="java.lang.Exception, com.duosecurity.duoweb.DuoWeb"%>

<html>
	<head>
		<title>Add Employee</title>
		<link href="main.css" rel="stylesheet"/>
		<link href='https://fonts.googleapis.com/css?family=Poppins&display=swap' rel='stylesheet'>
	<head>
	<body>
	<nav class='nav-container'><h4>Employee Management</h4></nav>
	<div class="topic"><span>Available Options</span></div>
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
		<div class="options">
			<a href="/sampleServlet/admin/addEmployee.jsp">Add Employee Details</a>
			<a href="/sampleServlet/admin/listEmployee.jsp">Search Employee</a>
			<a href="/sampleServlet/admin/deleteEmployee.jsp">Delete Employee Details</a>
			<a href="/sampleServlet/admin/updateEmployee.jsp">Update Employee Details</a>
			<a href="/sampleServlet/admin/logout.jsp">Logout</a>
		</div>
	</body>
</html>