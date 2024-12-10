package com.innoshare.mapper.elasticsearch;

import com.innoshare.model.doc.PatentDoc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PatentRepository extends ElasticsearchRepository<PatentDoc, String> {

}
