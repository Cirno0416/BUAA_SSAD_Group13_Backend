package com.innoshare.mapper.elasticsearch;

import com.innoshare.model.doc.PaperDoc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PaperRepository extends ElasticsearchRepository<PaperDoc, String> {

    List<PaperDoc> findByInfo(String info, Pageable pageable);

    List<PaperDoc> findByInfoContainingAndSubject(String info, String subject, Pageable pageable);
}
