package br.ufg.pm.writer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import br.ufg.pm.dao.PostDAO;
import br.ufg.pm.model.Comment;
import br.ufg.pm.model.Post;
import br.ufg.pm.util.DaoFactory;

public class FacebookObjectWriter {
	private PostDAO postDao;
	private PrintWriter printWriter;
	
	public FacebookObjectWriter() throws FileNotFoundException{
		this.postDao = DaoFactory.postDaoInstance();
		this.printWriter = new PrintWriter("C:\\Users\\Guthierrez\\Desktop\\teste.txt");
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		FacebookObjectWriter writer = new FacebookObjectWriter();
		List<Post> posts = writer.postDao.getLista();
		for (Post post : posts) {
			//writer.printWriter.println("{"+post.getAuthor().getName()+"} " + post.getMessage());
			for(Comment comment : post.getComments()){
				if(comment.getAnswers().size() == 0 && comment.getMessage().indexOf("?") != -1 && comment.getMessage().indexOf("http") == -1){
					writer.printWriter.println("{"+post.getAuthor().getName()+"} - ["+comment.getAuthor().getName()+"] " + comment.getMessage());
					//for(Comment answer : comment.getAnswers()){
					//	writer.printWriter.println("      ("+answer.getAuthor().getName()+") " + answer.getMessage());
					//}
					writer.printWriter.println();
				}
			}
			//writer.printWriter.println();
		}
		System.out.println("FIM");
	}
}
