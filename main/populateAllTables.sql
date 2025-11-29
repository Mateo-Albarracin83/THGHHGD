-- Script para poblar todas las tablas respetando las dependencias

-- Poblar la tabla AlpesCab
BEGIN
    FOR i IN 1..10 LOOP
        BEGIN
            INSERT INTO AlpesCab (idSistema, nombre)
            VALUES (i, 'Sistema ' || i);
        EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN NULL;
        END;
    END LOOP;
    COMMIT;
END;
/

-- Poblar la tabla Ciudad
BEGIN
    FOR i IN 1..10 LOOP
        BEGIN
            INSERT INTO Ciudad (nombre)
            VALUES ('Ciudad ' || i);
        EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN NULL;
        END;
    END LOOP;
    COMMIT;
END;
/

-- Poblar la tabla PuntoGeografico
BEGIN
    FOR i IN 1..10 LOOP
        BEGIN
            INSERT INTO PuntoGeografico (idPunto, nombre, direccion, latitud, longitud, ciudad)
            VALUES (i, 'Punto ' || i, 'Direccion ' || i, 4.6 + i / 100, -74.1 - i / 100, i);
        EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN NULL;
        END;
    END LOOP;
    COMMIT;
END;
/

-- Poblar la tabla Usuario
BEGIN
    FOR i IN 1..100 LOOP
        BEGIN
            INSERT INTO Usuario (cedula, nombre, correo, celular, tipo, calificacion, comentario)
            VALUES ('Cedula' || i, 'Usuario ' || i, 'usuario' || i || '@correo.com', '300' || LPAD(i, 7, '0'), 'Tipo ' || MOD(i, 3), MOD(i, 5) + 1, 'Comentario ' || i);
        EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN NULL;
        END;
    END LOOP;
    COMMIT;
END;
/

-- Poblar la tabla Vehiculo
BEGIN
    FOR i IN 1..100 LOOP
        BEGIN
            INSERT INTO Vehiculo (placa, cedula, tipo, marca, modelo, color, capacidadPasajeros, ciudadExpedicion, registrado)
            VALUES ('PLACA' || LPAD(i, 4, '0'), 'Cedula' || i, 'Tipo ' || CHR(65 + MOD(i, 3)), 'Marca ' || CHR(65 + MOD(i, 5)), 'Modelo ' || i, 'Color ' || CHR(65 + MOD(i, 7)), MOD(i, 7) + 1, MOD(i, 10) + 1, MOD(i, 2));
        EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN NULL;
        END;
    END LOOP;
    COMMIT;
END;
/

-- Poblar la tabla Servicio
BEGIN
    FOR i IN 1..100 LOOP
        BEGIN
            INSERT INTO Servicio (idServicio, tipo, distancia, tarifa, horaInicio, horaFin, placa, clienteCedula, conductorCedula)
            VALUES (i, CASE WHEN i BETWEEN 1 AND 33 THEN 'Domicilio' WHEN i BETWEEN 34 AND 66 THEN 'Mercancia' ELSE 'Pasajero' END, i * 2, i * 3, SYSTIMESTAMP + NUMTODSINTERVAL(i,'DAY'), SYSTIMESTAMP + NUMTODSINTERVAL(i+1,'DAY'), 'PLACA' || LPAD(i, 4, '0'), 'Cedula' || i, CASE WHEN i <= 50 THEN 'Cedula' || i ELSE NULL END);
        EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN NULL;
        END;
    END LOOP;
    COMMIT;
END;
/

-- Poblar la tabla Disponibilidad
BEGIN
    FOR i IN 1..100 LOOP
        BEGIN
            INSERT INTO Disponibilidad (fecha, placa, horaInicio, horaFin, tipoServicio)
            VALUES (TRUNC(SYSDATE) + MOD(i,30), 'PLACA' || LPAD(MOD(i-1,100)+1,4,'0'), TO_TIMESTAMP(TO_CHAR(TRUNC(SYSDATE) + MOD(i,30),'YYYY-MM-DD') || ' 08:00:00','YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP(TO_CHAR(TRUNC(SYSDATE) + MOD(i,30),'YYYY-MM-DD') || ' 12:00:00','YYYY-MM-DD HH24:MI:SS'), CASE WHEN MOD(i,3)=0 THEN 'PASAJEROS' WHEN MOD(i,3)=1 THEN 'MERCANCIAS' ELSE 'DOMICILIO' END);
        EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN NULL;
        END;
    END LOOP;
    COMMIT;
END;
/

-- Poblar la tabla EntregaDomicilio
BEGIN
    FOR i IN 1..20 LOOP
        BEGIN
            INSERT INTO EntregaDomicilio (idServicio, tarifaDomicilio, ubicacionRestaurante, puntoUsuario)
            VALUES (i, 1, 'Direccion ' || i, 'Direccion ' || (i + 100));
        EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN NULL;
        END;
    END LOOP;
    COMMIT;
END;
/

-- Poblar la tabla Rating
BEGIN
    FOR i IN 1..10 LOOP
        BEGIN
            INSERT INTO Rating (idComentario, idServicio, revisor, revisado, rating, comentario, fecha)
            VALUES (i, i, 'Cedula' || (i + 1) , 'Cedula' || (i+2), MOD(i, 5) + 1, 'Comentario ' || i, SYSDATE - i);
        EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN NULL;
        END;
    END LOOP;
    COMMIT;
END;
/

-- Poblar la tabla TarjetaCredito
BEGIN
    FOR i IN 1..10 LOOP
        BEGIN
            INSERT INTO TarjetaCredito (numero, cedula, codigoSeguridad, nombre, fechaVencimiento)
            VALUES (i, 'Cedula' || i, '123', 'Nombre ' || i, SYSDATE + INTERVAL '1' YEAR);
        EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN NULL;
        END;
    END LOOP;
    COMMIT;
END;
/

-- Poblar la tabla TransporteMercancias
BEGIN
    FOR i IN 1..10 LOOP
        BEGIN
            INSERT INTO TransporteMercancias (idServicio, tarifaMercancias)
            VALUES (i, i * 15);
        EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN NULL;
        END;
    END LOOP;
    COMMIT;
END;
/

-- Poblar la tabla TransportePasajeros
BEGIN
  FOR i IN 1..20 LOOP
    BEGIN
      INSERT INTO TransportePasajeros (idServicio, tarifaPasajeros, nivelTransporte)
      VALUES (i, DBMS_RANDOM.VALUE(300,3000), CASE WHEN i IN (3,6,9,12,15,18) THEN 'Estandar' WHEN i IN (1,4,7,10,13,16,19) THEN 'Confort' ELSE 'Large' END);
    EXCEPTION WHEN DUP_VAL_ON_INDEX THEN NULL;
    END;
  END LOOP;
  COMMIT;
END;
/

-- Poblar la tabla UsuarioCliente
BEGIN
    FOR i IN 1..10 LOOP
        BEGIN
            INSERT INTO UsuarioCliente (cedulaCliente)
            VALUES ('Cedula' || i);
        EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN NULL;
        END;
    END LOOP;
    COMMIT;
END;
/

-- Poblar la tabla UsuarioCoductor
BEGIN
    FOR i IN 1..10 LOOP
        BEGIN
            INSERT INTO UsuarioCoductor (cedulaConductor)
            VALUES ('Cedula' || i);
        EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN NULL;
        END;
    END LOOP;
    COMMIT;
END;
/

-- Poblar la tabla ServicioPunto 
BEGIN
    FOR i IN 1..10 LOOP
        BEGIN
            INSERT INTO ServicioPunto (idServicioPunto, idServicio, idPunto)
            VALUES (i, i, MOD(i-1,10) + 1);
        EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN
                NULL;
        END;
    END LOOP;
    COMMIT;
END;
/