package com.globits.da.rest;

import com.globits.da.MessageConstants;
import com.globits.da.dto.request.ProvinceRequest;
import com.globits.da.dto.response.*;
import com.globits.da.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/province")
public class RestProvinceController {

    private final ProvinceService provinceService;

    @PostMapping()
    public ResponseEntity<ApiResponse<ProvinceResponse>> addProvince(@Valid @RequestBody ProvinceRequest request) {
        ApiResponse<ProvinceResponse> province = provinceService.addProvince(request);
        return ResponseEntity.ok(province);
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<ProvinceResponse>> updateProvince(@Valid @RequestBody ProvinceRequest request) {
        ApiResponse<ProvinceResponse> province = provinceService.updateProvince(request);
        return ResponseEntity.ok(province);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ProvinceResponse>>> getAllProvinces() {
        ApiResponse<List<ProvinceResponse>> provinces = provinceService.getAllProvinces();
        return ResponseEntity.ok(provinces);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProvinceWithDistrictWithCommuneResponse>> getProvinceById(@PathVariable Integer id) {
        ApiResponse<ProvinceWithDistrictWithCommuneResponse> province = provinceService.getProvinceById(id);
        return ResponseEntity.ok(province);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteProvince(@PathVariable Integer id) {
        provinceService.deleteProvinceById(id);
        return ResponseEntity.ok(new MessageResponse(MessageConstants.DELETE_SUCCESS));
    }

    @PostMapping("/districts")
    public ResponseEntity<ApiResponse<ProvinceWithDistrictResponse>> addProvinceWithDistricts(@Valid @RequestBody ProvinceRequest request) {
        ApiResponse<ProvinceWithDistrictResponse> province = provinceService.addProvinceWithDistricts(request);
        return ResponseEntity.ok(province);
    }

    @PutMapping("/districts")
    public ResponseEntity<ApiResponse<ProvinceWithDistrictResponse>> updateProvinceWithDistricts(@Valid @RequestBody ProvinceRequest request) {
        ApiResponse<ProvinceWithDistrictResponse> province = provinceService.updateProvinceWithDistricts(request);
        return ResponseEntity.ok(province);
    }

    @PostMapping("/districts/communes")
    public ResponseEntity<ApiResponse<ProvinceWithDistrictWithCommuneResponse>> addProvinceWithDistrictsWithCommunes(@Valid @RequestBody ProvinceRequest request) {
        ApiResponse<ProvinceWithDistrictWithCommuneResponse> province = provinceService.addProvinceWithDistrictsWithCommunes(request);
        return ResponseEntity.ok(province);
    }
}
