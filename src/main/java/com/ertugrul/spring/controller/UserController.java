package com.ertugrul.spring.controller;


import com.ertugrul.spring.dto.UserDto;
import com.ertugrul.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {

        UserDto userDto = userService.findById(id);

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Object> getByUsername(@PathVariable String username) {

        UserDto userDto = userService.findByUsername(username);

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllUsers() {
        List<UserDto> all = userService.findAll();
        return ResponseEntity.ok(all);
    }


    @PostMapping
    public ResponseEntity<Object> create(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.save(userDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}