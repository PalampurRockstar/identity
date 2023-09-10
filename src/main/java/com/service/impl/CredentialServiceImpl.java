package com.service.impl;



import com.common.Errors;
import com.common.util.Jwt;
import com.exception.InvalidAccessTokenException;
import com.exception.InvalidCredentialsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.model.category.ClaimType;
import com.model.dto.*;
import com.model.table.User;
import com.repository.UserRepository;
import com.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.common.Constants.*;
import static com.model.mapper.UserMapper.USER_MAPPER_INSTANCE;

@Service
public class CredentialServiceImpl implements CredentialService {
    @Autowired
    private UserRepository userRepo;
    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    public boolean verify(String auth){
        return Optional.of(auth)
                .map(t->t.replace(BEARER+" ",""))
                .filter(t-> !t.isEmpty())
                .filter(Jwt::validateJwtToken)
                .map(t->Jwt.readClaim(Jwt.decodeJwt(t),new TypeReference<ClaimDto<UserDto>>() {}))
                .map(c->{
                    if (c.getType().equals(ClaimType.ACCESS) && !Jwt.isJwtExpired(c.getExp()))return true;
                    else return null;
                })
                .orElseThrow(()-> new InvalidAccessTokenException(Errors.P002));
    }
    @Override
    public UserDto findById(String id) {
        return userRepo.findById(id)
                .map(USER_MAPPER_INSTANCE::userToUserDto)
                .orElseThrow(() -> new RuntimeException ("** User not found for id :: " + id));
    }

    public TokenSetDto refresh(TokenSetDto old) {
        return Optional.of(old)
                .filter(t-> Jwt.validateJwtToken(t.getAccessToken()) && Jwt.validateJwtToken(t.getAccessToken()))
                .map(t->{
                    ClaimDto<UserDto> accessClaim=Jwt.readClaim(Jwt.decodeJwt(t.getAccessToken()), new TypeReference<ClaimDto<UserDto>>() {});
                    ClaimDto<UserDto> refreshClaim=Jwt.readClaim(Jwt.decodeJwt(t.getRefreshToken()), new TypeReference<ClaimDto<UserDto>>() {});
                    UserDto accessUDto=accessClaim.getInfo();
                    UserDto refreshUDto=refreshClaim.getInfo();
                    if (accessClaim.getType().equals(ClaimType.ACCESS) && refreshClaim.getType().equals(ClaimType.REFRESH) && refreshUDto.getUsername().equals(accessUDto.getUsername())){
                        if(Jwt.isJwtExpired(accessClaim.getExp()) && !Jwt.isJwtExpired(refreshClaim.getExp())) {
                            return userRepo.findByUsername(accessUDto.getUsername()).orElseGet(()->null);
                        }else{
                            throw new RuntimeException ("Condition: expiry of token is not matched" );
                        }
                    }else{
                        throw new RuntimeException ("Condition: claim type not matched" );
                    }
                })
                .map(USER_MAPPER_INSTANCE::userToUserDto)
                .map(c->new TokenSetDto(
                        Jwt.createToken( ACCESS_TKN_TIMEOUT,c,ClaimType.ACCESS),
                        Jwt.createToken( REFRESH_TKN_TIMEOUT,c,ClaimType.REFRESH))
                )
                .orElseThrow(()->new RuntimeException ("Condition doesn't match to generate refresh token" ));
    }
    @Override
    public TokenSetDto login(CredentialDto cred) {
        return userRepo.findByUsernameAndPassword(cred.getUsername(),cred.getPassword())
                .map(USER_MAPPER_INSTANCE::userToUserDto)
                .map(c->new TokenSetDto(
                        Jwt.createToken( ACCESS_TKN_TIMEOUT,c,ClaimType.ACCESS),
                        Jwt.createToken( REFRESH_TKN_TIMEOUT,c,ClaimType.REFRESH))
                )
                .orElseThrow(()-> new InvalidCredentialsException(Errors.P001));
    }
    @Override
    public User update(String id, User user) {
    	userRepo.findById(id).orElseThrow(() -> new RuntimeException ("** User not found for id :: " + id));
    	user.setId(id);
    	return userRepo.save(user);
    }
    @Override
    public void delete(String id) {
        userRepo.findById(id).ifPresent(User -> userRepo.delete(User));
    }
}
