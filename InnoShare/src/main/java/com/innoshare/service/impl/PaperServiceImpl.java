package com.innoshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.innoshare.mapper.PaperMapper;
import com.innoshare.model.po.Paper;
import com.innoshare.service.PaperService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class PaperServiceImpl implements PaperService {

    private static final Logger logger = LoggerFactory.getLogger(PaperServiceImpl.class);

    private static final HashMap<String, String> monthMap = new HashMap<>();
    static{
        monthMap.put("Jan", "01");
        monthMap.put("Feb", "02");
        monthMap.put("Mar", "03");
        monthMap.put("Apr", "04");
        monthMap.put("May", "05");
        monthMap.put("Jun", "06");
        monthMap.put("Jul", "07");
        monthMap.put("Aug", "08");
        monthMap.put("Sep", "09");
        monthMap.put("Oct", "10");
        monthMap.put("Nov", "11");
        monthMap.put("Dec", "12");
    }

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

    @Override
    public void fetchAndSavePapers() throws IOException, ParseException {
        int count = 0;
        List<String> urlList = new ArrayList<>();
        urlList.add("https://arxiv.org/list/math/recent?skip=0&show=2000");
        urlList.add("https://arxiv.org/list/physics/recent?skip=0&show=2000");
        urlList.add("https://arxiv.org/list/math/recent?skip=0&show=2000");
        urlList.add("https://arxiv.org/list/cs/recent?skip=0&show=2000");
        urlList.add("https://arxiv.org/list/q-bio/recent?skip=0&show=2000");
        urlList.add("https://arxiv.org/list/q-fin/recent?skip=0&show=2000");

        logger.info("Scheduled paper fetching started.");

        for (String url : urlList) {
            List<String> paperIds = fetchPaperIds(url);
            for (String paperId : paperIds) {
                String absUrl = "https://arxiv.org/abs/" + paperId;
                String pdfUrl = "https://arxiv.org/pdf/" + paperId;

                if (fetchPaper(absUrl, pdfUrl)) {
                    logger.info("Fetched paper {} successfully.", paperId);
                    count++;
                }
            }
        }

        logger.info("Scheduled paper fetching completed successfully, total count: {}.", count);
    }

    private List<String> fetchPaperIds(String url) throws IOException {
        Document doc = Jsoup.connect(url) .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
                .timeout(80000)
                .get();
        Random random = new Random();
        int delay = 2000 + random.nextInt(3000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<String> paperIds = new ArrayList<>();
        Elements links = doc.select("dt > a[href^=/abs/]");
        for (Element link : links) {
            String href = link.attr("href");
            String id = href.substring(href.lastIndexOf("/") + 1);
            paperIds.add(id);
        }
        if (paperIds.isEmpty()) {
            logger.error("No paper found");
        }
        return paperIds;
    }

    private boolean fetchPaper(String absUrl, String pdfUrl) throws IOException, ParseException {
        Document doc = Jsoup.connect(absUrl) .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
                .timeout(50000)
                .get();
        Random random = new Random();
        int delay = 2000 + random.nextInt(3000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Element doiElement = doc.selectFirst("td.tablecell.arxivdoi a[href^=https://doi.org/]");
        if (doiElement == null) {
            return false;
        }
        String doi = doiElement.text();
        // 去重
        if (getPaperByDoi(doi) != null && !getPaperByDoi(doi).isEmpty()) {
            logger.info("Skipped duplicate paper : {}", doi);
            return false;
        }

        Element titleElement = doc.selectFirst("h1.title");
        if (titleElement == null) {
            return false;
        }
        String title = titleElement.text().replace("Title:", "").trim();

        Element authorsElement = doc.selectFirst("div.authors");
        if (authorsElement == null) {
            return false;
        }
        String authors = authorsElement.text().replace("Authors:", "").trim();

        Element abstractElement = doc.selectFirst("blockquote.abstract");
        if (abstractElement == null) {
            return false;
        }
        String abstractText = abstractElement.text().replace("Abstract:", "").trim();

        Element publishElement = doc.selectFirst("div.submission-history");
        if (publishElement == null) {
            return false;
        }
        Timestamp published_at = convertToTimestamp(getLastSubmissionDate(publishElement.text()));

        Element subjectsElement = doc.selectFirst("td.tablecell.subjects");
        if (subjectsElement == null) {
            return false;
        }
        String subjects = subjectsElement.text();
        List<String> subjectsList = parseSubjects(subjects);

        for (String subject : subjectsList) {
            subject = subject.trim();
// todo
        }
        return true;
    }

    private List<Paper> getPaperByDoi(String doi) {
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doi", doi);
        return paperMapper.selectList(queryWrapper);
    }

    private String getLastSubmissionDate(String submissionHistory) {
        Pattern pattern = Pattern.compile("\\b(\\d{1,2} \\w{3} \\d{4} \\d{2}:\\d{2}:\\d{2})\\b");
        Matcher matcher = pattern.matcher(submissionHistory);
        String lastDate = null;
        while (matcher.find()) {
            lastDate = matcher.group(1);
        }
        // 将月份替换为数字形式
        for (Map.Entry<String, String> entry : monthMap.entrySet()) {
            if (lastDate != null) {
                lastDate = lastDate.replace(entry.getKey(), entry.getValue());
            }
        }
        return lastDate;
    }

    private Timestamp convertToTimestamp(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
        Date date = sdf.parse(dateString);
        return new Timestamp(date.getTime());
    }

    private List<String> parseSubjects(String subjects) {
        String[] splitSubjects = subjects.split(";");
        return new ArrayList<>(Arrays.asList(splitSubjects));
    }
}
