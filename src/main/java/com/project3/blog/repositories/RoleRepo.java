package com.project3.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project3.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
