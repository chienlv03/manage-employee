package com.globits.da.dto.request;

import com.globits.da.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceRequest {
    private Integer id;

    @Column(unique = true)
    @NotBlank(message = MessageConstants.REQUIRED_NAME)
    private String name;
    private List<DistrictRequest> districts;
}
