package com.innoshare.service.impl;

import com.innoshare.common.Response;
import com.innoshare.mapper.RecommendMapper;
import com.innoshare.model.po.Paper;
import com.innoshare.service.RecommendService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {
    private final RecommendMapper recommendMapper;
    @Data
    public  class RecommendReturn{
        private int limit;
        private int page;
        private List<Paper> papers;
        public RecommendReturn(int limit,int page,List<Paper> papers){
            this.limit=limit;
            this.page=page;
            this.papers=papers;
        }
    }
    @Override
    public RecommendReturn getHotPaper(Integer page, Integer limit) {
        List<Paper> hotPapers = recommendMapper.getHotPaper(limit);
        RecommendReturn recommendReturn = new RecommendReturn(limit,page,hotPapers);
        return recommendReturn;
    }
}
