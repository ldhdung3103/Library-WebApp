package controller;

import dao.BookDAO;
import model.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/managebooks")
public class BookManagementServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        BookDAO dao = new BookDAO();
        List<Book> books = dao.getAllBooks();

        request.setAttribute("books", books);

        request.getRequestDispatcher("librarian/managebooks.jsp")
               .forward(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        String action = request.getParameter("action");

        BookDAO dao = new BookDAO();

        if ("add".equals(action)) {

            Book book = new Book();
            book.setTitle(request.getParameter("title"));
            book.setAuthor(request.getParameter("author"));
            book.setCategory(request.getParameter("category"));
            book.setIsbn(request.getParameter("isbn"));
            
            int quantity =
                Integer.parseInt(request.getParameter("quantity"));

            book.setQuantity(quantity);
            book.setAvailableQuantity(quantity);

            dao.addBook(book);

        } else if ("delete".equals(action)) {

            int bookId =
                Integer.parseInt(request.getParameter("bookId"));
            
            boolean success = dao.deleteBook(bookId);
            
            if(!success){
                response.getWriter().println(
                    "<script>alert('Cannot delete: Book is currently borrowed or pending approval.');" +
                    "location='" + request.getContextPath() + "/managebooks';</script>"
                );
                return;
            }
        }

        response.sendRedirect(
            request.getContextPath() + "/managebooks"
        );
    }
}