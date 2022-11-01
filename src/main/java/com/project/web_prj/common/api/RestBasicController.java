package com.project.web_prj.common.api;

import com.project.web_prj.board.domain.Board;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

// jsp 뷰포워딩을 하지않고 클라이언트에게 JSON데이터를 전송함
@RestController
@Log4j2
public class RestBasicController {

    @GetMapping("/api/hello")
    public String hello() {
        return "hello!!!";
    }
    @GetMapping("/api/board")
    public Board board() {
        Board board = new Board();
        board.setBoardNo(10L);
        board.setContent("할룽~");
        board.setTitle("메룽~~");
        board.setWriter("박영희");


        return board;
    }

    @GetMapping("/api/arr")
    public String[] arr() {
        String[] foods = {"짜장면", "레몬에이드", "볶음밥"};
        return foods;
    }

    // post 요청처리
    @PostMapping("/api/join")
    public String join(@RequestBody List<String> info) {
        log.info("/api/join POST!! - {}", info);
        return "POST-OK";
    }
    // put 요청처리
    @PutMapping("/api/join")
    public String joinPut(@RequestBody Board board) {
        log.info("/api/join PUT!! - {}", board);
        return "PUT-OK";
    }
    // delete 요청처리
    @DeleteMapping("/api/join")
    public String joinDelete() {
        log.info("/api/join DELETE!!");
        return "DEL-OK";
    }


    // RestController에서 뷰포워딩하기
    @GetMapping("/hoho")
    public ModelAndView hoho() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }

}
