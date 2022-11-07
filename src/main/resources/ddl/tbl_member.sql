

-- 회원 관리 테이블
CREATE TABLE tbl_member (
    account VARCHAR(50),
    password VARCHAR(150) NOT NULL,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    auth VARCHAR(20) DEFAULT 'COMMON',
    reg_date DATE DEFAULT SYSDATE,
    CONSTRAINT pk_member PRIMARY KEY (account)
);

ALTER TABLE tbl_board ADD account VARCHAR(50) NOT NULL;   -- 회원이랑 게시판, 회원이랑 댓글관의 관계를 설정한다.
ALTER TABLE tbl_reply ADD account VARCHAR(50) NOT NULL;   --  즉, 회원 한명이 여러개의 댓글 생성이 가능해진다


SELECT * FROM tbl_member;

ALTER TABLE tbl_member ADD session_id VARCHAR(200) DEFAULT 'none';
ALTER TABLE tbl_member ADD limit_time DATETIME;



-- 회원 관리 테이블
CREATE TABLE tbl_member (
    account VARCHAR(50),
    password VARCHAR(150) NOT NULL,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    auth VARCHAR(20) DEFAULT 'COMMON',
    reg_date DATETIME DEFAULT current_timestamp,
    CONSTRAINT pk_member PRIMARY KEY (account)
);

ALTER TABLE tbl_board ADD account VARCHAR(50) NOT NULL;
ALTER TABLE tbl_reply ADD account VARCHAR(50) NOT NULL;


SELECT * FROM tbl_member;

ALTER TABLE tbl_member ADD session_id VARCHAR(200) DEFAULT 'none';
ALTER TABLE tbl_member ADD limit_time DATETIME;
