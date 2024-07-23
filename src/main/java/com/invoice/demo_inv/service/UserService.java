package com.invoice.demo_inv.service;


import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.invoice.demo_inv.entity.User;
import com.invoice.demo_inv.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<User> getAll(){ 
        return userRepo.findAll();
    }

    public User getById(int id){
        return userRepo.findById(id);
    }

    public void addUser(User userEntity){
        String password = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(password);
        userRepo.save(userEntity);
    }

    public void updateUser(int UserId, User UpdatedUser) {
        User curruser = userRepo.findById(UserId);
        curruser.setFirstName(UpdatedUser.getFirstName());
        curruser.setLastName(UpdatedUser.getLastName());
        userRepo.save(curruser);
    }

    public String deleteUser(int id){
        if(userRepo.findById(id)!=null){
            userRepo.deleteById(id);
            return "User deleted successfully";
        }
        return "User not found";
    }

}
