/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phinma_coc_gsd_ticketing_system.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author student
 */

public class DBConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/phinma_coc_gsd_ticketing_system"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "";
    
     public static Connection connect() { // Connection - return type, connect() - method
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "MySQL JDBC Driver not found! Make sure mysql-connector-j.jar is added to Libraries.", "Database Error", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Connection Failed: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}