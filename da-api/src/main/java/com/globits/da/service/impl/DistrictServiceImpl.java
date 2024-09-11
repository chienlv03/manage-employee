package com.globits.da.service.impl;

import com.globits.da.FindEntity;
import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import com.globits.da.dto.request.CommuneRequest;
import com.globits.da.dto.request.DistrictRequest;
import com.globits.da.dto.response.ApiResponse;
import com.globits.da.dto.response.DistrictResponse;
import com.globits.da.dto.response.DistrictWithCommuneResponse;
import com.globits.da.exception.ResourceNotFoundException;
import com.globits.da.mapper.DistrictMapper;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.service.DistrictService;
import com.globits.da.utils.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;
    private final CommuneRepository communeRepository;
    private final DistrictMapper districtMapper;
    private final FindEntity findEntity;
    private final Validation validation;

    @Override
    public ApiResponse<DistrictResponse> addDistrict(DistrictRequest request) {
        validation.validateDistrictName(request.getName());

        Province province = findEntity.findProvince(request.getProvinceId());

        District district = districtMapper.toEntity(request);
        district.setProvince(province);
        District savedDistrict = districtRepository.save(district);
        return new ApiResponse<>(districtMapper.toResponse(savedDistrict));
    }

    @Override
    public ApiResponse<List<DistrictResponse>> getAllDistricts() {
        List<District> districts = districtRepository.findAll();
        return new ApiResponse<>(districts.stream()
                .map(districtMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @Override
    public ApiResponse<DistrictWithCommuneResponse> getDistrictById(Integer id) {
        District district = findEntity.findDistrict(id);
        return new ApiResponse<>(districtMapper.toDistrictWithCommuneResponse(district));
    }

    @Override
    public ApiResponse<DistrictResponse> updateDistrict(DistrictRequest request) {
        Province province = findEntity.findProvince(request.getProvinceId());
        District district = findEntity.findDistrict(request.getId());
        district.setName(request.getName());
        district.setProvince(province);
        District updatedDistrict = districtRepository.save(district);
        return new ApiResponse<>(districtMapper.toResponse(updatedDistrict));
    }

    @Override
    public ApiResponse<List<DistrictResponse>> getDistrictsByProvinceId(Integer provinceId) {
        List<District> districts = districtRepository.findByProvinceId(provinceId);
        return new ApiResponse<>(districtMapper.toResponseList(districts));
    }

    @Override
    public void deleteDistrict(Integer id) {
        District district = findEntity.findDistrict(id);
        districtRepository.delete(district);
    }

    @Override
    public ApiResponse<DistrictWithCommuneResponse> addDistrictWithCommunes(DistrictRequest request) {
        validation.validateDistrictName(request.getName());
        Province province = findEntity.findProvince(request.getProvinceId());

        District district = districtMapper.toEntity(request);
        district.setProvince(province);

        for (Commune commune : district.getCommunes()) {
            commune.setDistrict(district);
        }

        District savedDistrict = districtRepository.save(district);
        return new ApiResponse<>(districtMapper.toDistrictWithCommuneResponse(savedDistrict));
    }

    @Override
    public ApiResponse<DistrictWithCommuneResponse> updateDistrictWithCommunes(DistrictRequest request) {
        Province province = findEntity.findProvince(request.getProvinceId());
        District district = findEntity.findDistrict(request.getId());

        district.setName(request.getName());
        district.setProvince(province);

        for (CommuneRequest communeRequest : request.getCommunes()) {
            validation.validateCommune(communeRequest.getId(), request.getId());
            if (communeRequest.getId() != null) {
                Commune commune = findEntity.findCommune(communeRequest.getId());

                commune.setName(communeRequest.getName());
                commune.setDistrict(district);

                communeRepository.save(commune);
            }
            else {
                throw new ResourceNotFoundException("Commune id is null or invalid for update.");
            }
        }

        District updatedDistrict = districtRepository.save(district);
        return new ApiResponse<>(districtMapper.toDistrictWithCommuneResponse(updatedDistrict));
    }
}
