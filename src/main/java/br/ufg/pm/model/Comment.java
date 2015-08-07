package br.ufg.pm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import br.ufg.pm.util.PageMiningUtil;

@Entity
public class Comment implements Serializable {
	
	private static final long serialVersionUID = 970960307832782366L;

	@Id
	private String id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "author_id")
	private Author author;
	
	@Lob
	private String message;
	
	private String createdTime;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name="comment_answers", joinColumns={@JoinColumn(name="comment_id")}, inverseJoinColumns={@JoinColumn(name="answer_id")})
	private List<Comment> answers;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = PageMiningUtil.removeInvalidCharacters(message);
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public List<Comment> getAnswers() {
		if(this.answers == null)
			this.answers = new ArrayList<Comment>(0);
		return answers;
	}

	public void setAnswers(List<Comment> answers) {
		this.answers = answers;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
