package com.sipentas.Api.service;

import com.sipentas.Api.entity.User;
import com.sipentas.Api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
        return userRepository.save(user);
    }

    public Optional<User> login(String nim, String password) {
        return userRepository.findByNimAndPassword(nim, password);
    }

    public Optional<User> findByNim(String nim) {
        return userRepository.findByNim(nim);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User updatePassword(String nim, String newPassword) {
        Optional<User> userOptional = userRepository.findByNim(nim);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(newPassword);
            return userRepository.save(user);
        }
        return null;
    }

    public boolean deleteByNim(String nim) {
        if (userRepository.existsByNim(nim)) {
            userRepository.deleteByNim(nim);
            return true;
        }
        return false;
    }
}