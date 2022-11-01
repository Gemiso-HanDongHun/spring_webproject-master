package com.project.web_prj.reply.domain;

import lombok.*;

import java.util.Date;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply {

    private Long replyNo; //댓글번호
    private String replyText; //댓글내용
    private String replyWriter; //댓글작성자
    private Date replyDate; //작성일자
    private Long boardNo; //원본 글번호
    private String account; // 작성자 아이디
}
