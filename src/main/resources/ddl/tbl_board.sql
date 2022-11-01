

CREATE SEQUENCE seq_tbl_board;

DROP TABLE tbl_board;
CREATE TABLE tbl_board (
    board_no NUMBER(10),
    writer VARCHAR2(20) NOT NULL,
    title VARCHAR2(200) NOT NULL,
    content CLOB,
    view_cnt NUMBER(10) DEFAULT 0,
    reg_date DATE DEFAULT SYSDATE,
    CONSTRAINT pk_tbl_board PRIMARY KEY (board_no)
);



DROP TABLE tbl_board;
CREATE TABLE tbl_board (
    board_no INT(10) AUTO_INCREMENT,
    writer VARCHAR(20) NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    view_cnt INT(10) DEFAULT 0,
    reg_date DATETIME DEFAULT current_timestamp,
    CONSTRAINT pk_tbl_board PRIMARY KEY (board_no)
);