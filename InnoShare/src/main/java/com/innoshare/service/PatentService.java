package com.innoshare.service;


import com.innoshare.model.dto.PatentRequest;
import com.innoshare.model.po.Patent;

import java.util.List;

public interface PatentService {

    boolean addPatent(int userId, String patentId);

    boolean deletePatent(int userId, String patentId);

    boolean uploadPatent(PatentRequest patentRequest);

    boolean updatePatent(Patent patent);

    List<Patent> getAllPatentsByUserId(int userId);

    Patent getPatentById(String patentId);
}


