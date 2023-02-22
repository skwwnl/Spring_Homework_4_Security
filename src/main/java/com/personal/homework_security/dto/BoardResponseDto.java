package com.personal.homework_security.dto;

import com.personal.homework_security.entity.Board;

import java.time.LocalDateTime;

public class BoardResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String username;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;


    // 생성자
    public BoardResponseDto(Board board) {
    this.id = board.getId();
    this.username = board.getUser().getUsername();
    this.title = board.getTitle();
    this.contents = board.getContents();
    this.modifiedAt = board.getModifiedAt();
    this.createdAt = board.getCreatedAt();
    }

    // Getter
    public Long getId() {
        return id;
    }

    public String getUsername() { return username; }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}


