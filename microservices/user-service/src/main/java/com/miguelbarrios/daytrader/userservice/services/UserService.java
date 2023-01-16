package com.miguelbarrios.daytrader.userservice.services;



import com.miguelbarrios.daytrader.userservice.model.AppUser;
import com.miguelbarrios.daytrader.userservice.model.Role;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
}
