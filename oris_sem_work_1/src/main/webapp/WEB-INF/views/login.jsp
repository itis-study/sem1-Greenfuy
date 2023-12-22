<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log In</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            color: #333;
        }

        h1 {
            color: #4caf50;
            text-align: center;
            padding: 20px;
            background-color: #fff;
            margin: 0;
        }

        div.navbar {
            display: flex;
            justify-content: space-around;
            background-color: #333;
            padding: 10px 0;
        }

        div.navbar button {
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
        }

        div.navbar button:hover {
            background-color: #45a049;
        }

        div.content {
            margin: 20px;
            text-align: center;
        }

        form {
            margin-top: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        input {
            margin-bottom: 10px;
            padding: 10px;
            width: 300px;
            font-size: 14px;
        }

        #message {
            color: darkred;
            margin-bottom: 10px;
        }

        #password {
            background-color: #ffffff;
        }

        #submitBtn {
            padding: 10px 20px;
            background-color: #4caf50;
            color: #fff;
            border: none;
            cursor: pointer;
            font-size: 16px;
            border-radius: 5px;
        }

        #submitBtn:disabled {
            background-color: #ccc;
            cursor: not-allowed;
        }
    </style>
</head>
<body>
<div class="navbar">
    <button id="main" onclick="location.href='/semestrovka/main';">Main</button>
    <button id="bids" onclick="location.href='/semestrovka/orders';">My Orders</button>
    <button id="signup" onclick="location.href='/semestrovka/signup-choice';">Sign Up</button>
    <button id="login" onclick="location.href='/semestrovka/login';">Log In</button>
</div>

<h1>Log In</h1>

<div class="content">
    <form method="post" action="/semestrovka/login">
        <input name="email" type="email" placeholder="Email..." required><br>
        <input name="password" type="password" id="password" placeholder="Password..." oninput="validatePassword()" required>
        <span id="message"><%= "true".equals(request.getParameter("invalid")) ? "Invalid password or email" : ""%></span><br>
        <button id="submitBtn" disabled>Log In</button>
    </form>
</div>

<script>
    function validatePassword() {
        document.getElementById("message").textContent = "";
        const password = document.getElementById("password").value;
        const message = document.getElementById("message");
        const submitBtn = document.getElementById("submitBtn");

        const passwordValid = /^(?=.*[0-9])([a-zA-Z0-9]{8,})$/.test(password);

        message.textContent = passwordValid ? ""
            : "The password does not meet the requirements (8 characters, only Latin, at least one digit)";
        message.style.color = passwordValid ? "green" : "darkred";
        document.getElementById("password").style.backgroundColor = passwordValid ? "#c4ffc3" : "#ffc3c3";

        submitBtn.disabled = !(passwordValid);
    }
</script>
</body>
</html>
