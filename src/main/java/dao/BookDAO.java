package dao;

import model.Book;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM books";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                Book b = new Book();

                b.setBookId(rs.getInt("book_id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setCategory(rs.getString("category"));
                b.setIsbn(rs.getString("isbn"));
                b.setQuantity(rs.getInt("quantity"));
                b.setAvailableQuantity(rs.getInt("available_quantity"));

                books.add(b);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return books;
    }
}