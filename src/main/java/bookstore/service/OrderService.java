package bookstore.service;

import bookstore.dto.order.OrderRequestDto;
import bookstore.dto.order.OrderResponseDto;
import bookstore.dto.orderitem.OrderItemResponseDto;
import bookstore.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto createOrder(User user, OrderRequestDto orderRequestDto);

    List<OrderResponseDto> findAllOrders(Long userId, Pageable pageable);

    List<OrderItemResponseDto> findAllByOrderId(Long orderId, Pageable pageable, Long userId);

    OrderItemResponseDto findOrderItemByIdInOrder(Long orderId, Long id, Long userId);

    OrderResponseDto updateOrderStatus(Long orderId, String newStatus);
}
