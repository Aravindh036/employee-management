package employee.servlets;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutAdmin extends HttpServlet {

  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession(false);
	session.invalidate();
	session = request.getSession(false);
	request.logout();
	System.out.println("Session : " + request.getSession(false)+ " : "+request.getRemoteUser());
	System.out.println("Session : " + request.getSession() + " : "+request.getRemoteUser());
	response.sendRedirect(request.getContextPath() + "/admin/index.jsp");
    
  }

}