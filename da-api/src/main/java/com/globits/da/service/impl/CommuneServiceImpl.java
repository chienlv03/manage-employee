package com.globits.da.service.impl;

import com.globits.da.FindEntity;
import com.globits.da.domain.District;
import com.globits.da.domain.Commune;
import com.globits.da.dto.request.CommuneRequest;
import com.globits.da.dto.response.ApiResponse;
import com.globits.da.dto.response.CommuneResponse;
import com.globits.da.mapper.CommuneMapper;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.service.CommuneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommuneServiceImpl implements CommuneService {

    private final CommuneRepository communeRepository;
    private final CommuneMapper communeMapper;
    private final FindEntity findEntity;

    @Override
    public ApiResponse<CommuneResponse> addCommune(CommuneRequest request) {
        District district = findEntity.findDistrict(request.getDistrictId());

        Commune commune = communeMapper.toEntity(request);
        commune.setDistrict(district);
        Commune savedCommune = communeRepository.save(commune);
        return new ApiResponse<>(communeMapper.toResponse(savedCommune));
    }

    @Override
    public ApiResponse<List<CommuneResponse>> getAllCommunes() {
        List<Commune> communes = communeRepository.findAll();
        return new ApiResponse<>(communeMapper.toResponseList(communes));
    }

    @Override
    public ApiResponse<CommuneResponse> getCommuneById(Integer id) {
        Commune commune = findEntity.findCommune(id);
        return new ApiResponse<>(communeMapper.toResponse(commune));
    }

    @Override
    public ApiResponse<CommuneResponse> updateCommune(CommuneRequest request) {
        District district = findEntity.findDistrict(request.getDistrictId());
        Commune commune = findEntity.findCommune(request.getId());
        commune.setDistrict(district);
        commune.setName(request.getName());
        Commune updatedCommune = communeRepository.save(commune);
        return new ApiResponse<>(communeMapper.toResponse(updatedCommune));
    }

    @Override
    public ApiResponse<List<CommuneResponse>> getCommuneByDistrictId(Integer districtId) {
        List<Commune> commune = communeRepository.findByDistrictId(districtId);

        return new ApiResponse<>(communeMapper.toResponseList(commune));
    }

    @Override
    public void deleteCommune(Integer id) {
        Commune commune = findEntity.findCommune(id);
        communeRepository.deleteById(commune.getId());
    }
}
