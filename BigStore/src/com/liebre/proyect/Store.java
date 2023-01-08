package com.liebre.proyect;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

public class Store {

	public static void main(String[] args) {
		Store store = new Store();
		store.start();
	}

	private void start() {
		List<ShoppingCart> list = new ArrayList<ShoppingCart>();
		int selection = 0;
		JOptionPane.showMessageDialog(null, menu());
		while (selection != 6) {
			try {
				selection = Integer.parseInt(JOptionPane.showInputDialog(menu_2()));
				switch (selection) {
				case 1: // Add new products or increase the number of the already existing ones
					int productSelection = 1;
					while (productSelection == 1) {
						try {
							Integer userSelect = Integer.parseInt(JOptionPane.showInputDialog(menu_3()));
							productSelection = addtoCart(userSelect, list, productSelection);

						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Invalid selection\n");
						}
						char exit_menu = 0;
						while (exit_menu == 0) {
							try {
								Integer back_to_menu = Integer.parseInt(JOptionPane.showInputDialog(menu_4()));
								if (back_to_menu == 1 || back_to_menu == 2) {
									productSelection = (back_to(back_to_menu, productSelection));
									exit_menu = 1;
								} else {
									JOptionPane.showMessageDialog(null, "Select a valid option\n");
								}

							} catch (NoSuchElementException e) {
								JOptionPane.showMessageDialog(null, "Invalid selection\n");
							}
						}
					}
					break;
				case 2:// Show cart
					isCartEmpty(list);
					break;
					
				case 3:// Delete products
					toDeleate(list);
					break;
					
				case 4:// Show total and delete cart
					if (list == null || list.isEmpty()) {
						JOptionPane.showMessageDialog(null, "The Cart is empty.\n" + "Imposible to show the total.\n");
					} else {
						show_cart(list);
						total(list);
						list = delete_cart(list);
						thanks();
					}
					break;
					
				case 5:// Search on the Cart
					String search = JOptionPane
							.showInputDialog("Select a product to search:");
					searchItem(search, list);
					break;
					
				case 6:// Exit the program
					JOptionPane.showMessageDialog(null, "Exiting program");
					System.exit(0);
					break;
				}
				if (selection > 5) {
					JOptionPane.showMessageDialog(null, "Please select a valid option from the menu\n");
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Invalid selection\n");
			}
		}
	}
	
//method to delete an element from the list
	private void toDeleate(List<ShoppingCart> list) {
		if (list == null || list.isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"The Cart is empty\n" + "No products to delete\n");
		} else {
			show_cart(list);
			try {
				Integer delete = Integer
						.parseInt(JOptionPane.showInputDialog("Select a product to delete: \n"));
				if ((delete > 0) && (delete <= list.size())) {
					list = delete_item(list, delete);
				} else {
					JOptionPane.showMessageDialog(null, "Select a valid product from the list");
					JOptionPane.showMessageDialog(null, "Returning to the previous menu");
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Invalid selection.Returning to the previous menu\n");
			}
		}	
	}

	private void isCartEmpty(List<ShoppingCart> list) {
		if (list == null || list.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Empy Cart");
		} else {
			show_cart(list);
		}
	}

	//method to add to the cart
	private int addtoCart(Integer userSelect, List<ShoppingCart> list, int productSelection) {
		if (userSelect >= 1 && userSelect <= 7) {
			if (list == null || list.isEmpty()) {
				ShoppingCart cart = (newCart(userSelect, list));
				list.add(cart);
			} else {
				list = (existingCart(list, userSelect));
			}
		} else if (userSelect == 8) {
			return 2;
		} else {
			JOptionPane.showMessageDialog(null, "Please select a valid option fromthe menu");
		}
		return 1;
	}

	//Method that uses stream to find an element on the list
	private void searchItem(String search, List<ShoppingCart> list) {
		ShoppingCart findElement = list.stream()
				.filter(find -> find.getName().toLowerCase().contains(search.toLowerCase())).findFirst()
				.orElse(null);/*On a variable of type ShoppingCart(ShoppingCart findElement) will be equal to list 
					stream(the collection starts to iterate with the lambda function)().
					filter(to filter, intermediate method)(Predicate= name of the variable->
					variable.getNombre.toLowerCase (to make the variable into lower case y compare it with
					lower case characters). contains()(Verifies if an element or part of it it's contained
					compared with another one, on that case search).search.toLowerCase(to lower case
					and y compare it with the name))), ends the predicate
					.findFirst (returns an optional of the first found element on the stream
					with the searched name) .or Else (optional derived method, where if the value it's
					not found, will return null, otherwise will return that value.
								 */
		if (findElement != null) {
			JOptionPane.showMessageDialog(null,
					"Product found " + findElement.getName() + " with the price:" + findElement.gerPrice());
		} else {
			JOptionPane.showMessageDialog(null, "Product not found");
		}
	}
	
//Method that adds a product to an existing cart
	private List<ShoppingCart> existingCart(List<ShoppingCart> list, Integer selection2) {
		String product = "";
		Double price = 0.00;
		Double unitaryQuantity = 0.00;
		int index = 0;
		int i = 0;
		ShoppingCart cart = new ShoppingCart(product, price, unitaryQuantity);
		if (selection2 == 1) {
			product = "Fries";
			for (i = 0; i < list.size(); i++) {
				if (list.get(i).getName().equals("Frires")) {
					index = i;
				}
			}
			if (list.get(index).getName().equals("Fries")) {
				price = list.get(index).gerPrice();
				Double pieces = Double.parseDouble(JOptionPane.showInputDialog("How many pieces?: "));
				pieces = pieces * 45.50;
				price = price + pieces;
				list.get(index).setPrice(price);
				JOptionPane.showMessageDialog(null, "Product(s) added");
			} else {
				cart = newProduct(selection2, cart, product, price, unitaryQuantity);
				list.add(cart);
			}
		} else if (selection2 == 2) {
			for (i = 0; i < list.size(); i++) {
				if (list.get(i).getName().equals("Beer")) {
					index = i;
				}
			}
			if (list.get(index).getName().equals("Beer")) {
				price = list.get(index).gerPrice();
				Double pieces = Double.parseDouble(JOptionPane.showInputDialog("How many pieces?: "));
				pieces = pieces * 36.00;
				price = price + pieces;
				list.get(index).setPrice(price);
				JOptionPane.showMessageDialog(null, "Product(s) added");
			} else {
				product = "Beer";
				cart = newProduct(selection2, cart, product, price, unitaryQuantity);
				list.add(cart);
			}
		} else if (selection2 == 3) {
			for (i = 0; i < list.size(); i++) {
				if (list.get(i).getName().equals("Burger")) {
					index = i;
				}
			}
			if (list.get(index).getName().equals("Burger")) {
				price = list.get(index).gerPrice();
				Double pieces = Double.parseDouble(JOptionPane.showInputDialog("How many pieces?: "));
				pieces = pieces * 74.00;
				price = price + pieces;
				list.get(index).setPrice(price);
				JOptionPane.showMessageDialog(null, "Product(s) added");
			} else {
				product = "Burger";
				cart = newProduct(selection2, cart, product, price, unitaryQuantity);
				list.add(cart);
			}
		} else if (selection2 == 4) {
			for (i = 0; i < list.size(); i++) {
				if (list.get(i).getName().equals("Soda")) {
					index = i;
				}
			}
			if (list.get(index).getName().equals("Soda")) {
				price = list.get(index).gerPrice();
				Double pieces = Double.parseDouble(JOptionPane.showInputDialog("How many pieces?: "));
				pieces = pieces * 15.50;
				price = price + pieces;
				list.get(index).setPrice(price);
				JOptionPane.showMessageDialog(null, "Product(s) added");
			} else {
				product = "Soda";
				cart = newProduct(selection2, cart, product, price, unitaryQuantity);
				list.add(cart);
			}
		} else if (selection2 == 5) {
			for (i = 0; i < list.size(); i++) {
				if (list.get(i).getName().equals("Taco")) {
					index = i;
				}
			}
			if (list.get(index).getName().equals("Taco")) {
				price = list.get(index).gerPrice();
				Double pieces = Double.parseDouble(JOptionPane.showInputDialog("How many pieces?: "));
				pieces = pieces * 18.50;
				price = price + pieces;
				list.get(index).setPrice(price);
				JOptionPane.showMessageDialog(null, "Product(s) added");
			} else {
				product = "Taco";
				cart = newProduct(selection2, cart, product, price, unitaryQuantity);
				list.add(cart);
			}

		} else if (selection2 == 6) {
			for (i = 0; i < list.size(); i++) {
				if (list.get(i).getName().equals("Penuts")) {
					index = i;
				}
			}
			if (list.get(index).getName().equals("Penuts")) {
				price = list.get(index).gerPrice();
				Double pieces = Double.parseDouble(JOptionPane.showInputDialog("How many pieces?: "));
				pieces = pieces * 22.00;
				price = price + pieces;
				list.get(index).setPrice(price);
				JOptionPane.showMessageDialog(null, "Product(s) added");
			} else {
				product = "Penuts";
				cart = newProduct(selection2, cart, product, price, unitaryQuantity);
				list.add(cart);
			}

		} else if (selection2 == 7) {
			for (i = 0; i < list.size(); i++) {
				if (list.get(i).getName().equals("Big Bacon Burger")) {
					index = i;
				}
			}
			if (list.get(index).getName().equals("Big Bacon Burger")) {
				price = list.get(index).gerPrice();
				Double pieces = Double.parseDouble(JOptionPane.showInputDialog("How many pieces?: "));
				pieces = pieces * 65.00;
				price = price + pieces;
				list.get(index).setPrice(price);
				JOptionPane.showMessageDialog(null, "Product(s) added)");
			} else {
				product = "Big Bacon Burger"
						+ "";
				cart = newProduct(selection2, cart, product, price, unitaryQuantity);
				list.add(cart);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Canceled\n");
		}
		return list;
	}

//Method to finish the program
	private void thanks() {
		JOptionPane.showMessageDialog(null, "Thanks for shopping here. See you soon");
		try {
			JOptionPane.showMessageDialog(null, "Wait 10 seconds to continue or press a key to exit program");
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException ex) {
			System.exit(0);
		}
	}
//Method to delete the existing cart
	private List<ShoppingCart> delete_cart(List<ShoppingCart> list) {
		list.clear();
		return list;

	}
	
//Method to show the total price of all the products in the shopping list
	private void total(List<ShoppingCart> list) {
		Double total = 0d;
		for (ShoppingCart cart : list) {
			total += cart.gerPrice();
		}
		JOptionPane.showMessageDialog(null, "Your total is: $" + total);
	}
	
//Method to delete element based on the quantity of elements of each product
	private List<ShoppingCart> delete_item(List<ShoppingCart> list, int delete) {
		int count = 1;
		Double el = 0d;
		Double price = list.get(delete - 1).gerPrice();
		Double unitary = list.get(delete - 1).getUnitary();
		while (count == 1) {
			try {
				Double substraction = Double.parseDouble(JOptionPane.showInputDialog(deletePzs()));
				if (substraction > 0) {
					substraction = substraction * unitary;
					substraction = price - substraction;
					if (substraction >= 0) {
						list.get(delete - 1).setPrice(substraction);
						if (list.get(delete - 1).gerPrice().equals(el)) {//deletes an element if the quantity surpasses the maximum amount
							list.remove(delete - 1);
						}
						JOptionPane.showMessageDialog(null, "Product removed");
						count = 2;
					} else {
						JOptionPane.showMessageDialog(null,//the selected quantity surpasses the maximum number of products
								"The selected quantity surpassesd the maximun amount of products on the Cart");
					}
				} else if (substraction == 0) {
					JOptionPane.showMessageDialog(null, "Canceled. Returning to the previous menu");
					count = 2;
				} else {
					JOptionPane.showMessageDialog(null, "Select a valid option");
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Select a valid option");
			}
		}
		return list;
	}
	
//Via user input defines how many pieces are going to be deleted
	private String deletePzs() {
		String deleteP = String.format("How many pieces?: \n");
		deleteP = deleteP + String.format("Press 0 to cancel \n");
		return deleteP;
	}

//Method to add to cart
	private void show_cart(List<ShoppingCart> list) {
		Double price;
		Double productNo;
		Double unitary;
		StringBuilder sb = new StringBuilder();// create a string to show the elements of the Cart in a specific format
		for (int i = 0; i < list.size(); i++) {
			price = list.get(i).gerPrice();
			unitary = list.get(i).getUnitary();
			productNo = price / unitary;
			ShoppingCart print_cart = list.get(i);
			sb.append((i + 1) + ". " + print_cart.print(productNo));
		}
		JOptionPane.showMessageDialog(null, "Your Cart is empty: \n" + sb.toString());
	}

	private int back_to(int back_to_menu, int seleccion_prod) {
		if (back_to_menu == 1) {
			seleccion_prod = 1;
		} else if (back_to_menu == 2) {
			seleccion_prod = 2;
		}

		return seleccion_prod;
	}

	private String menu_4() {
		String menu4 = String.format("Do you wish to add more products? \n");
		menu4 = menu4 + String.format("1.- Add more products\n");
		menu4 = menu4 + String.format("2.- Return to previous menu\n");
		return menu4;
	}
	
//method to create a new cart
	private ShoppingCart newCart(Integer selection2, List<ShoppingCart> list) {
		String product = "";
		Double price = 0.00;
		Double unitaryQuantity = 0.00;
		ShoppingCart cart = new ShoppingCart(product, price, unitaryQuantity);
		if (selection2 == 1) {
			cart = newProduct(selection2, cart, product, price, unitaryQuantity);
		} else if (selection2 == 2) {
			cart = newProduct(selection2, cart, product, price, unitaryQuantity);
		} else if (selection2 == 3) {
			cart = newProduct(selection2, cart, product, price, unitaryQuantity);
		} else if (selection2 == 4) {
			cart = newProduct(selection2, cart, product, price, unitaryQuantity);
		} else if (selection2 == 5) {
			cart = newProduct(selection2, cart, product, price, unitaryQuantity);
		} else if (selection2 == 6) {
			cart = newProduct(selection2, cart, product, price, unitaryQuantity);
		} else if (selection2 == 7) {
			cart = newProduct(selection2, cart, product, price, unitaryQuantity);
			;
		} else {
			JOptionPane.showMessageDialog(null, "Canceled");
		}
		JOptionPane.showMessageDialog(null, "Products added");
		return cart;
	}

	//Method to add ton the cart if it's a new product
	private ShoppingCart newProduct(Integer selection2, ShoppingCart cart, String product, Double price,
			Double unitaryQuantity) {
		if (selection2 == 1) {
			product = "Fries";
			price = 45.50;
			unitaryQuantity = 45.50;
			Double pieces = Double.parseDouble(JOptionPane.showInputDialog("How many pieces?: "));
			price = price * pieces;
		} else if (selection2 == 2) {
			product = "Beer";
			price = 36.00;
			unitaryQuantity = 36.00;
			Double pieces = Double.parseDouble(JOptionPane.showInputDialog("How many pieces?: "));
			price = price * pieces;
		} else if (selection2 == 3) {
			product = "Burger";
			price = 74.00;
			unitaryQuantity = 74.00;
			Double pieces = Double.parseDouble(JOptionPane.showInputDialog("How many pieces?: "));
			price = price * pieces;
		} else if (selection2 == 4) {
			product = "Soda";
			price = 15.50;
			unitaryQuantity = 15.50;
			Double pieces = Double.parseDouble(JOptionPane.showInputDialog("How many pieces?: "));
			price = price * pieces;
		} else if (selection2 == 5) {
			product = "Taco";
			price = 18.50;
			unitaryQuantity = 18.50;
			Double pieces = Double.parseDouble(JOptionPane.showInputDialog("How many pieces?: "));
			price = price * pieces;
		} else if (selection2 == 6) {
			product = "Penuts";
			price = 22.00;
			unitaryQuantity = 22.00;
			Double pieces = Double.parseDouble(JOptionPane.showInputDialog("How many pieces?: "));
			price = price * pieces;
		} else if (selection2 == 7) {
			product = "Burger";
			price = 65.00;
			unitaryQuantity = 65.00;
			Double pieces = Double.parseDouble(JOptionPane.showInputDialog("How many pieces?: "));
			price = price * pieces;
		}
		cart = new ShoppingCart(product, price, unitaryQuantity);
		return cart;
	}

	private String menu_3() {
		String menu3 = String.format("Select a product to add to the Cart: \n");
		menu3 = menu3 + String.format("1.- Fries" + "      $45.50\n");
		menu3 = menu3 + String.format("2.- Beer" + "      $36.00\n");
		menu3 = menu3 + String.format("3.- Burger" + "      $74.00\n");
		menu3 = menu3 + String.format("4.- Soda" + "      $15.50\n");
		menu3 = menu3 + String.format("5.- Taco" + "      $18.50\n");
		menu3 = menu3 + String.format("6.- Penuts" + "      $22.00\n");
		menu3 = menu3 + String.format("7.- Big Bacon Burger" + "      $65.00\n");
		menu3 = menu3 + String.format("8.- Cancel");
		return menu3;
	}

	private String menu_2() {

		String menu2 = String.format("Select an option from the menu: \n");
		menu2 = menu2 + String.format("1.- Select a product to add to the Cart\n");
		menu2 = menu2 + String.format("2.- View shopping Cart\n");
		menu2 = menu2 + String.format("3.- Delete an existing product\n");
		menu2 = menu2 + String.format("4.- Show total and exit\n");
		menu2 = menu2 + String.format("5.- Search product on cart\n");
		menu2 = menu2 + String.format("6.- Exit program\n");
		return menu2;
	}

	private String menu() {
		String menu1 = String.format("Welcome to the Fine Store!");
		return menu1;
	}

}
