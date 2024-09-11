package com.globits.da.rest;

import com.globits.da.MessageConstants;
import com.globits.da.dto.response.ApiResponse;
import com.globits.da.dto.DiplomaDto;
import com.globits.da.dto.response.MessageResponse;
import com.globits.da.service.DiplomaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diploma")
public class RestDiplomaController {

    private final DiplomaService diplomaService;

    @PostMapping()
    public ResponseEntity<ApiResponse<DiplomaDto>> addDiploma(@Valid @RequestBody DiplomaDto diplomaDto) {
        ApiResponse<DiplomaDto> diploma = diplomaService.addDiploma(diplomaDto);
        return ResponseEntity.ok(diploma);
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<DiplomaDto>> updateDiploma(@Valid @RequestBody DiplomaDto diplomaDto) {
        ApiResponse<DiplomaDto> diploma = diplomaService.updateDiploma(diplomaDto);
        return ResponseEntity.ok(diploma);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<DiplomaDto>>> getAllDiplomas() {
        ApiResponse<List<DiplomaDto>> diploma = diplomaService.getAllDiplomas();
        return ResponseEntity.ok(diploma);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<DiplomaDto>>> findDiplomasByEmployeeId(@PathVariable Integer employeeId) {
        ApiResponse<List<DiplomaDto>> diploma = diplomaService.findDiplomaByEmployeeId(employeeId);
        return ResponseEntity.ok(diploma);
    }

    @GetMapping("/province/{provinceId}")
    public ResponseEntity<ApiResponse<List<DiplomaDto>>> findDiplomasByProvinceId(@PathVariable Integer provinceId) {
        ApiResponse<List<DiplomaDto>> diploma = diplomaService.findDiplomaByProvinceId(provinceId);
        return ResponseEntity.ok(diploma);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteDiploma(@PathVariable Integer id) {
        diplomaService.deleteDiploma(id);
        return ResponseEntity.ok(new MessageResponse(MessageConstants.DELETE_SUCCESS));
    }

}
