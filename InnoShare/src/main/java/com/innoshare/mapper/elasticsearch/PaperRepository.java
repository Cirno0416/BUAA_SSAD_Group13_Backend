package com.innoshare.mapper.elasticsearch;

import com.innoshare.model.doc.PaperDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PaperRepository extends ElasticsearchRepository<PaperDoc, String> {

}
