package com.project.web_prj.common.paging;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 페이지 렌더링 정보 생성
@Setter
@Getter
@ToString
public class PageMaker {

    // 한번에 그려낼 페이지 수
    private static final int PAGE_COUNT = 10;

    // 렌더링시 페이지 시작값, 페이지 끝값
    private int beginPage, endPage, finalPage;

    // 이전, 다음 버튼 활성화 여부
    private boolean prev, next;

    private Page page; // 현재 위치한 페이지 정보
    private int totalCount; // 총 게시물 수

    public PageMaker(Page page, int totalCount) {
        this.page = page;
        this.totalCount = totalCount;
        makePageInfo();
    }

    // 페이지 정보 생성 알고리즘
    private void makePageInfo() {

        //1. endPage 계산
        // 올림처리 (현재 위치한 페이지번호 / 한 화면에 배치할 페이지수 ) *  한 화면에 배치할 페이지 수
        this.endPage = (int) (Math.ceil(page.getPageNum() / (double)PAGE_COUNT) * PAGE_COUNT);

        //2. beginPage 계산
        this.beginPage = endPage - PAGE_COUNT + 1;

        /*

        - 총 게시물수가 237개고, 한 화면당 10개의 게시물을 배치하고 있다면
          페이지 구간은

          1 ~ 10페이지 구간 : 게시물 100개
          11 ~ 20페이지 구간: 게시물 100개
          21 ~ 24페이지 구간: 게시물 37개

        - 마지막 페이지 구간에서는 보정이 필요함.

        - 마지막 구간 끝페이지 보정 공식:
          올림처리(총 게시물 수 / 한 페이지당 배치할 게시물 수)

         */
        int realEnd = (int)Math.ceil((double) totalCount / page.getAmount());

        this.finalPage = realEnd;

        // 그러면 끝 페이지보정은 언제 일어나야 하는가
        // 마지막 페이지 구간에서 일어날수도 있고 아닐수도 있다
        if (realEnd < endPage) {
            this.endPage = realEnd;
        }

        // 이전 버튼 활성화 여부
        this.prev = beginPage > 1;

        // 다음 버튼 활성화 여부
        this.next = endPage < realEnd;
    }
}
