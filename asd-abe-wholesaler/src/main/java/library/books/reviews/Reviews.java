package library.books.reviews;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestMapping;

import library.books.Book;

//@Embeddable
//@Access(AccessType.FIELD)
@Entity
@RequestMapping(path = "book/{isbn}/reviews")
@RestResource(exported = true)
public class Reviews {
	@Id
	//@EmbeddedId
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String reviewer;
	private String reviewText;
	private int grade;

	@ManyToOne
	private Book book;

	public String getReviewer() {
		return reviewer;
	}
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	public String getReviewText() {
		return reviewText;
	}
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
}
