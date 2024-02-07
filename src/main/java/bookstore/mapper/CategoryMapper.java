package bookstore.mapper;

import bookstore.dto.CategoryDto;
import bookstore.dto.CreateCategoryRequestDto;
import bookstore.model.Category;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface CategoryMapper {
    Category toCategory(CreateCategoryRequestDto requestDto);

    CategoryDto toDto(Category category);

    void updateCategory(CreateCategoryRequestDto dto, @MappingTarget Category category);
}
