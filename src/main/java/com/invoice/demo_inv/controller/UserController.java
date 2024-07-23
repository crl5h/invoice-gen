package com.invoice.demo_inv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.invoice.demo_inv.entity.User;
import com.invoice.demo_inv.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<String> addUser(@RequestBody User userEntity) {
        userService.addUser(userEntity);
        return ResponseEntity.ok().body("User added successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmpById(@PathVariable("id") int id) {
        if (userService.getById(id) != null) {
            return ResponseEntity.ok().body(userService.getById(id));
        }
        return ResponseEntity.ok().body("User not found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> getUserService(@PathVariable(value = "id") int userId,
            @RequestBody User userEntity) {
        userService.updateUser(userId, userEntity);
        return ResponseEntity.ok().body("User updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
        if (userService.deleteUser(id) != null) {
            return ResponseEntity.ok().body("User deleted successfully");
        }
        return ResponseEntity.ok().body("User not found");
    }
}
