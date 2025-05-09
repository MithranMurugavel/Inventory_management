package Data_Structure;

	import java.util.ArrayList;
	import java.util.List;

	public class Inventory {
	 private List<Item> items;
	 private int nextId;
	 
	 public Inventory() {
	     this.items = new ArrayList<>();
	     this.nextId = 1;
	 }
	 
	 public Item addItem(String name, int quantity, double price) {
	     Item newItem = new Item(nextId++, name, quantity, price);
	     items.add(newItem);
	     return newItem;
	 }
	 
	 public boolean removeItem(int id) {
	     return items.removeIf(item -> item.getId() == id);
	 }
	 
	 public Item findItem(int id) {
	     for (Item item : items) {
	         if (item.getId() == id) {
	             return item;
	         }
	     }
	     return null;
	 }
	 
	 public List<Item> getAllItems() {
	     return new ArrayList<>(items);
	 }
	 
	 public boolean updateItemQuantity(int id, int newQuantity) {
	     Item item = findItem(id);
	     if (item != null) {
	         item.setQuantity(newQuantity);
	         return true;
	     }
	     return false;
	 }
	 
	 public boolean updateItemPrice(int id, double newPrice) {
	     Item item = findItem(id);
	     if (item != null) {
	         item.setPrice(newPrice);
	         return true;
	     }
	     return false;
	 }
	 
	 public double calculateInventoryValue() {
	     double totalValue = 0;
	     for (Item item : items) {
	         totalValue += item.getQuantity() * item.getPrice();
	     }
	     return totalValue;
	 }
	}
