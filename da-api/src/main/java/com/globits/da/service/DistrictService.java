package com.globits.da.service;

import com.globits.da.dto.request.DistrictRequest;
import com.globits.da.dto.response.ApiResponse;
import com.globits.da.dto.response.DistrictResponse;
import com.globits.da.dto.response.DistrictWithCommuneResponse;

import java.util.List;

public interface DistrictService {

    ApiResponse<DistrictResponse> addDistrict(DistrictRequest request);

    ApiResponse<List<DistrictResponse>> getAllDistricts();

    ApiResponse<DistrictWithCommuneResponse> getDistrictById(Integer id);

    ApiResponse<DistrictResponse> updateDistrict(DistrictRequest request);

    ApiResponse<List<DistrictResponse>> getDistrictsByProvinceId(Integer provinceId);

    void deleteDistrict(Integer id);

    ApiResponse<DistrictWithCommuneResponse> addDistrictWithCommunes(DistrictRequest request);

    ApiResponse<DistrictWithCommuneResponse> updateDistrictWithCommunes(DistrictRequest request);
}
