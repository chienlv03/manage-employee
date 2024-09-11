package com.globits.da.rest;

import com.globits.da.MessageConstants;
import com.globits.da.dto.request.DistrictRequest;
import com.globits.da.dto.response.ApiResponse;
import com.globits.da.dto.response.DistrictResponse;
import com.globits.da.dto.response.DistrictWithCommuneResponse;
import com.globits.da.dto.response.MessageResponse;
import com.globits.da.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/district")
public class RestDistrictController {

    private final DistrictService districtService;

    @PostMapping()
    public ResponseEntity<ApiResponse<DistrictResponse>> addDistrict(@RequestBody DistrictRequest request) {
        ApiResponse<DistrictResponse> district = districtService.addDistrict(request);
        return ResponseEntity.ok(district);
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<DistrictResponse>> updateDistrict(@RequestBody DistrictRequest request) {
        ApiResponse<DistrictResponse> district = districtService.updateDistrict(request);
        return ResponseEntity.ok(district);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<DistrictResponse>>> getAllDistricts() {
        ApiResponse<List<DistrictResponse>> districts = districtService.getAllDistricts();
        return ResponseEntity.ok(districts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DistrictWithCommuneResponse>> getDistrictById(@PathVariable Integer id) {
        ApiResponse<DistrictWithCommuneResponse> district = districtService.getDistrictById(id);
        return ResponseEntity.ok(district);
    }

    @GetMapping("/province/{provinceId}")
    public ResponseEntity<ApiResponse<List<DistrictResponse>>> getDistrictsByProvinceId(@PathVariable Integer provinceId) {
        ApiResponse<List<DistrictResponse>> districts = districtService.getDistrictsByProvinceId(provinceId);
        return ResponseEntity.ok(districts);
    }

    @PostMapping("/communes")
    public ResponseEntity<ApiResponse<DistrictWithCommuneResponse>> addDistrictWithCommunes(@RequestBody DistrictRequest request) {
        ApiResponse<DistrictWithCommuneResponse> district = districtService.addDistrictWithCommunes(request);
        return ResponseEntity.ok(district);
    }

    @PutMapping("/communes")
    public ResponseEntity<ApiResponse<DistrictWithCommuneResponse>> updateDistrictWithCommunes(@RequestBody DistrictRequest request) {
        ApiResponse<DistrictWithCommuneResponse> district = districtService.updateDistrictWithCommunes(request);
        return ResponseEntity.ok(district);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteDistrict(@PathVariable Integer id) {
        districtService.deleteDistrict(id);
        return ResponseEntity.ok(new MessageResponse(MessageConstants.DELETE_SUCCESS));
    }
}
