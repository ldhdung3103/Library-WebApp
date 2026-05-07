package controller;

import dao.BorrowDAO;
import model.User;

import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/borrow")
public class BorrowServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        User user = (User) request.getSession().getAttribute("user");

        int bookId = Integer.parseInt(request.getParameter("bookId"));

        BorrowDAO dao = new BorrowDAO();

        dao.borrowBook(user.getUserId(), bookId);

        response.sendRedirect(request.getContextPath() + "/books");
    }
}