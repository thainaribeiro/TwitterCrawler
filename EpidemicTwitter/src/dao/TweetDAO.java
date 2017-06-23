package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;

import model.Tweet;



public class TweetDAO {

	
	public void salvarTweet(Tweet tweet) {
		
		try {
			Connection con = ConnectionFactory.getConexaoMySQL();
        // cria um preparedStatement
        String sql = "insert into Tweets" +
                " (codeTweet,username,place,tweet,keyword)" +
                " values (?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);

        // preenche os valores
        stmt.setString(1, tweet.getCodigoTweet());
        stmt.setString(2, tweet.getUserName());
        stmt.setString(3, tweet.getPlace());
        stmt.setString(4, tweet.getTweet());
        stmt.setString(5, tweet.getKeyword());

        // executa
        stmt.execute();
        stmt.close();
        System.out.println("Gravado!");
        con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
}
