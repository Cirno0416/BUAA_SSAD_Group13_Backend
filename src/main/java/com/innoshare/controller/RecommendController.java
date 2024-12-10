package com.innoshare.controller;

import com.innoshare.common.Response;
import com.innoshare.service.RecommendService;
import com.innoshare.service.impl.RecommendServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/recommendations")
@RequiredArgsConstructor
public class RecommendController {
    private final RecommendService recommendService;

    //获取热门文章
    @GetMapping("hot")
    public RecommendServiceImpl.RecommendReturn getHotPaper(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return recommendService.getHotPaper(page, limit);
    }
}
