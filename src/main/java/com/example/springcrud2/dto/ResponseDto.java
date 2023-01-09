package com.example.springcrud2.dto;

import lombok.*;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
//@RequiredArgsConstructor
public class ResponseDto<D> {
    //private HttpMethod httpMethod;
    //private HttpStatus httpStatus;
    private Integer resultCode;
    private String message;

    public ResponseDto(Integer resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
        //this.data = data;
    }
}
