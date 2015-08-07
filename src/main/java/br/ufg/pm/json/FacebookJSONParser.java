package br.ufg.pm.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import br.ufg.pm.model.Author;
import br.ufg.pm.model.Comment;
import br.ufg.pm.model.Post;

public class FacebookJSONParser {
	
	public Post JSONToPost(JSONObject json){
		Post post = new Post();
		post.setId(json.getString("id"));
		try{
			post.setMessage(json.getString("message"));
		}catch(Exception e){
			post.setMessage(null);
		}
		try{
			post.setStory(json.getString("story"));
		}catch(Exception e){
			post.setStory(null);
		}
		post.setCreatedTime(json.getString("created_time"));
		return post;
	}
	
	public Comment JSONToComment(JSONObject json){
		Author author = new Author();
		Comment comment = new Comment();
		author.setId(json.getJSONObject("from").getString("id"));
		author.setName(json.getJSONObject("from").getString("name"));
		comment.setAuthor(author);
		comment.setId(json.getString("id"));
		comment.setMessage(json.getString("message"));
		comment.setCreatedTime(json.getString("created_time"));
		return comment;
	}
	
	public List<Post> JSONToPostList(JSONArray json, Author author){
		List<Post> posts = new ArrayList<Post>(0);
		for (int i = 0; i < json.length(); i++){
			JSONObject jsonObj = (JSONObject) json.get(i);
			Post post = new Post();
			post = this.JSONToPost(jsonObj);
			post.setAuthor(author);
			posts.add(post);
		}
		return posts;
	}
	
	public List<Comment> JSONToCommentList(JSONArray json){
		List<Comment> comments = new ArrayList<Comment>(0);
		for (int i = 0; i < json.length(); i++){
			JSONObject jsonObj = (JSONObject) json.get(i);
			comments.add(this.JSONToComment(jsonObj));
		}
		return comments;
	}
	
	public Author JSONToAuthor(JSONObject json){
		Author author = new Author();
		author.setId(json.getString("id"));
		author.setName(json.getString("name"));
		return author;
	}
}
