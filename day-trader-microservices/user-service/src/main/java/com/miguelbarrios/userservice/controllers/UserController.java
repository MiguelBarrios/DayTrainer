package com.miguelbarrios.userservice.controllers;

import com.miguelbarrios.userservice.dto.UserDto;
import com.miguelbarrios.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public String createUser(@RequestBody UserDto userDto){
        userService.createUser(userDto);
        return "user created successfully";
    }


}
