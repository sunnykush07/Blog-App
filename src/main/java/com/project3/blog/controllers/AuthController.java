package com.project3.blog.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.project3.blog.exceptions.ApiException;
import com.project3.blog.payloads.JwtAuthRequest;
import com.project3.blog.payloads.JwtAuthResponse;
import com.project3.blog.payloads.UserDto;
import com.project3.blog.security.JwtTokenHelper;
import com.project3.blog.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
    	try {
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	
	        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
	        String token = jwtTokenHelper.generateToken(userDetails);
	
	        return ResponseEntity.ok(new JwtAuthResponse(token));
    	} catch (BadCredentialsException ex) {
            // Return Unauthorized response if password is incorrect
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(new JwtAuthResponse("Invalid username or password"));

            throw (new ApiException("Invalid username or password"));
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
    	UserDto registered = this.userService.registerUser(userDto);
    	return new ResponseEntity<UserDto>(registered,HttpStatus.CREATED);
    }
    

}

