package br.ufg.pm.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.ufg.pm.dao.AuthorDAO;
import br.ufg.pm.dao.CommentDAO;
import br.ufg.pm.dao.PostDAO;

public final class DaoFactory {

	private static final String PERSISTENCE_UNIT_NAME = "pm-mysql";
	private static EntityManagerFactory entityManagerFactoryInstance;
	
	private static AuthorDAO authorDAO;
	private static PostDAO postDAO;
	private static CommentDAO commentDAO;
	
	public static EntityManagerFactory entityManagerFactoryInstance() {
		if (entityManagerFactoryInstance == null)
			entityManagerFactoryInstance = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

		return entityManagerFactoryInstance;
	}
	
	public static AuthorDAO authorDaoInstance() {
		if (authorDAO == null)
			authorDAO = new AuthorDAO();
		return authorDAO;
	}
	
	public static PostDAO postDaoInstance() {
		if (postDAO == null)
			postDAO = new PostDAO();
		return postDAO;
	}
	
	public static CommentDAO commentDaoInstance() {
		if (commentDAO == null)
			commentDAO = new CommentDAO();
		return commentDAO;
	}
}