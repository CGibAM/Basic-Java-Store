package com.project.project;


public class Carrito {
	private String nombre_producto;
	private Double precio;
	private Double cantidadUnitaria;
	
Carrito (String nombre, Double precio, Double cantidadUnitaria){
	this.nombre_producto=nombre;
	this.precio=precio;
	this.cantidadUnitaria= cantidadUnitaria;
	
}

public void setName(String nombre_producto) {
this.nombre_producto = nombre_producto;
}

public String getNombre() {
	return nombre_producto;
}

public void setPrecio(Double precio) {
	this.precio = precio;
}


public Double getPrecio() {
	return precio;
}

public void setUnitario(Double cantidadUnitaria) {
this.cantidadUnitaria = cantidadUnitaria;
}

public Double getUnitario() {
	return cantidadUnitaria;
}

public String imprimir(Double noProductos) {
	String productoPrecio = String.format("Producto: " + this.nombre_producto + " ("+ noProductos + "). ");
	productoPrecio= productoPrecio + String.format("Precio: $ " + this.precio + "\n");
	
	return productoPrecio;		
}
}
