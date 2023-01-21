package com.miguelbarrios.daytrader.userservice.services;

import com.miguelbarrios.daytrader.userservice.exceptions.AccountCreationException;
import com.miguelbarrios.daytrader.userservice.exceptions.UserNameUnavailableException;
import com.miguelbarrios.daytrader.userservice.model.Account;
import com.miguelbarrios.daytrader.userservice.model.AppUser;
import com.miguelbarrios.daytrader.userservice.model.Role;
import com.miguelbarrios.daytrader.userservice.repository.RoleRepository;
import com.miguelbarrios.daytrader.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final WebClient.Builder webClientBuilder;

    @Override
    public AppUser saveUser(AppUser newUser) {
        AppUser user = userRepository.findByUsername(newUser.getUsername());
        if(user != null){
            throw new UserNameUnavailableException(newUser.getUsername() + " not available");
        }

        log.info("Saving new user {} to the database: ", newUser.getFirstName());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user = userRepository.save(newUser);
        Account account = createAccount(user.getId());
        if(account == null){
            userRepository.delete(user);
            log.info("unable to create user entity, account unable to be created");
            throw new AccountCreationException("Unable to create Account");
        }
        addRoleToUser(user.getUsername(), "ROLE_USER");
        user.setAccountId(account.getId());
        return user;
    }

    public Account createAccount(Long userId){
        // Call Inventory Service and place order if product is in stock
        Account account = webClientBuilder.build().post()
                .uri("http://account-service/api/v1/account/" + userId)
                .retrieve()
                .bodyToMono(Account.class)
                .block();

        return account;
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving Role {} to the database: ", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

        AppUser user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        // Because we have @Transactional at the top we do not need to call .save on the user repository
        log.info("Adding Role {} to user {}", role.getId(), user.getFirstName());
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Fetching user {} ", username);
        return userRepository.findByUsername((username));
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);
        if(user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in database");
        } else{
            log.info("User found in database: {}", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList();
        user.getRoles().forEach( role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
