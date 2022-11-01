package com.project.web_prj.interceptor;

import com.project.web_prj.member.domain.Member;
import com.project.web_prj.member.repository.MemberMapper;
import com.project.web_prj.util.LoginUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.project.web_prj.util.LoginUtils.*;

@Configuration
@RequiredArgsConstructor
public class AutoLoginInterceptor implements HandlerInterceptor {

    private final MemberMapper memberMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1. 자동로그인 쿠키 탐색
        Cookie c = getAutoLoginCookie(request);

        //2. 자동로그인 쿠키가 발견될 경우 쿠키값을 읽어서 세션아이디를 확인
        if (c != null) {
            String sessionId = c.getValue();

            //3. 쿠키에 저장된 세션아이디와 같은 값을 가진 회원정보 조회
            Member member = memberMapper.findMemberBySessionId(sessionId);

            if (member != null) {
                // 4. 세션에 해당 회원정보를 저장
                request.getSession().setAttribute(LOGIN_FLAG, member);
            }
        }
        return true;
    }
}
