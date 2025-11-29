-- Script para limpiar toda la base de datos eliminando los registros de todas las tablas

-- Deshabilitar restricciones de claves foráneas temporalmente
BEGIN
    EXECUTE IMMEDIATE 'ALTER TABLE EntregaDomicilio DISABLE CONSTRAINT fk_servicio';
    EXECUTE IMMEDIATE 'ALTER TABLE EntregaDomicilio DISABLE CONSTRAINT fk_ubicacion_restaurante';
    EXECUTE IMMEDIATE 'ALTER TABLE EntregaDomicilio DISABLE CONSTRAINT fk_punto_usuario';
    EXECUTE IMMEDIATE 'ALTER TABLE Rating DISABLE CONSTRAINT fk_idServicio';
    EXECUTE IMMEDIATE 'ALTER TABLE Rating DISABLE CONSTRAINT fk_revisor';
    EXECUTE IMMEDIATE 'ALTER TABLE Rating DISABLE CONSTRAINT fk_revisado';
    EXECUTE IMMEDIATE 'ALTER TABLE Servicio DISABLE CONSTRAINT fk_vehiculo_placa';
    EXECUTE IMMEDIATE 'ALTER TABLE Vehiculo DISABLE CONSTRAINT fk_vehiculo_usuario';
    EXECUTE IMMEDIATE 'ALTER TABLE Vehiculo DISABLE CONSTRAINT fk_vehiculo_ciudad';
    EXECUTE IMMEDIATE 'ALTER TABLE PuntoGeografico DISABLE CONSTRAINT fk_ciudad';
    EXECUTE IMMEDIATE 'ALTER TABLE TransporteMercancias DISABLE CONSTRAINT fk_servicio';
    EXECUTE IMMEDIATE 'ALTER TABLE TransportePasajeros DISABLE CONSTRAINT fk_servicio';
    EXECUTE IMMEDIATE 'ALTER TABLE UsuarioCoductor DISABLE CONSTRAINT fk_Usuario';
    EXECUTE IMMEDIATE 'ALTER TABLE TarjetaCredito DISABLE CONSTRAINT fk_tarjetacredito_usuario';
END;
/

-- Eliminar registros de todas las tablas en el orden correcto
BEGIN
    DELETE FROM EntregaDomicilio;
    DELETE FROM Rating;
    DELETE FROM Disponibilidad;
    DELETE FROM TransporteMercancias;
    DELETE FROM TransportePasajeros;
    DELETE FROM Servicio;
    DELETE FROM Vehiculo;
    DELETE FROM UsuarioCoductor;
    DELETE FROM UsuarioCliente;
    DELETE FROM TarjetaCredito;
    DELETE FROM PuntoGeografico;
    DELETE FROM Ciudad;
    DELETE FROM AlpesCab;
    COMMIT;
END;
/

-- Rehabilitar restricciones de claves foráneas
BEGIN
    EXECUTE IMMEDIATE 'ALTER TABLE EntregaDomicilio ENABLE CONSTRAINT fk_servicio';
    EXECUTE IMMEDIATE 'ALTER TABLE EntregaDomicilio ENABLE CONSTRAINT fk_ubicacion_restaurante';
    EXECUTE IMMEDIATE 'ALTER TABLE EntregaDomicilio ENABLE CONSTRAINT fk_punto_usuario';
    EXECUTE IMMEDIATE 'ALTER TABLE Rating ENABLE CONSTRAINT fk_idServicio';
    EXECUTE IMMEDIATE 'ALTER TABLE Rating ENABLE CONSTRAINT fk_revisor';
    EXECUTE IMMEDIATE 'ALTER TABLE Rating ENABLE CONSTRAINT fk_revisado';
    EXECUTE IMMEDIATE 'ALTER TABLE Servicio ENABLE CONSTRAINT fk_vehiculo_placa';
    EXECUTE IMMEDIATE 'ALTER TABLE Vehiculo ENABLE CONSTRAINT fk_vehiculo_usuario';
    EXECUTE IMMEDIATE 'ALTER TABLE Vehiculo ENABLE CONSTRAINT fk_vehiculo_ciudad';
    EXECUTE IMMEDIATE 'ALTER TABLE PuntoGeografico ENABLE CONSTRAINT fk_ciudad';
    EXECUTE IMMEDIATE 'ALTER TABLE TransporteMercancias ENABLE CONSTRAINT fk_servicio';
    EXECUTE IMMEDIATE 'ALTER TABLE TransportePasajeros ENABLE CONSTRAINT fk_servicio';
    EXECUTE IMMEDIATE 'ALTER TABLE UsuarioCoductor ENABLE CONSTRAINT fk_Usuario';
    EXECUTE IMMEDIATE 'ALTER TABLE TarjetaCredito ENABLE CONSTRAINT fk_tarjetacredito_usuario';
END;