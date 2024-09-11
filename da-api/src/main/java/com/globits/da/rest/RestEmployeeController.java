package com.globits.da.rest;

import com.globits.da.MessageConstants;
import com.globits.da.dto.request.EmployeeRequest;
import com.globits.da.dto.response.ApiResponse;
import com.globits.da.dto.response.EmployeeResponse;
import com.globits.da.dto.response.MessageResponse;
import com.globits.da.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employee")
public class RestEmployeeController {
    private final EmployeeService employeeService;

    public RestEmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllEmployees() {
        ApiResponse<List<EmployeeResponse>> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(@PathVariable Integer id) {
        ApiResponse<EmployeeResponse> employeeDto = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponse>> addEmployee(@Valid @RequestBody EmployeeRequest request) {
        ApiResponse<EmployeeResponse> response = employeeService.addEmployee(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(@Valid @RequestBody EmployeeRequest request) {
        ApiResponse<EmployeeResponse> response = employeeService.updateEmployee(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(new MessageResponse(MessageConstants.DELETE_SUCCESS));
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse> deleteAllEmployees() {
        employeeService.deleteAllEmployees();
        return ResponseEntity.ok(new MessageResponse(MessageConstants.DELETE_SUCCESS));
    }


    @GetMapping("/export-excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        employeeService.exportExcel(response);
    }

    @PostMapping("/import-excel")
    public ResponseEntity<?> importExcel(@RequestParam("file") MultipartFile file) {
        try {
            employeeService.importExcel(file);
            return ResponseEntity.ok(new MessageResponse(MessageConstants.IMPORT_SUCCESS));
        } catch (IllegalArgumentException e) {
            String[] errorLines = e.getMessage().split("\n");
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errors", Arrays.asList(errorLines));
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
