package com.ruffy.EmployeeManagementApp.controller;

import com.ruffy.EmployeeManagementApp.model.User;
import com.ruffy.EmployeeManagementApp.service.JWTService;
import com.ruffy.EmployeeManagementApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/addUser")
    public String addUser( @RequestBody User user){
        return userService.addUser(user);
    }

    @PostMapping("/login")
    public String loginUser( @RequestBody User user){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));


        // If authentication is successful, generate and return JWT token
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUserName());
        } else {
            throw new UsernameNotFoundException("Invalid User Request");
        }

    }
}
