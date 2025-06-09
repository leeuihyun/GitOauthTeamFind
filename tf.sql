DROP DATABASE IF EXISTS githubprj;
CREATE DATABASE IF NOT EXISTS githubprj;
use githubprj;

CREATE TABLE member
(
    member_id     BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '깃허브 사용자 식별자',
    member_name   VARCHAR(30)  NOT NULL COMMENT '깃허브 사용자 이름',
    member_avatar VARCHAR(100) NOT NULL COMMENT '깃허브 사용자 이미지',
    created_at    DATETIME COMMENT '생성일'
);

CREATE TABLE board
(
    board_id      BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '게시판 식별자',
    member_id     BIGINT COMMENT '게시판 작성자 식별자',
    board_title   VARCHAR(100) NOT NULL COMMENT '게시판 제목',
    board_content TEXT         NOT NULL COMMENT '게시판 내용',
    created_at    DATETIME COMMENT '생성일',
    updated_at    DATETIME COMMENT '수정일',

    FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE
);

CREATE TABLE board_view
(
    view_id    BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '게시판 뷰 식별자',
    viewer_id  BIGINT COMMENT '게시판 뷰어 식별자',
    board_id   BIGINT COMMENT '게시판 식별자',
    created_at DATETIME COMMENT '생성일',

    FOREIGN KEY (viewer_id) REFERENCES member (member_id) ON DELETE CASCADE,
    FOREIGN KEY (board_id) REFERENCES board (board_id) ON DELETE CASCADE
);

CREATE TABLE board_like
(
    like_id    BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '게시판 좋아요 식별자',
    liker_id   BIGINT COMMENT '좋아요 누른 사람 식별자',
    board_id   BIGINT COMMENT '게시판 식별자',
    created_at DATETIME COMMENT '생성일',

    FOREIGN KEY (liker_id) REFERENCES member (member_id) ON DELETE CASCADE,
    FOREIGN KEY (board_id) REFERENCES board (board_id) ON DELETE CASCADE
);