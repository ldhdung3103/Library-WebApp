<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library Management System</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #2563eb, #1e3a8a);
            color: white;
        }

        .container {
            height: 100vh;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        h1 {
            font-size: 52px;
            margin-bottom: 10px;
        }

        p {
            font-size: 20px;
            margin-bottom: 40px;
            opacity: 0.9;
        }

        .btn {
            padding: 15px 35px;
            font-size: 18px;
            background: white;
            color: #2563eb;
            text-decoration: none;
            border-radius: 10px;
            font-weight: bold;
            transition: 0.3s;
        }

        .btn:hover {
            background: #dbeafe;
        }

        .features {
            display: flex;
            gap: 30px;
            margin-top: 60px;
        }

        .card {
            background: rgba(255,255,255,0.15);
            padding: 20px;
            border-radius: 12px;
            width: 220px;
            text-align: center;
        }

        .card h3 {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<div class="container">

    <h1>Library Management System</h1>
    <p>Smart Digital Library for Students, Librarians and Managers</p>

    <a href="login.jsp" class="btn">Get Started</a>

    <div class="features">
        <div class="card">
            <h3>📚 Book Search</h3>
            <p>Search and explore books instantly</p>
        </div>

        <div class="card">
            <h3>🔄 Borrow & Return</h3>
            <p>Easy borrowing management</p>
        </div>

        <div class="card">
            <h3>📊 Dashboard</h3>
            <p>Track records and reports</p>
        </div>
    </div>

</div>

</body>
</html>