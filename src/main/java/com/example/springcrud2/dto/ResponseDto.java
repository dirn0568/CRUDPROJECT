package com.example.springcrud2.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@RequiredArgsConstructor
public class ResponseDto<D> {
    //private HttpMethod httpMethod;
    //private HttpStatus httpStatus;
    private final Integer resultCode;
    private final String message;
    private final D data;
}
