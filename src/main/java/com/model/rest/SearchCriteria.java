package com.model.rest;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import com.model.jpa.PriceFilter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class SearchCriteria {
    private String location;
    private String breed;
    private String type;
    private String gender;

    private PriceFilter priceFilter;

}