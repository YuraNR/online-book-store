package bookstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record OrderStatusUpdateDto(@NotBlank @Length(max = 50) String newStatus) {
}
