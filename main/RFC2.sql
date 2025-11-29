SELECT u.cedula,
       u.nombre,
       COUNT(*) AS servicios_prestados
FROM Servicio s
JOIN Usuario u ON u.cedula = s.conductorCedula
GROUP BY u.cedula, u.nombre
ORDER BY servicios_prestados DESC
FETCH FIRST 20 ROWS ONLY;
