package com.innoshare.service.impl;


import com.innoshare.common.Response;
import com.innoshare.mapper.RecordMapper;
import com.innoshare.model.dto.BrowseRequest;
import com.innoshare.model.dto.DownloadRequest;
import com.innoshare.model.po.Browse;
import com.innoshare.model.po.Download;
import com.innoshare.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {
    private final RecordMapper recordMapper;
    @Override
    public Response addDownload(DownloadRequest downloadRequest) {
        Download download=new Download();
        BeanUtils.copyProperties(downloadRequest,download);
        download.setCreatedAt(downloadRequest.getDownloadTime());
        download.setUpdatedAt(downloadRequest.getDownloadTime());
        recordMapper.addDownload(download);
        return Response.success("下载记录添加成功",download);
    }

    @Override
    public Response addBrowse(BrowseRequest browseRequest) {
        Browse browse = new Browse();
        BeanUtils.copyProperties(browseRequest,browse);
        browse.setCreatedAt(browseRequest.getBrowseTime());
        browse.setUpdatedAt(browseRequest.getBrowseTime());
        recordMapper.addBrowse(browse);
        return Response.success("浏览记录添加成功",browse);
    }

    @Override
    public Response getDownloadCount(Integer paperId) {
        int downloadCount = recordMapper.getDownloadCount(paperId);
        return Response.success("获取下载次数成功",downloadCount);
    }

    @Override
    public Response getBrowseCount(Integer paperId) {
        int browseCount = recordMapper.getBrowseCount(paperId);
        return Response.success("获取浏览次数成功",browseCount);
    }
}
