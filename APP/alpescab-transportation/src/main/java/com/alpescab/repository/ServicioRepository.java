package com.alpescab.repository;

import java.util.List;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alpescab.model.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    @Query(value="SELECT * FROM Servicio", nativeQuery=true)
    Collection<Servicio> darServicio();

    @Query(value = "SELECT COALESCE(MAX(IDSERVICIO),0) + 1 FROM SERVICIO", nativeQuery = true)
    Long nextId();

    List<Servicio> findByCliente_CedulaOrderByHoraInicioDesc(String clienteCedula);

    List<Servicio> findByConductor_CedulaOrderByHoraInicioDesc(String conductorCedula);
}
