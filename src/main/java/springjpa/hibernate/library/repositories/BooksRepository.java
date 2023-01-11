package springjpa.hibernate.library.repositories;
import springjpa.hibernate.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByNameStartingWith(String startingWith);
}
