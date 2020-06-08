package jinstamanager.instagram.requests;

import java.io.IOException;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;

public class GetProfile {
	
	public InstagramUser getConnectedProfile(Instagram4j instagram) {
		return getProfile(instagram, instagram.getUsername());	
	}
	
	public InstagramUser getProfile(Instagram4j instagram, String username) {
		InstagramSearchUsernameResult userResult = null;
		try {
			userResult = instagram.sendRequest(new InstagramSearchUsernameRequest(username));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return userResult.getUser();
	}

}
