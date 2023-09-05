package com.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.model.category.ClaimType;
import com.model.dto.ClaimDto;
import com.model.dto.UserDto;
import com.model.mapper.UserMapper;
import com.model.table.User;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static com.common.Constants.ACCESS_TKN_TIMEOUT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JwtTest {
    private User expectedUser;
    @Autowired
    private ObjectMapper om;
    @BeforeEach
    void setUp() throws JsonProcessingException {
        MockitoAnnotations.openMocks(this);
        om=new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        expectedUser=om.readValue("{\"first_name\":\"sourabh\",\"last_name\":\"raghav\",\"date_of_birth\":\"02-09-2023\",\"username\":\"sourabhraghav\",\"password\":\"some_password\",\"created_at\":\"02-09-2023 12:02:48 +0700\",\"updated_at\":\"02-09-2023 12:02:48 +0700\",\"type\":\"SYSTEM\"}",User.class);
    }
    @Test
    void testCreateJwtAndVerify() throws JsonProcessingException {
        var expectedUserDto=UserMapper.USER_MAPPER_INSTANCE.userToUserDto(expectedUser);
        var jwt=Jwt.createToken(ACCESS_TKN_TIMEOUT,expectedUserDto,ClaimType.ACCESS);
        assertTrue(Jwt.validateJwtToken(jwt));
        ClaimDto<UserDto> claim=Jwt.readClaim(Jwt.decodeJwt(jwt), new TypeReference<ClaimDto<UserDto>>() {});
        UserDto actuallyUserDto=claim.getInfo();
        assertFalse(Jwt.isJwtExpired(claim.getExp()));
        assertEquals(claim.getType(), ClaimType.ACCESS);
        assertEquals(expectedUser.getId(), actuallyUserDto.getId());
        assertEquals(expectedUser.getFirstName(), actuallyUserDto.getFirstName());
        assertEquals(expectedUser.getLastName(), actuallyUserDto.getLastName());
        assertEquals(expectedUser.getDateOfBirth(), actuallyUserDto.getDateOfBirth());
        assertEquals(expectedUser.getUsername(), actuallyUserDto.getUsername());
        assertEquals(expectedUser.getPassword(), actuallyUserDto.getPassword());
        assertEquals(expectedUser.getCreatedAt(), actuallyUserDto.getCreatedAt());
        assertEquals(expectedUser.getUpdatedAt(), actuallyUserDto.getUpdatedAt());
        assertEquals(expectedUser.getType(), actuallyUserDto.getType());
    }




}