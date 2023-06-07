package com.springwiki.api.data.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDto {
    private String name;

    private int price;

    private int stock;
}
