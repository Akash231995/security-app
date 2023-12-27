package com.techsavvy.security.prototype;

import com.techsavvy.security.data.PermissionDTO;
import com.techsavvy.security.data.RoleDTO;
import com.techsavvy.security.data.UserDTO;
import com.techsavvy.security.domain.Permission;
import com.techsavvy.security.domain.Role;
import com.techsavvy.security.domain.User;
import com.techsavvy.security.exception.UserNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface UserPrototype {
    UserDTO getUserById(Long id) throws UserNotFoundException;
    UserDTO getUserByName(String name) throws UserNotFoundException;
    List<UserDTO> getAllUsers();
    UserDTO create(UserDTO userDTO);
    UserDTO update(Long id, UserDTO userDTO) throws UserNotFoundException;
    void delete(Long id) throws UserNotFoundException;

    static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        // Converting
        Set<RoleDTO> roles = user.getRoles().stream().map(role -> {
            RoleDTO roleDTO = new RoleDTO();
            BeanUtils.copyProperties(role,roleDTO);
            Set<PermissionDTO> permissions = role.getPermissions().stream().map(permission -> {
                PermissionDTO permissionDTO = new PermissionDTO();
                BeanUtils.copyProperties(permission,permissionDTO);
                return permissionDTO;
            }).collect(Collectors.toCollection(LinkedHashSet::new));
            roleDTO.setPermissions(permissions);
            return roleDTO;
        }).collect(Collectors.toCollection(LinkedHashSet::new));
        userDTO.setRoles(roles);
        return userDTO;
    }


    static com.techsavvy.security.domain.User toEntity(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        Set<Role> roles = userDTO.getRoles().stream().map(roleDTO -> {
            Role role = new Role();
            BeanUtils.copyProperties(roleDTO,role);
            Set<Permission> permissions = roleDTO.getPermissions().stream().map(permissionDTO -> {
                Permission permission = new Permission();
                BeanUtils.copyProperties(permissionDTO,permission);
                return permission;
            }).collect(Collectors.toCollection(LinkedHashSet::new));
            role.setPermissions(permissions);
            return role;
        }).collect(Collectors.toCollection(LinkedHashSet::new));
        user.setRoles(roles);
        return user;
    }

    static void copy(UserDTO dto, User entity) {
        User newUser = UserPrototype.toEntity(dto);
        entity.setRoles(newUser.getRoles());
        entity.setUsername(newUser.getUsername());
        entity.setPassword(newUser.getPassword());
    }

}
