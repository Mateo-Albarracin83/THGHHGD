package com.alpescab.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.alpescab.model.City;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query(value="SELECT * FROM Ciudad", nativeQuery=true)
    Collection<City> darCities();

    @Query(value="SELECT * FROM Ciudad WHERE idCiudad=:idCiudad", nativeQuery=true)
    City darCiudad(@Param("idCiudad") long idCiudad);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO Ciudad (idCiudad, nombre) VALUES(Ciudad_sequence.nextval, :nombre)", nativeQuery = true)
    void insertarCiudad(@Param("nombre") String nombre);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM Ciudad WHERE idCiudad =:idCiudad", nativeQuery = true)
    void eliminarCiudad(@Param("idCiudad") int idCiudad);

}