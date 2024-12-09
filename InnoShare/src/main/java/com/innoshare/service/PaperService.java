package com.innoshare.service;

import com.innoshare.model.dto.UpdatePaperRequest;
import com.innoshare.model.dto.UpdatePapersRequest;
import com.innoshare.model.po.Paper;

import java.util.List;

public interface PaperService {

    List<Paper> listAllPapers();

    Paper getPaperById(int id);

    //这里实现返回id=paperId的所有paper（即返回一篇paper的所有主题条目）
    List<Paper> getPapersById(Integer paperId);

    List<Paper> getPapersByIds(List<Integer> paperIds);

    List<Paper> getPapersByDoi(String doi);

    List<Paper> getPapersByAuthorName(String authorName);

    boolean updatePaper(UpdatePaperRequest updatePaperRequest);

    boolean updatePapers(UpdatePapersRequest updatePapersRequest);
    

}
