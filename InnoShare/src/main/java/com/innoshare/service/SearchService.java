package com.innoshare.service;

import com.innoshare.model.doc.PaperDoc;

import java.util.List;

public interface SearchService {
    List<PaperDoc> search(String query, String category, String sortBy, String order, Integer page, Integer limit);
}
