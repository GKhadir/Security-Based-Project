package com.example.sb.Service;

import com.example.sb.Entity.Users;
import com.example.sb.Repositry.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    public String register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        if(user.getRole()!=null){
        user.setRole(user.getRole());
        }
        repo.save(user);
        return "Registered successfully";
    }

    public Users login(String username, String password) {
        Users user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
}
