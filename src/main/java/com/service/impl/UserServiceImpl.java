package com.service.impl;


import com.common.util.GenerateUserNames;
import com.model.dto.UserDto;
import com.model.dto.UserNameSearchResponseDto;
import com.model.table.User;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.repository.UserRepository;
import com.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.model.mapper.UserMapper.USER_MAPPER_INSTANCE;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository petRepo;
    @Override
    public List<User> findAll() {
        return petRepo.findAll();
    }
    public UserNameSearchResponseDto findByUsername(String userName,int limit){
        return petRepo.findByUsername(userName)
                .map(GenerateUserNames::generateUserNameList)
                .map(gl->{
                    var userNameList=new HashSet<>(gl);
                    userNameList.removeAll(new HashSet<>(petRepo.findIfUserNameListExist(gl)));
                    return new UserNameSearchResponseDto(true, new ArrayList<>(limit>0?userNameList
                            .stream()
                            .limit(limit)
                            .collect(Collectors
                                    .toList()):userNameList));
                }).orElse(new UserNameSearchResponseDto(false,null));
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
