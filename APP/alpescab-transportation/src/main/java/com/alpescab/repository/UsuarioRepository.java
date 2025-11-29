package com.alpescab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.alpescab.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    @Query(value="SELECT * FROM Usuario", nativeQuery=true)
    Collection<Usuario> darUsuarios();

    @Query(value = "SELECT * FROM Usuario u WHERE u.ROL = 'CONDUCTOR' "
                 + "AND u.CEDULA NOT IN (SELECT s.CONDUCTORCEDULA FROM Servicio s WHERE s.HORAFIN IS NULL) "
                 + "FETCH FIRST 1 ROWS ONLY",
           nativeQuery = true)
    Usuario findFirstAvailableConductor();
}