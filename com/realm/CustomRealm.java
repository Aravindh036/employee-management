package com.realm;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.apache.catalina.realm.RealmBase;
import org.apache.catalina.realm.GenericPrincipal;

import com.dblayer.DatabaseActions;

public class CustomRealm extends RealmBase {
 
private String username;
private String password;
   DatabaseActions dbactions = new DatabaseActions();
	@Override
	public Principal authenticate(String username, String credentials) {
		System.out.println("Authentication is taking place with userid: "+username);
		DatabaseActions dbactions = new DatabaseActions();
		System.out.println("out of databse cred check");
		if(dbactions.checkCreds(username,credentials,"users")){
			System.out.println("in databse cred check");
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
	protected Principal getPrincipal(String string) {
		List<String> roles = new ArrayList<String>();
		System.out.println("welcome role : "+dbactions.getColumnValue(username,"user_roles","user_name","role_name"));
		roles.add(dbactions.getColumnValue(username,"user_roles","user_name","role_name").toString()); 
		System.out.println("Realm: "+this);
		Principal principal = new GenericPrincipal(username, password,roles);
		System.out.println("Principal: "+principal);
		return principal;
	}
}