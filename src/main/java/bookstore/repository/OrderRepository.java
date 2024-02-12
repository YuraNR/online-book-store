package bookstore.repository;

import bookstore.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"user", "orderItems"})
    List<Order> findAllOrdersByUserId(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"user", "orderItems"})
    Optional<Order> findById(Long id);
}
