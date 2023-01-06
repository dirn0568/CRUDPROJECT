package com.example.springcrud2.dto;

import javax.validation.constraints.Pattern;

public class RegisterRequestDto {
    Long id;
    @Pattern(regexp="^[a-z0-9]*$")
    String name;
    @Pattern(regexp="^[a-zA-z0-9@$!%*#?&]*$")
    String pw;
    boolean role = false;
    String roleSecret;
}
