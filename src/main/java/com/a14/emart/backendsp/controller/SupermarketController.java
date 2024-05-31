package com.a14.emart.backendsp.controller;

import com.a14.emart.backendsp.event.SupermarketCreatedEvent;
import com.a14.emart.backendsp.model.Supermarket;
import com.a14.emart.backendsp.model.SupermarketBuilder;
import com.a14.emart.backendsp.service.CreateService;
import com.a14.emart.backendsp.service.ReadService;
import com.a14.emart.backendsp.service.UpdateService;
import com.a14.emart.backendsp.service.DeleteService;
import com.a14.emart.backendsp.controller.ApiResponse;
import com.a14.emart.backendsp.dto.SupermarketResponse;
import com.a14.emart.backendsp.service.JwtService;
import com.a14.emart.backendsp.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/supermarket")
@RequiredArgsConstructor
public class SupermarketController {

    @Autowired
    private CreateService<Supermarket> createService;

    @Autowired
    private ReadService<Supermarket> readService;

    @Autowired
    private UpdateService<Supermarket> updateService;

    @Autowired
    private DeleteService<Supermarket> deleteService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final JwtService jwtService;
    private final CloudinaryService cloudinaryService;


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
    public ResponseEntity<ApiResponse<SupermarketResponse>> createSupermarket(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("pengelola") Long pengelola,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestHeader("Authorization") String token) {
        try {
            String tokenWithoutBearer = token.replace("Bearer ", "");
            String role = jwtService.extractRole(tokenWithoutBearer);
            System.out.println(role);

            if (!role.equalsIgnoreCase("admin")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse<>(false, null, "You have no access."));
            }

            String imageUrl = null;
            if (file != null) {
                imageUrl = cloudinaryService.uploadFile(file);
            }

            Supermarket supermarket = new Supermarket(name, description, pengelola, 0L, 0L, imageUrl);
            Supermarket created = createService.create(supermarket);
            if (created == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(false, null, "Failed to create supermarket. Input may be invalid."));
            }
            return ResponseEntity.ok(new ApiResponse<>(true, new SupermarketResponse(created), "Supermarket created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, null, "Internal Server Error: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SupermarketResponse>>> getAllSupermarkets() {
        try {
            List<Supermarket> supermarkets = readService.findAll();
            return ResponseEntity.ok(new ApiResponse<List<SupermarketResponse>>(true, buildMultipleResponses(supermarkets), "Successfully retrieved data!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<List<SupermarketResponse>>(false, null, "Internal Server Error: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SupermarketResponse>> getSupermarketById(@PathVariable UUID id) {
        try {
            Supermarket supermarket = readService.findById(id);
            if (supermarket == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<SupermarketResponse>(false, null, "Supermarket with ID " + id + " does not exist!"));
            }
            return ResponseEntity.ok(new ApiResponse<SupermarketResponse>(true, buildSingleResponse(supermarket), "Supermarket found"));
        } catch (Exception e) {
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

    @PostMapping("/rating/")
    public ResponseEntity<ApiResponse<Void>> updateRating(
            @RequestParam Long score,
            @RequestParam UUID supermarketId) {
        Supermarket target = readService.findById(supermarketId);
        if (target == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<Void>(false, null, "Supermarket with ID " + supermarketId + " not found"));
        }
        Long totalReview = target.getTotalReview();
        totalReview++;
        Long totalScore = target.getTotalScore();
        totalScore += score;
        Supermarket tempSupermarket = new SupermarketBuilder()
                .setName(target.getName())
                .setDescription(target.getDescription())
                .setPengelola(target.getPengelola())
                .setTotalReview(totalReview)
                .setTotalScore(totalScore)
                .build();
        updateService.update(supermarketId, tempSupermarket);

        return ResponseEntity
                .ok(new ApiResponse<Void>(true, null, "Supermarket has been updated successfully"));
    }

}
