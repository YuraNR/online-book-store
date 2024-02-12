package bookstore.dto.shoppingcart;

import bookstore.dto.cartitem.CartItemResponseDto;
import java.util.Set;

public record ShoppingCartResponseDto(Long id,
                                      Long userId,
                                      Set<CartItemResponseDto> cartItems) {
}
