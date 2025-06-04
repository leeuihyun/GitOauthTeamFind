package com.hyun.oauthboard.service;

import com.hyun.oauthboard.domain.dto.board.BoardCreateRequestDto;
import com.hyun.oauthboard.domain.dto.board.BoardResponse;
import com.hyun.oauthboard.domain.entity.Board;
import com.hyun.oauthboard.domain.entity.Member;
import com.hyun.oauthboard.enums.BoardError;
import com.hyun.oauthboard.enums.MemberError;
import com.hyun.oauthboard.exception.CustomException;
import com.hyun.oauthboard.jwt.JwtPayload;
import com.hyun.oauthboard.repository.BoardRepository;
import com.hyun.oauthboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @PreAuthorize("@securityAuthorizationBoard.checkOwner(jwtPayload.memberId, #boardCreateRequestDto.boardId)")
    @Transactional
    @Override
    public BoardResponse createBoard(JwtPayload jwtPayload,
        BoardCreateRequestDto boardCreateRequestDto) {
        Member member = memberRepository.findById(jwtPayload.getMemberId())
            .orElseThrow(() -> new CustomException(MemberError.MEMBER_ID_NOT_EXIST));

        return boardRepository.save(boardCreateRequestDto.toEntity(member)).toDto();
    }

    @PreAuthorize("@securityAuthorizationBoard.checkOwner(jwtPayload.memberId, #boardCreateRequestDto.boardId)")
    @Transactional
    @Override
    public BoardResponse updateBoard(JwtPayload jwtPayload,
        BoardCreateRequestDto boardCreateRequestDto) {

        Board board = boardRepository.findById(
                boardCreateRequestDto.getBoardId())
            .orElseThrow(() -> new CustomException(BoardError.BOARD_ID_NOT_EXIST));
        board.updateBoard(boardCreateRequestDto);
        return board.toDto();
    }

    @Transactional
    @Override
    public void deleteBoard(JwtPayload jwtPayload, Long boardId) {
        Member member = memberRepository.findById(jwtPayload.getMemberId())
            .orElseThrow(() -> new CustomException(MemberError.MEMBER_ID_NOT_EXIST));

        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new CustomException(BoardError.BOARD_ID_NOT_EXIST));

        if (!member.getMemberId().equals(board.getMember().getMemberId())) {
            throw new CustomException(MemberError.MEMBER_ID_MISMATCH);
        }

        boardRepository.delete(board);
    }
}
