package com.miguelbarrios.userservice.controllers;

import com.miguelbarrios.userservice.dto.UserDto;
import com.miguelbarrios.userservice.models.User;
import com.miguelbarrios.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void createUser(@RequestBody UserDto userDto){
        userService.createUser(userDto);
    }

    @GetMapping("/isAvailable/{username}")
    public boolean isUserNameAvailable(@PathVariable String username){
        return userService.isUserNameAvailable(username);
    }

    @PutMapping
    public void updateUser(@RequestBody UserDto userDto){
        userService.updateUser(userDto);
    }

    @PutMapping("username/{username}")
    public void updateUsername(@PathVariable String username, @RequestBody UserDto userDto){
        userService.updateUsername(username, userDto.getUsername());
    }


}
