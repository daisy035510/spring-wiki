package com.springwiki.api.controller;

import com.springwiki.api.common.exception.Constants;
import com.springwiki.api.common.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping
    public void getRuntimeExceiption() {
        throw new RuntimeException("getRuntimeExceiption 호출");
    }

    @GetMapping("/custom")
    public void getCustomException() throws CustomException {
        throw new CustomException(Constants.ExceptionClass.PRODUCT, HttpStatus.BAD_REQUEST, "getRuntimeExceiption 호출");
    }
}
