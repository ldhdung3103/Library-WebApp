/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import java.sql.Connection;
/**
 *
 * @author MSI
 */
public class TestConnection {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();

        if(conn != null){
            System.out.println("Database connected successfully");
        } else {
            System.out.println("Connection failed");
        }
    }
}
