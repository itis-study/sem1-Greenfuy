<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Profile</title>
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

        form {
            margin: 20px;
            text-align: center;
        }

        label {
            margin-top: 10px;
            display: block;
        }

        input, textarea {
            margin-bottom: 10px;
            padding: 8px;
            width: 100%;
            box-sizing: border-box;
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
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<div class="navbar">
    <button id="main" onclick="location.href='/semestrovka/main';">Main</button>
    <button id="bids" onclick="location.href='/semestrovka/orders';">My Orders</button>
    <button id="profile" onclick="location.href='/semestrovka/profile';">Profile</button>
    <button id="signout" onclick="location.href='/semestrovka/signout';">Sign Out</button>
</div>

<form action="/semestrovka/profile-edit" method="post">
    <c:choose>
        <%--@elvariable id="role" type="java.lang.String"--%>
        <c:when test="${role eq 'customers'}">
            <%--@elvariable id="user" type="ru.itis.entities.Customer"--%>
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="firstName" value="${user.firstName}">
            <br>
            <label for="lastName">Last Name:</label>
            <input type="text" id="lastName" name="lastName" value="${user.lastName}">
            <br>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${user.email}">
            <br>
        </c:when>
        <c:when test="${role eq 'repairmen'}">
            <%--@elvariable id="user" type="ru.itis.entities.Repairman"--%>
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="firstName" value="${user.firstName}">
            <br>
            <label for="lastName">Last Name:</label>
            <input type="text" id="lastName" name="lastName" value="${user.lastName}">
            <br>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${user.email}">
            <br>
            <label for="description">Description:</label><br>
            <textarea id="description" name="description">${user.description}</textarea>
            <br>
        </c:when>
    </c:choose>
    <input type="submit" value="Save">
</form>
</body>
</html>
