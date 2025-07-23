package com.example.PruebaEdwslme.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseDto {
    private Boolean success;
    private String message;
}
