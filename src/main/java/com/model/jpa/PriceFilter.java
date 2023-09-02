package com.model.jpa;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration

public class PriceFilter {
    private Double from;
    private Double to;
}
