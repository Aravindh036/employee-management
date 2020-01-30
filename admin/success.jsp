<%@ page import="com.realm.CustomRealm"%>
<%@ page import="com.duosecurity.duoweb.DuoWeb"%>
<html>
<head>
<link href='https://fonts.googleapis.com/css?family=Poppins&display=swap' rel='stylesheet'/>
	<style>
	* {
	  margin: 0;
	  border: 0;
	  box-sizing: border-box;
	  font-family: "Poppins", sans-serif;
	}
	body {
	  width: 100%;
	  height: 100vh;
	  background-color: #537eff;
	}
	.topic{
		text-align:center;
		margin:30px 0;
	}
	.topic span{
		margin:20px 60px;
		font-size:25px;
		font-weight:bold;
	}
	.m-t-100{
		margin:100px;
	}

	.m-t-200{
		margin:200px;
	}

	.m-t-300{
		margin:300px;
	}

	.m-t-400{
		margin:400px;
	}

	.m-t-500{
		margin:500px;
	}

	.m-t-600{
		margin:600px;
	}
	.bold{
		font-weight:bold;
		font-size:15px;
	}
	
	.options{
		text-align:center;
	}
	a {
	  text-decoration: none;
	  color: #fff;
	  display: block;
	  margin: 15px 20px;
	}
	a:hover{
		color:#032380;
		font-weight:bold;
	}
	
	iframe {
		width: 100%;
		min-width: 304px;
		max-width: 620px;
		height: 330px;
	}
	.iframe{
		text-align:center;
	}
	</style>
	<script src="Duo-Web-v2.js"></script>
	<meta http-equiv="content-type" content="text/html; charset=windows-1252"/>
</head>
<body>
	<%
		session.setAttribute("key",request.getParameter("resp"));
		session.setAttribute("login","done");
	%>
	<div class="topic"><span>Login Sucess</span></div>
	<div class = "options">
		<a href="/sampleServlet/admin/index.jsp">Home</a>
	</div>
</body>
</html>