package com.davejang.blockchain_ticketing.user.service;

import com.davejang.blockchain_ticketing.user.domain.User;
import com.davejang.blockchain_ticketing.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Transactional
    public User registerUser(@Valid User user) {
        Optional<User> validUser = userRepository.findByName(user.getName());
        if(validUser.isPresent()) {
            throw new IllegalArgumentException("이미 등록된 회원입니다");
        }
        return userRepository.save(user);
    }


}
