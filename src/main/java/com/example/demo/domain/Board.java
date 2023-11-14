package com.example.demo.domain;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import com.example.demo.domain.Member;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "borads")
public class Board {
    @Id
    @GeneratedValue
    @Column(name ="board_id")
    private Long id;

    private String title;

    @Lob // 대용량 데이터
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // Many : Board , One : Member 한명의 유저가 여러개의 게시글 작성
    @JoinColumn(name = "member_id")
    private Member member;

    @ColumnDefault("0")
    private int viewCount;

    private char deleteYn; // 삭제 여부

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    //==연관관계 메서드==//

    public void setMember (Member member) {
        this.member = member;
    }

}
