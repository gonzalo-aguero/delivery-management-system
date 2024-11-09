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

