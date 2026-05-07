<%@ page import="java.util.List" %>
<%@ page import="model.BorrowRecord" %>

<%
List<BorrowRecord> records =
(List<BorrowRecord>) request.getAttribute("records");
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Borrow Records</title>
    </head>
    <body>

        <h2>All Borrow Records</h2>

        <table border="1" cellpadding="10">
            <tr>
                <th>Student</th>
                <th>Book</th>
                <th>Borrow Date</th>
                <th>Due Date</th>
                <th>Status</th>
                <th>Action</th>
            </tr>

        <% for(BorrowRecord r : records){ %>
        <tr>
            <td><%= r.getUsername() %></td>
            <td><%= r.getTitle() %></td>
            <td><%= r.getBorrowDate() %></td>
            <td><%= r.getDueDate() %></td>
            <td><%= r.getStatus() %></td>
            <td>
                <% if(r.getStatus().equals("borrowed")) { %>
                    <form action="${pageContext.request.contextPath}/return"
                          method="post">

                        <input type="hidden"
                               name="borrowId"
                               value="<%= r.getBorrowId() %>">

                        <button type="submit">Return</button>
                    </form>
                <% } else { %>
                    Returned
                <% } %>
            </td>
        </tr>
        <% } %>

        </table>

        <br>

        <a href="${pageContext.request.contextPath}/librarian/dashboard.jsp">
            Back
        </a>

    </body>
</html>