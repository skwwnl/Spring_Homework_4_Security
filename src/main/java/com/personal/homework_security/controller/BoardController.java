package com.personal.homework_security.controller;


import com.personal.homework_security.dto.BoardDeleteDto;
import com.personal.homework_security.dto.BoardRequestDto;
import com.personal.homework_security.dto.BoardResponseDto;
import com.personal.homework_security.entity.UserRoleEnum;
import com.personal.homework_security.security.UserDetailsImpl;
import com.personal.homework_security.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public List<BoardResponseDto> allShow() {
        return boardService.allShow();
    }

    // Post 방식으로 게시판 추가 API
    @Secured(UserRoleEnum.Authority.ADMIN) // ADMIN만 가능한 보안
    @PostMapping("/board")
    // 인증 객체에 담겨져 있는 UserDetailsImpl을 파라미터로 요청을 받아 받아올 수 있다.
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(requestDto, userDetails.getUser());
    }

    // Get 방식으로 선택한 게시글 조회 API
    @GetMapping("/board/{id}")
    public BoardResponseDto selectBoard(@PathVariable Long id) {
        return boardService.selectBoard(id);
    }

    // Put 방식으로 선택한 게시글 수정 API
    @Secured(UserRoleEnum.Authority.ADMIN) // ADMIN만 가능한 보안
    @PutMapping("/board/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id,
                                        @RequestBody BoardRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updateBoard(id, requestDto, userDetails.getUser());
    }

    // Delete 방식으로 선택한 게시글 삭제 API
    @Secured(UserRoleEnum.Authority.ADMIN)
    @DeleteMapping("/board/{id}")
    public BoardDeleteDto deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.deleteBoard(id, userDetails.getUser());
    }
}
