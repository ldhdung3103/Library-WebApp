package controller;

import dao.BookDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/books")
public class BookServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        BookDAO dao = new BookDAO();

        request.setAttribute("books", dao.getAllBooks());

        RequestDispatcher rd =
                request.getRequestDispatcher("student/books.jsp");

        rd.forward(request, response);
    }
}