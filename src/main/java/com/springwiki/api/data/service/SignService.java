package com.springwiki.api.data.service;

import com.springwiki.api.data.dto.SignInResultDto;
import com.springwiki.api.data.dto.SignUpResultDto;

public interface SignService {
    SignUpResultDto signUp(String id, String password, String name, String role);

    SignInResultDto signIn(String id, String password) throws RuntimeException;

}
