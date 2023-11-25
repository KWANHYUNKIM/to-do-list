package com.example.demo.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    private String filename; // 파일 이름

    private String filepath; // 파일 경로

    @ManyToOne(fetch = FetchType.LAZY) // Many : Board , One : Member 한명의 유저가 여러개의 게시글 작성
    @JoinColumn(name = "member_id")
    private Member member;

    @ColumnDefault("0")
    private int viewCount;

    @ColumnDefault("0")
    private int likes;

    private char deleteYn; // 삭제 여부

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    //==연관관계 메서드==//
    public void setMember (Member member) {
        this.member = member;
    }

    //==비즈니스 로직==//
    /**
     * 게시글 삭제
     */
    public void cancel() {

    }

}
