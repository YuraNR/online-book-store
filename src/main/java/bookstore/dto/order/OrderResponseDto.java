package bookstore.dto.order;

import bookstore.dto.orderitem.OrderItemResponseDto;
import bookstore.model.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderResponseDto(Long userId,
                               Long orderId,
                               Order.Status status,
                               BigDecimal total,
                               LocalDateTime orderDate,
                               String shippingAddress,
                               Set<OrderItemResponseDto> orderItems) {
}
