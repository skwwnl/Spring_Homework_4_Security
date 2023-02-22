package com.personal.homework_security.service;

import com.personal.homework_security.dto.BoardDeleteDto;
import com.personal.homework_security.dto.BoardRequestDto;
import com.personal.homework_security.dto.BoardResponseDto;
import com.personal.homework_security.entity.Board;
import com.personal.homework_security.entity.User;
import com.personal.homework_security.repository.BoardRepository;
import com.personal.homework_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<BoardResponseDto> allShow() {
        List<Board> boards = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDto> responseDtos = new ArrayList<>();
        for (Board board : boards) {
            responseDtos.add(new BoardResponseDto(board));
        }
        return responseDtos;
    }

    @Transactional
    // DB에 연결을 해서 저장을 하려면 @Entity가 걸려있는 Board 클래스를 인스턴스로 만들어서
    // 그 값을 사용해서 저장을 해야한다.
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        System.out.println("======================================");
        System.out.println("BoardService.createBoard");
        System.out.println("user.getUsername() = " + user.getUsername());
        System.out.println("======================================");

        // 요청 받은 DTO로 DB에 저장할 객체 만들기
        Board board = boardRepository.saveAndFlush(new Board(requestDto, user));

        return new BoardResponseDto(board);


//        // Request 에서 Token 가져오기
//        String token = jwtUtil.resolveToken(request);
//        Claims claims;
//
//        // 토큰이 있는 경우에만 관심상품 추가 가능
//        if (token != null) {
//            if (jwtUtil.validateToken(token)) {
//                // 토큰에서 사용자 정보 가져오기
//                claims = jwtUtil.getUserInfoFromToken(token);
//            } else {
//                throw new IllegalArgumentException("Token Error");
//            }
//
//            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
//            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
//                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
//            );
//
//            // 요청받은 DTO 로 DB에 저장할 객체 만들기
//            Board board = boardRepository.saveAndFlush(new Board(requestDto, user));
//
//            return new BoardResponseDto(board);
//        } else {
//            return null;
//        }
    }

    // DB에서 나오는 값은 아니고 생성자에서 나옴.
//        Board board = new Board(requestDto);
    // save 함수 안에 board 인스턴스를 넣어주면 자동으로 쿼리가 생성되며 DB에 연결되며 저장됨.
//        return boardRepository.save(board);
    // DB에 저장되지 않은 참조변수 board
//        return board;

    @Transactional
    public BoardResponseDto selectBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 번호의 게시글이 존재하지 않습니다.")
        );
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto, User user) {
        System.out.println("======================================");
        System.out.println("BoardService.selectBoard");
        System.out.println("user.getUsername() = " + user.getUsername());
        System.out.println("======================================");

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );

        // 에외 처리

        board.update(requestDto);
        return new BoardResponseDto(board);
//        // Request 에서 Token 가져오기
//        String token = jwtUtil.resolveToken(request);
//        Claims claims;
//
//        // 토큰이 있는 경우에만 게시판 변경 가능
//        if (token != null) {
//            if (jwtUtil.validateToken(token)) {
//                // 토큰에서 사용자 정보 가져오기
//                claims = jwtUtil.getUserInfoFromToken(token);
//            } else {
//                throw new IllegalArgumentException("Token Error");
//            }
//
//            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
//            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
//                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
//            );
//            Board board = boardRepository.findById(user.getId()).orElseThrow(
//                    () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
//            );
//
//            board.update(requestDto);
//            return new BoardResponseDto(board);
//        } else {
//            return null;
//        }
    }

    @Transactional
    public BoardDeleteDto deleteBoard(Long id, User user) {
        System.out.println("======================================");
        System.out.println("BoardService.selectBoard");
        System.out.println("user.getUsername() = " + user.getUsername());
        System.out.println("======================================");

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다.")
        );

        boardRepository.deleteByid(id);
        return new BoardDeleteDto();
//        // Request 에서 Token 가져오기
//        String token = jwtUtil.resolveToken(request);
//        Claims claims;
//
//
//        // 토큰이 있는 경우에만 게시판 삭제
//        if (token != null) {
//            if (jwtUtil.validateToken(token)) {
//                // 토큰에서 사용자 정보 가져오기
//                claims = jwtUtil.getUserInfoFromToken(token);
//            } else {
//                throw new IllegalArgumentException("Token Error");
//            }
//
//            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
//            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
//                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
//            );
//            Board board = boardRepository.findById(user.getId()).orElseThrow(
//                    () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
//            );
//
//            boardRepository.deleteById(id);
//            return new BoardDeleteDto();
//        } else {
//            return null;
//        }
    }
}