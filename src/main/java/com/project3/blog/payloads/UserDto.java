package com.project3.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.project3.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min=3,message = "Name consists atleast 4 char")
    private String name;
    
    @NotEmpty
    @Email
    private String email;
    
    @NotEmpty
    @Size(min=4,message = "Password consists atleast 4 char")
    private String password;
    
    @NotEmpty
    @Size(min=10,message = "Name consists atleast 4 char")
    private String about;
    
    private Set<Role> roles=new HashSet<>();
}	

