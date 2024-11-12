CREATE DATABASE IF NOT EXISTS `tpdesarrollo` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE `tpdesarrollo`;


CREATE TABLE vendedor (
    id_vendedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255),
    latitud DOUBLE,
    longitud DOUBLE
); 

INSERT INTO vendedor (nombre, direccion, latitud, longitud) VALUES
('McDonald\'s', 'Av. de la Ciudad de Barcelona, 45, Madrid', 40.4168, -3.7038),
('Coca-Cola', 'Calle de Recoletos, 22, Madrid', 40.4270, -3.7010),
('Starbucks', 'Calle Gran Vía, 50, Madrid', 40.4237, -3.7049),
('Burger King', 'Calle de Alcalá, 125, Madrid', 40.4210, -3.6861),
('PepsiCo', 'Avenida de América, 4, Madrid', 40.4362, -3.6842);


CREATE TABLE cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    cuit VARCHAR(20) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    direccion VARCHAR(255),
    latitud DOUBLE,
    longitud DOUBLE
);

INSERT INTO cliente (cuit, nombre, email, direccion, latitud, longitud) VALUES{
('20-12345678-9', 'Juan Perez', 'juan.perez@example.com', 'Calle Falsa, 123, Madrid', 40.4165, -3.7026),
('23-87654321-0', 'Maria Gomez', 'maria.gomez@example.com', 'Calle Verdadera, 456, Madrid', 40.4170, -3.7030),
('27-11223344-5', 'Carlos Lopez', 'carlos.lopez@example.com', 'Calle Imaginaria, 789, Madrid', 40.4180, -3.7040),
('30-55667788-1', 'Ana Martinez', 'ana.martinez@example.com', 'Calle Inventada, 101, Madrid', 40.4190, -3.7050),
('33-99887766-2', 'Luis Rodriguez', 'luis.rodriguez@example.com', 'Calle Real, 202, Madrid', 40.4200, -3.7060);

'


CREATE TABLE FormaPago (
    id INT AUTO_INCREMENT PRIMARY KEY,
    montoFinal DOUBLE NOT NULL,
    tipoFormaPago VARCHAR(50) NOT NULL,  
    alias VARCHAR(100),                 
    cuitCliente VARCHAR(20),             
    cbuCliente VARCHAR(20)              
);

CREATE TABLE DetallePedido (
    id INT AUTO_INCREMENT PRIMARY KEY
    
);

CREATE TABLE Pago (
    id INT AUTO_INCREMENT PRIMARY KEY,
    monto DOUBLE NOT NULL,
    formaPago VARCHAR(50) NOT NULL,
    fecha DATE NOT NULL,
    nombreCliente VARCHAR(100),
    cuitCliente VARCHAR(20)
);

CREATE TABLE Pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    clienteId INT,
    estado_pedido VARCHAR(20),
    detallePedidoId INT,
    datosPagoId INT,
    formaPagoId INT,
    FOREIGN KEY (clienteId) REFERENCES Cliente(id_cliente),
    FOREIGN KEY (detallePedidoId) REFERENCES DetallePedido(id),
    FOREIGN KEY (datosPagoId) REFERENCES Pago(id),
    FOREIGN KEY (formaPagoId) REFERENCES FormaPago(id)
);
--Hay que ver que onda item menu