package com.alpescab.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpescab.model.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {
    @Query(value="SELECT * FROM Vehiculo", nativeQuery=true)
    Collection<Vehiculo> darVehiculos();

    @Query(value = "SELECT * FROM Vehiculo v WHERE v.CEDULACONDUCTOR = ?1 FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    Vehiculo findFirstByConductorCedula(Long conductorCedula);
}