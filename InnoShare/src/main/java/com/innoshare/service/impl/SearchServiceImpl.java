package com.innoshare.service.impl;

import com.innoshare.mapper.elasticsearch.PatentRepository;
import com.innoshare.model.doc.PaperDoc;
import com.innoshare.model.doc.PatentDoc;
import com.innoshare.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final PatentRepository patentRepository;

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;


    @Override
    public List<PaperDoc> searchPapers(String query, String subject, Integer subjectLevel,
                                 String sortBy, String order, Integer page, Integer limit) {

        Sort sort=order.equals("desc")?Sort.by(Sort.Order.desc(sortBy)):Sort.by(Sort.Order.asc(sortBy));
        Pageable pageable = PageRequest.of(page, limit, sort);

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.matchQuery("info", query));

        if(!subject.isEmpty()){
            if(subjectLevel==1) boolQueryBuilder.must(QueryBuilders.termQuery("subject1", subject));
            else boolQueryBuilder.must(QueryBuilders.termQuery("subject2", subject));
        }

        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        nativeSearchQueryBuilder.withPageable(pageable);
        return elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), PaperDoc.class)
                .getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    @Override
    public List<PatentDoc> searchPatents(String query, String classification, String sortBy,
                                         String order, Integer page, Integer limit) {

        Sort sort=order.equals("desc")?Sort.by(Sort.Order.desc(sortBy)):Sort.by(Sort.Order.asc(sortBy));
        Pageable pageable = PageRequest.of(page, limit, sort);

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.matchQuery("title", query));
        if(!classification.isEmpty()) boolQueryBuilder.must(QueryBuilders.termQuery("classification", classification));
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        nativeSearchQueryBuilder.withPageable(pageable);

        return elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), PatentDoc.class)
                .getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
