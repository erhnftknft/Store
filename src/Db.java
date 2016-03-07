import model.Customer;
import model.Product;
import model.Purchase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Db {
    private final Connection conn;

    public Db() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:Db");

    }

    public void insert(Purchase purchase) throws SQLException {
        PreparedStatement prep = conn.prepareStatement("INSERT  INTO  Purchases (PRODUCT_ID,CUSTOMER_ID,AMOUNT,PURCHASE_DATE) VALUES (?,?,?,?)");
        prep.setLong(1, purchase.productId);
        prep.setLong(2, purchase.customerId);
        prep.setDouble(3, purchase.amount);
        prep.setDate(4, new Date(purchase.purchaseDate.getTime()));
        prep.addBatch();

        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);
    }

    public List<Purchase> findAllPurchases() throws SQLException {


        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("SELECT * FROM PURCHASES");
        List<Purchase> list = new ArrayList<>();
        while (rs.next()) {
            Purchase purchase = new Purchase(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getDouble(4), rs.getDate(5).getTime());
            list.add(purchase);
        }
        rs.close();
        return list;
    }

    public void deleteCustomer(long id) throws SQLException {

        PreparedStatement prep = conn.prepareStatement("DELETE FROM Customers WHERE ID=?");
        prep.setLong(1, id);

        prep.addBatch();
        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);

    }

    public void deleteCustomer(String name) throws SQLException {

        PreparedStatement prep = conn.prepareStatement("DELETE FROM Customers WHERE NAME=?");
        prep.setString(1, name);

        prep.addBatch();
        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);
    }

    public void insertNewProduct(Product product) throws SQLException {
        PreparedStatement prep = conn.prepareStatement("INSERT INTO Products  VALUES (null,?,?)");
        prep.setString(1, product.name);
        prep.setDouble(2, product.price);
        prep.addBatch();

        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);
    }

    public void insertCustomer(Customer customer) throws SQLException {
        PreparedStatement prep = conn.prepareStatement("INSERT INTO Customers  VALUES (null,?,?)");
        prep.setString(1, customer.name);
        prep.setDate(2, new Date(customer.dateBirthDay.getTime()));
        prep.addBatch();

        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);
    }

    public List<Customer> findAllCustomers() throws SQLException {
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("SELECT * FROM CUSTOMERS");
        List<Customer> list = new ArrayList<>();
        while (rs.next()) {
            Customer customer = new Customer(rs.getLong(1), rs.getString(2), rs.getDate(3).getTime());
            list.add(customer);
        }
        rs.close();
        return list;
    }

    public void deleteProduct(long id) throws SQLException {
        PreparedStatement prep;
        prep = conn.prepareStatement("DELETE FROM Products WHERE ID=?");
        prep.setLong(1, id);

        prep.addBatch();
        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);

    }

    public void deleteProduct(String name) throws SQLException {
        PreparedStatement prep;
        prep = conn.prepareStatement("DELETE FROM Products WHERE NAME=?");
        prep.setString(1, name);

        prep.addBatch();
        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);
    }

    public List<Product> findAllProducts() throws SQLException {
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("SELECT * FROM PRODUCTS");
        List<Product> list1 = new ArrayList<>();
        while (rs.next()) {
            Product product = new Product(rs.getLong(1), rs.getString(2), rs.getDouble(3));
            list1.add(product);
        }
        rs.close();

        return list1;
    }
}
