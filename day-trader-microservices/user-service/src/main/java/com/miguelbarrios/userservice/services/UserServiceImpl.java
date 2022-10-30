package com.miguelbarrios.userservice.services;

import com.miguelbarrios.userservice.dto.UserDto;
import com.miguelbarrios.userservice.exceptions.UserNameNotAvailableException;
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
        boolean userNameIsAvailable = isUserNameAvailable(user.getUsername());
        if(userNameIsAvailable){
            userRepository.save(user);
        }
        else{
            throw  new UserNameNotAvailableException("Username: " + user.getUsername() + " is not available");
        }

    }

    @Override
    public boolean isUserNameAvailable(String username){
        return !userRepository.existsByUsername(username);
    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setProfilePictureUrl(userDto.getProfilePictureUrl());

        userRepository.save(user);
    }

    @Override
    public void updateUsername(String username, String newUsername) {
        boolean userNameIsAvailable = isUserNameAvailable(newUsername);
        System.out.println(newUsername + " available: " + userNameIsAvailable);
        if(userNameIsAvailable){
            User user = userRepository.findByUsername(username);
            user.setUsername(newUsername);
            userRepository.save(user);
        }
        else{
            throw new UserNameNotAvailableException();
        }
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
