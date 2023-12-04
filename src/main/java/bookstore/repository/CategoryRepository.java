package bookstore.repository;

import bookstore.model.Book;
import bookstore.model.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "from Book b join fetch b.categories c where c.id= :categoryId")
    List<Book> findAllByCategoryId(@Param("categoryId") Long categoryId);
}
