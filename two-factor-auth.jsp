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
		box-shadow:0 2px 4px rgba(0, 0, 0, 0.336);
		border-radius:4px;
	}
	.iframe{
		text-align:center;
	}
	.base-wrapper {
		border:0!important;
	}
	
	</style>
	<script src="Duo-Web-v2.js"></script>
	<meta http-equiv="content-type" content="text/html; charset=windows-1252"/>
</head>
<body>
	<div class="topic"><span>Two Factor Authentication</span></div>
	<% 
		String duoIntegrationKey = "DIP8GKFBFD0SXIBA8FE8";
		String duoSecretKey = "RmGwUK5j8PG2tLmzAjkThaSkBcoJOVQ6f6VbR7k8";
		String duoApplicationKey = "QpWTYCufqU4npxKJtFNmRwA9JID9AZGBO1S4U1Iw";
		String signedRequest = DuoWeb.signRequest(duoIntegrationKey,duoSecretKey,duoApplicationKey, "admin");
	%>
	<div class="iframe">
		<iframe id="duo_iframe"
		frameBorder="0"
		data-host="api-9c2ee39c.duosecurity.com"
		data-sig-request="<%=signedRequest%>"
		data-post-action="/sampleServlet/admin/success.jsp"
		data-post-argument="resp"
		></iframe>
	</div>
</body>
</html>