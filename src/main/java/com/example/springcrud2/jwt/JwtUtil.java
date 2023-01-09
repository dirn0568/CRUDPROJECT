package com.example.springcrud2.jwt;

import com.example.springcrud2.security.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component // 빈으로 등록해준거 같음?
@RequiredArgsConstructor
public class JwtUtil {

    private static final String AUTHORIZATION_KEY = "auth";
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    private Key key;
    @Value("${jwt.secret.key}") // application properties의 jwt.secret.key 값이 들어왔음
    private String secretKey;

    private final UserDetailsServiceImpl userDetailsService;
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
        //key = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(Long id) {
        Date date = new Date();
        return "Bearer " +
                Jwts.builder()
                .setSubject(Long.toString(id)) // 토큰의 유저정보를 넣을때 id값을 넣고 싶은데 id가 숫자값이라서 넣기가 힘들어서 이렇게 숫자값을 String으로 넣었는데 애초에 id 값을 숫자형으로 넣을 수 있는 다른방법이 있을까요
                .claim(AUTHORIZATION_KEY, "tt") // payload 부분에 들어갈 정보 조각
                .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                .setIssuedAt(date)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String getToken(HttpServletRequest request) { // static과 non-static의 차이

        String token = request.getHeader("Authorization");

        if (token != null) {
            System.out.println("아니 애초에 토큰이 있어??");
            System.out.println(token);
            if (!(StringUtils.hasText("Bearer ") && token.startsWith("Bearer "))) { // StringUtils = 그냥 문자열 찾는거 도와주는 라이브러리
                System.out.println("설마 여기까지 옴???");
                throw new IllegalArgumentException("올바른 토큰 형식이 아닙니다."); // 이게 실행이 되면 올스톱 작용
            }
            System.out.println("여기서 막혔을꺼 같은데2");
            token = token.substring(7);
            System.out.println("여기서 막혔을꺼 같은데3");
        }

        return token;
    }

    public boolean vaildation(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public Claims getUserInfoFromToken(String token) {
        System.out.println("이 겟유저인폴ㄹ프롬토큰이 왜 실행이 되는거냐ㅓㅈ대ㅑ거ㅓㄱㅂ댜ㅐㅈ");
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody(); // 이건 또 뭘까
    }

    public Authentication createAuthentication(Long userId) { // 임포트가 안됨
        System.out.println("이 인증은 대체 언제 시작이 되는거여@$#!#@!#!@#");
        UserDetails userDetails = userDetailsService.loadUserByUserId(userId);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

//    public String createToken(String userPk, List<String> roles) {
//        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
//        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
//        Date now = new Date();
//        return Jwts.builder()
//                .setClaims(claims) // 정보 저장
//                .setIssuedAt(now) // 토큰 발행 시간 정보
//                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
//                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
//                // signature 에 들어갈 secret값 세팅
//                .compact();
//    }
//
//    // JWT 토큰에서 인증 정보 조회
//    public Authentication getAuthentication(String token) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//    }
//
//    // 토큰에서 회원 정보 추출
//    public String getUserPk(String token) {
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
//    }
//
//    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
//    public String resolveToken(HttpServletRequest request) {
//        return request.getHeader("X-AUTH-TOKEN");
//    }
//
//    // 토큰의 유효성 + 만료일자 확인
//    public boolean validateToken(String jwtToken) {
//        try {
//            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
//            return !claims.getBody().getExpiration().before(new Date());
//        } catch (Exception e) {
//            return false;
//        }
//    }

    //            Map<String, Object> headers = new HashMap<>();
    //            headers.put("typ", "JWT");
    //            headers.put("alg", "HS256");
    //
    //            //payload 부분 설정
    //            Map<String, Object> payloads = new HashMap<>();
    //            payloads.put("KEY", "HelloWorld"); //
    //            payloads.put("NickName","Erjuer"); //
    //            payloads.put("Age","29"); //
    //            payloads.put("TempAuth","Y"); //
    //
    //            Long expiredTime = 1000 * 60L * 60L * 1L; // 토큰 유효 시간 (2시간)
    //
    //            Date date = new Date(); // 토큰 만료 시간
    //            date.setTime(date.getTime() + expiredTime);
    //
    //            Key key = Keys.hmacShaKeyFor("7ZWt7ZW0OTntmZTsnbTtjIXtlZzqta3snYTrhIjrqLjshLjqs4TroZzrgpjslYTqsIDsnpDtm4zrpa3tlZzqsJzrsJzsnpDrpbzrp4zrk6TslrTqsIDsnpA=".getBytes(StandardCharsets.UTF_8));
    //
    //            // 토큰 Builder
    //            String jwt = Jwts.builder()
    //                    .setHeader(headers) // Headers 설정
    //                    .setClaims(payloads) // Claims 설정
    //                    .setSubject("Test") // 토큰 용도
    //                    .setExpiration(date) // 토큰 만료 시간 설정
    //                    .signWith(key, SignatureAlgorithm.HS256)
    //                    .compact(); // 토큰 생성
    //
    //
    //            System.out.println(">> jwt : " + jwt);
}
