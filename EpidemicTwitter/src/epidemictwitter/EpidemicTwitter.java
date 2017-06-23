package epidemictwitter;

import model.Tweet;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import util.Criptografia;
import util.FileManager;
import business.TweetPersist;

public class EpidemicTwitter {

    public static void main(String[] args) {
    	final String palavrasChaves[] = new FileManager().getKeywords("/home/thaina/TCC/keywords.txt");
//    	final String palavrasChaves[] = new FileManager().getKeywords(args[0]);
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("xxxxx");
        cb.setOAuthConsumerSecret("xxxx");
        cb.setOAuthAccessToken("xxxx");
        cb.setOAuthAccessTokenSecret("xxxx");

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        StatusListener listener = new StatusListener() {

            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStatus(Status status) {
                String id;
                String username;
                String place = null;
                String latitude = null;
                String longitude = null;
                String conteudo;

                User user = status.getUser();

                //twitter id
                long tweetId = status.getId();
                id = String.valueOf(tweetId);

                //Username
                username = status.getUser().getScreenName();

                if (user.getLocation() != null) {
                    //location_Place
                    place = user.getLocation();
                }

                if (status.getGeoLocation() != null) {
                    //GEO_location
                    latitude = String.valueOf(status.getGeoLocation().getLatitude());
                    longitude = String.valueOf(status.getGeoLocation().getLongitude());
                }

                //conteudo
                String content = status.getText();
                conteudo = content;
                
                
                for (int i = 0; i < palavrasChaves.length; i++) {
					if(conteudo.toUpperCase().contains(palavrasChaves[i].toUpperCase())) {
						Tweet tweet = new Tweet();
		                tweet.setCodigoTweet(id);
		                tweet.setPlace(place);
		                tweet.setUserName(new Criptografia().criptografar(username));
		                tweet.setTweet(conteudo);
		                tweet.setKeyword(palavrasChaves[i]);
		                new TweetPersist().PersistTweet(tweet);
					}
				}                
                
                
                System.out.println(id+"|;|"+username+"|;|"+place+"|;|"+latitude+"|;|"+longitude+"|;|"+conteudo);

            }

            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStallWarning(StallWarning sw) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        };
        FilterQuery fq = new FilterQuery();

        String keywords[] = palavrasChaves;

        fq.track(keywords);
        fq.language("pt");
        twitterStream.addListener(listener);
        twitterStream.filter(fq);
    }
}
