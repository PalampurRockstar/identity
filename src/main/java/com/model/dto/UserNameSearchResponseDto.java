package com.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.model.category.UserType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserNameSearchResponseDto {
    private boolean found;

    @JsonProperty("name_recommendation")
    private List<String> nameRecommendation;
}