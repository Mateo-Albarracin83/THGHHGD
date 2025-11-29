package com.alpescab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpescab.model.TransporteMercancias;

@Repository
public interface TransporteMercanciasRepository extends JpaRepository<TransporteMercancias, Long> {
}

