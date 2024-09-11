package com.globits.da.rest;

import com.globits.da.dto.MyFirstApiDto;
import com.globits.da.service.MyFirstApiService;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api/my-first-api")
public class RestMyFirstApiController {
    @Autowired
    MyFirstApiService myFirstApiService;

    @GetMapping()
    public String getStringMyFirstApi() {
            return "MyFirstApi";
        }

    @GetMapping("/string-service")
    public String getStringMyFirstApiService() {
            return myFirstApiService.getStringMyFirstApi();
        }

    @PostMapping("/request-body")
    public MyFirstApiDto myFirstApi(@RequestBody MyFirstApiDto dto) {
        return dto;
    }

    @PostMapping("/primitive")
    public MyFirstApiDto postMyApiPrimitive(@RequestBody MyFirstApiDto dto) {
        return dto;
    }

    @PostMapping("/request-param")
    public MyFirstApiDto postMyApiForm(@RequestParam String code,
                                       @RequestParam String name,
                                       @RequestParam Integer age) {
        return new MyFirstApiDto(code, name, age);
    }

    @PostMapping("/path/{code}/{name}/{age}")
    public MyFirstApiDto postMyApiPath(@PathVariable String code,
                                       @PathVariable String name,
                                       @PathVariable Integer age) {
        return new MyFirstApiDto(code, name, age);
    }

    @PostMapping("/no-anotation")
    public MyFirstApiDto postMyApiNoAnotation(MyFirstApiDto myFirstApiDto) {
        return myFirstApiDto;
    }

    @PostMapping("/excel")
    public void postMyApiExcel(@RequestParam("file") MultipartFile[] submissions) {

        for (MultipartFile file : submissions) {
            myFirstApiService.readFileExcel(file);
        }
    }

    @PostMapping("/text")
    public void postMyApiText(@RequestParam("file") MultipartFile[] submissions) {
        myFirstApiService.readFileText(submissions[0]);
    }
}
