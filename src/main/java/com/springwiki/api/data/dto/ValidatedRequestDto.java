package com.springwiki.api.data.dto;

import com.springwiki.api.config.annotation.Telephone;
import com.springwiki.api.data.group.ValidationGroup1;
import com.springwiki.api.data.group.ValidationGroup2;
import lombok.*;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class ValidatedRequestDto {

    @NotBlank
    private String name;

    @Email
    private String email;

    @Telephone
    private String phoneNumber;

    @Min(value = 20, groups = ValidationGroup1.class)
    @Max(value = 40, groups = ValidationGroup1.class)
    //@Min(value = 20)
    //@Max(value = 40)
    private int age;

    @Size(min = 0, max = 40)
    private String description;

    @Positive(groups = ValidationGroup2.class)
    //@Positive
    private int count;

    @AssertTrue
    private boolean booleanCheck;
}


