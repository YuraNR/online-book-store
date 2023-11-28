package bookstore.service.impl;

import bookstore.dto.user.UserRegistrationRequestDto;
import bookstore.dto.user.UserResponseDto;
import bookstore.exception.RegistrationException;
import bookstore.mapper.UserMapper;
import bookstore.model.Role;
import bookstore.model.User;
import bookstore.repository.RoleRepository;
import bookstore.repository.UserRepository;
import bookstore.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.email())) {
            throw new RegistrationException("User with such email already exists");
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        user.setRoles(Set.of(roleRepository.findRoleByName(Role.RoleName.ROLE_USER)));
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponseDto(savedUser);
    }
}
