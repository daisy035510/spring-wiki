package com.springwiki.api.data.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {
    private Long number;

    private String name;

    private int price;

    private int stock;

}
