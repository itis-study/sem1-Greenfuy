<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
        }

        form {
            margin-bottom: 20px;
            display: flex;
            align-items: center;
        }

        label {
            font-weight: bold;
            margin-right: 10px;
        }

        select {
            padding: 8px;
            font-size: 14px;
        }

        input[type="submit"] {
            padding: 10px 20px;
            background-color: #4caf50;
            color: #fff;
            border: none;
            cursor: pointer;
            font-size: 16px;
            border-radius: 5px;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        ul.pagination {
            list-style-type: none;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
        }

        ul.pagination li {
            margin-right: 5px;
        }

        ul.pagination li a {
            padding: 10px;
            text-decoration: none;
            background-color: #4caf50;
            color: #fff;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
            cursor: pointer;
        }

        ul.pagination li a:hover {
            background-color: #45a049;
        }

        div.order-card {
            cursor: pointer;
            border: 1px solid #ccc;
            padding: 20px;
            margin-top: 20px;
            background-color: #fff;
            border-radius: 5px;
        }

        div.order-card:hover {
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
    </style>
    <title>Orders</title>
</head>
<body>
<div class="navbar">
    <button id="main" onclick="location.href='/semestrovka/main';">Main</button>
    <button id="bids" onclick="location.href='/semestrovka/orders';">My Orders</button>
    <button id="profile" onclick="location.href='/semestrovka/profile';">Profile</button>
    <button id="signout" onclick="location.href='/semestrovka/signout';">Sign Out</button>
</div>

<h1>My Orders</h1>

<div class="content">
    <form action="/semestrovka/orders" method="get">
        <label for="show">Show:</label>
        <select id="show" name="show" >
            <option value="DEFAULT">All</option>
            <option value="CREATED">Created</option>
            <option value="IN_PROGRESS">In progress</option>
            <option value="COMPLETED">Completed</option>
            <option value="CANCELLED">Cancelled</option>
        </select>
        <input id="submit" type="submit" value="Show">
    </form>

    <div>
        <c:choose>
            <%--@elvariable id="orders" type="java.util.List<ru.itis.entities.Order>"--%>
            <c:when test="${not empty orders}">
                <c:forEach items="${orders}" var="order">
                    <div class="order-card" onclick="location.href='/semestrovka/order-page?id=${order.id}';">
                        <p>Repairman name: ${order.repairmanName}</p>
                        <p>Price: ${order.price}</p>
                        <p>Status: ${order.status}</p>
                        <p>Created at: ${order.createdAt}</p>
                        <p>Updated at: ${order.updatedAt}</p>
                        <p>Description: ${fn:substring(order.description, 0, 30)}${fn:length(order.description) > 30 ? '...' : ''}</p>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="no-orders">No orders yet</div>
            </c:otherwise>
        </c:choose>

        <%--@elvariable id="pageCount" type="java.lang.Integer"--%>
        <c:if test="${pageCount > 1}">
            <ul class="pagination">
                <c:forEach begin="1" end="${pageCount}" var="pageNumber">
                    <li>
                        <a href="/semestrovka/orders?page=${pageNumber}&show=${param.sort}">${pageNumber}</a>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
    </div>
</div>

</body>
<script>
    function showOrders(select) {
        const sortParam = select.value;
        window.location.href = "/semestrovka/orders?show=" + sortParam;
    }
</script>
</html>
