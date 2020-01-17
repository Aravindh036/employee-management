package employee;

import java.sql.*;
import java.util.*;
import javax.security.auth.Subject;
import javax.security.auth.login.*;

import employee.actions.AdminAction;
import employee.actions.EmployeeAction;
import employee.actions.OperatorAction;
import employee.view.View;
import employee.dblayer.DatabaseActions;

import java.security.Principal;
import java.security.PrivilegedAction;


public class Driver {
	public static void main(String[] args) throws SQLException {
		
		String type = null,name=null;
		View helper = new View();
		LoginContext login = null;
		
		try {
			DatabaseActions dbactions = new DatabaseActions();
			System.out.println("Hello main : ");
			login = new LoginContext("employee", new CallbackHandlerClass());
			helper.homeChoices();
			login.login();
			Subject subject = login.getSubject();
			Iterator<Principal> principalIterator = subject.getPrincipals().iterator();
			while (principalIterator.hasNext()) {
				Principal p = (Principal) principalIterator.next();
				System.out.println(p.toString());
				type = p.toString();
				p = (Principal) principalIterator.next();
				name = p.toString();
			}
			if (type.equals("Admin")) {
				PrivilegedAction<AdminAction> adminAction = new AdminAction();
				Subject.doAsPrivileged(subject, adminAction, null);
			} else if (type.equals("Operator")) {
				PrivilegedAction<OperatorAction> operatorAction = new OperatorAction();
				Subject.doAsPrivileged(subject, operatorAction, null);
			} else if (type.equals("Employee")) {
				PrivilegedAction<EmployeeAction> employeeAction = new EmployeeAction(name);
				Subject.doAsPrivileged(subject, employeeAction, null);
			}
			/*
			 * int choice = sc.nextInt(); sc.nextLine(); if (choice == 1) { login.login();
			 * System.out.println("Authenticated!"); Subject subject = login.getSubject();
			 * Iterator principalIterator = subject.getPrincipals().iterator();
			 * System.out.println("Authenticated user has the following Principals:"); while
			 * (principalIterator.hasNext()) { Principal p =
			 * (Principal)principalIterator.next(); System.out.println("\t" + p.toString());
			 * } break; } else if (choice == 2) { System.out.print("Admin ID : "); String
			 * adminId = sc.nextLine(); System.out.print("Old Password : "); String password
			 * = sc.nextLine(); if (dbactions.checkAdmin(adminId, password, adminTable)) {
			 * System.out.print("New Password : "); String newPassword = sc.nextLine();
			 * dbactions.changePassword(adminId, newPassword, adminTable);
			 * System.out.println("Password changed successfully!!"); continue; } else {
			 * System.out.println("ID or Password incorrect!"); continue; } } }
			 */
		} catch (Exception e) {
			System.out.println("hello : "+e);
		}
	}
}
