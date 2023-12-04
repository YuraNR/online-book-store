package bookstore.service;

import bookstore.dto.BookDtoWithoutCategoryIds;
import bookstore.dto.CategoryDto;
import bookstore.dto.CreateCategoryRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId);

    CategoryDto save(CreateCategoryRequestDto requestDto);

    CategoryDto updateCategory(Long id, CreateCategoryRequestDto requestDto);

    void deleteCategoryById(Long id);
}
