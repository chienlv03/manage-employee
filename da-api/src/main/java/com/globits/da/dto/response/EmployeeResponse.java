package com.globits.da.dto.response;

import com.globits.da.dto.DiplomaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private Integer id;
    private String code;
    private String name;
    private String email;
    private String phone;
    private Integer age;
    private ProvinceResponse province;
    private DistrictResponse district;
    private CommuneResponse commune;
    private List<DiplomaDto> diplomas;
}
