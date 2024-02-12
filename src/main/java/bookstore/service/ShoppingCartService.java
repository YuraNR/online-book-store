package bookstore.service;

import bookstore.dto.cartitem.CartItemResponseDto;
import bookstore.dto.cartitem.CreateCartItemDto;
import bookstore.dto.cartitem.UpdateCartItemDto;
import bookstore.dto.shoppingcart.ShoppingCartResponseDto;
import bookstore.model.User;

public interface ShoppingCartService {
    ShoppingCartResponseDto save(CreateCartItemDto createCartItemDto, User user);

    ShoppingCartResponseDto findByUserId(Long id);

    void deleteCartItemFromShoppingCart(Long userId, Long cartItemId);

    CartItemResponseDto updateCartItem(Long userId, Long cartItemId, UpdateCartItemDto cartItemDto);
}
