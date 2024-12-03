//package com.innoshare;
//
//
//import com.innoshare.mapper.elasticsearch.PaperRepository;
//import com.innoshare.model.doc.PaperDoc;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//@SpringBootTest
//public class ElasticSearchTest {
//    @Autowired
//    private PaperRepository paperRepository;
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Test
//    public void importPapersToElasticsearch() {
//        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM papers");
//
//        for (Map<String, Object> row : rows) {
//            PaperDoc paper = new PaperDoc();
//            paper.setPaper_id((Integer)row.get("paper_id"));
//            paper.setUser_id((Integer)row.get("user_id"));
//            paper.setTitle((String)row.get("title"));
//            paper.setAuthor((String)row.get("author"));
//            paper.setAbstract_text((String)row.get("abstract_text"));
//            paper.setKeywords((String)row.get("keywords"));
//            paper.setFile_path((String)row.get("file_path"));
//            paper.setPublished_at((Date)row.get("published_at"));
//            paper.setCreated_at((Date)row.get("created_at"));
//            paper.setUpdated_at((Date)row.get("updated_at"));
//            paper.setDoi((String)row.get("doi"));
//            paper.setDownload_url((String)row.get("download_url"));
//            paperRepository.save(paper); // 将数据保存到 Elasticsearch
//        }
//    }
//
//    @Test
//    public void searchTest() {
//        System.out.println(paperRepository.findByInfo("ai",PageRequest.of(0, 10)));
//        //PageRequest.of(1, 10, Sort.by(Sort.Order.desc("_score")))
//    }
//}