
create table producto
(
	producto_id int auto_increment,
	nombre text not null,
	precio_venta int not null,
	existencias int default 0 not null,
	constraint producto_pk
		primary key (producto_id)
);



create table ingrediente
(
	ingrediente_id int auto_increment,
	nombre text not null,
	metrica text not null,
	precio_unidad int not null,
	existencias int default 0 not null,
	constraint ingrediente_pk
		primary key (ingrediente_id)
);

create table relacion_ingrediente_producto
(
	rel_ing_prod_id int auto_increment,
	producto int not null,
	ingrediente int not null,
	cantidad int not null,
	constraint relacion_ingrediente_producto_pk
		primary key (rel_ing_prod_id),
	constraint relacion_ingrediente_producto_ingrediente_ingrediente_id_fk
		foreign key (ingrediente) references ingrediente (ingrediente_id)
			on update cascade on delete cascade,
	constraint relacion_ingrediente_producto_producto_producto_id_fk
		foreign key (producto) references producto (producto_id)
			on update cascade on delete cascade
);











