SELECT s.conductorCedula,
       s.placa,
       COUNT(*) AS servicios_count,
       SUM(NVL(s.valorServicio,0)) AS brutoRecibido
FROM Servicio s
WHERE s.conductorCedula IS NOT NULL
GROUP BY s.conductorCedula, s.placa
ORDER BY brutoRecibido DESC;