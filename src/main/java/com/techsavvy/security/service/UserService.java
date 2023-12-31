package com.techsavvy.security.service;

import com.techsavvy.security.data.UserDTO;
import com.techsavvy.security.domain.User;
import com.techsavvy.security.exception.UserNotFoundException;
import com.techsavvy.security.prototype.UserPrototype;
import com.techsavvy.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserPrototype {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDTO getUserById(Long id) throws UserNotFoundException {
        com.techsavvy.security.domain.User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(""));
        return UserPrototype.toDTO(user);
    }

    @Override
    public UserDTO getUserByName(String name) throws UserNotFoundException {
        com.techsavvy.security.domain.User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new UserNotFoundException(""));
        return UserPrototype.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserPrototype::toDTO)
                .toList();
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        User user = UserPrototype.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user = userRepository.save(user);
        return UserPrototype.toDTO(user);
    }

    @Override
    public UserDTO update(Long id, UserDTO userDTO) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(""));
        UserPrototype.copy(userDTO,user);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return UserPrototype.toDTO(userRepository.save(user));
    }

    @Override
    public void delete(Long id) throws UserNotFoundException {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(""));
        userRepository.deleteById(id);
    }
}
