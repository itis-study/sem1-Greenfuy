<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        h1 {
            color: #4caf50;
            text-align: center;
            padding: 20px;
            background-color: #fff;
            margin: 0;
        }

        form {
            margin-top: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .sign-up-input {
            margin-bottom: 10px;
            padding: 10px;
            width: 300px;
            font-size: 14px;
        }

        .sign-up-input[type="checkbox"] {
            margin-right: 5px;
        }

        a {
            color: #4caf50;
        }

        span {
            color: darkred;
        }

        button#submitBtn {
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
            margin-top: 10px;
        }

        button#submitBtn:disabled {
            background-color: #ddd;
            color: #666;
            cursor: not-allowed;
        }
    </style>
    <title>Sign Up</title>
</head>
<body>
<div class="navbar">
    <button id="main" onclick="location.href='/semestrovka/main';">Main</button>
    <button id="bids" onclick="location.href='/semestrovka/orders';">My Orders</button>
    <button id="signup" onclick="location.href='/semestrovka/signup-choice';">Sign Up</button>
    <button id="login" onclick="location.href='/semestrovka/login';">Log In</button>
</div>

<h1>Sign Up</h1>
<form name="signUp" method="post" action="/semestrovka/signup">
    <input class="sign-up-input" name="firstName" type="text" placeholder="First Name..." required><br>
    <input class="sign-up-input" name="lastName" type="text" placeholder="Last Name..." required><br>
    <span id="emailExistsMessage"><%= "true".equals(request.getParameter("emailExists")) ? "User with this email already exists!" : ""%></span>
    <input class="sign-up-input" name="email" type="email" placeholder="Email..." oninput="clearSpan()" required><br>
    <input class="sign-up-input" name="password" type="password" id="password" placeholder="Password..." oninput="validatePassword()" required>
    <span id="passwordMessage"></span><br>
    <input class="sign-up-input" type="password" id="confirmPassword" placeholder="Confirm Password..." oninput="validatePassword()">
    <span id="confirmPasswordMessage"></span><br>
    <button id="submitBtn" disabled>Sign Up</button>
</form>

<script>
    function validatePassword() {
        const password = document.getElementById("password").value;
        const confirmPassword = document.getElementById("confirmPassword").value;
        const passwordMessage = document.getElementById("passwordMessage");
        const confirmPasswordMessage = document.getElementById("confirmPasswordMessage");
        const submitBtn = document.getElementById("submitBtn");

        const passwordValid = /^(?=.*[0-9])([a-zA-Z0-9]{8,})$/.test(password);

        const passwordsMatch = password === confirmPassword;

        passwordMessage.textContent = passwordValid ? " "
            : "The password does not meet the requirements (8 characters, only Latin, at least one digit)";
        passwordMessage.style.color = passwordValid ? "green" : "darkred";
        document.getElementById("password").style.backgroundColor = passwordValid ? "#e5ffe5" : "#fcd8d8";

        confirmPasswordMessage.textContent = passwordsMatch ? " " : "Password mismatch";
        confirmPasswordMessage.style.color = passwordsMatch ? "green" : "darkred";
        document.getElementById("confirmPassword").style.backgroundColor = passwordsMatch ? "#e5ffe5" : "#fcd8d8";

        submitBtn.disabled = !(passwordValid && passwordsMatch);
    }

    function clearSpan() {
        document.getElementById("emailExistsMessage").textContent = "";
    }
</script>
</body>
</html>
