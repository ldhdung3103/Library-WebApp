package controller;

import dao.BorrowDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/allborrows")
public class AllBorrowServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        BorrowDAO dao = new BorrowDAO();

        request.setAttribute("records",
                dao.getAllBorrowRecords());

        request.getRequestDispatcher("librarian/allborrows.jsp")
               .forward(request, response);
    }
}