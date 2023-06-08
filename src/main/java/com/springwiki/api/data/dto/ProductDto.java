package com.springwiki.api.data.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private String name;

    private int price;

    private int stock;
}
