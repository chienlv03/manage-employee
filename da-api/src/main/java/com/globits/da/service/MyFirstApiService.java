package com.globits.da.service;

import org.springframework.web.multipart.MultipartFile;

public interface MyFirstApiService {
    String getStringMyFirstApi();

    void readFileExcel(MultipartFile file);

    void readFileText(MultipartFile submission);
}
