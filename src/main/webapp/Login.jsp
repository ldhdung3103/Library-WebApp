<%-- 
    Document   : Login
    Created on : May 4, 2026, 6:23:30 PM
    Author     : MSI
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Library Management - Login</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: #f4f6f8;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }

            .login-box {
                background: white;
                padding: 40px;
                border-radius: 12px;
                width: 350px;
                box-shadow: 0 5px 20px rgba(0,0,0,0.15);
            }

            h2 {
                text-align: center;
                margin-bottom: 25px;
            }

            input {
                width: 100%;
                padding: 12px;
                margin: 10px 0;
                border: 1px solid #ccc;
                border-radius: 8px;
                box-sizing: border-box;
            }

            button {
                width: 100%;
                padding: 12px;
                background: #2563eb;
                border: none;
                color: white;
                border-radius: 8px;
                cursor: pointer;
                font-size: 16px;
            }

            button:hover {
                background: #1d4ed8;
            }

            .error {
                color: red;
                text-align: center;
                margin-top: 15px;
            }

            .title {
                text-align: center;
                color: #555;
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <div class="login-box">
            <h2>Library Login</h2>
            <div class="title">Library Management System</div>

            <form action="login" method="post">
                <input type="text"
                       name="username"
                       placeholder="Enter username"
                       required>

                <input type="password"
                       name="password"
                       placeholder="Enter password"
                       required>

                <button type="submit">Login</button>
            </form>

            <%
                if(request.getParameter("error") != null){
            %>
                <div class="error">
                    Invalid username or password
                </div>
            <%
                }
            %>
        </div>
    </body>
</html>
