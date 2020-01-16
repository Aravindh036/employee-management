package employee;
import java.util.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.spi.LoginModule;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;

public class LoginModuleClass implements LoginModule{
	private CallbackHandler callbackHandler = null;
	private boolean authentication = false;
	@Override
	public boolean login() throws LoginException{
		Callback[] callback = new Callback[2];
		callback[0] = new NameCallback("Username : ");
		callback[1] = new PasswordCallback("Password : ",false);
		try{
			callbackHandler.handle(callback);
		}
		catch(Exception e){
			System.out.println("Exception : "+e);
		}
		String name = ((NameCallback) callback[0]).getName();
		String password = new String(((PasswordCallback) callback[1]).getPassword());
		DatabaseActions dbactions = new DatabaseActions();
		if(dbactions.checkAdmin(name,password,"admin_auth")){
			System.out.println("login success");
			authentication = true;
		}
		else{
			authentication = false;
			throw new FailedLoginException("login failure");
		}
		return authentication;
	}

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String,?> sharedState, Map<String,?> options){
		this.callbackHandler = callbackHandler;
	}

	@Override 
	public boolean commit() throws LoginException{
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