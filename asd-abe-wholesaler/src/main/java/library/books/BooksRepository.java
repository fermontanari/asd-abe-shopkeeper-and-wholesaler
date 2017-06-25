package library.books;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "book", path = "book")
public interface BooksRepository extends JpaRepository<Book, Long>{

	List<Book> findByTitle(@Param("title") String title);
	
	List<Book> findByAuthors(@Param("author") String author);
	
	List<Book> findByKeyWords(@Param("keyWord") String keyWord);

}
