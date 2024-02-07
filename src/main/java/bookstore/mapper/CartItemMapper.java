package bookstore.mapper;

import bookstore.dto.cartitem.CartItemResponseDto;
import bookstore.dto.cartitem.CreateCartItemDto;
import bookstore.dto.cartitem.UpdateCartItemDto;
import bookstore.model.Book;
import bookstore.model.CartItem;
import bookstore.service.BookService;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl",
        uses = BookService.class)
public interface CartItemMapper {

    @Autowired
    BookService bookService = null;

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemResponseDto toResponseDto(CartItem cartItem);

    @Mapping(source = "bookId", target = "book")
    CartItem toCartItem(CreateCartItemDto createCartItemDto);

    void updateCartItem(UpdateCartItemDto updateCartItemDto, @MappingTarget CartItem cartItem);

    @Named("mapLongToBook")
    default Book map(Long bookId) {
        return bookService.getBookById(bookId);
    }
}
