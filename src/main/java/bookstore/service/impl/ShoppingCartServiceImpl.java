package bookstore.service.impl;

import bookstore.dto.cartitem.CartItemResponseDto;
import bookstore.dto.cartitem.CreateCartItemDto;
import bookstore.dto.cartitem.UpdateCartItemDto;
import bookstore.dto.shoppingcart.ShoppingCartResponseDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.CartItemMapper;
import bookstore.mapper.ShoppingCartMapper;
import bookstore.model.Book;
import bookstore.model.CartItem;
import bookstore.model.ShoppingCart;
import bookstore.model.User;
import bookstore.repository.BookRepository;
import bookstore.repository.CartItemRepository;
import bookstore.repository.ShoppingCartRepository;
import bookstore.repository.UserRepository;
import bookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final BookRepository bookRepository;

    @Transactional
    @Override
    public ShoppingCartResponseDto save(CreateCartItemDto createCartItemDto, User user) {
        User existingUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("User with email %s isn't found"
                        .formatted(user.getEmail()))
        );
        Book book = bookRepository.findById(createCartItemDto.bookId()).orElseThrow(
                () -> new EntityNotFoundException("Book with id %d isn't found"
                        .formatted(createCartItemDto.bookId()))
        );
        ShoppingCart shoppingCart = existingUser.getShoppingCart();
        shoppingCart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(createCartItemDto.bookId()))
                .findFirst()
                .ifPresentOrElse(item -> item.setQuantity(item.getQuantity()
                                + createCartItemDto.quantity()),
                        () -> addCartItemToCart(createCartItemDto, book, shoppingCart));
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto findByUserId(Long id) {
        ShoppingCart shoppingCart = getCartByUserId(id);
        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Transactional
    @Override
    public void deleteCartItemFromShoppingCart(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart = getCartByUserId(userId);
        CartItem cartItem = getCartItemById(cartItemId);
        shoppingCart.removeCartItem(cartItem);
    }

    @Transactional
    @Override
    public CartItemResponseDto updateCartItem(Long userId,
                                              Long cartItemId,
                                              UpdateCartItemDto cartItemDto) {
        ShoppingCart shoppingCart = getCartByUserId(userId);
        CartItem cartItem = getCartItemById(cartItemId);
        if (!cartItem.getShoppingCart().getId().equals(shoppingCart.getId())) {
            throw new EntityNotFoundException(
                    "User with id %d doesn't have cart item with id %d"
                            .formatted(userId, cartItemId));
        }
        cartItemMapper.updateCartItem(cartItemDto, cartItem);
        return cartItemMapper.toResponseDto(cartItem);
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

    private void addCartItemToCart(CreateCartItemDto itemDto, Book book, ShoppingCart cart) {
        CartItem cartItem = cartItemMapper.toCartItem(itemDto);
        cartItem.setBook(book);
        cart.addCartItem(cartItem);
    }
}
