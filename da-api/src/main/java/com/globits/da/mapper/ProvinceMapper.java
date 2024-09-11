package com.globits.da.mapper;

import com.globits.da.domain.Province;
import com.globits.da.dto.request.ProvinceRequest;
import com.globits.da.dto.response.ProvinceResponse;
import com.globits.da.dto.response.ProvinceWithDistrictResponse;
import com.globits.da.dto.response.ProvinceWithDistrictWithCommuneResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProvinceMapper {
    @Autowired
    private DistrictMapper districtMapper;

    public ProvinceWithDistrictWithCommuneResponse toProvinceWithDistrictWithCommuneResponse(Province province) {
        if (province == null) {
            return null;
        }

        return new ProvinceWithDistrictWithCommuneResponse(province.getId(), province.getName(),
                districtMapper.toResponseForProvinceList(province.getDistricts()));
    }

    public Province toEntity(ProvinceRequest request) {
        Province province = new Province();
        province.setName(request.getName());
        province.setDistricts(districtMapper.toEntityList(request.getDistricts()));
        return province;
    }

    public ProvinceResponse toResponse(Province province) {
        return new ProvinceResponse(province.getId(), province.getName());
    }

    public ProvinceWithDistrictResponse toProvinceAndDistrictResponse(Province province) {
        return new ProvinceWithDistrictResponse(province.getId(), province.getName(),
                districtMapper.toResponseList(province.getDistricts()));
    }

}
