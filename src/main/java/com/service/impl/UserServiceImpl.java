package com.service.impl;


import com.model.dto.UserDto;
import com.model.table.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.repository.UserRepository;
import com.service.UserService;

import java.util.List;

import static com.model.mapper.UserMapper.USER_MAPPER_INSTANCE;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository petRepo;
    @Override
    public List<User> findAll() {
        return petRepo.findAll();
    }

    @Override
    public UserDto findById(String id) {
        return petRepo.findById(id)
                .map(USER_MAPPER_INSTANCE::userToUserDto)
                .orElseThrow(() -> new RuntimeException ("** User not found for id :: " + id));
    }
    @Override
    public User save(User user) {
        return petRepo.save(user);
    }
    @Override
    public User update(String id, User user) {
    	petRepo.findById(id).orElseThrow(() -> new RuntimeException ("** User not found for id :: " + id));
    	user.setId(id);
    	return petRepo.save(user);
    }
    @Override
    public void delete(String id) {
        petRepo.findById(id).ifPresent(User -> petRepo.delete(User));
    }
}
