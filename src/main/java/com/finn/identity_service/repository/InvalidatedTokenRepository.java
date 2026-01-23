package com.finn.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finn.identity_service.entity.InvalidatedToken;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {}
