<%-------------------------------------------------------------------%>
<%-- Copyright 2013 Code Strategies                                --%>
<%-- This code may be freely used and distributed in any project.  --%>
<%-- However, please do not remove this credit if you publish this --%>
<%-- code in paper or electronic form, such as on a web site.      --%>
<%-------------------------------------------------------------------%>

<%@ page session="true"%>

User '<%=request.getRemoteUser()%>' has been logged out.

<% session.invalidate();
	response.sendRedirect(request.getContextPath()+ "/admin/index.jsp");
 %>

<br/><br/>
<a href="test">Click here to go to test servlet</a>