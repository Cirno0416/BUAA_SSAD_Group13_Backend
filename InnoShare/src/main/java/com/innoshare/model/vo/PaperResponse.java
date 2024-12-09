package com.innoshare.model.vo;

import com.innoshare.model.po.Paper;
import com.innoshare.model.po.PaperReference;
import lombok.Data;

import java.util.List;

@Data
public class PaperResponse {
    private PaperStd paper;//修改为paperstd
    private List<PaperReference> paperReferences;
}