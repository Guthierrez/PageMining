package br.ufg.pm.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.ufg.pm.model.Author;
import br.ufg.pm.model.Comment;
import br.ufg.pm.model.Post;

public class FacebookObjectReader {
	
	private FacebookJSONParser parser;
	private final String ACCESS_TOKEN;
	
	public FacebookObjectReader(){
		this.parser = new FacebookJSONParser();
		this.ACCESS_TOKEN = "932493860141525|AKx5l0PdR7zSePx-8sjOGTAqFyE";
	}
	
	public Author readAuthor(String name) throws JSONException, IOException{
		JSONObject json = this.readJSONFromUrl("https://graph.facebook.com/v2.3/"+ name +"?access_token=" + ACCESS_TOKEN);
		return this.parser.JSONToAuthor(json);
	}
	
	public List<Post> readPosts(Author author, Integer numPages) throws JSONException, IOException{
		List<Post> posts = new ArrayList<Post>(0);
		numPages = numPages < 1 || numPages == null ? 1 : numPages;
		JSONObject postPage = this.readJSONFromUrl("https://graph.facebook.com/v2.3/"+ author.getId() +"/posts?access_token=" + ACCESS_TOKEN + "&limit=100");
		JSONArray postsJSON = postPage.getJSONArray("data");
		posts.addAll(parser.JSONToPostList(postsJSON, author));
		for(int i = 0;  i < numPages-1; i++){
			try{
				String next = postPage.getJSONObject("paging").getString("next");
				postPage = this.readJSONFromUrl(next);
				postsJSON = postPage.getJSONArray("data");
				if(postsJSON.length() == 0)
					break;
				else
					posts.addAll(parser.JSONToPostList(postsJSON, author));
			}catch(Exception e){
				break;
			}
		}
		return posts;
	}
	
	public List<Comment> readComments(String postID) throws JSONException, IOException{
		List<Comment> comments = new ArrayList<Comment>(0);
		JSONObject postComments = this.readJSONFromUrl("https://graph.facebook.com/v2.3/"+ postID +"/comments?access_token=" + ACCESS_TOKEN + "&limit=100");
		JSONArray commentsJSON = postComments.getJSONArray("data");
		comments.addAll(parser.JSONToCommentList(commentsJSON));
		while(true){
			try{
				postComments = this.readJSONFromUrl(postComments.getJSONObject("paging").getString("next"));
				commentsJSON = postComments.getJSONArray("data");
				comments.addAll(parser.JSONToCommentList(commentsJSON));
			}catch(Exception e){
				break;
			}
		}
		return comments;
	}
	
	public JSONObject readJSONFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}
	
	private String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}	
}
