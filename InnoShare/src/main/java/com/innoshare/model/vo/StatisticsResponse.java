package com.innoshare.model.vo;

import lombok.Data;

@Data
public class StatisticsResponse {

    private int totalUsers;

    private int authenticatedUsers;

    private int totalAchievements;

    private int pendingAuthRequests;

    private int totalDownloads;

}
