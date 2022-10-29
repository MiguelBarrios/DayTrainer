package com.miguelbarrios.userservice.services;

import com.miguelbarrios.userservice.dto.UserDto;
import com.miguelbarrios.userservice.models.User;
import com.miguelbarrios.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public void createUser(UserDto userDto) {
        User user = userDtoToUser(userDto);
        userRepository.save(user);
    }

    public User userDtoToUser(UserDto userDto){
        return User.builder()
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(userDto.getPassword())
                .enabled(true)
                .role("USER")
                .email(userDto.getEmail())
                .dateCreated(LocalDateTime.now())
                .profilePictureUrl(userDto.getProfilePictureUrl())
                .build();
    }


}
