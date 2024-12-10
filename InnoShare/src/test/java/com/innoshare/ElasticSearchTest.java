//package com.innoshare;
//
//import com.innoshare.mapper.elasticsearch.PaperRepository;
//import com.innoshare.mapper.elasticsearch.PatentRepository;
//import com.innoshare.model.doc.PaperDoc;
//import com.innoshare.model.doc.PatentDoc;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.jdbc.core.JdbcTemplate;
//import java.util.*;
//
//@SpringBootTest
//public class ElasticSearchTest {
//    @Autowired
//    private PaperRepository paperRepository;
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    @Qualifier("patentRepository")
//    @Autowired
//    private PatentRepository patentRepository;
//
//    @Test
//    public void importPapersToElasticsearch() {
//        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM papers");
//
//
////        int i=0;
////        for(Map<String, Object> row : rows) {
////            i++;
////
////            String subject = (String) row.get("subject");
////            String subject2=subject.split("[()]")[0];
////            String subject1=subject.split("[()]")[1].split("\\.")[0];
////            System.out.println(subject1+"   "+subject2);
////
////            if(i==100)break;
////        }
//
//
//        PaperDoc paper = new PaperDoc();
//        Map<String, Object> row0 = rows.get(0);
//        paper.setDoi((String)row0.get("doi"));
//        paper.setPaper_id((Integer)row0.get("paper_id"));
//        paper.setUser_id((Integer)row0.get("user_id"));
//        paper.setTitle((String)row0.get("title"));
//        paper.setAuthor((String)row0.get("author"));
//        paper.setAbstract_text((String)row0.get("abstract_text"));
//
//        paper.setFile_path((String)row0.get("file_path"));
//        paper.setPublished_at((Date)row0.get("published_at"));
//        paper.setCreated_at((Date)row0.get("created_at"));
//        paper.setUpdated_at((Date)row0.get("updated_at"));
//        paper.setDownload_url((String)row0.get("download_url"));
//
//        ArrayList<String> subjects1=new ArrayList<>();
//        ArrayList<String> subjects2=new ArrayList<>();
//        String subject = (String) row0.get("subject");
//        String subject2=subject.split("[()]")[0];
//        subject2=subject2.substring(0, subject2.length()-1);
//        String subject1=subject.split("[()]")[1].split("\\.")[0];
//        subjects1.add(subject1);
//        subjects2.add(subject2);
//
//
//        for (int i=1;i<rows.size();i++) {
//            Map<String, Object> row = rows.get(i);
//
//            String doi=(String)row.get("doi");
//            if(doi.equals(paper.getDoi())){
//
//                subject = (String) row.get("subject");
//                subject2=subject.split("[()]")[0];
//                subject2=subject2.substring(0, subject2.length()-1);
//                subject1=subject.split("[()]")[1].split("\\.")[0];
//                if(!subjects1.contains(subject1)) subjects1.add(subject1);
//                if(!subjects2.contains(subject2)) subjects2.add(subject2);
//                continue;
//            }
//
//            paper.setSubject1(subjects1);
//            paper.setSubject2(subjects2);
//            System.out.println(paper);
//            System.out.println(i);
//            System.out.println();
//            paperRepository.save(paper); // 将数据保存到 Elasticsearch
//
//            paper = new PaperDoc();
//            paper.setDoi((String)row.get("doi"));
//            paper.setPaper_id((Integer)row.get("paper_id"));
//            paper.setUser_id((Integer)row.get("user_id"));
//            paper.setTitle((String)row.get("title"));
//            paper.setAuthor((String)row.get("author"));
//            paper.setAbstract_text((String)row.get("abstract_text"));
//
//            paper.setFile_path((String)row.get("file_path"));
//            paper.setPublished_at((Date)row.get("published_at"));
//            paper.setCreated_at((Date)row.get("created_at"));
//            paper.setUpdated_at((Date)row.get("updated_at"));
//            paper.setDownload_url((String)row.get("download_url"));
//
//            subjects1.clear();
//            subjects2.clear();
//            subject = (String) row0.get("subject");
//            subject2=subject.split("[()]")[0];
//            subject2=subject2.substring(0, subject2.length()-1);
//            subject1=subject.split("[()]")[1].split("\\.")[0];
//            subjects1.add(subject1);
//            subjects2.add(subject2);
//        }
//    }
//
//    @Test
//    public void importPatentsToElasticsearch(){
//        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM patents");
//
//        for(Map<String, Object> row : rows){
//            PatentDoc patent = new PatentDoc();
//            patent.setId((String)row.get("id"));
//            patent.setTitle((String)row.get("title"));
//            patent.setAssignee((String)row.get("assignee"));
//            patent.setAuthor((String)row.get("author"));
//            patent.setCreation_date((Date)row.get("creation_date"));
//            patent.setPublication_date((Date)row.get("publication_date"));
//            patent.setResult_url((String)row.get("result_url"));
//            patent.setPdf_url((String)row.get("pdf_url"));
//
//            String classification=(String)row.get("classification");
//            String[] classes=classification.split(";");
//            List<String> list=new ArrayList<>();
//            list.add(classes[0]);
//            for(int i=1;i<classes.length;i++) {
//                list.add(classes[i].substring(1));
//            }
//            System.out.println(patent);
//            System.out.println(Arrays.toString(classes));
//            System.out.println();
//            patent.setClassification(list);
//            patentRepository.save(patent);
//
//        }
//    }
//
//}