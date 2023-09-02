package com.controller;

import com.AppStart;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.dto.PetDto;
import com.model.table.*;
import lombok.var;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

import java.util.*;

import static com.model.mapper.PetMapper.petMapperInstance;
import static graphql.Assert.assertNotNull;
import static graphql.Assert.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AppStart.class)
@TestPropertySource(locations = "classpath:application.yaml")
@ActiveProfiles("local")
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ConfigurableApplicationContext context;

//    @BeforeEach
//    public void before() {
//        ConfigurableEnvironment environment = context.getEnvironment();
//        String[] activeProfiles = environment.getActiveProfiles();
//        System.out.println("Active Profiles: " + String.join(", ", activeProfiles));
//        System.out.println("Properties from application.yml:");
//        for (PropertySource<?> propertySource : environment.getPropertySources()) {
//                if (propertySource instanceof org.springframework.core.env.EnumerablePropertySource) {
//                    org.springframework.core.env.EnumerablePropertySource<?> enumerablePropertySource =
//                            (org.springframework.core.env.EnumerablePropertySource<?>) propertySource;
//                    for (String propertyName : enumerablePropertySource.getPropertyNames()) {
//                        Object propertyValue = enumerablePropertySource.getProperty(propertyName);
//                        System.out.println("  " + propertyName + ": " + propertyValue);
//                    }
//                }
//        }
//    }

    @Test
    public void shouldTestIntegration() throws JsonProcessingException {
        //when
        Address expectedAddress= createAddress();

        //trigger
        ResponseEntity<Address> addressResponse = restTemplate.postForEntity("/address", expectedAddress,Address.class);
        Address actualAddress= addressResponse.getBody();

        //verify
        assertThat(addressResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(actualAddress.getId());

        //when
        Location expectedLocation= createLocation();
        expectedLocation.setAddress(actualAddress);

        //trigger
        ResponseEntity<Location> responseLocation = restTemplate.postForEntity("/location", expectedLocation,Location.class);
        Location actualLocation= responseLocation.getBody();

        //verify
        assertThat(responseLocation.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(actualLocation.getId());




        //when
        Breeder expectedBreeder= createBreeder();
        expectedBreeder.setLocation(actualLocation);

        //trigger
        ResponseEntity<Breeder> responseBreeder = restTemplate.postForEntity("/breeder", expectedBreeder,Breeder.class);
        Breeder actualBreeder= responseBreeder.getBody();

        //verify
        assertThat(responseBreeder.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(actualBreeder.getId());

        //when
        Image expectedImage0= createImage();
        expectedImage0.setBreeder(actualBreeder);
        expectedImage0.setIsProfilePicture(true);
        expectedImage0.setFile("sourabh.jpg");
        expectedImage0.setPath("http://localhost:8888/api/images/");

        //trigger
        ResponseEntity<Image> responseImage0 = restTemplate.postForEntity("/image", expectedImage0,Image.class);
        Image actualImage0= responseImage0.getBody();

        //verify
        assertThat(responseImage0.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(actualImage0.getId());

        //when
        ResponseEntity<Image[]> breederResponse0 = restTemplate.getForEntity("/breeder/"+actualBreeder.getId()+"/image", Image[].class);
        Image[] getbreeder1= breederResponse0.getBody();

        //verify
        assertThat(breederResponse0.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getbreeder1.length).isEqualTo(1);
        assertThat(getbreeder1[0].getId()).isEqualTo(actualImage0.getId());

        //when
        Price expectedPrice= createPrice();

        ResponseEntity<Price> responsePrice = restTemplate.postForEntity("/price", expectedPrice,Price.class);
        Price actualPrice= responsePrice.getBody();

        //verify
        assertThat(responsePrice.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(actualPrice.getId());

        //when
        User expectedPet= createPet();
        expectedPet.setBreeder(actualBreeder);
        expectedPet.setLocation(actualLocation);
        expectedPet.setPrice(actualPrice);

        ResponseEntity<User> responsePet = restTemplate.postForEntity("/user", expectedPet,User.class);
        User actualPet= responsePet.getBody();

        //verify
        assertThat(responsePet.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(actualPet.getId());

        //when
        Image expectedImage1= createImage();
        expectedImage1.setPet(actualPet);
        expectedImage1.setIsProfilePicture(true);

        //trigger
        ResponseEntity<Image> responseImage1 = restTemplate.postForEntity("/image", expectedImage1,Image.class);
        Image actualImage1= responseImage1.getBody();

        //verify
        assertThat(responseImage1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(actualImage1.getId());

        //when
        Document expectedDocument= createDocument();
        expectedDocument.setPet(actualPet);

        //trigger
        ResponseEntity<Document> responseDocument = restTemplate.postForEntity("/document", expectedDocument,Document.class);
        Document actualDocument= responseDocument.getBody();

        //verify
        assertThat(responseDocument.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(actualDocument.getId());

        //when
        Review expectedReview1= createReview("Comment for expectedReview1");
        expectedReview1.setPet(actualPet);

        //trigger
        ResponseEntity<Review> responseReview1 = restTemplate.postForEntity("/review", expectedReview1,Review.class);
        Review actualReview1= responseReview1.getBody();
        //verify
        assertThat(responseReview1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(actualReview1.getId());

        //when
        Review expectedReview2= createReview("Comment for expectedReview2");
        expectedReview2.setBreeder(actualBreeder);


        //trigger
        ResponseEntity<Review> responseReview2 = restTemplate.postForEntity("/review", expectedReview2,Review.class);
        Review actualReview2= responseReview2.getBody();

        //verify
        assertThat(responseReview2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualReview2.getId());

        //when
        ResponseEntity<PetDto> petResponse = restTemplate.getForEntity("/user/"+actualPet.getId(), PetDto.class);
        PetDto getPetDto= petResponse.getBody();
        User getPet= petMapperInstance.petDtoToPetWithGEtterSetter(getPetDto);

        //verify
        assertThat(petResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(getPet.getId());
        assertThat(getPetDto.getProfilePicture().getId()).isEqualTo(actualImage1.getId());

        //when
        ResponseEntity<Review> reviewResponse2 = restTemplate.getForEntity("/review/"+actualReview2.getId(), Review.class);
        Review getReview2= reviewResponse2.getBody();

        assertThat(reviewResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getReview2.getBreeder().getId()).isEqualTo(actualBreeder.getId());

        //when
        ResponseEntity<Review> reviewResponse1 = restTemplate.getForEntity("/review/"+actualReview1.getId(), Review.class);
        Review getReview1= reviewResponse1.getBody();

        assertThat(reviewResponse1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getReview1.getPet().getId()).isEqualTo(getPet.getId());

        //when
        ResponseEntity<Document> documentResponse = restTemplate.getForEntity("/document/"+actualDocument.getId(), Document.class);
        Document getDocument= documentResponse.getBody();

        assertThat(documentResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getDocument.getPet().getId()).isEqualTo(getPet.getId());

        //when
        ResponseEntity<Image> imageResponse = restTemplate.getForEntity("/image/"+actualImage1.getId(), Image.class);
        Image getImage= imageResponse.getBody();

        assertThat(imageResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getImage.getPet().getId()).isEqualTo(getPet.getId());

        ResponseEntity<Review[]> reviewListResponse = restTemplate.getForEntity("/user/"+actualPet.getId()+"/review", Review[].class);
        Review[] reviewList= reviewListResponse.getBody();

        assertThat(reviewListResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reviewList.length).isEqualTo(1);
        assertTrue(reviewList[0].getId().equals(actualReview1.getId()));


        ResponseEntity<Document[]> documentListResponse = restTemplate.getForEntity("/user/"+actualPet.getId()+"/document", Document[].class);
        Document[] documentList= documentListResponse.getBody();

        assertThat(documentListResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(documentList.length).isEqualTo(1);
        assertTrue(documentList[0].getId().equals(actualDocument.getId()));

        ResponseEntity<Image[]> imageListResponse = restTemplate.getForEntity("/user/"+actualPet.getId()+"/image", Image[].class);
        Image[] imageList= imageListResponse.getBody();

        assertThat(imageListResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(imageList.length).isEqualTo(1);
        assertTrue(imageList[0].getId().equals(actualImage1.getId()));

        ResponseEntity<Breeder> breederListResponse = restTemplate.getForEntity("/user/"+actualPet.getId()+"/breeder", Breeder.class);
        Breeder breederList= breederListResponse.getBody();

        assertThat(breederListResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(breederList);
        assertTrue(breederList.getId().equals(actualBreeder.getId()));

        ResponseEntity<BreederDto> breederResponse = restTemplate.getForEntity("/breeder/"+actualBreeder.getId(), BreederDto.class);
        BreederDto getBreeder= breederResponse.getBody();

        System.out.println("getBreeder: "+mapper.writeValueAsString(getBreeder));
        assertNotNull(breederResponse);
        assertThat(breederResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(getBreeder.getId().equals(actualBreeder.getId()));
        assertTrue(getBreeder.getProfilePicture().getId().equals(actualImage0.getId()));

    }

    private Breeder createBreeder() throws JsonProcessingException {
        return mapper.readValue("{\"name\":\"Sourabh\",\"code\":\"B001\",\"rating\":3}", Breeder.class);
    }
    private Location createLocation() throws JsonProcessingException {
        return mapper.readValue("{\"name\":\"Chiang mai\",\"longitude\":\"111111\",\"latitude\":\"222222\"}", Location.class);
    }
    private Address createAddress() throws JsonProcessingException {
        return mapper.readValue("{\"street\":\"Some street\",\"pin\":\"176064\"}", Address.class);
    }
    private User createPet() throws JsonProcessingException {
        return mapper.readValue("{" +
                "\"breed\": \"Beagle\"," +
                "\"title\": \"Adorable Silver Chinchilla Persian\"," +
                "  \"type_code\": \"\"," +
                "  \"type\": \"cat\"," +
                "  \"image\": \"\"," +
                "  \"gender\": \"male\"," +
                "  \"rating\": 3," +
                "  \"dob\": \"2023-01-28T16:44:03.520Z\",\n" +
                "  \"description\":\"Lorem ipsum dolor sit ame\"}", User.class);
    }
    private Image createImage() throws JsonProcessingException {
        return mapper.readValue("{\"path\":\"some path\",\"file\":\"image1.jpg\",\"cdn\":\"host1\"}", Image.class);
    }
    private Document createDocument() throws JsonProcessingException {
        return mapper.readValue("{\"name\":\"Vaccination certificate\",\"validUntil\":\"2023-01-28T16:44:03.520Z\",\"documentNumber\":\"34343\"}", Document.class);
    }

    private Review createReview(String comment) throws JsonProcessingException {
        return mapper.readValue("{\n" +
                "    \"comment\": \""+comment+"\",\n" +
                "    \"likeCount\": 2,\n" +
                "    \"disLikeCount\": 3\n" +
                "}", Review.class);
    }
    private Price createPrice() throws JsonProcessingException {
        return mapper.readValue("{\n" +
                "  \"amount\": 3.55,\n" +
                "  \"currencyCode\": \"INR\"\n" +
                "}", Price.class);
    }
}
