package com.innoshare.scheduler;

import com.innoshare.service.PaperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PaperFetchScheduler {

    private static final Logger logger = LoggerFactory.getLogger(PaperFetchScheduler.class);

    private PaperService paperService;

    @Scheduled(fixedRate = 7 * 24 * 60 * 60 * 1000)
    public void scheduledFetchPapers() {
        try {
            paperService.fetchAndSavePapers();
        } catch (Exception e) {
            logger.error("Scheduled paper fetching failed.", e);
        }
    }
}
