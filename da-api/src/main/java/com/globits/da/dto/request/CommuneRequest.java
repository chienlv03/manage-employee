package com.globits.da.dto.request;

import com.globits.da.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommuneRequest {
    private Integer id;
    @NotBlank(message = MessageConstants.REQUIRED_NAME)
    private String name;
    @NotNull(message = MessageConstants.REQUIRED_DISTRICT)
    private Integer districtId;
}
