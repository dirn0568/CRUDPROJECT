package com.example.springcrud2.dto;

import lombok.Getter;

import java.time.LocalDateTime;
//
@Getter
public class BoardRequestDto {
    private String title;
    private String content;
}
