package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor
@NoArgsConstructor
@Table(name = "members")
public class Member  {
    @Id @GeneratedValue
    @Column(name ="member_id")
    private Long id;

    private String email; // 이메일

    private String username; // 이름

    private String birthday; // 생일

    private String password; // 패스워드

    private String phonenumber; // 핸드폰번호

    private String position;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

}
