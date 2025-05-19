package com.hyun.oauthboard.service;

import com.hyun.oauthboard.domain.dto.BoardCreateRequestDto;
import com.hyun.oauthboard.domain.entity.Board;
import com.hyun.oauthboard.domain.entity.Member;
import com.hyun.oauthboard.enums.BoardError;
import com.hyun.oauthboard.enums.MemberError;
import com.hyun.oauthboard.exception.CustomException;
import com.hyun.oauthboard.repository.BoardRepository;
import com.hyun.oauthboard.repository.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    public BoardServiceImpl(BoardRepository boardRepository, MemberRepository memberRepository) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    @Override
    public Board createBoard(Authentication authentication,
        BoardCreateRequestDto boardCreateRequestDto) {
        Member member = memberRepository.findById((long) Integer.parseInt(authentication.getName()))
            .orElseThrow(() -> new CustomException(MemberError.MEMBER_ID_NOT_EXIST));

        return boardRepository.save(boardCreateRequestDto.toEntity(member));
    }

    @Transactional
    @Override
    public Board updateBoard(Authentication authentication,
        BoardCreateRequestDto boardCreateRequestDto) {
        Member member = memberRepository.findById(
                (long) Integer.parseInt(authentication.getName()))
            .orElseThrow(() -> new CustomException(MemberError.MEMBER_ID_NOT_EXIST));

        Board board = boardRepository.findById(
                boardCreateRequestDto.getBoardId())
            .orElseThrow(() -> new CustomException(BoardError.BOARD_ID_NOT_EXIST));

        if (!member.getMemberId().equals(board.getMember().getMemberId())) {
            throw new CustomException(MemberError.MEMBER_ID_MISMATCH);
        }

        board.updateBoard(boardCreateRequestDto);
        return board;

    }

    @Transactional
    @Override
    public void deleteBoard(Authentication authentication, Long boardId) {
        Member member = memberRepository.findById(
                (long) Integer.parseInt(authentication.getName()))
            .orElseThrow(() -> new CustomException(MemberError.MEMBER_ID_NOT_EXIST));

        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new CustomException(BoardError.BOARD_ID_NOT_EXIST));

        if (!member.getMemberId().equals(board.getMember().getMemberId())) {
            throw new CustomException(MemberError.MEMBER_ID_MISMATCH);
        }

        boardRepository.delete(board);
    }
}
