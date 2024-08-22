package com.albin.lesgo.service;

import com.albin.lesgo.model.Users;
import com.albin.lesgo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

   @Autowired
   private UserRepo repo;

   @Autowired
   AuthenticationManager authenticationManager;

   @Autowired
    JWTService jwtService;


    BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public List<Users> getUsers() {
        return repo.findAll();
    }


    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public String verify(Users user) {
        Authentication authentication=
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.genareteToken(user.getUsername());
        }

        return "failure";
    }
}
