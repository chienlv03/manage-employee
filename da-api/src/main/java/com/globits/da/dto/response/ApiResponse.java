package com.globits.da.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {
    private T data;
    private Integer errorCode;
    private String errorMessage;

    public ApiResponse() {}

    public ApiResponse(T data) {
        this.data = data;
    }


}
