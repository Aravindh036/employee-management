package employee;
import java.io.*;
import javax.security.auth.callback.*;


public class CallbackHandlerClass implements CallbackHandler{
	@Override
	public void handle(Callback[] callback) throws IOException, UnsupportedCallbackException{
		NameCallback name = null;
		PasswordCallback password = null;
		int counter = 0;
		while(counter < callback.length){
			System.out.println("dd : "+callback[counter]);
			if(callback[counter] instanceof NameCallback){
				name = (NameCallback)callback[counter++];
				System.out.println(name.getPrompt());
				name.setName((new BufferedReader(new InputStreamReader(System.in))).readLine());
			}
			else if(callback[counter] instanceof PasswordCallback) {
				password = (PasswordCallback)callback[counter++];
				System.out.println(password.getPrompt());
				password.setPassword((new BufferedReader(new InputStreamReader(System.in))).readLine().toCharArray());
			}
		}
	}
}