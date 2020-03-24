DROP database IF EXISTS fresno;

CREATE database fresno;

USE fresno;

CREATE TABLE puesto(
	id INT NOT NULL auto_increment PRIMARY KEY,
	nombre VARCHAR(50) NOT NULL,
	salario FLOAT NOT NULL,
	fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion DATETIME,
	fecha_eliminacion DATETIME
);

CREATE TABLE usuario(
	id INT NOT NULL auto_increment PRIMARY KEY,
	tipo VARCHAR(10) NOT NULL,
	fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion DATETIME,
	fecha_eliminacion DATETIME
);

CREATE TABLE proveedor(
	id INT NOT NULL auto_increment PRIMARY KEY,
	nombre VARCHAR(100) NOT NULL,
	telefono VARCHAR(10) UNIQUE,
	fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion DATETIME,
	fecha_eliminacion DATETIME
);

CREATE TABLE categoria(
	id INT NOT NULL auto_increment PRIMARY KEY,
	nombre VARCHAR(100) NOT NULL,
	fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion DATETIME,
	fecha_eliminacion DATETIME
);

CREATE TABLE producto(
	id INT NOT NULL auto_increment PRIMARY KEY,
	id_categoria INT NOT NULL,
	codigo_barra VARCHAR(20) UNIQUE,
	nombre VARCHAR(100) NOT NULL,
	marca VARCHAR(100) NOT NULL,
	precio_compra FLOAT NOT NULL,
	precio_venta FLOAT NOT NULL,
	existencia INT NOT NULL,
	tamano VARCHAR(10),
	fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion DATETIME,
	fecha_eliminacion DATETIME,
	FOREIGN KEY(id_categoria) REFERENCES categoria(id) ON UPDATE CASCADE
);

CREATE TABLE empleado(
	id INT NOT NULL auto_increment PRIMARY KEY,
	id_puesto INT NOT NULL,
	id_usuario INT NOT NULL,
	nombre VARCHAR(20) NOT NULL,
	apellido_paterno VARCHAR(15) NOT NULL,
	apellido_materno VARCHAR(15) NOT NULL,
	telefono VARCHAR(10) UNIQUE,
	contrasena VARCHAR(255) NOT NULL,
	fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion DATETIME,
	fecha_eliminacion DATETIME,
	FOREIGN KEY(id_puesto) REFERENCES puesto(id) ON UPDATE CASCADE,
	FOREIGN KEY(id_usuario) REFERENCES usuario(id) ON UPDATE CASCADE
);

CREATE TABLE venta(
	id BIGINT NOT NULL auto_increment PRIMARY KEY,
	id_empleado INT NOT NULL,
	cliente BIGINT NOT NULL UNIQUE,
	efectivo FLOAT NOT NULL,
	total FLOAT NOT NULL,
	fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion DATETIME,
	fecha_eliminacion DATETIME,
	FOREIGN KEY(id_empleado) REFERENCES empleado(id) ON UPDATE CASCADE
);

CREATE TABLE detalle_venta(
	id BIGINT NOT NULL auto_increment PRIMARY KEY,
	id_venta BIGINT NOT NULL,
	id_producto INT NOT NULL,
	cantidad INT NOT NULL,
	monto FLOAT NOT NULL,
	fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion DATETIME,
	fecha_eliminacion DATETIME,
	FOREIGN KEY(id_venta) REFERENCES venta(id) ON UPDATE CASCADE,
	FOREIGN KEY(id_producto) REFERENCES producto(id) ON UPDATE CASCADE
);

CREATE TABLE compra(
	id BIGINT NOT NULL auto_increment PRIMARY KEY,
	id_empleado INT NOT NULL,
	id_proveedor INT NOT NULL,
	efectivo FLOAT NOT NULL,
	total FLOAT NOT NULL,
	fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion DATETIME,
	fecha_eliminacion DATETIME,
	FOREIGN KEY(id_empleado) REFERENCES empleado(id) ON UPDATE CASCADE,
	FOREIGN KEY(id_proveedor) REFERENCES proveedor(id) ON UPDATE CASCADE
);

CREATE TABLE detalle_compra(
	id BIGINT NOT NULL auto_increment PRIMARY KEY,
	id_compra BIGINT NOT NULL,
	id_producto INT NOT NULL,
	cantidad INT NOT NULL,
	monto FLOAT NOT NULL,
	fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion DATETIME,
	fecha_eliminacion DATETIME,
	FOREIGN KEY(id_compra) REFERENCES compra(id) ON UPDATE CASCADE,
	FOREIGN KEY(id_producto) REFERENCES producto(id) ON UPDATE CASCADE
);

CREATE TABLE adeudo(
	id BIGINT NOT NULL auto_increment PRIMARY KEY,
	id_compra BIGINT NOT NULL,
	monto FLOAT NOT NULL,
	estatus BOOLEAN NOT NULL DEFAULT 0,
	fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion DATETIME,
	fecha_eliminacion DATETIME,
	FOREIGN KEY(id_compra) REFERENCES compra(id) ON UPDATE CASCADE
);

CREATE TABLE abono(
	id BIGINT NOT NULL auto_increment PRIMARY KEY,
	id_adeudo BIGINT NOT NULL,
	monto FLOAT NOT NULL,
	fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion DATETIME,
	fecha_eliminacion DATETIME,
	FOREIGN KEY(id_adeudo) REFERENCES adeudo(id) ON UPDATE CASCADE
);

CREATE TABLE saldo(
	id BIGINT NOT NULL auto_increment PRIMARY KEY,
	id_empleado INT NOT NULL,
	fondo_caja FLOAT NOT NULL,
	abono_compra FLOAT,
	compra_total FLOAT NOT NULL,
	venta_total FLOAT NOT NULL,
	total FLOAT NOT NULL,
	fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion DATETIME,
	fecha_eliminacion DATETIME,
	FOREIGN KEY(id_empleado) REFERENCES empleado(id) ON UPDATE CASCADE
);