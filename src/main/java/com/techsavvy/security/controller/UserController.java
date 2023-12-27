package com.techsavvy.security.controller;

import com.techsavvy.security.annotations.ApiPermission;
import com.techsavvy.security.data.UserDTO;
import com.techsavvy.security.exception.UserNotFoundException;
import com.techsavvy.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("id/{id}")
    @ApiPermission("READ_USER")
    public UserDTO getById(@PathVariable Long id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @GetMapping("{name}")
    public UserDTO getUserByName(@PathVariable String username) throws UserNotFoundException {
        return userService.getUserByName(username);
    }

    @PostMapping
    public UserDTO create(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @PutMapping("{id}")
    public UserDTO update(@PathVariable Long id, @RequestBody UserDTO userDTO) throws UserNotFoundException {
        return userService.update(id, userDTO);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) throws UserNotFoundException {
        userService.delete(id);
    }
}
