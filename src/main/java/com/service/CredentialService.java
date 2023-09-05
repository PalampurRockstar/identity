package com.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.dto.CredentialDto;
import com.model.dto.TokenSetDto;
import com.model.dto.UserDto;
import com.model.table.User;

import java.util.List;

public interface CredentialService {

    List<?> findAll();

    UserDto findById(String id);

    boolean verify(String auth);
    TokenSetDto login(CredentialDto superHero);
    TokenSetDto refresh(TokenSetDto old) throws JsonProcessingException;

	User update(String id, User User);

    void delete(String id);

}
