package com.alpescab.repository;

import java.util.List;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alpescab.model.TarjetaCredito;

@Repository
public interface TarjetaCreditoRepository extends JpaRepository<TarjetaCredito, Long> {
    @Query(value = "SELECT * FROM TARJETACREDITO", nativeQuery = true)
    Collection<TarjetaCredito> darTarjetaCredito();

    @Query(value = "SELECT * FROM TARJETACREDITO t WHERE t.CEDULA = ?1", nativeQuery = true)
    List<TarjetaCredito> findByUsuario_Cedula(String cedula);
}
