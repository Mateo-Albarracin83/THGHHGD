SELECT s.idServicio,
       s.tipo,
       s.distancia,
       s.tarifa,
       s.horaInicio,
       s.horaFin,
       s.conductorCedula,
       s.placa,
       tp.tarifaPasajeros,
       tme.tarifaMercancias,
        ed.tarifaDomicilio
FROM Servicio s
LEFT JOIN TransportePasajeros tp ON tp.idServicio = s.idServicio
LEFT JOIN TransporteMercancias tme ON tme.idServicio = s.idServicio
LEFT JOIN EntregaDomicilio ed ON ed.idServicio = s.idServicio
WHERE s.clienteCedula = 'Cedula1'
ORDER BY s.horaInicio DESC;
