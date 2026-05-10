package dao;

import utils.DBConnection;
import java.sql.*;
import java.util.*;

import model.BorrowRecord;

public class BorrowDAO {

    // Student request borrow (status = pending)
    public boolean borrowBook(int userId, int bookId) {

        String sql =
            "INSERT INTO borrow_records(user_id, book_id, borrow_date, due_date, status) " +
            "VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY), 'pending')";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, bookId);

            return ps.executeUpdate() > 0;

        } catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    // Student view borrowed books
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

    // Librarian view all requests
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

    // Approve borrow request
    public boolean approveBorrow(int borrowId) {
        String checkSql =
            "SELECT b.available_quantity " +
            "FROM books b " +
            "JOIN borrow_records br ON b.book_id = br.book_id " +
            "WHERE br.borrow_id=?";

        String updateBorrow =
            "UPDATE borrow_records SET status='borrowed' WHERE borrow_id=?";

        String updateBook =
            "UPDATE books " +
            "SET available_quantity = available_quantity - 1 " +
            "WHERE book_id = (" +
            "SELECT book_id FROM borrow_records WHERE borrow_id=?" +
            ")";

        try(Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            PreparedStatement check = conn.prepareStatement(checkSql);
            check.setInt(1, borrowId);

            ResultSet rs = check.executeQuery();

            if(rs.next()) {

                int available = rs.getInt("available_quantity");

                if(available <= 0){
                    conn.rollback();
                    return false;
                }

                PreparedStatement ps1 = conn.prepareStatement(updateBorrow);
                ps1.setInt(1, borrowId);
                ps1.executeUpdate();

                PreparedStatement ps2 = conn.prepareStatement(updateBook);
                ps2.setInt(1, borrowId);
                ps2.executeUpdate();

                conn.commit();
                return true;
            }

            conn.rollback();

        } catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    // Reject borrow request
    public void rejectBorrow(int borrowId) {

        String sql =
            "UPDATE borrow_records SET status='rejected' WHERE borrow_id=?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, borrowId);
            ps.executeUpdate();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // Return book
    public boolean returnBook(int borrowId) {

        String getBookId =
            "SELECT book_id FROM borrow_records WHERE borrow_id=?";

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

            if(rs.next()) {
                bookId = rs.getInt("book_id");
            } else {
                conn.rollback();
                return false;
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