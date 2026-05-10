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
    
    public void addBook(Book book) {
        String sql =
            "INSERT INTO books(title,author,category,isbn,quantity,available_quantity) VALUES(?,?,?,?,?,?)";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setString(4, book.getIsbn());
            ps.setInt(5, book.getQuantity());
            ps.setInt(6, book.getQuantity());

            ps.executeUpdate();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public boolean deleteBook(int bookId) {
        String checkSql =
            "SELECT COUNT(*) FROM borrow_records " +
            "WHERE book_id=? AND status IN ('pending','borrowed')";

        String deleteSql =
            "DELETE FROM books WHERE book_id=?";

        try(Connection conn = DBConnection.getConnection()) {

            PreparedStatement check = conn.prepareStatement(checkSql);
            check.setInt(1, bookId);

            ResultSet rs = check.executeQuery();

            if(rs.next() && rs.getInt(1) > 0){
                return false;
            }

            PreparedStatement delete = conn.prepareStatement(deleteSql);
            delete.setInt(1, bookId);

            return delete.executeUpdate() > 0;

        } catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
}