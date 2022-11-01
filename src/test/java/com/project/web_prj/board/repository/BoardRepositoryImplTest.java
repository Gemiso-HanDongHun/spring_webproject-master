package com.project.web_prj.board.repository;

import com.project.web_prj.board.domain.Board;
import com.project.web_prj.common.paging.Page;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryImplTest {

    @Autowired
//    @Qualifier("bri")
    BoardRepository repository;

    @Test
    @DisplayName("300개의 게시물을 삽입해야 한다.")
    void bulkInsert() {

        Board board;
        for (int i = 1; i <= 300; i++) {
            board = new Board();
            board.setTitle("제목" + i);
            board.setWriter("길동이" + i);
            board.setContent("안녕하세요요요요요요요~~" + i);
            repository.save(board);
        }
    }

    @Test
    @DisplayName("전체 게시물을 조회하고 반환된 리스트의 사이즈는 300이어야 한다.")
    void findAllTest() {

        List<Board> boardList = repository.findAll();
        boardList.forEach(b -> System.out.println(b));

        assertEquals(300, boardList.size());
    }

    @Test
    @DisplayName("특정 게시물을 조회하고 글제목이 일치해야 한다.")
    void findOneTest() {

        Board board = repository.findOne(300L);
        System.out.println("board = " + board);

        assertEquals("제목300", board.getTitle());
    }

    @Test
    @DisplayName("특정 게시물을 삭제하고 해당 글이 조회되지 않아야 한다.")
    @Transactional
    @Rollback
    void removeTest() {

        boolean remove = repository.remove(300L);
        assertTrue(remove);

        assertThrows(DataAccessException.class
                , () -> repository.findOne(300L));

    }

    @Test
    @DisplayName("특정 게시물을 수정하고 해당 글을 조회했을 때 수정된 제목이 일치해야 한다.")
    @Transactional
    @Rollback
    void modifyTest() {

        //given
        Board newBoard = new Board();
        newBoard.setBoardNo(300L);
        newBoard.setTitle("수정된 제목");
        newBoard.setWriter("수정된 작성자");
        newBoard.setContent("메롱메롱");

        //when
        boolean modify = repository.modify(newBoard);
        Board board = repository.findOne(newBoard.getBoardNo());

        //then
        assertTrue(modify);
        assertEquals("수정된 제목", board.getTitle());
        assertEquals("수정된 작성자", board.getWriter());
    }

    @Test
    @DisplayName("전체 게시물 수를 조회해야 한다.")
    void countTest() {
        int totalCount = repository.getTotalCount();
        assertTrue(totalCount == 300);
    }

    @Test
    @DisplayName("원하는 페이지수와 게시물양에 따라 게시물 목록을 조회해야 한다.")
    void pagingTest() {
        Page page = new Page(1, 20);

        repository.findAll(page).forEach(System.out::println);
    }

}