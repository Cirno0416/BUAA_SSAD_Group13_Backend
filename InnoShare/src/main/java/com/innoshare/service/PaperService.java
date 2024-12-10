package com.innoshare.service;

import com.innoshare.model.po.Paper;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface PaperService {

    List<Paper> listAllPapers();

    Paper getPaperById(int id);

    void fetchAndSavePapers() throws IOException, ParseException;
}
