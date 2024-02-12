package bookstore.repository;

import bookstore.model.OrderItem;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrderId(Long orderId, Pageable pageable);

    @Query(value = """
            SELECT oi FROM OrderItem oi
            JOIN FETCH oi.order o
            WHERE oi.id = :orderItemId AND o.id = :orderId AND o.user.id = :userId""")
    OrderItem findByIdAndOrderIdAndUserId(@Param("orderItemId") Long orderItemId,
                                          @Param("orderId") Long orderId,
                                          @Param("userId") Long userId);
}
