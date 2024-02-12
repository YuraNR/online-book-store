package bookstore.service.impl;

import bookstore.dto.order.OrderRequestDto;
import bookstore.dto.order.OrderResponseDto;
import bookstore.dto.orderitem.OrderItemResponseDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.exception.OrderException;
import bookstore.mapper.OrderItemMapper;
import bookstore.mapper.OrderMapper;
import bookstore.model.CartItem;
import bookstore.model.Order;
import bookstore.model.OrderItem;
import bookstore.model.ShoppingCart;
import bookstore.model.User;
import bookstore.repository.OrderItemRepository;
import bookstore.repository.OrderRepository;
import bookstore.repository.ShoppingCartRepository;
import bookstore.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public OrderResponseDto createOrder(User user, OrderRequestDto orderRequestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("There is no user with the id: "
                        + user.getId()));
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new OrderException("There is no order");
        }
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.Status.CREATED);
        order.setShippingAddress(orderRequestDto.shippingAddress());
        order.setOrderItems(createOrderItems(cartItems, order));
        order.setTotal(countTotalSum(order.getOrderItems()));
        shoppingCart.getCartItems().clear();
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderResponseDto> findAllOrders(Long userId, Pageable pageable) {
        return orderRepository.findAllOrdersByUserId(userId, pageable).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderItemResponseDto> findAllByOrderId(Long orderId,
                                                       Pageable pageable,
                                                       Long userId) {
        Order order = checkIfOrderExistsById(orderId);
        checkIfOrderBelongsToUser(order.getUser().getId(), userId, orderId);
        return orderItemRepository.findAllByOrderId(orderId, pageable)
                .stream()
                .map(orderItemMapper:: toDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto findOrderItemByIdInOrder(Long orderId,
                                                         Long orderItemId, Long userId) {
        OrderItem byIdAndOrderIdAndUserId = orderItemRepository
                .findByIdAndOrderIdAndUserId(orderItemId, orderId, userId);
        return orderItemMapper.toDto(byIdAndOrderIdAndUserId);
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrderStatus(Long orderId, String newStatus) {
        Order order = checkIfOrderExistsById(orderId);
        order.setStatus(Order.Status.valueOf(newStatus));
        return orderMapper.toDto(order);
    }

    private BigDecimal countTotalSum(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Set<OrderItem> createOrderItems(Set<CartItem> cartItems, Order order) {
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getBook().getPrice());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
            cartItem.setShoppingCart(null);
        }
        return orderItems;
    }

    private void checkIfOrderBelongsToUser(Long userIdFromRequest,
                                           Long actualUserId,
                                           Long orderId) {
        if (!userIdFromRequest.equals(actualUserId)) {
            throw new EntityNotFoundException("There is no order with id %d "
                    .formatted(orderId));
        }
    }

    private Order checkIfOrderExistsById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("There is now order with id: "));
    }
}
