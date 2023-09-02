package com.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.model.table.User;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {
    String dateTime = "02-09-2023 12:02:48 +0700";
    String date = "02-09-2023";
//    String mockedDateTime= String.valueOf(LocalDateTime.parse(dateTime,appDateTimeFormat()));
//    String mockedDate=String.valueOf(LocalDate.parse(dateTime,appDateFormat()));
    String expectedUser="{\"id\":\"U8687687687687686\",\"first_name\":\"firstName\",\"last_name\":\"lastName\",\"date_of_birth\":\"02-09-2023\",\"username\":\"sourabh\",\"password\":\"password\",\"created_at\":\"02-09-2023 12:02:48 +0700\",\"updated_at\":\"02-09-2023 12:02:48 +0700\",\"type\":null}";
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        om=new ObjectMapper();
        om.registerModule(new JavaTimeModule());
    }
    @Autowired
    private ObjectMapper om;
    @Test
    void testObjectToJson() throws JsonProcessingException {
        User user=new User();
        user.setId("U8687687687687686");
        user.setCreatedAt(dateTime);
        user.setUpdatedAt(dateTime);
        user.setDateOfBirth(date);
        user.setUsername("sourabh");
        user.setPassword("password");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        String json=om.writeValueAsString(user);
        System.out.println("json: "+json);
        assertThat(json).isEqualTo(expectedUser);
    }

    @Test
    void testDateConverter() throws JsonProcessingException {
        var actualUser=om.readValue(expectedUser, User.class);
        assertThat(actualUser.getDateOfBirth()).isEqualTo(date);
        assertThat(actualUser.getCreatedAt()).isEqualTo(dateTime);
        assertThat(actualUser.getUpdatedAt()).isEqualTo(dateTime);
    }


}