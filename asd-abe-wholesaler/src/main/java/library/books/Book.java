package library.books;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import org.springframework.data.rest.core.annotation.RestResource;

import library.books.reviews.Reviews;

@Entity
public class Book {
	
	public Book(long isbn) {
		super();
		this.isbn = isbn;
	}
	
	public Book() {
		super();
	}
	@Id
	@Column(name = "isbn")
	private long isbn;

	private String title;
	private String publishYear;
	private String edition;
	private String publisher;
	
	@Lob
	@Column( length = 100000 )
	private String description;

	@ElementCollection
    @CollectionTable(name="listOfAuthors")
	private List<String> authors = new ArrayList<String>();
	
	@ElementCollection
    @CollectionTable(name="listOfKeyWords")
	private List<String> keyWords = new ArrayList<String>();
	
	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)//(mappedBy = "reviews")
	@RestResource(exported = true)
	public List<Reviews> reviews =  new ArrayList<Reviews>();
	
	@Lob
	@Column( length = 100000 )
	private String bookResume;
	
	@ElementCollection
    @CollectionTable(name="listOfRelatedBooks")
	private List<Long> relatedBooks; 
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public List<String> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(List<String> keyWords) {
		this.keyWords = keyWords;
	}

	public List<Reviews> getReviews() {
		return reviews;
	}
	public void setReviews(List<Reviews> reviews) {
		this.reviews = reviews;
	}

	public String getPublishYear() {
		return publishYear;
	}

	public void setPublishYear(String publishYear) {
		this.publishYear = publishYear;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getBookResume() {
		return bookResume;
	}

	public void setBookResume(String bookResume) {
		this.bookResume = bookResume;
	}

	public List<Long> getRelatedBooks() {
		return relatedBooks;
	}

	public void setRelatedBooks(List<Long> relatedBooks) {
		this.relatedBooks = relatedBooks;
	}

	public String getTitle() {
		return title;
	}

	public long getISBN() {
		return isbn;
	}

	public void setISBN(long isbn) {
		this.isbn = isbn;
	}
	public void setTile(String title) {
		this.title = title;
	}

}
