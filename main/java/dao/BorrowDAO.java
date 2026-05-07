package dao;

import utils.DBConnection;
import java.sql.*;
import java.util.*;
import model.BorrowRecord;

public class BorrowDAO {

    public boolean borrowBook(int userId, int bookId) {

        String insertBorrow =
            "INSERT INTO borrow_records(user_id, book_id, borrow_date, due_date, status) " +
            "VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY), 'borrowed')";

        String updateBook =
            "UPDATE books SET available_quantity = available_quantity - 1 " +
            "WHERE book_id = ? AND available_quantity > 0";

        try(Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            PreparedStatement ps1 = conn.prepareStatement(updateBook);
            ps1.setInt(1, bookId);

            int updated = ps1.executeUpdate();

            if(updated == 0){
                conn.rollback();
                return false;
            }

            PreparedStatement ps2 = conn.prepareStatement(insertBorrow);
            ps2.setInt(1, userId);
            ps2.setInt(2, bookId);

            ps2.executeUpdate();

            conn.commit();
            return true;

        } catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    
    public List<BorrowRecord> getBorrowedBooks(int userId) {

        List<BorrowRecord> list = new ArrayList<>();

        String sql =
            "SELECT br.borrow_id, b.title, br.borrow_date, br.due_date, br.status " +
            "FROM borrow_records br " +
            "JOIN books b ON br.book_id = b.book_id " +
            "WHERE br.user_id = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                BorrowRecord br = new BorrowRecord();

                br.setBorrowId(rs.getInt("borrow_id"));
                br.setTitle(rs.getString("title"));
                br.setBorrowDate(rs.getString("borrow_date"));
                br.setDueDate(rs.getString("due_date"));
                br.setStatus(rs.getString("status"));

                list.add(br);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
    
    public List<BorrowRecord> getAllBorrowRecords() {

        List<BorrowRecord> list = new ArrayList<>();

        String sql =
            "SELECT br.borrow_id, u.username, b.title, br.borrow_date, br.due_date, br.status " +
            "FROM borrow_records br " +
            "JOIN users u ON br.user_id = u.user_id " +
            "JOIN books b ON br.book_id = b.book_id";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {

                BorrowRecord br = new BorrowRecord();

                br.setBorrowId(rs.getInt("borrow_id"));
                br.setUsername(rs.getString("username"));
                br.setTitle(rs.getString("title"));
                br.setBorrowDate(rs.getString("borrow_date"));
                br.setDueDate(rs.getString("due_date"));
                br.setStatus(rs.getString("status"));

                list.add(br);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
    
    public boolean returnBook(int borrowId) {

        String getBookId =
            "SELECT book_id FROM borrow_records WHERE borrow_id = ?";

        String updateBorrow =
            "UPDATE borrow_records " +
            "SET status='returned', return_date=CURDATE() " +
            "WHERE borrow_id=?";

        String updateBook =
            "UPDATE books " +
            "SET available_quantity = available_quantity + 1 " +
            "WHERE book_id=?";

        try(Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            int bookId = 0;

            PreparedStatement ps1 = conn.prepareStatement(getBookId);
            ps1.setInt(1, borrowId);

            ResultSet rs = ps1.executeQuery();

            if(rs.next()){
                bookId = rs.getInt("book_id");
            }

            PreparedStatement ps2 = conn.prepareStatement(updateBorrow);
            ps2.setInt(1, borrowId);
            ps2.executeUpdate();

            PreparedStatement ps3 = conn.prepareStatement(updateBook);
            ps3.setInt(1, bookId);
            ps3.executeUpdate();

            conn.commit();

            return true;

        } catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
}