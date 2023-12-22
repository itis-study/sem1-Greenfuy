<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.itis.entities.Order" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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

    div.content {
        margin: 20px;
        text-align: center;
    }

    p#desc {
        word-wrap: break-word;
        max-width: 600px;
        margin: 0 auto;
    }

    h2 {
        margin-top: 20px;
    }

    ul.pagination li {
        display: inline-block;
        margin-right: 5px;
    }

    ul.pagination li a {
        text-decoration: none;
        padding: 5px 10px;
        background-color: #4caf50;
        color: #fff;
        border-radius: 3px;
    }

    ul.pagination li a:hover {
        background-color: #45a049;
    }

    button {
        padding: 10px 20px;
        background-color: #4caf50;
        color: #fff;
        border: none;
        cursor: pointer;
        font-size: 16px;
        border-radius: 5px;
        margin-top: 20px;
    }

    button:hover {
        background-color: #45a049;
    }
</style>
<head>
    <title>Order Page</title>
</head>
<body>
<div class="navbar">
    <button id="main" type="submit" onclick="location.href='/semestrovka/main';">Main</button>
    <button id="bids" type="submit" onclick="location.href='/semestrovka/orders';">My Orders</button>
    <button id="profile" type="submit" onclick="location.href='/semestrovka/profile';">Profile</button>
    <button id="signout" type="submit" onclick="location.href='/semestrovka/signout';">Sign Out</button>
</div>

<% Order order = (Order) request.getSession().getAttribute("order"); %>
<h1>Order of <%=order.getRepairmanName()%> services</h1>

<div class="content">
    <%--@elvariable id="order" type="ru.itis.entities.Order"--%>
    <p>Status: ${order.status}</p>
        <p>Created at: ${order.createdAt}</p>
    <p>Last changes: ${order.updatedAt}</p>
        <p>Price: ${order.price}</p>
    <br>
    <div>
        <p id="desc">Description: <%=order.getDescription()%></p>
    </div><br>

    <button type="submit" onclick="location.href='/semestrovka/chat';">To Chat</button><br>

    <c:choose>
        <%--@elvariable id="role" type="java.lang.String"--%>
        <c:when test="${role == 'repairmen'}">
            <button type="submit" onclick="location.href='/semestrovka/change-order-status?status=1';">Accept Order</button>
        </c:when>
        <c:otherwise>
            <div>
                <button type="submit" onclick="location.href='/semestrovka/change-order-status?status=2';">Cancel Order</button>
                <button type="submit" onclick="location.href='/semestrovka/change-order-status?status=3';">Complete Order</button>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
