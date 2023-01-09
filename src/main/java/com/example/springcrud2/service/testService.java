//package com.example.springcrud2.service;
//
//import org.springframework.web.bind.annotation.PostMapping;
//
//public class testService {
//    @PostMapping("/login")
//    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) {
//        UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto, response);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json",
//                Charset.forName("UTF-8")));
//
//        return new ResponseEntity<>(userLoginResponseDto, headers, userLoginResponseDto.getStatus());
//    }
//}

