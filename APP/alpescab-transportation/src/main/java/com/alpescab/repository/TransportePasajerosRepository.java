package com.alpescab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpescab.model.TransportePasajeros;

@Repository
public interface TransportePasajerosRepository extends JpaRepository<TransportePasajeros, Long> {
}
