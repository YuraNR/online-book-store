package bookstore.service;

import bookstore.dto.BookDto;
import bookstore.dto.BookDtoWithoutCategoryIds;
import bookstore.dto.CreateBookRequestDto;
import bookstore.model.Book;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto updateById(Long id, CreateBookRequestDto requestDto);

    List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId);

    Book getBookById(Long bookId);
}
