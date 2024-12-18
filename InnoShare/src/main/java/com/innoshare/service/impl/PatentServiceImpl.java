package com.innoshare.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.innoshare.mapper.PatentMapper;
import com.innoshare.mapper.UserPatentsMapper;
import com.innoshare.model.dto.PatentRequest;
import com.innoshare.model.po.Patent;
import com.innoshare.model.po.UserPatents;
import com.innoshare.service.PatentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PatentServiceImpl implements PatentService {

    private final PatentMapper patentMapper;
    private final UserPatentsMapper userPatentsMapper;

    @Override
    public boolean addPatent(int userId, String patentId) {
        // Check if the patent exists
        Patent patent = patentMapper.selectById(patentId);
        if (patent == null) {
            return false;
        }
        // Check if the relationship already exists
        QueryWrapper<UserPatents> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("patent_id", patentId);
        if (userPatentsMapper.selectCount(queryWrapper) > 0) {
            return false;
        }
        // Add to user_patents
        UserPatents userPatent = new UserPatents();
        userPatent.setUserId(userId);
        userPatent.setPatentId(patentId);
        return userPatentsMapper.insert(userPatent) > 0;
    }

    @Override
    public boolean deletePatent(int userId, String patentId) {
        QueryWrapper<UserPatents> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("patent_id", patentId);
        return userPatentsMapper.delete(queryWrapper) > 0;
    }

    @Override
    public boolean uploadPatent(PatentRequest patentRequest) {
        Patent patent = new Patent();
        patent.setId(patentRequest.getPatent().getId());
        patent.setTitle(patentRequest.getPatent().getTitle());
        patent.setAssignee(patentRequest.getPatent().getAssignee());
        patent.setAuthor(patentRequest.getPatent().getAuthor());
        patent.setCreationDate(patentRequest.getPatent().getCreationDate());
        patent.setPublicationDate(patentRequest.getPatent().getPublicationDate());
        patent.setResultUrl(patentRequest.getPatent().getResultUrl());
        patent.setPdfUrl(patentRequest.getPatent().getPdfUrl());
        // Convert classification list to string
        if (patentRequest.getPatent().getClassification() != null) {
            patent.setClassification(String.join(",", patentRequest.getPatent().getClassification()));
        }
        // Convert timeline list to comma-separated string
        if (patentRequest.getPatent().getTimeline() != null) {
            patent.setTimeline(String.join(",", patentRequest.getPatent().getTimeline()));
        }
        // Set abstract
        patent.setAbstractText(patentRequest.getPatent().getAbstractText());
        // Insert into patents table
        int result = patentMapper.insert(patent);
        if (result > 0) {
            // Insert into user_patents
            UserPatents userPatent = new UserPatents();
            userPatent.setUserId(patentRequest.getUserId());
            userPatent.setPatentId(patent.getId());
            return userPatentsMapper.insert(userPatent) > 0;
        }
        return false;
    }

    @Override
    public boolean updatePatent(Patent patent) {
        return patentMapper.updateById(patent) > 0;
    }

    @Override
    public List<Patent> getAllPatentsByUserId(int userId) {
        QueryWrapper<UserPatents> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserPatents> userPatentsList = userPatentsMapper.selectList(queryWrapper);
        if (userPatentsList.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> patentIds = userPatentsList.stream()
                .map(UserPatents::getPatentId)
                .distinct()
                .toList();
        return patentMapper.selectBatchIds(patentIds);
    }

    @Override
    public Patent getPatentById(String patentId) {
        return patentMapper.selectById(patentId);
    }
}

