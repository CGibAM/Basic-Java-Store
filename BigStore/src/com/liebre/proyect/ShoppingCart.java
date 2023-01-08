package com.liebre.proyect;

public class ShoppingCart {
	private String productName;
	private Double price;
	private Double singleUnit;

	ShoppingCart(String name, Double price, Double unity) {
		this.productName = name;
		this.price = price;
		this.singleUnit = unity;

	}

	public void setName(String product_name) {
		this.productName = product_name;
	}

	public String getName() {
		return productName;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double gerPrice() {
		return price;
	}

	public void setUnitary(Double single_unit) {
		this.singleUnit = single_unit;
	}

	public Double getUnitary() {
		return singleUnit;
	}
	//format the elements on the shopping cart to show the name and price
	public String print(Double productsNum) {
		String producPrices = String.format("Product: " + this.productName + 
				" (" + productsNum + "). ");
		producPrices = producPrices + String.format("Price: $ " + this.price 
				+ "\n");

		return producPrices;
	}
}
