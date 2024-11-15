INSERT INTO vendedor (id_vendedor, nombre, direccion, latitud, longitud) VALUES
(1, 'McDonald\'s', 'Av. de la Ciudad de Barcelona, 45, Madrid', 40.4168, -3.7038),
(2, 'Coca-Cola', 'Calle de Recoletos, 22, Madrid', 40.4270, -3.7010),
(3, 'Starbucks', 'Calle Gran Vía, 50, Madrid', 40.4237, -3.7049),
(4, 'Burger King', 'Calle de Alcalá, 125, Madrid', 40.4210, -3.6861),
(5, 'PepsiCo', 'Avenida de América, 4, Madrid', 40.4362, -3.6842);

INSERT INTO cliente (id_cliente, cuit, nombre, email, direccion, latitud, longitud) VALUES 
(1, '20-12345678-9', 'Juan Perez', 'juan.perez@example.com', 'Calle Falsa, 123, Madrid', 40.4165, -3.7026),
(2, '23-87654321-0', 'Maria Gomez', 'maria.gomez@example.com', 'Calle Verdadera, 456, Madrid', 40.4170, -3.7030),
(3, '27-11223344-5', 'Carlos Lopez', 'carlos.lopez@example.com', 'Calle Imaginaria, 789, Madrid', 40.4180, -3.7040),
(4, '30-55667788-1', 'Ana Martinez', 'ana.martinez@example.com', 'Calle Inventada, 101, Madrid', 40.4190, -3.7050),
(5, '33-99887766-2', 'Luis Rodriguez', 'luis.rodriguez@example.com', 'Calle Real, 202, Madrid', 40.4200, -3.7060);



INSERT INTO ItemMenu (id, nombre, descripcion, precio, categoria, calorias, aptoCeliaco, aptoVegetariano, aptoVegano, id_vendedor) VALUES
(1, 'Milanesa de Pollo', 'Milanesa de pollo con guarnición', 12.99, 'Plato Principal', 800, true, false, false, 1),
(2, 'Pizza Margarita', 'Pizza con tomate, mozzarella y albahaca', 9.99, 'Plato Principal', 700, true, true, false, 1),
(3, 'Ensalada César', 'Ensalada con pollo, lechuga, crutones y aderezo César', 7.99, 'Plato Principal', 400, true, false, false, 1),
(4, 'Coca-Cola', 'Bebida gaseosa', 1.99, 'Bebida', 150, true, true, true, 2),
(5, 'Cerveza Artesanal', 'Cerveza artesanal de trigo', 3.99, 'Bebida', 200, true, true, true, 2),
(6, 'Jugo de Naranja', 'Jugo de naranja natural', 2.99, 'Bebida', 100, true, true, true, 2);

INSERT INTO Plato (id_item, peso) VALUES
(1, 500),
(2, 600),
(3, 300);

INSERT INTO Bebida (id_item, graduacionAlcoholica, volumenEnMl) VALUES
(4, 0, 500),
(5, 5.0, 330),
(6, 0, 250);