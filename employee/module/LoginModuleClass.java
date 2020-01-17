package employee.module;
import java.util.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.spi.LoginModule;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextInputCallback;

import employee.principal.EmployeePrincipal;
import employee.dblayer.DatabaseActions;

public class LoginModuleClass implements LoginModule{
	private CallbackHandler callbackHandler = null;
	private boolean authentication = false;
	private Subject subject;
	private EmployeePrincipal employeeType, employeeName;
	private String name, password, choice;
	private String choiceArray[] = {"Admin","Operator","Employee"};
	private int id;
	
	@Override
	public boolean login() throws LoginException{
		Callback[] callback = new Callback[3];
		callback[0] = new TextInputCallback("Choice : ");
		callback[1] = new NameCallback("Username : ");
		callback[2] = new PasswordCallback("Password : ",false);
		try{
			callbackHandler.handle(callback);
		}
		catch(Exception e){
			System.out.println("Exception : "+e);
		}
		choice = ((TextInputCallback) callback[0]).getText();
		name = ((NameCallback) callback[1]).getName();
		password = new String(((PasswordCallback) callback[2]).getPassword());
		DatabaseActions dbactions = new DatabaseActions();
		switch(Integer.parseInt(choice)){
			case 1:
				if(dbactions.checkCreds(name,password,"admin_auth")){
					System.out.println("login success");
					authentication = true;
				}
				else{
					authentication = false;
					throw new FailedLoginException("login failure");
				}
				break;
			case 2:
				if(dbactions.checkCreds(name,password,"operator_auth")){
					System.out.println("login success");
					authentication = true;
				}
				else{
					authentication = false;
					throw new FailedLoginException("login failure");
				}
				break;
			case 3:
				if(dbactions.checkCreds(name,password,"employee_auth")){					
					System.out.println("login success");
					authentication = true;
				}
				else{
					authentication = false;
					throw new FailedLoginException("login failure");
				}
				break;
			default:
		}
		return authentication;
	}

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String,?> sharedState, Map<String,?> options){
		this.callbackHandler = callbackHandler;
		this.subject = subject;
	}

	@Override 
	public boolean commit() throws LoginException{
		if (authentication == false) {
            return false;
        } else {
			employeeType = new EmployeePrincipal(choiceArray[Integer.parseInt(choice)-1]);
			employeeName = new EmployeePrincipal(name);
            if (!subject.getPrincipals().contains(employeeType)){
				subject.getPrincipals().add(employeeType);	
				subject.getPrincipals().add(employeeName);
			}
		}
		return authentication;
	}

	@Override
	public boolean abort() throws LoginException{
		return false;
	}

	@Override
	public boolean logout() throws LoginException{
		return false;
	}
}
