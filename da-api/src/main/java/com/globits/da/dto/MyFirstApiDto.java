package com.globits.da.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MyFirstApiDto {
    private String code;
    private String name;
    private Integer age;

    public MyFirstApiDto() {
    }

    public MyFirstApiDto(String code, String name, Integer age) {
        this.code = code;
        this.name = name;
        this.age = age;
    }
}
