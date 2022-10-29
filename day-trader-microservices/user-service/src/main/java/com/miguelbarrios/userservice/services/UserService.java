package com.miguelbarrios.userservice.services;

import com.miguelbarrios.userservice.dto.UserDto;
import org.springframework.stereotype.Service;

public interface UserService {
    void createUser(UserDto userDto);
}
