<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
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

        .profile-info {
            margin-bottom: 20px;
        }

        button {
            padding: 10px 20px;
            background-color: #4caf50;
            color: #fff;
            border: none;
            cursor: pointer;
            font-size: 16px;
            border-radius: 5px;
            margin-bottom: 10px;
        }

        button:hover {
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

<h1>My profile</h1>

<div class="content">
    <c:choose>
        <%--@elvariable id="role" type="java.lang.String"--%>
        <c:when test="${role eq 'customers'}">
            <%--@elvariable id="user" type="ru.itis.entities.Customer"--%>
            <div class="profile-info">
                <p><c:out value="${user.firstName} ${user.lastName}" /></p>
                <p><c:out value="${user.email}" /></p>
            </div>
        </c:when>
        <c:when test="${role eq 'repairmen'}">
            <%--@elvariable id="user" type="ru.itis.entities.Repairman"--%>
            <div class="profile-info">
                <p><c:out value="${user.firstName} ${user.lastName}" /></p>
                <p><c:out value="${user.email}" /></p>
                <p><c:out value="Rating: ${user.ratingSum} (Finished Orders: ${user.finishedOrdersCount})" /></p>
                <p><c:out value="Decription: ${user.description}" /></p>
            </div>
        </c:when>
    </c:choose>

    <div>
        <button onclick="location.href='/semestrovka/profile-edit';">Edit profile</button>
    </div>

    <div>
        <button onclick="location.href='/semestrovka/orders';">My Orders</button>
    </div>
</div>
</body>
</html>
