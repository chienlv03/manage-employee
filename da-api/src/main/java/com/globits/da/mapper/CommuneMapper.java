package com.globits.da.mapper;

import com.globits.da.domain.Commune;
import com.globits.da.dto.request.CommuneRequest;
import com.globits.da.dto.response.CommuneResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommuneMapper {

    public CommuneResponse toResponse(Commune commune) {
        if (commune == null) {
            return null;
        }

        return new CommuneResponse(commune.getId(), commune.getName(), commune.getDistrict().getId());
    }

    public List<CommuneResponse> toResponseList(List<Commune> communes) {
        if (communes == null || communes.isEmpty()) {
            return new ArrayList<>();
        }

        return communes.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<Commune> toEntityList(List<CommuneRequest> communes) {
        if (communes == null || communes.isEmpty()) {
            return new ArrayList<>();
        }

        return communes.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public Commune toEntity(CommuneRequest request) {
        if (request == null) {
            return null;
        }

        Commune commune = new Commune();
        commune.setName(request.getName());

        return commune;
    }
}
