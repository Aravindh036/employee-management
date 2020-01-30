package employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class TwoFALoginModule implements LoginModule {

  private CallbackHandler handler;
  private Subject subject;
  private UserPrincipal userPrincipal;
  private RolePrincipal rolePrincipal;
  private String login;
  private List<String> userGroups;

  @Override
  public void initialize(Subject subject,
      CallbackHandler callbackHandler,
      Map<String, ?> sharedState,
      Map<String, ?> options) {

    handler = callbackHandler;
    this.subject = subject;
  }

  @Override
  public boolean login() throws LoginException {
    Callback[] callbacks = new Callback[2];
    callbacks[0] = new NameCallback("login");
    callbacks[1] = new PasswordCallback("password", true);
    try {
      handler.handle(callbacks);
      String name = ((NameCallback) callbacks[0]).getName();
      String password = String.valueOf(((PasswordCallback) callbacks[1])
          .getPassword());
		if(password.length()<20){
			System.out.println("in primary auth");
			login = name;
			userGroups = new ArrayList<String>();
			userGroups.add("admin");
			return true;
		}
		System.out.println("in custom login file "+name);
      if (name != null && name.equals("admin") && password != null && password.equals("admin")) {
			login = name;
			userGroups = new ArrayList<String>();
			userGroups.add("user");
			return true;
      }

      throw new LoginException("Authentication failed");

    } catch (IOException e) {
      throw new LoginException(e.getMessage());
    } catch (UnsupportedCallbackException e) {
      throw new LoginException(e.getMessage());
    }

  }

  @Override
  public boolean commit() throws LoginException {

    userPrincipal = new UserPrincipal(login);
    subject.getPrincipals().add(userPrincipal);

    if (userGroups != null && userGroups.size() > 0) {
      for (String groupName : userGroups) {
        rolePrincipal = new RolePrincipal(groupName);
        subject.getPrincipals().add(rolePrincipal);
      }
    }

    return true;
  }

  @Override
  public boolean abort() throws LoginException {
    return false;
  }

  @Override
  public boolean logout() throws LoginException {
	System.out.println("in logout in 2fa login module");
    subject.getPrincipals().remove(userPrincipal);
    subject.getPrincipals().remove(rolePrincipal);
    return true;
  }

}