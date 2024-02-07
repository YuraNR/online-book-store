package bookstore.service.impl;

import bookstore.dto.cartitem.CartItemResponseDto;
import bookstore.dto.cartitem.CreateCartItemDto;
import bookstore.dto.cartitem.UpdateCartItemDto;
import bookstore.dto.shoppingcart.ShoppingCartResponseDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.CartItemMapper;
import bookstore.mapper.ShoppingCartMapper;
import bookstore.model.CartItem;
import bookstore.model.ShoppingCart;
import bookstore.model.User;
import bookstore.repository.CartItemRepository;
import bookstore.repository.ShoppingCartRepository;
import bookstore.repository.UserRepository;
import bookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCartResponseDto save(CreateCartItemDto createCartItemDto, User user) {
        User existingUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("User with email %s isn't found"
                        .formatted(user.getEmail()))
        );
        ShoppingCart shoppingCart = existingUser.getShoppingCart();
        CartItem cartItem = cartItemMapper.toCartItem(createCartItemDto);
        shoppingCart.addCartItem(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto findByUserId(Long id) {
        ShoppingCart shoppingCart = getCartByUserId(id);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public void deleteCartItemFromShoppingCart(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart = getCartByUserId(userId);
        CartItem cartItem = getCartItemById(cartItemId);
        shoppingCart.removeCartItem(cartItem);
    }

    @Override
    public CartItemResponseDto updateCartItem(Long userId,
                                              Long cartItemId,
                                              UpdateCartItemDto cartItemDto) {
        ShoppingCart shoppingCart = getCartByUserId(userId);
        CartItem cartItem = getCartItemById(cartItemId);
        if (cartItem.getShoppingCart().getId().equals(shoppingCart.getId())) {
            cartItemMapper.updateCartItem(cartItemDto, cartItem);
            return cartItemMapper.toResponseDto(cartItem);
        } else {
            throw new EntityNotFoundException(
                    "User with id %d doesn't have cart item with id %d"
                            .formatted(userId, cartItemId));
        }
    }

    private ShoppingCart getCartByUserId(Long id) {
        return shoppingCartRepository.findShoppingCartByUserId(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("There is no user with the id: " + id));
    }

    private CartItem getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new EntityNotFoundException(
                        "There is no cart item with the id: " + cartItemId)
        );
    }
}
