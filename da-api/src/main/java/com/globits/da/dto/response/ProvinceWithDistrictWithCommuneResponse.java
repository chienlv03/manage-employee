package com.globits.da.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceWithDistrictWithCommuneResponse {
    private Integer id;
    private String name;
    private List<DistrictWithCommuneResponse> districts;
}
