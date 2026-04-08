INSERT IGNORE INTO categorias (id, nombre, descripcion) VALUES
(1, 'Abarrotes', 'Productos básicos de despensa'),
(2, 'Bebidas', 'Bebidas gaseosas, jugos y agua'),
(3, 'Limpieza', 'Artículos de limpieza para el hogar');

INSERT IGNORE INTO productos (id, nombre, descripcion, codigo_barras, precio_compra, precio_venta, stock, activo, categoria_id) VALUES
(1, 'Arroz 1kg', 'Arroz blanco premium', '770100000001', 3200.00, 4000.00, 50, true, 1),
(2, 'Aceite 900ml', 'Aceite vegetal', '770100000002', 8500.00, 10500.00, 35, true, 1),
(3, 'Gaseosa Cola 1.5L', 'Bebida gaseosa sabor cola', '770100000003', 4200.00, 5500.00, 40, true, 2),
(4, 'Detergente 500g', 'Detergente en polvo', '770100000004', 5500.00, 7200.00, 20, true, 3);

INSERT IGNORE INTO proveedores (id, nombre, nit, telefono, correo, direccion) VALUES
(1, 'Distribuidora Central', '900123456-1', '3001234567', 'ventas@dcentral.com', 'Cra 10 # 20-30'),
(2, 'Bebidas del Café', '900987654-2', '3019876543', 'contacto@bebidascafe.com', 'Calle 15 # 8-22');

INSERT IGNORE INTO producto_proveedor (proveedor_id, producto_id) VALUES
(1, 1),
(1, 2),
(2, 3);

INSERT IGNORE INTO empleados (id, cedula, nombre, cargo, fecha_ingreso, salario) VALUES
(1, '1099001001', 'Laura Gómez', 'ADMINISTRADOR', '2024-01-15', 3200000.00),
(2, '1099001002', 'Carlos Ramírez', 'CAJERO', '2024-03-10', 1800000.00),
(3, '1099001003', 'María Torres', 'AUXILIAR', '2024-06-05', 1600000.00);

INSERT IGNORE INTO usuarios (id, username, password, rol) VALUES
(1, 'admin', '$2a$10$rnHIkoCEnC6yb7D1JmkTlu2dROKKv/w00c9VRyh0v/ARBIultcs/S', 'ROLE_ADMIN');
