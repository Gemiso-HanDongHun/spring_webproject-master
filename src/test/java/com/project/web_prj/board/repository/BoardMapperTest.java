package com.project.web_prj.board.repository;

import com.project.web_prj.board.domain.Board;
import com.project.web_prj.board.dto.ValidateMemberDTO;
import com.project.web_prj.common.paging.Page;
import com.project.web_prj.common.search.Search;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardMapperTest {

    @Autowired
    BoardMapper mapper;

    @Test
    @DisplayName("제목으로 검색된 목록을 조회해야 한다.")
    void searchByTitleTest() {

        Search search = new Search(
                            "tc"
                            , "ㅋㅋ"
                        );

        mapper.findAll2(search).forEach(System.out::println);
    }

    @Test
    @DisplayName("300개의 게시물을 삽입해야 한다.")
    void bulkInsert() {

        Board board;
        for (int i = 1; i <= 300; i++) {
            board = new Board();
            board.setTitle("제목" + i);
            board.setWriter("길동이" + i);
            board.setContent("안녕하세요요요요요요요~~" + i);
            board.setAccount("hello");
            mapper.save(board);
        }
    }


    @Test
    @DisplayName("특정 게시물에 첨부된 파일경로들을 조회한다.")
    void findFileNamesTest() {
        //given
        Long bno = 304L;

        //when
        List<String> fileNames = mapper.findFileNames(bno);

        //then
        fileNames.forEach(System.out::println);
        assertEquals(2, fileNames.size());

    }

    @Test
    @DisplayName("게시물 번호로 글쓴이의 계정명과 권한정보를 가져와야 한다.")
    void findMemberTest() {

        //given
        Long boardNo = 310L;

        //when
        ValidateMemberDTO member = mapper.findMemberByBoardNo(boardNo);

        //then
        System.out.println("member = " + member);
        assertEquals("frodo", member.getAccount());
        assertEquals("COMMON", member.getAuth().toString());
    }

}