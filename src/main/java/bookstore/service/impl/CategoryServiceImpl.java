package bookstore.service.impl;

import bookstore.dto.BookDtoWithoutCategoryIds;
import bookstore.dto.CategoryDto;
import bookstore.dto.CreateCategoryRequestDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.BookMapper;
import bookstore.mapper.CategoryMapper;
import bookstore.model.Category;
import bookstore.repository.CategoryRepository;
import bookstore.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final BookMapper bookMapper;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId) {
        return categoryRepository.findAllByCategoryId(categoryId)
                .stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    @Override
    public CategoryDto save(CreateCategoryRequestDto requestDto) {
        Category category = categoryMapper.toCategory(requestDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategory(Long id, CreateCategoryRequestDto requestDto) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("There is no category with the id: " + id)
        );
        categoryMapper.updateCategory(requestDto, category);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("There is no category by id: " + id)
        );
        categoryRepository.deleteById(id);
    }
}
