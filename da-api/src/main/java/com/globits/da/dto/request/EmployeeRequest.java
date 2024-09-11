package com.globits.da.dto.request;

import com.globits.da.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    private Integer id;

    @Pattern(regexp = MessageConstants.CODE_REGEX, message = MessageConstants.CODE_VALID)
    @NotBlank(message = MessageConstants.REQUIRED_CODE)
    @Column(unique = true)
    private String code;

    @NotBlank(message = MessageConstants.REQUIRED_NAME)
    private String name;

    @Email(message = MessageConstants.NOT_VALID)
    @NotBlank(message = MessageConstants.REQUIRED_EMAIL)
    private String email;

    @NotBlank(message = MessageConstants.REQUIRED_PHONE)
    @Pattern(regexp = MessageConstants.PHONE_REGEX, message = MessageConstants.PHONE_VALID)
    private String phone;

    @Min(value = 0, message = MessageConstants.AGE_VALID)
    @NotNull(message = MessageConstants.REQUIRED_AGE)
    private Integer age;

    @NotNull(message = MessageConstants.REQUIRED_PROVINCE)
    private Integer provinceId;

    @NotNull(message = MessageConstants.REQUIRED_DISTRICT)
    private Integer districtId;

    @NotNull(message = MessageConstants.REQUIRED_COMMUNE)
    private Integer communeId;
}
