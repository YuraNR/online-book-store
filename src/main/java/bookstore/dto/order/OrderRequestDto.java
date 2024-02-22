package bookstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record OrderRequestDto(@NotBlank @Length(max = 255) String shippingAddress) {
}
