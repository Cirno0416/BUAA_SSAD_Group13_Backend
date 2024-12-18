package com.innoshare.controller;


import com.innoshare.common.Response;
import com.innoshare.model.dto.BrowseRequest;
import com.innoshare.model.dto.DownloadRequest;
import com.innoshare.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("record")
@RequiredArgsConstructor
public class RecordController {//包括下载和浏览
    private final RecordService recordService;

    @PostMapping("add/download")
    public Response addDownload(@RequestBody DownloadRequest downloadRequest) {
        return recordService.addDownload(downloadRequest);
    }

    //根据paperId获取下载次数
    @GetMapping("get/download/count")
    public Response getDownloadCount(@RequestParam Integer paperId){
        return recordService.getDownloadCount(paperId);
    }

    @PostMapping("add/browse")
    public Response addBrowse(@RequestBody BrowseRequest browseRequest){
        return recordService.addBrowse(browseRequest);
    }

    //根据paperId获取浏览次数
    @GetMapping("get/browse/count")
    public Response getBrowseCount(@RequestParam Integer paperId){
        return recordService.getBrowseCount(paperId);
    }
}
