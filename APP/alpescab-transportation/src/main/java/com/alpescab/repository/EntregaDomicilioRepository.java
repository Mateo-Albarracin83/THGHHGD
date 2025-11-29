package com.alpescab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.alpescab.model.EntregaDomicilio;

@Repository
public interface EntregaDomicilioRepository extends JpaRepository<EntregaDomicilio, Long> {
}

