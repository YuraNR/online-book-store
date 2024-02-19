package bookstore.controller;

import bookstore.dto.order.OrderRequestDto;
import bookstore.dto.order.OrderResponseDto;
import bookstore.dto.order.OrderStatusUpdateDto;
import bookstore.dto.orderitem.OrderItemResponseDto;
import bookstore.model.User;
import bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Validated
@Tag(name = "Order Management",
        description = "Endpoints to create, update and get orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create order", description = "User can create order")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    public OrderResponseDto createOrder(Authentication authentication,
                                        @RequestBody OrderRequestDto orderRequestDto) {
        User user = (User) authentication.getPrincipal();
        return orderService.createOrder(user, orderRequestDto);
    }

    @GetMapping
    @Operation(summary = "get All orders",
            description = "User can get all orders")
    @PreAuthorize("hasRole('USER')")
    public List<OrderResponseDto> getAllOrders(Authentication authentication,
                                               Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.findAllOrders(user.getId(), pageable);
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasRole('User')")
    @Operation(summary = "Get orderItem from Order",
            description = "User can get all order items")
    public List<OrderItemResponseDto> getAllOrdersItemsFromOrder(
            @PathVariable @Positive Long orderId,
            @PageableDefault(size = 5)
            Pageable pageable,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.findAllByOrderId(orderId, pageable, user.getId());
    }

    @GetMapping("/{orderId}/items/id")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get order item from order",
            description = "User can get order item")
    public OrderItemResponseDto getOrderItemFromOrder(@PathVariable @Positive Long orderId,
                                                      @PathVariable @Positive Long id,
                                                      Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.findOrderItemByIdInOrder(orderId, id, user.getId());
    }

    @PutMapping("/orderId")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update order status",
            description = "Admin change status")
    public OrderResponseDto updateStatus(@PathVariable @Positive Long orderId,
                                         @RequestBody OrderStatusUpdateDto updateDto) {
        return orderService.updateOrderStatus(orderId, updateDto.newStatus());
    }
}
