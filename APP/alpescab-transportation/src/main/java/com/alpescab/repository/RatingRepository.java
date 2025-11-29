package com.alpescab.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpescab.model.Rating;
import java.util.*;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query(value = "SELECT COALESCE(MAX(IDCOMENTARIO),0) + 1 FROM RATING", nativeQuery = true)
    Long nextId();

    List<Rating> findByRevisado_CedulaOrderByFechaDesc(String revisadoCedula);

    @Query(value = "SELECT revisado, AVG(rating) AS avg_rating, COUNT(*) AS cnt FROM Rating GROUP BY revisado", nativeQuery = true)
    List<Object[]> avgAndCountPerRevisado();

    @Query(value = "SELECT AVG(RATING) FROM Rating WHERE REVISADO = ?1", nativeQuery = true)
    Double avgRatingForRevisado(String revisadoCedula);
}
