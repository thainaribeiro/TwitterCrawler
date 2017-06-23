package business;

import dao.TweetDAO;
import model.Tweet;

public class TweetPersist {
	
	
	public void PersistTweet(Tweet tweet) {
		TweetDAO dao = new TweetDAO();
		dao.salvarTweet(tweet);
	}

}
