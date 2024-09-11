package com.globits.da.service;

import com.globits.da.dto.request.ProvinceRequest;
import com.globits.da.dto.response.ApiResponse;
import com.globits.da.dto.response.ProvinceResponse;
import com.globits.da.dto.response.ProvinceWithDistrictResponse;
import com.globits.da.dto.response.ProvinceWithDistrictWithCommuneResponse;

import java.util.List;

public interface ProvinceService {

    ApiResponse<List<ProvinceResponse>> getAllProvinces();

    ApiResponse<ProvinceWithDistrictWithCommuneResponse> getProvinceById(Integer id);

    void deleteProvinceById(Integer id);

    ApiResponse<ProvinceResponse> addProvince(ProvinceRequest request);

    ApiResponse<ProvinceResponse> updateProvince(ProvinceRequest request);

    ApiResponse<ProvinceWithDistrictResponse> addProvinceWithDistricts(ProvinceRequest request);

    ApiResponse<ProvinceWithDistrictResponse> updateProvinceWithDistricts(ProvinceRequest request);

    ApiResponse<ProvinceWithDistrictWithCommuneResponse> addProvinceWithDistrictsWithCommunes(ProvinceRequest request);
}
