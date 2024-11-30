package com.innoshare.controller;

import com.innoshare.model.doc.PaperDoc;
import com.innoshare.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("achievements")
    public List<PaperDoc> searchPapers(@RequestParam String query, @RequestParam(required = false) String category,
                                       @RequestParam(required = false) String[] tags, @RequestParam(required = false) String sortBy,
                                       @RequestParam(required = false) String order, @RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer limit) {
        return searchService.search(query, category, tags, sortBy, order, page, limit);
    }
}
