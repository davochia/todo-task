package com.example.integrifytask.dao;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDao {

//    @Autowired
//    private UserRepository userRepository;

//    private final static List<UserDetails> APP_USERS = Arrays.asList(
//            new User("johndoe@example.com", "password123", Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))),
//            new User("johnsmith@example.com", "password123", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
//
//    );

    private final static List<UserDetails> APP_USERS = Arrays.asList(
            new User("johndoe@example.com", "password123", Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))),
            new User("johnsmith@example.com", "password123", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))

    );

    public UserDetails findUserByEmail(String email){
        return APP_USERS
                .stream()
                .filter(user -> user.getUsername().equals(email))
                .findFirst()
                .orElseThrow(()-> new UsernameNotFoundException("user not found"));
    }
}
