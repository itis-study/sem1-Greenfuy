<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
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

        div.repairman-card {
            cursor: pointer;
            border: 1px solid #ccc;
            padding: 20px;
            margin-top: 20px;
            background-color: #fff;
            border-radius: 5px;
        }

        div.repairman-card:hover {
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
<div class="navbar">
    <button id="main" onclick="location.href='/semestrovka/main';">Main</button>
    <button id="bids" onclick="location.href='/semestrovka/orders';">My Orders</button>
    <c:choose>
        <%--@elvariable id="userId" type="java.lang.String"--%>
        <c:when test="${userId eq null}">
            <button id="signup" onclick="location.href='/semestrovka/signup-choice';">Sign Up</button>
            <button id="login" onclick="location.href='/semestrovka/login';">Log In</button>
        </c:when>
        <c:otherwise>
            <button id="profile" onclick="location.href='/semestrovka/profile';">Profile</button>
            <button id="signout" onclick="location.href='/semestrovka/signout';">Sign Out</button>
        </c:otherwise>
    </c:choose>
</div>

<h1>Dashboard</h1>

<div class="content">
    <form action="/semestrovka/main" method="get">
        <label for="sort">Sort by:</label>
        <select id="sort" name="sort" >
            <option value="default">Default</option>
            <option value="finished_bids_count">By finished orders</option>
            <option value="rating_sum">By rating</option>
        </select>
        <input id="submit" type="submit" value="Sort">
    </form>

    <div>
        <%--@elvariable id="repairmen" type="java.util.List"--%>
        <c:forEach items="${repairmen}" var="repairman">
            <div class="repairman-card" onclick="location.href='/semestrovka/repairman?id=${repairman.id}';">
                <p>Name: ${repairman.firstName} ${repairman.lastName}</p>
                <p>Rating: ${repairman.ratingSum}</p>
                <p>Bids finished: ${repairman.finishedOrdersCount}</p>
                <p>Description: ${fn:substring(repairman.description, 0, 30)}${fn:length(repairman.description) > 30 ? '...' : ''}</p>
            </div>
        </c:forEach>
    </div>

    <%--@elvariable id="pageCount" type="java.lang.Integer"--%>
    <c:if test="${pageCount > 1}">
        <ul class="pagination">
            <c:forEach begin="1" end="${pageCount}" var="pageNumber">
                <li>
                    <a href="/semestrovka/main?page=${pageNumber}&sort=${param.sort}">${pageNumber}</a>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</div>
</body>
</html>
