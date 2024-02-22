package bookstore.repository;

import bookstore.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b JOIN FETCH b.categories c WHERE c.id = :id")
    List<Book> findAllByCategoryId(Long id);

    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.categories")
    List<Book> findAllWithCategories(Pageable pageable);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.categories WHERE b.id = :id")
    Optional<Book> findByIdWithCategories(@Param("id") Long id);
}
