package jinstamanager.instagram.requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowingRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;

public class GetFollowing {
	
	public List<InstagramUserSummary> getFollowingList(Instagram4j instagram, InstagramUser user) {
		String nextMaxId = null;
		List<InstagramUserSummary> users = new ArrayList<InstagramUserSummary>();
		
        while (true) {
            InstagramGetUserFollowersResult followersResult = null;
			try {
				followersResult = instagram.sendRequest(new InstagramGetUserFollowingRequest(user.getPk(), nextMaxId));
			} catch (IOException e) {
				e.printStackTrace();
			}                
            users.addAll(followersResult.getUsers());
            nextMaxId = followersResult.getNext_max_id();          
            
            if (nextMaxId == null) {
                break;
            }
        }
		
		return users;
	}

}
