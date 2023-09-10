package com.service;


import com.model.dto.UserDto;
import com.model.dto.UserNameSearchResponseDto;
import com.model.table.User;

import java.util.List;

public interface UserService {

    List<?> findAll();

    UserDto findById(String id);
    UserNameSearchResponseDto findByUsername(String userName, int limit);

    User save(User superHero);

	User update(String id, User User);

    void delete(String id);

}
