package bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateBookRequestDto(Long id,
                                   @NotNull
                                   String title,
                                   @NotNull
                                   String author,
                                   @NotNull
                                   String isbn,
                                   @NotNull
                                   @Min(1)
                                   BigDecimal price,
                                   String description,
                                   String coverImage) {
}
