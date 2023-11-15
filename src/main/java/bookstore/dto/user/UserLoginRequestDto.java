package bookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @NotBlank
        @Email
        @Size(max = 255)
        String email,
        @NotBlank
        @Size(min = 7, max = 20)
        String password) {
}
