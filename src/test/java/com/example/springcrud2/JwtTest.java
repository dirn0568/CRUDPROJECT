//package com.example.springcrud2;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//
//@SpringBootTest
//public class JwtTest {
//    @Test
//    public void createToken() {
//        //Header 부분 설정
//        Map<String, Object> headers = new HashMap<>();
//        headers.put("typ", "JWT");
//        headers.put("alg", "HS256");
//
//        //payload 부분 설정
//        Map<String, Object> payloads = new HashMap<>();
//        payloads.put("KEY", "HelloWorld");
//        payloads.put("NickName","Erjuer");
//        payloads.put("Age","29");
//        payloads.put("TempAuth","Y");
//
//        Long expiredTime = 1000 * 60L * 60L * 1L; // 토큰 유효 시간 (2시간)
//
//        Date date = new Date(); // 토큰 만료 시간
//        date.setTime(date.getTime() + expiredTime);
//
//        Key key = Keys.hmacShaKeyFor("MyNickNameisErjuerAndNameisMinsu".getBytes(StandardCharsets.UTF_8));
//
//        // 토큰 Builder
//        String jwt = Jwts.builder()
//                .setHeader(headers) // Headers 설정
//                .setClaims(payloads) // Claims 설정
//                .setSubject("Test") // 토큰 용도
//                .setExpiration(date) // 토큰 만료 시간 설정
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact(); // 토큰 생성
//
//
//        System.out.println(">> jwt : " + jwt);
//    }
//}
