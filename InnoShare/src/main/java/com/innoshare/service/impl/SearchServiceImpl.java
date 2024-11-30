package com.innoshare.service.impl;

import com.innoshare.mapper.elasticsearch.PaperRepository;
import com.innoshare.model.doc.PaperDoc;
import com.innoshare.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final PaperRepository paperRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<PaperDoc> search(String query, String category, String[] tags, String sortBy, String order, Integer page, Integer limit) {
        int pageNumber = (page != null && page!=0) ? page : 0;
        int pageSize = (limit != null && limit!=0)? limit : 10;
        Sort sort=getSort(sortBy, order);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return paperRepository.findByInfo(query, pageable);
    }

    private Sort getSort(String sortBy, String order) {
        String sortField=(sortBy==null||sortBy.isEmpty()||sortBy.equals("relevance"))?"_score":sortBy;
        return (order==null||order.isEmpty()||order.equals("desc"))?Sort.by(Sort.Order.desc(sortField)):Sort.by(Sort.Order.asc(sortField));
    }

}
