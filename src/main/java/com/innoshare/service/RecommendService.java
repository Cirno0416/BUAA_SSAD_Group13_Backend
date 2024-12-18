package com.innoshare.service;

import com.innoshare.common.Response;
import com.innoshare.service.impl.RecommendServiceImpl;

public interface RecommendService {
    RecommendServiceImpl.RecommendReturn getHotPaper(Integer page, Integer limit);
    RecommendServiceImpl.RecommendReturn getNewPaper(Integer page, Integer limit);
    RecommendServiceImpl.RecommendReturn getRecommendPaper(Integer page, Integer limit);
}
