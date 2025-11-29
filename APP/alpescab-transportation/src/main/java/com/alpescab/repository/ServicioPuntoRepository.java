package com.alpescab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alpescab.model.ServicioPunto;

import java.util.List;

@Repository
public interface ServicioPuntoRepository extends JpaRepository<ServicioPunto, Long> {
    @Query(value = "SELECT COALESCE(MAX(IDSERVICIOPUNTO),0) + 1 FROM SERVICIOPUNTO", nativeQuery = true)
    Long nextId();

    List<ServicioPunto> findByServicio_IdServicio(Long idServicio);
}
