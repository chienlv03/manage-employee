package com.globits.da.dto.search;

import com.globits.da.domain.Employee;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeSearchDto {
    private String code;
    private String name;
    private String email;
    private String phone;
    private Integer age;

    public EmployeeSearchDto() {
    }

    public EmployeeSearchDto(Employee employee) {
        if (employee != null) {
            this.code = employee.getCode();
            this.name = employee.getName();
            this.email = employee.getEmail();
            this.phone = employee.getPhone();
            this.age = employee.getAge();
        }
    }

}
