package com.globits.da.mapper;

import com.globits.da.domain.District;
import com.globits.da.dto.request.DistrictRequest;
import com.globits.da.dto.response.DistrictResponse;
import com.globits.da.dto.response.DistrictWithCommuneResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DistrictMapper {

    @Autowired
    private CommuneMapper communeMapper;

    public DistrictResponse toResponse(District district) {
        if (district == null) {
            return null;
        }

        return new DistrictResponse(district.getId(), district.getName(), district.getProvince().getId());
    }

    public DistrictWithCommuneResponse toDistrictWithCommuneResponse(District district) {
        if (district == null) {
            return null;
        }

        return new DistrictWithCommuneResponse(district.getProvince().getId(), district.getId(), district.getName(),
                communeMapper.toResponseList(district.getCommunes()));
    }

    public List<DistrictWithCommuneResponse> toResponseForProvinceList(List<District> districts) {
        if (districts == null || districts.isEmpty()) {
            return new ArrayList<>();
        }

        return districts.stream()
                .map(this::toDistrictWithCommuneResponse)
                .collect(Collectors.toList());
    }

    public List<DistrictResponse> toResponseList(List<District> districts) {
        if (districts == null || districts.isEmpty()) {
            return new ArrayList<>();
        }

        return districts.stream()
                .map(district -> new DistrictResponse(district.getId(), district.getName() , district.getProvince().getId()))
                .collect(Collectors.toList());
    }

    public District toEntity(DistrictRequest request) {
        District district = new District();
        district.setName(request.getName());
        district.setCommunes(communeMapper.toEntityList(request.getCommunes()));
        return district;
    }

    public List<District> toEntityList(List<DistrictRequest> districts) {
        if (districts == null || districts.isEmpty()) {
            return new ArrayList<>();
        }

        return districts.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
