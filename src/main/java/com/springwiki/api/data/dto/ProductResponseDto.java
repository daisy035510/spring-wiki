package com.springwiki.api.data.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductResponseDto {
    private Long number;

    private String name;

    private int price;

    private int stock;

}
