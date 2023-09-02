package com.model.dto;

import com.model.category.ClaimType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClaimDto<T> {
    private Long iat;
    private Long exp;
    private ClaimType type;
    private T info;
}