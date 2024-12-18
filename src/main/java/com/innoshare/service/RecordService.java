package com.innoshare.service;

import com.innoshare.common.Response;
import com.innoshare.model.dto.BrowseRequest;
import com.innoshare.model.dto.DownloadRequest;
import org.springframework.stereotype.Repository;

public interface RecordService {
    Response addDownload(DownloadRequest downloadRequest);
    Response addBrowse(BrowseRequest browseRequest);
    Response getDownloadCount(Integer paperId);
    Response getBrowseCount(Integer paperId);
}
