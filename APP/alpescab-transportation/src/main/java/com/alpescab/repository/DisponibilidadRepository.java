package com.alpescab.repository;

import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import com.alpescab.model.Disponibilidad;
import com.alpescab.model.Vehiculo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Long> {
    @Query(value="SELECT * FROM Disponibilidad", nativeQuery=true)
    Collection<Disponibilidad> darDisponibilidad();

    @Query("SELECT d FROM Disponibilidad d WHERE d.vehiculo.conductor.cedula = :cedula")
    List<Disponibilidad> findByConductorCedula(@Param("cedula") String cedula);

    @Query(value = "SELECT * FROM Disponibilidad d " +
                   "WHERE UPPER(d.TIPOSERVICIO) = ?1 " +
                   "  AND d.FECHA = ?2 " +
                   "  AND ?3 BETWEEN d.HORAINICIO AND d.HORAFIN " +
                   "  AND NVL(d.ASIGNADA,0) = 0 " +
                   "FETCH FIRST 1 ROWS ONLY",
           nativeQuery = true)
    Optional<Disponibilidad> findFirstAvailableForTipoAndFechaAndTime(String tipoPrefix, LocalDate fecha, LocalDateTime hora);

    @Query("""
        SELECT d FROM Disponibilidad d
        WHERE d.vehiculo.conductor.cedula = :cedula
          AND d.fecha = :fecha
          AND d.idDisponibilidad <> :id
          AND (:horaInicio < d.horaFin AND :horaFin > d.horaInicio)
        """)
    List<Disponibilidad> findOverlapping(
        @Param("cedula") String cedula,
        @Param("fecha") LocalDate fecha,
        @Param("horaInicio") LocalDateTime horaInicio,
        @Param("horaFin") LocalDateTime horaFin,
        @Param("id") Long id
    );

    Optional<Disponibilidad> findFirstByVehiculo_PlacaAndFechaAndHoraInicioLessThanEqualAndHoraFinGreaterThanEqualAndAsignada(
            String placa, LocalDate fecha, LocalDateTime hora, LocalDateTime hora2, Integer asignada);
}
