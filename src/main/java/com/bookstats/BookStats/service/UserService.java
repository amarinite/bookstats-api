package com.bookstats.BookStats.service;

import com.bookstats.BookStats.dto.request.UserRequestDTO;
import com.bookstats.BookStats.dto.response.UserResponseDTO;
import com.bookstats.BookStats.entity.User;
import com.bookstats.BookStats.exception.DuplicateEmailException;
import com.bookstats.BookStats.exception.DuplicateUsernameException;
import com.bookstats.BookStats.exception.UserNotFoundException;
import com.bookstats.BookStats.mapper.UserMapper;
import com.bookstats.BookStats.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        // Check for duplicate username
        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new DuplicateUsernameException(userRequestDTO.getUsername());
        }

        // Check for duplicate email
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new DuplicateEmailException(userRequestDTO.getEmail());
        }

        // Create new user
        User user = userMapper.toEntity(userRequestDTO);
        user.setPasswordHash(passwordEncoder.encode(userRequestDTO.getPassword()));

        User savedUser = userRepository.save(user);
        log.info("Created new user: {}", savedUser.getUsername());

        return userMapper.toResponseDTO(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.byId(id));
        return userMapper.toResponseDTO(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> UserNotFoundException.byUsername(username));
        return userMapper.toResponseDTO(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> UserNotFoundException.byEmail(email));
        return userMapper.toResponseDTO(user);
    }

    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponseDTO);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.byId(id));

        // Check username change
        if (!existingUser.getUsername().equals(userRequestDTO.getUsername())) {
            if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
                throw new DuplicateUsernameException(userRequestDTO.getUsername());
            }
            existingUser.setUsername(userRequestDTO.getUsername());
        }

        // Check email change
        if (!existingUser.getEmail().equals(userRequestDTO.getEmail())) {
            if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
                throw new DuplicateEmailException(userRequestDTO.getEmail());
            }
            existingUser.setEmail(userRequestDTO.getEmail());
        }

        // Update password if provided
        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().trim().isEmpty()) {
            existingUser.setPasswordHash(passwordEncoder.encode(userRequestDTO.getPassword()));
        }

        User updatedUser = userRepository.save(existingUser);
        log.info("Updated user: {}", updatedUser.getUsername());

        return userMapper.toResponseDTO(updatedUser);
    }

    public UserResponseDTO updateUserPartial(Long id, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.byId(id));

        boolean updated = false;

        // Update username if provided and different
        if (userRequestDTO.getUsername() != null &&
                !userRequestDTO.getUsername().equals(existingUser.getUsername())) {
            if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
                throw new DuplicateUsernameException(userRequestDTO.getUsername());
            }
            existingUser.setUsername(userRequestDTO.getUsername());
            updated = true;
        }

        // Update email if provided and different
        if (userRequestDTO.getEmail() != null &&
                !userRequestDTO.getEmail().equals(existingUser.getEmail())) {
            if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
                throw new DuplicateEmailException(userRequestDTO.getEmail());
            }
            existingUser.setEmail(userRequestDTO.getEmail());
            updated = true;
        }

        // Update password if provided
        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().trim().isEmpty()) {
            existingUser.setPasswordHash(passwordEncoder.encode(userRequestDTO.getPassword()));
            updated = true;
        }

        if (updated) {
            User updatedUser = userRepository.save(existingUser);
            log.info("Partially updated user: {}", updatedUser.getUsername());
            return userMapper.toResponseDTO(updatedUser);
        }

        return userMapper.toResponseDTO(existingUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw UserNotFoundException.byId(id);
        }
        userRepository.deleteById(id);
        log.info("Deleted user with id: {}", id);
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public long getTotalUserCount() {
        return userRepository.count();
    }

    // Method for authentication/security purposes
    @Transactional(readOnly = true)
    public User findUserEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> UserNotFoundException.byUsername(username));
    }
}
