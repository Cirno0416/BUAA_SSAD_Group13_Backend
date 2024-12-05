package com.innoshare.model.vo;

import com.innoshare.model.po.AuthApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetApplicationsResponse {
    Integer total;
    List<AuthApplication> applications;

}
