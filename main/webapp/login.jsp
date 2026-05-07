<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management - Login</title>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            background: linear-gradient(135deg, #2563eb, #1e3a8a);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .login-box {
            background: rgba(255,255,255,0.95);
            backdrop-filter: blur(10px);
            padding: 40px;
            border-radius: 18px;
            width: 400px;
            box-shadow: 0 15px 40px rgba(0,0,0,0.25);
        }

        h2 {
            text-align: center;
            color: #1e3a8a;
            margin-bottom: 8px;
            font-size: 30px;
        }

        .title {
            text-align: center;
            color: #666;
            margin-bottom: 28px;
            font-size: 15px;
        }

        input {
            width: 100%;
            padding: 14px;
            margin: 10px 0;
            border: 1px solid #d1d5db;
            border-radius: 10px;
            font-size: 15px;
            transition: 0.3s;
        }

        input:focus {
            border-color: #2563eb;
            outline: none;
            box-shadow: 0 0 6px rgba(37,99,235,0.3);
        }

        button {
            width: 100%;
            padding: 14px;
            margin-top: 10px;
            background: #2563eb;
            border: none;
            color: white;
            border-radius: 10px;
            cursor: pointer;
            font-size: 16px;
            font-weight: bold;
            transition: 0.3s;
        }

        button:hover {
            background: #1d4ed8;
            transform: translateY(-2px);
        }

        .error {
            color: #dc2626;
            text-align: center;
            margin-top: 15px;
            font-size: 14px;
        }

        .link {
            text-align: center;
            margin-top: 22px;
            font-size: 14px;
            color: #555;
        }

        .link a {
            color: #2563eb;
            text-decoration: none;
            font-weight: bold;
        }

        .link a:hover {
            text-decoration: underline;
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