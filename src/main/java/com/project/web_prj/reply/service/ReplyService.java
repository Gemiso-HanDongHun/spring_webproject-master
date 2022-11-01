package com.project.web_prj.reply.service;

import com.project.web_prj.common.paging.Page;
import com.project.web_prj.common.paging.PageMaker;
import com.project.web_prj.reply.domain.Reply;
import com.project.web_prj.reply.repository.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyMapper replyMapper;

    //댓글 목록 조회
    public Map<String, Object> getList(Long boardNo, Page page) {
        PageMaker maker
                = new PageMaker(page, getCount(boardNo));

        Map<String, Object> replyMap = new HashMap<>();
        replyMap.put("replyList", replyMapper.findAll(boardNo, page));
        replyMap.put("maker", maker);
        replyMap.put("count", replyMapper.getReplyCount(boardNo));

        return replyMap;
    }
    //댓글 총 개수 조회
    public int getCount(Long boardNo) {
        return replyMapper.getReplyCount(boardNo);
    }
    //댓글 개별 조회
    public Reply get(Long replyNo) {
        return replyMapper.findOne(replyNo);
    }

    //댓글 쓰기 중간처리
    public boolean write(Reply reply) {
        return replyMapper.save(reply);
    }

    //댓글 수정 중간처리
    public boolean modify(Reply reply) {
        return replyMapper.modify(reply);
    }
    //댓글 삭제 중간처리
    public boolean remove(Long replyNo) {
        return replyMapper.remove(replyNo);
    }
}
