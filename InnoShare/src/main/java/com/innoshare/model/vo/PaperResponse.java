package com.innoshare.model.vo;

import com.innoshare.model.po.Paper;
import com.innoshare.model.po.PaperReference;
import lombok.Data;

import java.util.List;

@Data
public class PaperResponse {
    private Paper paper;
    private List<PaperReference> paperReferences;
}