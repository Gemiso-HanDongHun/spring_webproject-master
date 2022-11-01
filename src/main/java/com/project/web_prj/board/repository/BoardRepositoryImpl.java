package com.project.web_prj.board.repository;

import com.project.web_prj.board.domain.Board;
import com.project.web_prj.common.paging.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bri")
@Log4j2
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final JdbcTemplate template;

    @Override
    public boolean save(Board board) {
        log.info("save process with jdbc - {}", board);
        String sql = "INSERT INTO tbl_board " +
                    "(board_no, writer, title, content) " +
                    "VALUES (seq_tbl_board.nextval, ?, ?, ?)";
        return template.update(sql
                , board.getWriter()
                , board.getTitle()
                , board.getContent()
        ) == 1;
    }

    @Override
    public List<Board> findAll() {
        return null;
    }

//    @Override
//    public List<Board> findAll() {
//        String sql = "SELECT  *\n" +
//                    "FROM (SELECT ROWNUM rn, v_board.*\n" +
//                    "        FROM (\n" +
//                    "                SELECT *\n" +
//                    "                FROM tbl_board\n" +
//                    "                ORDER BY board_no DESC\n" +
//                    "                ) v_board)\n" +
//                    "WHERE rn BETWEEN 1 AND 10";
//        return template.query(sql, (rs, rn) -> new Board(rs));
//    }

    @Override
    public List<Board> findAll(Page page) {

        /*
            만약에 1페이지를 보고 싶고 10개씩 보고 싶으면
            -> BETWEEN 1 AND 10
            만약에 2페이지를 보고 싶고 10개씩 보고 싶으면
            -> BETWEEN 11 AND 20

            만약에 1페이지를 보고 싶고 20개씩 보고 싶으면
            -> BETWEEN 1 AND 20
            만약에 2페이지를 보고 싶고 20개씩 보고 싶으면
            -> BETWEEN 21 AND 40

            공식 :  BETWEEN [ (pageNum - 1) * amount + 1 ]
                            AND [ pageNum * amount ]

         */

        String sql = "SELECT  *\n" +
                "FROM (SELECT ROWNUM rn, v_board.*\n" +
                "        FROM (\n" +
                "                SELECT *\n" +
                "                FROM tbl_board\n" +
                "                ORDER BY board_no DESC\n" +
                "                ) v_board)\n" +
                "WHERE rn BETWEEN ? AND ?";
        return template.query(sql, (rs, rn) -> new Board(rs)
                , (page.getPageNum() - 1) * page.getAmount() + 1
                , page.getPageNum() * page.getAmount()
        );
    }

    @Override
    public Board findOne(Long boardNo) {

        String sql = "SELECT * FROM tbl_board " +
                "WHERE board_no=?";
        return template.queryForObject(sql, (rs, rn) -> new Board(rs), boardNo);
    }

    @Override
    public boolean remove(Long boardNo) {
        String sql = "DELETE FROM tbl_board WHERE board_no=?";
        return template.update(sql, boardNo) == 1;
    }

    @Override
    public boolean modify(Board board) {
        String sql = "UPDATE tbl_board " +
                "SET writer = ?, title=?, content=? " +
                "WHERE board_no=?";
        return template.update(sql, 
                board.getWriter(), 
                board.getTitle(), 
                board.getContent(),
                board.getBoardNo()
                ) == 1;
    }

    @Override
    public int getTotalCount() {
        String sql = "SELECT COUNT(*) AS cnt FROM tbl_board";
        return template.queryForObject(sql, Integer.class);
    }

    @Override
    public void upViewCount(Long boardNo) {
        String sql = "UPDATE tbl_board " +
                    "SET view_cnt = view_cnt + 1 " +
                    "WHERE board_no=?";
        template.update(sql, boardNo);
    }
}
