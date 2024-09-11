package com.globits.da.rest;

import com.globits.da.dto.request.CommuneRequest;
import com.globits.da.dto.response.ApiResponse;
import com.globits.da.dto.response.CommuneResponse;
import com.globits.da.dto.response.MessageResponse;
import com.globits.da.service.CommuneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/commune")
public class RestCommuneController {

    private final CommuneService communeService;

    @PostMapping()
    public ResponseEntity<ApiResponse<CommuneResponse>> addCommune(@RequestBody CommuneRequest request) {
        ApiResponse<CommuneResponse> commune = communeService.addCommune(request);
        return ResponseEntity.ok(commune);
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<CommuneResponse>> updateCommune(@RequestBody CommuneRequest request) {
        ApiResponse<CommuneResponse> commune = communeService.updateCommune(request);
        return ResponseEntity.ok(commune);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<CommuneResponse>>> getAllCommunes() {
        ApiResponse<List<CommuneResponse>> communes = communeService.getAllCommunes();
        return ResponseEntity.ok(communes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CommuneResponse>> getCommuneById(@PathVariable Integer id) {
        ApiResponse<CommuneResponse> commune = communeService.getCommuneById(id);
        return ResponseEntity.ok(commune);
    }

    @GetMapping("/district/{districtId}")
    public ResponseEntity<ApiResponse<List<CommuneResponse>>> getCommuneByDistrictId(@PathVariable Integer districtId) {
        ApiResponse<List<CommuneResponse>> communes = communeService.getCommuneByDistrictId(districtId);
        return ResponseEntity.ok(communes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCommune(@PathVariable Integer id) {
        communeService.deleteCommune(id);
        return ResponseEntity.ok(new MessageResponse("Commune deleted successfully"));
    }
}
