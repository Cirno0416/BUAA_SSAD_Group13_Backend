package com.innoshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.innoshare.mapper.PaperMapper;
import com.innoshare.model.po.Paper;
import com.innoshare.service.PaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PaperServiceImpl implements PaperService {

    private final PaperMapper paperMapper;

    @Override
    public Paper getPaperById(int id) {
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("paper_id", id);
        return paperMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Paper> listAllPapers(){
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
        return paperMapper.selectList(queryWrapper);
    }
}
