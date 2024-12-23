package com.innoshare.model.vo;

import lombok.Data;
import java.util.List;

@Data
public class PortalResponse {
    private String authorName;
    private List<PaperStd> papers;
    private List<String> coAuthors;
}