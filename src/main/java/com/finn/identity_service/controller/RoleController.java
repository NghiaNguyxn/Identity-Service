package com.finn.identity_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.finn.identity_service.dto.request.ApiResponse;
import com.finn.identity_service.dto.request.RoleRequest;
import com.finn.identity_service.dto.response.RoleResponse;
import com.finn.identity_service.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService RoleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(RoleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(RoleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<String> deleteRole(@PathVariable String role) {
        RoleService.delete(role);
        return ApiResponse.<String>builder().result("Role has been deleted").build();
    }
}
