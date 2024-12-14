-- Inserción en la tabla 'persona' (padre de 'cliente' y 'vendedor')
INSERT INTO persona (id, nombre, direccion) VALUES
(1, 'Juan Pérez', 'Calle Falsa 123'),
(2, 'María Gómez', 'Avenida Siempre Viva 742'),
(3, 'Carlos López', 'Boulevard de los Sueños 99'),
(4, 'Sofía Martínez', 'Calle del Sol 456'),
(5, 'Pedro Sánchez', 'Paseo de la Luna 789');

-- Inserción en la tabla 'coordenada' (relacionada con 'persona')
INSERT INTO coordenada (id, latitud, longitud, persona_id) VALUES
(1, 40.4168, -3.7038, 1),
(2, 40.4379, -3.6796, 2),
(3, 40.4520, -3.6878, 3),
(4, 40.4260, -3.6860, 4),
(5, 40.4300, -3.7000, 5);

-- Inserción en la tabla 'cliente' (hereda de 'persona')
INSERT INTO cliente (id, cuit, email) VALUES
(1, '20-12345678-9', 'juan.perez@example.com'),
(2, '23-87654321-0', 'maria.gomez@example.com'),
(3, '27-11223344-5', 'carlos.lopez@example.com');

-- Inserción en la tabla 'vendedor' (hereda de 'persona')
INSERT INTO vendedor (id) VALUES
(4),
(5);

-- Inserción en la tabla 'categoria'
INSERT INTO categoria (id, descripcion, tipo_item) VALUES
(1, 'Bebidas sin alcohol', 'BEBIDA'),
(2, 'Bebidas alcohólicas', 'BEBIDA'),
(3, 'Entrantes', 'COMIDA'),
(4, 'Platos principales', 'COMIDA'),
(5, 'Postres', 'COMIDA');

-- Inserción en la tabla 'itemmenu'
INSERT INTO itemmenu (id, nombre, descripcion, precio, apto_celiaco, apto_vegetariano, apto_vegano, calorias, categoria_id, vendedor_id) VALUES
(1, 'Agua Mineral', 'Agua embotellada', 1.50, b'1', b'1', b'1', 0, 1, 4),
(2, 'Cerveza Artesanal', 'Cerveza local', 3.50, b'1', b'0', b'0', 150, 2, 4),
(3, 'Ensalada César', 'Lechuga, pollo y aderezo', 5.00, b'1', b'0', b'0', 350, 3, 4),
(4, 'Hamburguesa Clásica', 'Carne, queso y bacon', 8.00, b'0', b'0', b'0', 800, 4, 4),
(5, 'Tarta de Queso', 'Postre casero', 4.50, b'1', b'1', b'0', 400, 5, 4),
(6, 'Zumo de Naranja', 'Zumo natural', 2.00, b'1', b'1', b'1', 100, 1, 5),
(7, 'Vino Tinto', 'Copa de vino', 4.00, b'1', b'1', b'1', 120, 2, 5);

-- Inserción en la tabla 'bebida' (extiende 'itemmenu')
INSERT INTO bebida (id, volumen_en_ml, graduacion_alcoholica) VALUES
(1, 500, 0),
(2, 330, 5),
(6, 250, 0),
(7, 200, 13);

-- Inserción en la tabla 'plato' (extiende 'itemmenu')
INSERT INTO plato (id, peso) VALUES
(3, 300),
(4, 450),
(5, 200);

-- Inserción en la tabla 'pedido'
INSERT INTO pedido (id, cliente_id, forma_pago_tipo, estado) VALUES
(1, 1, 'Tarjeta', 'EN_PROCESO'),
(2, 2, 'Efectivo', 'PENDIENTE_DE_PAGO'),
(3, 3, 'Tarjeta', 'FINALIZADO');


-- Inserción en la tabla 'detallepedido' (relaciona 'pedido' e 'itemmenu')
INSERT INTO detallepedido (detalle_pedido_id, pedido_id, itemmenu_id, cantidad) VALUES
(1, 1, 4, 2),
(2, 1, 1, 1),
(3, 2, 3, 1),
(4, 2, 2, 2),
(5, 3, 5, 1),
(6, 3, 6, 2);

-- Inserción en la tabla 'pago' (relacionada con 'pedido')
INSERT INTO pago (pedido_id, monto, fecha, forma_pago, nombre_cliente, cuit_cliente) VALUES
(3, 25.00, '2023-12-14 12:00:00', 'Tarjeta', 'Carlos López', '27-11223344-5');
