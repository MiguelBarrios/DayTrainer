package com.miguelbarrios.userservice.repositories;

import com.miguelbarrios.userservice.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
