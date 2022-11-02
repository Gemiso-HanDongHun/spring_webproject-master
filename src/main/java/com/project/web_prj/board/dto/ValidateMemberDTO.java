package com.project.web_prj.board.dto;

import com.project.web_prj.member.domain.Auth;
import lombok.*;

@Getter @Setter @ToString
//@NoArgsConstructor
@AllArgsConstructor
public class ValidateMemberDTO {

    private String account;
    private Auth auth;

    
    
    // get account를 학되면 no가 발생한다
    public ValidateMemberDTO() {
        account = "no";
        auth = Auth.COMMON;
    }
}
