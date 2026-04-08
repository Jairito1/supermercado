CREATE DATABASE IF NOT EXISTS supermercado
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE supermercado;

CREATE TABLE IF NOT EXISTS categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion VARCHAR(255),
    codigo_barras VARCHAR(50) NOT NULL UNIQUE,
    precio_compra DECIMAL(12,2) NOT NULL,
    precio_venta DECIMAL(12,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    categoria_id BIGINT NOT NULL,
    CONSTRAINT fk_producto_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS proveedores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    nit VARCHAR(30) NOT NULL UNIQUE,
    telefono VARCHAR(30),
    correo VARCHAR(120),
    direccion VARCHAR(255)
) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS producto_proveedor (
    proveedor_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    PRIMARY KEY (proveedor_id, producto_id),
    CONSTRAINT fk_pp_proveedor FOREIGN KEY (proveedor_id) REFERENCES proveedores(id),
    CONSTRAINT fk_pp_producto FOREIGN KEY (producto_id) REFERENCES productos(id)
) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS empleados (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cedula VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(150) NOT NULL,
    cargo VARCHAR(30) NOT NULL,
    fecha_ingreso DATE NOT NULL,
    salario DECIMAL(12,2) NOT NULL
) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS ventas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_venta DATETIME NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    iva DECIMAL(12,2) NOT NULL,
    total DECIMAL(12,2) NOT NULL,
    empleado_id BIGINT NOT NULL,
    CONSTRAINT fk_venta_empleado FOREIGN KEY (empleado_id) REFERENCES empleados(id)
) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS detalle_ventas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(12,2) NOT NULL,
    subtotal_linea DECIMAL(12,2) NOT NULL,
    producto_id BIGINT NOT NULL,
    venta_id BIGINT NOT NULL,
    CONSTRAINT fk_detalle_producto FOREIGN KEY (producto_id) REFERENCES productos(id),
    CONSTRAINT fk_detalle_venta FOREIGN KEY (venta_id) REFERENCES ventas(id)
) ENGINE=InnoDB;

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
