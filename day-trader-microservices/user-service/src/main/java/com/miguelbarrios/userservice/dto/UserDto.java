package com.miguelbarrios.userservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String profilePictureUrl;
}
