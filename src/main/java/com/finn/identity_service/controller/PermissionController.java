package com.finn.identity_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.finn.identity_service.dto.request.ApiResponse;
import com.finn.identity_service.dto.request.PermissionRequest;
import com.finn.identity_service.dto.response.PermissionResponse;
import com.finn.identity_service.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResponse<String> deletePermission(@PathVariable String permission) {
        permissionService.delete(permission);
        return ApiResponse.<String>builder()
                .result("Permission has been deleted")
                .build();
    }
}
