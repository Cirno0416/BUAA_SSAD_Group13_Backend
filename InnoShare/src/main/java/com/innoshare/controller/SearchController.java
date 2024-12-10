package com.innoshare.controller;

import com.innoshare.model.doc.PaperDoc;
import com.innoshare.model.doc.PatentDoc;
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
    public List<PaperDoc> searchPapers(@RequestParam String query,
                                       @RequestParam(defaultValue = "") String subject,
                                       @RequestParam(defaultValue = "1") Integer subjectLevel,
                                       @RequestParam(defaultValue = "_score") String sortBy,
                                       @RequestParam(defaultValue = "desc") String order,
                                       @RequestParam(defaultValue = "0") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit) {

        query=query2exp(query);
        return searchService.searchPapers(query, subject, subjectLevel, sortBy, order, page, limit);
    }

    @GetMapping("patents")
    public List<PatentDoc> searchPatents(@RequestParam String query,
                                         @RequestParam(defaultValue = "") String classification,
                                         @RequestParam(defaultValue = "_score") String sortBy,
                                         @RequestParam(defaultValue = "desc") String order,
                                         @RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "10") Integer limit) {

        query=query2exp(query);
        return searchService.searchPatents(query, classification, sortBy, order, page, limit);
    }

    private String query2exp(String query) {
        String[] clauses = query.split(" ");
        StringBuilder stringBuilder=new StringBuilder('\"'+clauses[0]+'\"');
        for(int i=1;i<clauses.length;i++) stringBuilder.append("+").append('\"').append(clauses[i]).append('\"');
        return stringBuilder.toString();
    }

}
