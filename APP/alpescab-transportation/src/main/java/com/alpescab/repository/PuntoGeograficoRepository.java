package com.alpescab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.alpescab.model.PuntoGeografico;

public interface PuntoGeograficoRepository extends JpaRepository<PuntoGeografico, Long> {
}
