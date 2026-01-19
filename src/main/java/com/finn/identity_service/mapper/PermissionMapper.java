package com.finn.identity_service.mapper;

import com.finn.identity_service.dto.request.PermissionRequest;
import com.finn.identity_service.dto.request.UserCreationRequest;
import com.finn.identity_service.dto.request.UserUpdateRequest;
import com.finn.identity_service.dto.response.PermissionResponse;
import com.finn.identity_service.dto.response.UserResponse;
import com.finn.identity_service.entity.Permission;
import com.finn.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

}
