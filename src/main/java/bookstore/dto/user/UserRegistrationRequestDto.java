package bookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequestDto(
        @NotBlank
        @Email
        @Size(max = 225)
        String email,
        @NotBlank
        @Size(min = 7, max = 20)
        String password,
        @NotBlank
        @Size(min = 7, max = 20)
        String repeatPassword,
        @NotBlank
        @Size(max = 255)
        String firstName,
        @NotBlank
        @Size(max = 255)
        String lastName,
        @Size(max = 255)
        String shippingAddress) {
}
