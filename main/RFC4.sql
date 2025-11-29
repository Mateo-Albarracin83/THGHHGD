WITH servicios_ciudad AS (
  SELECT DISTINCT s.idServicio,
         s.tipo,
         tp.nivelTransporte
  FROM Servicio s
  JOIN ServicioPunto sp ON sp.idServicio = s.idServicio
  JOIN PuntoGeografico pg ON pg.idPunto = sp.idPunto
  LEFT JOIN TransportePasajeros tp ON tp.idServicio = s.idServicio
  WHERE pg.ciudad = '1' AND TRUNC(s.horaInicio) BETWEEN TO_DATE('2024-06-01', 'YYYY-MM-DD') AND TO_DATE('2025-12-30', 'YYYY-MM-DD'))

SELECT tipo,
       NVL(nivelTransporte, 'N/A') AS nivelTransporte,
       COUNT(*) AS cantidad_servicios,
       ROUND( COUNT(*) * 100.0 / SUM(COUNT(*)) OVER (), 2 ) AS porcentaje_del_total
FROM servicios_ciudad
GROUP BY tipo, nivelTransporte
ORDER BY cantidad_servicios DESC;
