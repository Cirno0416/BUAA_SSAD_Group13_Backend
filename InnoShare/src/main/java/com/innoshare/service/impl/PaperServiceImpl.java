package com.innoshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.innoshare.mapper.PaperMapper;
import com.innoshare.mapper.PaperReferenceMapper;
import com.innoshare.mapper.UserPapersMapper;
import com.innoshare.model.dto.*;
import com.innoshare.model.po.*;
import com.innoshare.service.PaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class PaperServiceImpl implements PaperService {

    private final PaperMapper paperMapper;
    private final PaperReferenceMapper paperReferenceMapper;
    private final UserPapersMapper userPapersMapper;

    @Override
    public Paper getPaperById(int id) {
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("paper_id", id);
        return paperMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Paper> getPapersById(Integer paperId) {
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("paper_id", paperId);
        return paperMapper.selectList(queryWrapper);
    }

    @Override
    public List<Paper> listAllPapers(){
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
        return paperMapper.selectList(queryWrapper);
    }

    @Override
    public List<Paper> getPapersByDoi(String doi) {
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doi", doi);
        return paperMapper.selectList(queryWrapper);
    }

    @Override
    public List<Paper> getPapersByAuthorName(String authorName) {
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("author", authorName);
        return paperMapper.selectList(queryWrapper);
    }

    @Override
    public boolean updatePaper(UpdatePaperRequest updatePaperRequest) {
        return savePaperAndReferences(updatePaperRequest.getUserId(), updatePaperRequest.getPaperRequest());
    }

    @Override
    public boolean updatePapers(UpdatePapersRequest updatePapersRequest) {
        boolean result = true;
        for (PaperRequest paperRequest : updatePapersRequest.getPaperRequests()) {
            result &= savePaperAndReferences(updatePapersRequest.getUserId(), paperRequest);
        }
        return result;
    }

    private boolean savePaperAndReferences(Integer userId, PaperRequest paperRequest) {
        try {
            String[] subjects = paperRequest.getSubjects().split(",");
            for (String subject : subjects) {
                Paper paper = new Paper();
                paper.setTitle(paperRequest.getTitle());
                paper.setAuthor(paperRequest.getAuthor());
                paper.setAbstractText(paperRequest.getAbstractText());
                paper.setSubject(subject.trim());
                paper.setPublishedAt(paperRequest.getPublishedAt());
                paper.setDoi(paperRequest.getDoi());
                paper.setDownloadUrl(paperRequest.getDownloadUrl());
                paperMapper.insert(paper);

                // 插入user_papers表
                UserPapers userPapers = new UserPapers();
                userPapers.setUserId(userId);
                userPapers.setPaperId(paper.getPaperId());
                userPapersMapper.insert(userPapers);

                // 插入paper_references表
                if (paperRequest.getReferences() != null) {
                    for (ReferenceRequest referenceRequest : paperRequest.getReferences()) {
                        PaperReference paperReference = new PaperReference();
                        paperReference.setCitingPaperDoi(paper.getDoi());
                        paperReference.setCitedPaperDoi(referenceRequest.getDoi());
                        paperReference.setCitingPaperTitle(paper.getTitle());
                        paperReference.setCreatedAt(new Date());
                        paperReferenceMapper.insert(paperReference);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
