package com.project.web_prj.board.repository;

import com.project.web_prj.board.domain.Board;
import com.project.web_prj.common.paging.Page;

import java.util.List;

public interface BoardRepository {

    // 게시글 쓰기 기능
    boolean save(Board board);

    List<Board> findAll();

    // 게시글 전체 조회 with paging
    List<Board> findAll(Page page);

    // 게시글 상세 조회
    Board findOne(Long boardNo);

    // 게시글 삭제
    boolean remove(Long boardNo);

    // 게시글 수정
    boolean modify(Board board);

    // 전체 게시물 수 조회
    int getTotalCount();

    // 조회수 상승 처리
    void upViewCount(Long boardNo);
}
