package com.project.web_prj.member.domain;

import lombok.*;

import java.util.Date;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private String account;
    private String password;
    private String name;
    private String email;
    private Auth auth;
    private Date regDate;
    private String sessionId;
    private Date limitTime;


    // 아이디 , 패스워드, AutoLogin 상태가 따로 빼두어서 관리하면 유지보수가 된다
    // 그것이 LoginDTO.java 클래스 이다.
}
