SET SERVEROUTPUT ON;

INSERT INTO Ciudad (idCiudad, nombre) VALUES (1, 'Bogotá');
INSERT INTO Ciudad (idCiudad, nombre) VALUES (2, 'Medellín');

COMMIT;

INSERT INTO PuntoGeografico (nombre, direccion, latitud, longitud, idCiudad)
VALUES ('ParqueCentral', 'Cra 10 #20-30', 4.609710, -74.081750, 1);

INSERT INTO PuntoGeografico (nombre, direccion, latitud, longitud, idCiudad)
VALUES ('EstacionNorte', 'Av 15 #40-20', 4.620000, -74.070000, 1);

INSERT INTO PuntoGeografico (nombre, direccion, latitud, longitud, idCiudad)
VALUES ('CentroMedellin', 'Cl 50 #10-5', 6.244203, -75.581211, 2);

COMMIT;

INSERT INTO Usuario (cedula, nombre, correo, celular, tipo, calificacion_promedio)
VALUES ('1098765432','Ana Pérez','ana@ej.com','3001234567','CLIENTE', 4.86);

INSERT INTO Usuario (cedula, nombre, correo, celular, tipo, calificacion_promedio)
VALUES ('205555333','Carlos Conductor','carlos@ej.com','3115553333','CONDUCTOR', 4.92);

COMMIT;

INSERT INTO UsuarioCliente (cedula) VALUES ('1098765432');
INSERT INTO UsuarioConductor (cedula) VALUES ('205555333','LIC-205555333');

COMMIT;

INSERT INTO Vehiculo (placa, cedula, tipo, marca, modelo, color, capacidadPasajeros, ciudadExpedicion, registrado)
VALUES ('ABC123','205555333','VAN','Hyundai','i30','Blanco',4,1,'STANDARD',1);

COMMIT;

INSERT INTO TarjetaCredito (numero, cedulaUsuario, codigoSeguridad, nombre, fechaVencimiento)
VALUES ('29959291354', '1098765432', '1234', 'ANA PEREZ', DATE '2027-05-31');

COMMIT;

INSERT INTO TarjetaCredito (numero, cedulaUsuario, codigoSeguridad, nombre, fechaVencimiento)
VALUES ('90284781204', '1098765432', '5678', 'ANA PEREZ', DATE '2028-03-31');

SELECT numero, cedulaUsuario, codigoSeguridad, nombre, fechaVencimiento
FROM TarjetaCredito
WHERE cedulaUsuario = '1098765432';

INSERT INTO TarjetaCredito (numero, cedulaUsuario, codigoSeguridad, nombre, fechaVencimiento)
VALUES ('39938192508', '1098765432', '4321', 'ANA PEREZ', DATE '2026-11-30');

COMMIT;

INSERT INTO TarjetaCredito (numero, cedulaUsuario, codigoSeguridad, nombre, fechaVencimiento)
VALUES ('00000000000', '0000000000', '0000', 'INV', DATE '2026-01-01');

INSERT INTO Servicio (idServicio, tipo, distancia, valorServicio, horaInicio, horaFin, cedula)
VALUES (1, 'PASAJEROS', 5.0, 15000.00, TO_TIMESTAMP('2025-08-25 15:00:00','YYYY-MM-DD HH24:MI:SS'), NULL, '1098765432');

COMMIT;

INSERT INTO Revision (idComentario, idServicio, revisor, revisado, rating, comentario)
VALUES (2001, 1, '1098765432', '205555333', 5.0, 'Excelente servicio');

COMMIT;

INSERT INTO Revision (idComentario, idServicio, revisor, revisado, rating, comentario)
VALUES (2002, 1, '1098765432', '205555333', 6.0, 'Invalido');

INSERT INTO Disponibilidad (disponibilidad_id, vehiculo_placa, fecha, hora_inicio, hora_fin, tipo_servicio)
VALUES (3001, 'ABC123', DATE '2025-09-01', TO_TIMESTAMP('2025-09-01 08:00:00','YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-09-01 12:00:00','YYYY-MM-DD HH24:MI:SS'), 'PASAJEROS');

COMMIT;

INSERT INTO Disponibilidad (placa, dia, horaInicio, horaFin, tipoServicio)
VALUES ('ABC123', DATE '2025-09-01', TO_TIMESTAMP('2025-09-01 11:00:00','YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-09-01 13:00:00','YYYY-MM-DD HH24:MI:SS'), 'PASAJEROS');

SELECT d1.dia AS id1, d2.dia AS id2, d1.placa
FROM Disponibilidad d1
JOIN Disponibilidad d2 ON d1.placa = d2.placa
AND d1.dia = d2.dia
AND d1.dia <> d2.dia
WHERE NOT (d1.horaFin <= d2.horaInicio OR d1.horaInicio >= d2.horaFin);

INSERT INTO Servicio (idServicio, tipo, distancia, valorServicio, horaInicio, horaFin, cedula)
VALUES (4001, 'PASAJEROS', 10.0, NULL, NULL, NULL, '1098765432');

COMMIT;

UPDATE Servicio
SET cedula = '205555333', placa = 'ABC123'
WHERE idServicio = 4001;

COMMIT;

UPDATE Servicio SET horaInicio = SYSTIMESTAMP WHERE idServicio = 4001;

UPDATE Servicio SET horaFin = SYSTIMESTAMP, distancia = 11.2 WHERE idServicio = 4001;

COMMIT;

SELECT idServicio, tipo, distancia, valorServicio FROM Servicio WHERE idServicio = 4001;

UPDATE Servicio SET valorServicio = distancia * 3000 WHERE idServicio = 4001;
COMMIT;
SELECT idServicio, distancia, valorServicio FROM Servicio WHERE idServicio = 4001;

SELECT cedula, COUNT(*) AS servicios_finalizados
FROM Servicio
WHERE conductor_cedula IS NOT NULL
GROUP BY conductor_cedula
ORDER BY servicios_finalizados DESC
FETCH FIRST 20 ROWS ONLY;

SELECT placa, SUM(valorServicio) AS total_recaudado
FROM Servicio
WHERE valor_servicio IS NOT NULL
GROUP BY placa
ORDER BY total_recaudado DESC;

SELECT c.nombre AS ciudad, COUNT(DISTINCT s.idServicio) AS servicios
FROM Servicio s
JOIN ServicioPunto sp ON s.idServicio = sp.idServicio
JOIN PuntoGeografico p ON sp.direccion = p.direccion
JOIN Ciudad c ON p.idCiudad = c.idCiudad
WHERE sp.tipo = 'PARTIDA'
GROUP BY c.nombre
ORDER BY servicios DESC;

SELECT placa, SUM(valorServicio) AS total
FROM Servicio
GROUP BY placa
ORDER BY total DESC
FETCH FIRST 10 ROWS ONLY;

INSERT INTO Usuario (cedula, nombre, correo, celular, tipo)
VALUES ('1098765432','Ana Duplicada','ana2@ej.com','3000000000','CLIENTE');

INSERT INTO Servicio (idServicio, tipo, distancia, cedula)
VALUES (5001, 'DOMICILIO', 3.0, '9999999999');

INSERT INTO EntregaDomicilio (idServicio, tarifaDomicilio, ubicacionRestaurante, puntoUsuario)
VALUES (6001, 8000.00, 9999, 2);

COMMIT;
