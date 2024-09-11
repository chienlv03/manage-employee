package com.globits.da.service.impl;

import com.globits.da.dto.request.DistrictRequest;
import com.globits.da.dto.request.ProvinceRequest;
import com.globits.da.dto.response.*;
import com.globits.da.FindEntity;
import com.globits.da.MessageConstants;
import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import com.globits.da.exception.BadRequestException;
import com.globits.da.mapper.ProvinceMapper;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.repository.ProvinceRepository;
import com.globits.da.service.ProvinceService;
import com.globits.da.utils.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;
    private final ProvinceMapper provinceMapper;
    private final DistrictRepository districtRepository;
    private final FindEntity findEntity;
    private final Validation validation;

    @Override
    public ApiResponse<ProvinceResponse> addProvince(ProvinceRequest request) {
        validation.validateProvinceName(request.getName());
        Province province = provinceMapper.toEntity(request);
        Province savedProvince = provinceRepository.save(province);
        return new ApiResponse<>(provinceMapper.toResponse(savedProvince));
    }

    @Override
    public ApiResponse<ProvinceResponse> updateProvince(ProvinceRequest request) {
        Province province = findEntity.findProvince(request.getId());
        validation.validateProvinceName(request.getName());
        province.setName(request.getName());
        Province updatedProvince = provinceRepository.save(province);
        return new ApiResponse<>(provinceMapper.toResponse(updatedProvince));
    }

    @Override
    public ApiResponse<List<ProvinceResponse>> getAllProvinces() {
        List<Province> provinces = provinceRepository.findAll();
        return new ApiResponse<>(provinces.stream()
                .map(provinceMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @Override
    public ApiResponse<ProvinceWithDistrictWithCommuneResponse> getProvinceById(Integer id) {
        Province province = findEntity.findProvince(id);
        return new ApiResponse<>(provinceMapper.toProvinceWithDistrictWithCommuneResponse(province));
    }

    @Override
    public void deleteProvinceById(Integer id) {
            Province province = findEntity.findProvince(id);
        provinceRepository.deleteById(province.getId());
    }

    @Override
    public ApiResponse<ProvinceWithDistrictResponse> addProvinceWithDistricts(ProvinceRequest request) {
        validation.validateProvinceName(request.getName());
        for (DistrictRequest response : request.getDistricts()) {
            validation.validateDistrictName(response.getName());
        }
        Province province = provinceMapper.toEntity(request);

        for (District district : province.getDistricts()) {
            validation.validateDistrictName(district.getName());

            district.setName(district.getName());
            district.setProvince(province);
        }
        Province savedProvince = provinceRepository.save(province);
        return new ApiResponse<>(provinceMapper.toProvinceAndDistrictResponse(savedProvince));
    }

    @Override
    public ApiResponse<ProvinceWithDistrictResponse> updateProvinceWithDistricts(ProvinceRequest request) {

        Province province =  findEntity.findProvince(request.getId());

        province.setName(request.getName());

        for (DistrictRequest response : request.getDistricts()) {
            validation.validateDistrict(response.getId(), request.getId());

            if (response.getId() != null) {
                District existingDistrict = findEntity.findDistrict(response.getId());

                existingDistrict.setName(response.getName());
                existingDistrict.setProvince(province);

                districtRepository.save(existingDistrict);
            } else {
                throw new BadRequestException(MessageConstants.DISTRICT_NOT_EXITED);
            }
        }

        Province updatedProvince = provinceRepository.save(province);
        return new ApiResponse<>(provinceMapper.toProvinceAndDistrictResponse(updatedProvince));
    }

    @Override
    public ApiResponse<ProvinceWithDistrictWithCommuneResponse> addProvinceWithDistrictsWithCommunes(ProvinceRequest request) {
        validation.validateProvinceName(request.getName());
        for (DistrictRequest response : request.getDistricts()) {
            validation.validateDistrictName(response.getName());
        }
        Province province = provinceMapper.toEntity(request);

        for (District district : province.getDistricts()) {
            district.setProvince(province);
            for (Commune commune : district.getCommunes()) {
                commune.setDistrict(district);
            }
        }
        Province savedProvince = provinceRepository.save(province);
        return new ApiResponse<>(provinceMapper.toProvinceWithDistrictWithCommuneResponse(savedProvince));
    }
}
