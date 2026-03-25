package com.example.sb.Service;

import com.example.sb.Entity.Users;
import com.example.sb.Repositry.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService  {

    @Autowired
    private UserRepository repo;

    @Cacheable("user")
    public Users loadUserByUsername(String username) {
        Users user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user;
    }
}
