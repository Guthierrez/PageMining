package br.ufg.pm.main;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import br.ufg.pm.dao.AuthorDAO;
import br.ufg.pm.dao.CommentDAO;
import br.ufg.pm.dao.PostDAO;
import br.ufg.pm.json.FacebookObjectReader;
import br.ufg.pm.model.Author;
import br.ufg.pm.model.Comment;
import br.ufg.pm.model.Post;
import br.ufg.pm.util.DaoFactory;

public class Main {
	
	public static FacebookObjectReader reader = new FacebookObjectReader();
	public static AuthorDAO authorDAO = DaoFactory.authorDaoInstance();
	public static PostDAO postDAO = DaoFactory.postDaoInstance();
	public static CommentDAO commentDAO = DaoFactory.commentDaoInstance();
	
	public static void main(String[] args) throws IOException, JSONException {
		Author page = reader.readAuthor("magazineluiza");
		List<Post> posts = reader.readPosts(page, 2);
		Integer numPosts = 1;
		for (Post post : posts) {
			post.setComments(reader.readComments(post.getId()));
			for (Comment comment : post.getComments()) {
				comment.setAnswers(reader.readComments(comment.getId()));
				for(Comment answer : comment.getAnswers()){
					authorDAO.update(answer.getAuthor());
					commentDAO.update(answer);
				}
				authorDAO.update(comment.getAuthor());
				commentDAO.update(comment);
			}
			authorDAO.update(post.getAuthor());
			postDAO.update(post);
			System.out.println( "Post " + numPosts + " salvo!");
			numPosts++;
		}
		System.out.println("FIM!");
	}
}
