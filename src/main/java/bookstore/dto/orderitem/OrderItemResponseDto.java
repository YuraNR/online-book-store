package bookstore.dto.orderitem;

public record OrderItemResponseDto(Long id,
                                   Long bookId,
                                   Integer quantity) {
}
