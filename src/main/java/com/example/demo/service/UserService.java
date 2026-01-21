package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final List<User> userList = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public UserResponseDTO register(UserDTO userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
            throw new RuntimeException("Mật khẩu nhập lại không khớp!");
        }
        if (userList.stream().anyMatch(u -> u.getEmail().equals(userDTO.getEmail()))) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        User newUser = new User(
                idCounter.getAndIncrement(),
                userDTO.getFullName(),
                userDTO.getEmail(),
                userDTO.getPhoneNumber(),
                userDTO.getPassword());
        userList.add(newUser);
        return new UserResponseDTO(newUser.getId(), newUser.getFullName(), newUser.getEmail(),
                newUser.getPhoneNumber());
    }

    public List<UserResponseDTO> getAllUsers() {
        return userList.stream()
                .map(u -> new UserResponseDTO(u.getId(), u.getFullName(), u.getEmail(), u.getPhoneNumber()))
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        User user = findUserById(id);
        return new UserResponseDTO(user.getId(), user.getFullName(), user.getEmail(), user.getPhoneNumber());
    }

    public UserResponseDTO updateUser(Long id, UserUpdateDTO updateDTO) {
        User user = findUserById(id);
        user.setFullName(updateDTO.getFullName());
        user.setPhoneNumber(updateDTO.getPhoneNumber());
        return new UserResponseDTO(user.getId(), user.getFullName(), user.getEmail(), user.getPhoneNumber());
    }

    public void changePassword(Long id, ChangePasswordDTO dto) {
        User user = findUserById(id);
        if (!user.getPassword().equals(dto.getOldPassword())) {
            throw new RuntimeException("Mật khẩu cũ không đúng!");
        }
        if (!dto.getNewPassword().equals(dto.getRetypeNewPassword())) {
            throw new RuntimeException("Mật khẩu mới không khớp!");
        }
        user.setPassword(dto.getNewPassword());
    }

    public void deleteUser(Long id) {
        User user = findUserById(id);
        userList.remove(user);
    }

    private User findUserById(Long id) {
        return userList.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("User ID " + id + " không tồn tại!"));
    }
}