package com.a14.emart.backendsp.controller;

import com.a14.emart.backendsp.model.Supermarket;
import com.a14.emart.backendsp.service.CreateService;
import com.a14.emart.backendsp.service.ReadService;
import com.a14.emart.backendsp.service.UpdateService;
import com.a14.emart.backendsp.service.DeleteService;
import com.a14.emart.backendsp.controller.ApiResponse;
import com.a14.emart.backendsp.dto.SupermarketResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/supermarket")
public class SupermarketController {

    @Autowired
    private CreateService<Supermarket> createService;

    @Autowired
    private ReadService<Supermarket> readService;

    @Autowired
    private UpdateService<Supermarket> updateService;

    @Autowired
    private DeleteService<Supermarket> deleteService;


    public SupermarketResponse buildSingleResponse(Supermarket supermarket){
        return new SupermarketResponse(supermarket);
    }
    public List<SupermarketResponse> buildMultipleResponses(List<Supermarket> supermarkets){
        List<SupermarketResponse> supermarketResponses = new ArrayList<>();
        for(Supermarket supermarket : supermarkets){
            supermarketResponses.add(new SupermarketResponse(supermarket));
        }
        return supermarketResponses;
    }
    @PostMapping
    public ResponseEntity<ApiResponse<SupermarketResponse>> createSupermarket(@RequestBody Supermarket supermarket) {
        try {
            Supermarket created = createService.create(supermarket);
            if (created == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<SupermarketResponse>(false, null, "Failed to create supermarket. Input may be invalid."));
            }
            return ResponseEntity.ok(new ApiResponse<SupermarketResponse>(true, buildSingleResponse(created), "Supermarket created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<SupermarketResponse>(false, null, "Internal Server Error: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SupermarketResponse>>> getAllSupermarkets() {
        try {
            List<Supermarket> supermarkets = readService.findAll();
            return ResponseEntity.ok(new ApiResponse<List<SupermarketResponse>>(true, buildMultipleResponses(supermarkets), "Successfully retrieve data!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<List<SupermarketResponse>>(false, null, "Internal Server Error: " + e.getMessage()));
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SupermarketResponse>> getSupermarketById(@PathVariable UUID id) {
        try{
            Supermarket supermarket = readService.findById(id);
            if (supermarket == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<SupermarketResponse>(false, null, "Supermarket with ID " + id + " does not exist!" ));
            }
            return ResponseEntity.ok(new ApiResponse<SupermarketResponse>(true, buildSingleResponse(supermarket), "Supermarket found"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<SupermarketResponse>(false, null, "Internal Server Error"));
        }

    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SupermarketResponse>>> searchSupermarkets(@RequestParam String query) {
        try {
            List<Supermarket> supermarkets = readService.findByMatch(query);
            if (supermarkets.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse<List<SupermarketResponse>>(false, null, "No supermarkets found matching the query"));
            }
            return ResponseEntity.ok(new ApiResponse<List<SupermarketResponse>>(true, buildMultipleResponses(supermarkets), "Supermarkets found successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<List<SupermarketResponse>>(false, null, "Internal Server Error during search: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SupermarketResponse>> updateSupermarket(@PathVariable UUID id, @RequestBody Supermarket supermarket) {
        try {
            Supermarket updated = updateService.update(id, supermarket);
            if (updated == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<SupermarketResponse>(false, null, "Supermarket with ID " + id + " not found"));
            }

            return ResponseEntity.ok(new ApiResponse<SupermarketResponse>(true, buildSingleResponse(updated), "Supermarket updated successfully"));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<SupermarketResponse>(false, null, "Failed to update supermarket: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSupermarket(@PathVariable UUID id) {
        boolean deleted = deleteService.deleteById(id);
        if (!deleted) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<Void>(false, null, "Supermarket with ID " + id + " not found"));
        }

        return ResponseEntity
                .ok(new ApiResponse<Void>(true, null, "Supermarket deleted successfully"));
    }
}
