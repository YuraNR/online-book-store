package bookstore.service;

import bookstore.dto.CategoryDto;
import bookstore.dto.CreateCategoryRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getCategoryById(Long id);

    CategoryDto save(CreateCategoryRequestDto requestDto);

    CategoryDto updateCategoryById(Long id, CreateCategoryRequestDto requestDto);

    void deleteCategoryById(Long id);
}
