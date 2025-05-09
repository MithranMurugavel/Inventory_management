package Data_Structure;



//InventoryManagementSystem.java - Main application class with GUI
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InventoryManagementSystem extends JFrame {
 private Inventory inventory;
 private JTable itemTable;
 private DefaultTableModel tableModel;
 private JTextField nameField, quantityField, priceField, idField;
 private JLabel statusLabel, valueLabel;
 
 public InventoryManagementSystem() {
     // Initialize inventory
     inventory = new Inventory();
     
     // Add sample items
     inventory.addItem("Laptop", 10, 899.99);
     inventory.addItem("Mouse", 25, 24.99);
     inventory.addItem("Keyboard", 15, 49.99);
     
     // Set up the JFrame
     setTitle("Inventory Management System");
     setSize(800, 600);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setLocationRelativeTo(null);
     
     // Create components
     createComponents();
     
     // Display the window
     setVisible(true);
     
     // Update the table initially
     refreshTable();
     updateInventoryValue();
 }
 
 private void createComponents() {
     // Main panel with BorderLayout
     JPanel mainPanel = new JPanel(new BorderLayout());
     
     // Table panel (CENTER)
     createTablePanel(mainPanel);
     
     // Form panel (WEST)
     createFormPanel(mainPanel);
     
     // Status panel (SOUTH)
     createStatusPanel(mainPanel);
     
     // Add the main panel to the frame
     add(mainPanel);
 }
 
 private void createTablePanel(JPanel mainPanel) {
     // Create table model with column names
     String[] columnNames = {"ID", "Name", "Quantity", "Price", "Total Value"};
     tableModel = new DefaultTableModel(columnNames, 0) {
         @Override
         public boolean isCellEditable(int row, int column) {
             return false; // Make table read-only
         }
     };
     
     // Create table with the model
     itemTable = new JTable(tableModel);
     
     // Add table to a scroll pane
     JScrollPane scrollPane = new JScrollPane(itemTable);
     scrollPane.setBorder(BorderFactory.createTitledBorder("Inventory Items"));
     
     // Add to main panel
     mainPanel.add(scrollPane, BorderLayout.CENTER);
 }
 
 private void createFormPanel(JPanel mainPanel) {
     // Form panel
     JPanel formPanel = new JPanel(new GridBagLayout());
     formPanel.setBorder(BorderFactory.createTitledBorder("Item Management"));
     
     // GridBag constraints
     GridBagConstraints gbc = new GridBagConstraints();
     gbc.fill = GridBagConstraints.HORIZONTAL;
     gbc.insets = new Insets(5, 5, 5, 5);
     
     // ID Field (for finding/updating/removing)
     gbc.gridx = 0;
     gbc.gridy = 0;
     formPanel.add(new JLabel("ID:"), gbc);
     
     gbc.gridx = 1;
     gbc.gridy = 0;
     idField = new JTextField(10);
     formPanel.add(idField, gbc);
     
     // Name Field
     gbc.gridx = 0;
     gbc.gridy = 1;
     formPanel.add(new JLabel("Name:"), gbc);
     
     gbc.gridx = 1;
     gbc.gridy = 1;
     nameField = new JTextField(10);
     formPanel.add(nameField, gbc);
     
     // Quantity Field
     gbc.gridx = 0;
     gbc.gridy = 2;
     formPanel.add(new JLabel("Quantity:"), gbc);
     
     gbc.gridx = 1;
     gbc.gridy = 2;
     quantityField = new JTextField(10);
     formPanel.add(quantityField, gbc);
     
     // Price Field
     gbc.gridx = 0;
     gbc.gridy = 3;
     formPanel.add(new JLabel("Price:"), gbc);
     
     gbc.gridx = 1;
     gbc.gridy = 3;
     priceField = new JTextField(10);
     formPanel.add(priceField, gbc);
     
     // Buttons
     JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 5, 5));
     
     // Add button
     JButton addButton = new JButton("Add Item");
     addButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             addItem();
         }
     });
     buttonPanel.add(addButton);
     
     // Remove button
     JButton removeButton = new JButton("Remove Item");
     removeButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             removeItem();
         }
     });
     buttonPanel.add(removeButton);
     
     // Find button
     JButton findButton = new JButton("Find Item");
     findButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             findItem();
         }
     });
     buttonPanel.add(findButton);
     
     // Update Quantity button
     JButton updateQuantityButton = new JButton("Update Quantity");
     updateQuantityButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             updateQuantity();
         }
     });
     buttonPanel.add(updateQuantityButton);
     
     // Update Price button
     JButton updatePriceButton = new JButton("Update Price");
     updatePriceButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             updatePrice();
         }
     });
     buttonPanel.add(updatePriceButton);
     
     // Clear button
     JButton clearButton = new JButton("Clear Fields");
     clearButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             clearFields();
         }
     });
     buttonPanel.add(clearButton);
     
     // Add button panel to form
     gbc.gridx = 0;
     gbc.gridy = 4;
     gbc.gridwidth = 2;
     formPanel.add(buttonPanel, gbc);
     
     // Add form panel to main panel
     mainPanel.add(formPanel, BorderLayout.WEST);
 }
 
 private void createStatusPanel(JPanel mainPanel) {
     JPanel statusPanel = new JPanel(new BorderLayout());
     statusPanel.setBorder(BorderFactory.createEtchedBorder());
     
     // Status label on the left
     statusLabel = new JLabel("Ready");
     statusPanel.add(statusLabel, BorderLayout.WEST);
     
     // Total value label on the right
     valueLabel = new JLabel("Total Value: $0.00");
     statusPanel.add(valueLabel, BorderLayout.EAST);
     
     // Add status panel to main panel
     mainPanel.add(statusPanel, BorderLayout.SOUTH);
 }
 
 private void refreshTable() {
     // Clear the table
     tableModel.setRowCount(0);
     
     // Add all items to the table
     List<Item> items = inventory.getAllItems();
     for (Item item : items) {
         double totalValue = item.getQuantity() * item.getPrice();
         Object[] row = {
             item.getId(),
             item.getName(),
             item.getQuantity(),
             String.format("$%.2f", item.getPrice()),
             String.format("$%.2f", totalValue)
         };
         tableModel.addRow(row);
     }
 }
 
 private void updateInventoryValue() {
     double totalValue = inventory.calculateInventoryValue();
     valueLabel.setText(String.format("Total Value: $%.2f", totalValue));
 }
 
 private void setStatus(String message) {
     statusLabel.setText(message);
 }
 
 private void addItem() {
     try {
         String name = nameField.getText().trim();
         if (name.isEmpty()) {
             setStatus("Error: Name cannot be empty");
             return;
         }
         
         int quantity = Integer.parseInt(quantityField.getText().trim());
         double price = Double.parseDouble(priceField.getText().trim());
         
         if (quantity < 0 || price < 0) {
             setStatus("Error: Quantity and price must be positive");
             return;
         }
         
         Item newItem = inventory.addItem(name, quantity, price);
         refreshTable();
         updateInventoryValue();
         clearFields();
         setStatus("Item added successfully: " + newItem.getName());
     } catch (NumberFormatException e) {
         setStatus("Error: Invalid quantity or price. Please enter numbers only.");
     }
 }
 
 private void removeItem() {
     try {
         String idStr = idField.getText().trim();
         if (idStr.isEmpty()) {
             setStatus("Error: Please enter an ID");
             return;
         }
         
         int id = Integer.parseInt(idStr);
         if (inventory.removeItem(id)) {
             refreshTable();
             updateInventoryValue();
             clearFields();
             setStatus("Item removed successfully");
         } else {
             setStatus("Error: No item found with ID: " + id);
         }
     } catch (NumberFormatException e) {
         setStatus("Error: Invalid ID. Please enter a number.");
     }
 }
 
 private void findItem() {
     try {
         String idStr = idField.getText().trim();
         if (idStr.isEmpty()) {
             setStatus("Error: Please enter an ID");
             return;
         }
         
         int id = Integer.parseInt(idStr);
         Item item = inventory.findItem(id);
         
         if (item != null) {
             // Fill the form fields with item data
             nameField.setText(item.getName());
             quantityField.setText(String.valueOf(item.getQuantity()));
             priceField.setText(String.valueOf(item.getPrice()));
             setStatus("Item found: " + item.getName());
             
             // Highlight the row in the table
             for (int i = 0; i < tableModel.getRowCount(); i++) {
                 if (tableModel.getValueAt(i, 0).equals(id)) {
                     itemTable.setRowSelectionInterval(i, i);
                     break;
                 }
             }
         } else {
             setStatus("Error: No item found with ID: " + id);
         }
     } catch (NumberFormatException e) {
         setStatus("Error: Invalid ID. Please enter a number.");
     }
 }
 
 private void updateQuantity() {
     try {
         String idStr = idField.getText().trim();
         if (idStr.isEmpty()) {
             setStatus("Error: Please enter an ID");
             return;
         }
         
         String quantityStr = quantityField.getText().trim();
         if (quantityStr.isEmpty()) {
             setStatus("Error: Please enter a quantity");
             return;
         }
         
         int id = Integer.parseInt(idStr);
         int newQuantity = Integer.parseInt(quantityStr);
         
         if (newQuantity < 0) {
             setStatus("Error: Quantity must be positive");
             return;
         }
         
         if (inventory.updateItemQuantity(id, newQuantity)) {
             refreshTable();
             updateInventoryValue();
             setStatus("Quantity updated successfully");
         } else {
             setStatus("Error: No item found with ID: " + id);
         }
     } catch (NumberFormatException e) {
         setStatus("Error: Invalid ID or quantity. Please enter numbers only.");
     }
 }
 
 private void updatePrice() {
     try {
         String idStr = idField.getText().trim();
         if (idStr.isEmpty()) {
             setStatus("Error: Please enter an ID");
             return;
         }
         
         String priceStr = priceField.getText().trim();
         if (priceStr.isEmpty()) {
             setStatus("Error: Please enter a price");
             return;
         }
         
         int id = Integer.parseInt(idStr);
         double newPrice = Double.parseDouble(priceStr);
         
         if (newPrice < 0) {
             setStatus("Error: Price must be positive");
             return;
         }
         
         if (inventory.updateItemPrice(id, newPrice)) {
             refreshTable();
             updateInventoryValue();
             setStatus("Price updated successfully");
         } else {
             setStatus("Error: No item found with ID: " + id);
         }
     } catch (NumberFormatException e) {
         setStatus("Error: Invalid ID or price. Please enter numbers only.");
     }
 }
 
 private void clearFields() {
     idField.setText("");
     nameField.setText("");
     quantityField.setText("");
     priceField.setText("");
     itemTable.clearSelection();
 }
 
 public static void main(String[] args) {
     // Use the Event Dispatch Thread for Swing applications
     SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
             new InventoryManagementSystem();
         }
     });
 }
}
