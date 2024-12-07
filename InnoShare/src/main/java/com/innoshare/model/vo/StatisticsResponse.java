package com.innoshare.model.vo;

import lombok.Data;

@Data
public class StatisticsResponse {

    private int totalUsers;

    private int authenticatedUsers;

    private int pendingAuthRequests;

    private int dailyNewUsers;

    private int weeklyNewUsers;

    private int monthlyNewUsers;

    private int dailyActiveUsers;

    private int weeklyActiveUsers;

    private int monthlyActiveUsers;

    private int totalPapers;

    private int totalPatents;

    private int totalBrowse;

    private int totalDownloads;

}
