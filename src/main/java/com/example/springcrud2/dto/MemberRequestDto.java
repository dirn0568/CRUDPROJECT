package com.example.springcrud2.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class MemberRequestDto {
    //Long id;
    @Size(min=4,max=10)
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]*$") // "^(?=.*[0-9])(?=.*[a-zA-Z])*$", "^[a-z0-9]*$", ^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]*$
    String name;
    @Size(min=8,max=15) ///^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!%*#?&]{8,20}$/; // ^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d$@$!%*#?&]*$
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]*$") // "^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]$", "^[a-zA-z0-9@$!%*#?&]*$"
    String pw;
    boolean role = false;
    String adminToken;
}
