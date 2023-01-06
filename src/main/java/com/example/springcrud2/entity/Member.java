package com.example.springcrud2.entity;

import com.example.springcrud2.Enum.MemberEnum;
import com.example.springcrud2.dto.RegisterDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
public class Member extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // auto로 하면 id값이 혼자서 올라가는데 IDENTITY로 바꾸니 id가 제대로 올라감 IDENTITY도 이상한데?
    Long id;

    @Size(min=4,max=10)
    @Pattern(regexp="^[a-z0-9]*$")
    @Column(nullable = false)
    String name;

    @Size(min=8,max=15)
    @Pattern(regexp="^[a-zA-z0-9@$!%*#?&]*$")
    @Column(nullable = false)
    String pw;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    MemberEnum role;

    @Transient // 이거하면 컬럼에서 제외 시킬 수 있음
    String adminToken;
}
