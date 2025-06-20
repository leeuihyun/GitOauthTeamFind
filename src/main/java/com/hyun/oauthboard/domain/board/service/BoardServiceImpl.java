package com.hyun.oauthboard.domain.board.service;

import com.hyun.oauthboard.domain.board.dto.BoardCreateRequestDto;
import com.hyun.oauthboard.domain.board.dto.BoardIdReponse;
import com.hyun.oauthboard.domain.board.dto.BoardResponse;
import com.hyun.oauthboard.domain.board.entity.Board;
import com.hyun.oauthboard.domain.board.repository.BoardRepository;
import com.hyun.oauthboard.domain.member.entity.Member;
import com.hyun.oauthboard.domain.member.repository.MemberRepository;
import com.hyun.oauthboard.enums.BoardError;
import com.hyun.oauthboard.enums.MemberError;
import com.hyun.oauthboard.exception.CustomException;
import com.hyun.oauthboard.jwt.JwtPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public BoardResponse readBoard(JwtPayload jwtPayload, Long boardId) {

        return boardRepository.findBoardLeftJoinViewsAndLikes(boardId);
    }

    @Override
    @Transactional
    @PreAuthorize("@securityAuthorizationBoard.checkOwner(jwtPayload.memberId, #boardCreateRequestDto.boardId)")
    public BoardIdReponse createBoard(JwtPayload jwtPayload,
        BoardCreateRequestDto boardCreateRequestDto) {

        Member member = memberRepository.findById(jwtPayload.getMemberId())
            .orElseThrow(() -> new CustomException(MemberError.MEMBER_ID_NOT_EXIST));

        return new BoardIdReponse(
            boardRepository.save(boardCreateRequestDto.toEntity(member)).getBoardId());
    }


    @Override
    @Transactional
    @PreAuthorize("@securityAuthorizationBoard.checkOwner(jwtPayload.memberId, #boardCreateRequestDto.boardId)")
    public BoardIdReponse updateBoard(JwtPayload jwtPayload,
        BoardCreateRequestDto boardCreateRequestDto) {

        Board board = boardRepository.findById(
                boardCreateRequestDto.getBoardId())
            .orElseThrow(() -> new CustomException(BoardError.BOARD_ID_NOT_EXIST));

        board.updateBoard(boardCreateRequestDto);

        return new BoardIdReponse(board.getBoardId());
    }


    @Override
    @Transactional
    @PreAuthorize("@securityAuthorizationBoard.checkOwner(jwtPayload.memberId, boardId)")
    public void deleteBoard(JwtPayload jwtPayload, Long boardId) {

        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new CustomException(BoardError.BOARD_ID_NOT_EXIST));

        boardRepository.delete(board);
    }
}
