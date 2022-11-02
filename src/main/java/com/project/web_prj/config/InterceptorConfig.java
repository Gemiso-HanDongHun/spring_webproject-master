package com.project.web_prj.config;

import com.project.web_prj.interceptor.AfterLoginInterceptor;
import com.project.web_prj.interceptor.AutoLoginInterceptor;
import com.project.web_prj.interceptor.BoardInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 다양한 인터셉터들을 관리하는 설정 클래스   --> 인터셉터를 등록하는 페키지가 필요하여 interceptor 패키지를 생성해준다
@Configuration  // 이것으로 설정파일임을 알려준다
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final BoardInterceptor boardInterceptor;
    private final AfterLoginInterceptor afterLoginInterceptor;
    private final AutoLoginInterceptor autoLoginInterceptor;

    // 인터셉터 설정 추가 메서드
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 게시판 인터셉터 설정
        registry.addInterceptor(boardInterceptor)
                .addPathPatterns("/board/*")
                .excludePathPatterns("/board/list", "/board/content");

        // 애프터 로그인 인터셉터 설정
        registry.addInterceptor(afterLoginInterceptor)
                .addPathPatterns("/member/sign-in", "/member/sign-up");

        // 자동 로그인 인터셉터 설정
        registry.addInterceptor(autoLoginInterceptor)
                .addPathPatterns("/**");

    }
}
