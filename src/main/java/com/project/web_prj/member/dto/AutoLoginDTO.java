package com.project.web_prj.member.dto;

import lombok.*;

import java.util.Date;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class AutoLoginDTO {

    private String account;
    private String sessionId;
    private Date limitTime;
}
