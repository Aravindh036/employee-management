package com.realm;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.apache.catalina.realm.RealmBase;
import org.apache.catalina.realm.GenericPrincipal;
import com.duosecurity.duoweb.DuoWeb;

import com.dblayer.DatabaseActions;

public class CustomRealm extends RealmBase {
private String username;
private String password;
   DatabaseActions dbactions = new DatabaseActions();
	@Override
	public Principal authenticate(String username, String credentials) {
		DatabaseActions dbactions = new DatabaseActions();
		if(dbactions.checkCreds(username,credentials,"users")){
			this.username = username;
			this.password = credentials;
			return getPrincipal(username);
		}
		else{
			return null;
		}
	}
	protected String getName() {
		return username;
	}

	@Override
	protected String getPassword(String username) {
		return password;
	}

	@Override
	public Principal getPrincipal(String string) {
		List<String> roles = new ArrayList<String>();
		roles.add(dbactions.getColumnValue(username,"user_roles","user_name","role_name").toString()); 
		Principal principal = new GenericPrincipal(username, password,roles);
		return principal;
	}
}