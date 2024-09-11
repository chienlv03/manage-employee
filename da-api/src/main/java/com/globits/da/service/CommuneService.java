package com.globits.da.service;

import com.globits.da.dto.request.CommuneRequest;
import com.globits.da.dto.response.ApiResponse;
import com.globits.da.dto.response.CommuneResponse;

import java.util.List;

public interface CommuneService {
    ApiResponse<CommuneResponse> addCommune(CommuneRequest request);

    ApiResponse<List<CommuneResponse>> getAllCommunes();

    ApiResponse<CommuneResponse> getCommuneById(Integer id);

    ApiResponse<CommuneResponse> updateCommune(CommuneRequest request);

    ApiResponse<List<CommuneResponse>> getCommuneByDistrictId(Integer districtId);

    void deleteCommune(Integer id);
}
