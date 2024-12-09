package com.innoshare.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.innoshare.common.Response;
import com.innoshare.mapper.PaperReferenceMapper;
import com.innoshare.mapper.UserPapersMapper;
import com.innoshare.model.dto.UpdatePaperRequest;
import com.innoshare.model.dto.UpdatePapersRequest;
import com.innoshare.model.po.Paper;
import com.innoshare.model.po.PaperReference;
import com.innoshare.model.po.UserPapers;
import com.innoshare.model.vo.PaperResponse;
import com.innoshare.service.PaperService;
import com.innoshare.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/academic")
@RequiredArgsConstructor
public class AcademicController {

    private final UserService userService;
    private final PaperService paperService;
    private final UserPapersMapper userPapersMapper;
    private final PaperReferenceMapper paperReferenceMapper;
    

    @PostMapping("/add")
    public Response addPaper(@RequestParam int userId, @RequestParam String paperDoi) {
        List<Paper> papers = paperService.getPapersByDoi(paperDoi);
        if (papers.isEmpty()) {
            return Response.warning("No papers found with the specified DOI.");
        }
        for (Paper paper : papers) {
            UserPapers userPaper = new UserPapers();
            userPaper.setUserId(userId);
            userPaper.setPaperId(paper.getPaperId());
            userPapersMapper.insert(userPaper);
        }
        return Response.success("Papers added successfully.");
    }

    @GetMapping("/delete")
    public Response deletePaper(@RequestParam int userId, @RequestParam String paperDoi) {
        List<Paper> papers = paperService.getPapersByDoi(paperDoi);
        if (papers.isEmpty()) {
            return Response.warning("No papers found with the specified DOI.");
        }
        for (Paper paper : papers) {
            QueryWrapper<UserPapers> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId).eq("paper_id", paper.getPaperId());
            int deleted = userPapersMapper.delete(queryWrapper);
            if (deleted == 0) {
                return Response.warning("No matching user-paper association found.");
            }
        }
        return Response.success("Papers deleted successfully.");
    }

    @PostMapping("/addByName")
    public Response addPaperByName(@RequestParam int userId) {
        String username = userService.getUsernameByUserId(userId);
        if (username == null) {
            return Response.warning("User not found.");
        }

        List<Paper> papers = paperService.getPapersByAuthorName(username);
        if (papers.isEmpty()) {
            return Response.warning("No papers found for the author.");
        }

        for (Paper paper : papers) {
            UserPapers userPaper = new UserPapers();
            userPaper.setUserId(userId);
            userPaper.setPaperId(paper.getPaperId());
            userPapersMapper.insert(userPaper);
        }

        return Response.success("Papers added successfully.");
    }

    @PostMapping("/update")
    public Response updatePaper(@RequestBody UpdatePaperRequest updatePaperRequest) {
        boolean success = paperService.updatePaper(updatePaperRequest);
        return success ? Response.success("Paper updated successfully.") : Response.error("Failed to update paper.");
    }

    @PostMapping("/updateList")
    public Response updatePapers(@RequestBody UpdatePapersRequest updatePapersRequest) {
        boolean success = paperService.updatePapers(updatePapersRequest);
        return success ? Response.success("Papers updated successfully.") : Response.error("Failed to update papers.");
    }

    @GetMapping("/allPaper")
    public Response allUserPaper(@RequestParam int userId) {
        // 查询 user_papers 表，获取用户关联的所有 paperId
        QueryWrapper<UserPapers> userPapersQuery = new QueryWrapper<>();
        userPapersQuery.eq("user_id", userId);
        List<UserPapers> userPapersList = userPapersMapper.selectList(userPapersQuery);

        if (userPapersList.isEmpty()) {
            return Response.success("No papers found for the user.", Collections.emptyList());
        }

        List<PaperResponse> paperResponses = new ArrayList<>();

        for (UserPapers userPaper : userPapersList) {
            // 根据 paperId 获取 Papers 对象
            List<Paper> papers = paperService.getPapersById(userPaper.getPaperId());

            if (papers.isEmpty()) {
                continue; // 如果论文未找到，跳过
            }
            for (Paper paper : papers) {
                // 根据论文的 DOI 查询引用信息
                QueryWrapper<PaperReference> referenceQuery = new QueryWrapper<>();
                referenceQuery.eq("citing_paper_doi", paper.getDoi());
                List<PaperReference> paperReferences = paperReferenceMapper.selectList(referenceQuery);

                // 构建 PaperResponse 对象
                PaperResponse paperResponse = new PaperResponse();
                paperResponse.setPaper(paper);
                paperResponse.setPaperReferences(paperReferences.isEmpty() ? null : paperReferences);

                // 添加到列表
                paperResponses.add(paperResponse);
            }
        }

        return Response.success("Papers retrieved successfully.", paperResponses);
    }

    
    @GetMapping("/getPaper")
    public Response getPaper(@RequestParam String paperDoi) {
        List<Paper> papers = paperService.getPapersByDoi(paperDoi);
        if (papers.isEmpty()) {
            return Response.success("No papers found with the specified DOI.", null);
        }
        return Response.success("Papers retrieved successfully.", papers);
    }

    
    @GetMapping("/getPaperReferences")
    public Response getPaperReferences(@RequestParam String paperDoi) {
        QueryWrapper<PaperReference> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("citing_paper_doi", paperDoi);
        List<PaperReference> paperReferences = paperReferenceMapper.selectList(queryWrapper);
        if (paperReferences.isEmpty()) {
            return Response.success("No references found for the specified DOI.", null);
        }
        return Response.success("Paper references retrieved successfully.", paperReferences);
    }




}
