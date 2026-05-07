package controller;

import dao.BorrowDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/mybooks")
public class MyBooksServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        BorrowDAO dao = new BorrowDAO();

        request.setAttribute("records",
                dao.getBorrowedBooks(user.getUserId()));

        request.getRequestDispatcher("student/mybooks.jsp")
               .forward(request, response);
    }
}