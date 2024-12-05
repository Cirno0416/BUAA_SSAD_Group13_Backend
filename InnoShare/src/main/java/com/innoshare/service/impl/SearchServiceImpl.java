package com.innoshare.service.impl;

import com.innoshare.mapper.elasticsearch.PaperRepository;
import com.innoshare.model.doc.PaperDoc;
import com.innoshare.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final PaperRepository paperRepository;

    @Override
    public List<PaperDoc> search(String query, String category, String sortBy, String order, Integer page, Integer limit) {
        Sort sort=order.equals("desc")?Sort.by(Sort.Order.desc(sortBy)):Sort.by(Sort.Order.asc(sortBy));
        Pageable pageable = PageRequest.of(page, limit, sort);

        return category.isEmpty()?paperRepository.findByInfo(query, pageable):paperRepository.findByInfoContainingAndSubject(query, category, pageable);
    }


}
