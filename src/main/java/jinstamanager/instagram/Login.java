package jinstamanager.instagram;

import java.io.IOException;

import org.brunocvcunha.instagram4j.Instagram4j;

public class Login {
	
	private Instagram4j instagram = null;
	
	public Instagram4j login(String username, String password) {
		instagram = Instagram4j.builder().username(username).password(password).build();
		instagram.setup();
		try {
			instagram.login();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return instagram;
	}

	public Instagram4j getInstagramInstance() {
		return instagram;
	}

}
