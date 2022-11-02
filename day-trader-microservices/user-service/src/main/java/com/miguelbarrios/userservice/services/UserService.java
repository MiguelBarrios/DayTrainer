package com.miguelbarrios.userservice.services;

import com.miguelbarrios.userservice.dto.UserDto;

public interface UserService {
    void createUser(UserDto userDto);

    boolean isUserNameAvailable(String username);

    void updateUser(UserDto userDto);

    void updateUsername(String username, String newUsername);
}
