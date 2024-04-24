package com.ruffy.EmployeeManagementApp.service;


import com.ruffy.EmployeeManagementApp.model.User;
import com.ruffy.EmployeeManagementApp.model.UserPrincipal;
import com.ruffy.EmployeeManagementApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepo.findByUserName(username);
        return user.map(UserPrincipal::new).orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }

    // Add a new user to the repository and encrypt the password
    public String addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "user added successfully";
    }
}
