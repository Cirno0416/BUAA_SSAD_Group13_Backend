package com.innoshare.service;

import com.innoshare.model.doc.PaperDoc;
import com.innoshare.model.doc.PatentDoc;

import java.util.List;

public interface SearchService {

    List<PaperDoc> searchPapers(String query, String subject, Integer subjectLevel,
                          String sortBy, String order, Integer page, Integer limit);

    List<PatentDoc> searchPatents(String query, String subject, String sortBy,
                                  String order, Integer page, Integer limit);

}
