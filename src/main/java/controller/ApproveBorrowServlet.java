package controller;

import dao.BorrowDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/approve")
public class ApproveBorrowServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        int borrowId =
                Integer.parseInt(request.getParameter("borrowId"));

        BorrowDAO dao = new BorrowDAO();

        boolean success = dao.approveBorrow(borrowId);

        if(success){
            response.sendRedirect(
                    request.getContextPath() + "/allborrows"
            );
        } else {
            response.getWriter().println(
                    "Approve failed: No available books."
            );
        }
    }
}