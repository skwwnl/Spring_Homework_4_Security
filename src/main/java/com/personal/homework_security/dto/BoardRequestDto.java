package com.personal.homework_security.dto;


public class BoardRequestDto {
    private String title;
    private String contents;

    // 생성자 2개 속성
    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public BoardRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
