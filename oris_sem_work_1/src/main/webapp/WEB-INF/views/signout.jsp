<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            color: #333;
            text-align: center;
        }

        h1 {
            color: #4caf50;
            padding: 20px;
            background-color: #fff;
            margin: 0;
        }

        form {
            margin-top: 20px;
        }

        input[type="submit"] {
            background-color: #4caf50;
            color: #fff;
            border: none;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
    <title>Sign Out</title>
</head>
<body>
<h1>Are you sure you want to sign out?</h1>
<form action="/semestrovka/signout" method="post">
    <input name="confirmed" type="hidden" value="true">
    <input type="submit" value="Yes, I am sure">
</form>
</body>
</html>
