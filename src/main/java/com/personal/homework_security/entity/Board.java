package com.personal.homework_security.entity;


import com.personal.homework_security.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID가 자동으로 생성 및 증가
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Board(BoardRequestDto boardRequestDto, User user){
        this.title = boardRequestDto.getTitle();
        this.contents = boardRequestDto.getContents();
        this.user = user;
    }

    public void update(BoardRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public User getUser(){ return user;}
}
