<%--@elvariable id="repairman" type="ru.itis.entities.Repairman"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.itis.entities.Repairman" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Repairman</title>
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

        div.review {
            border: 1px solid #ddd;
            padding: 10px;
            margin-bottom: 10px;
        }

        ul.pagination {
            list-style: none;
            padding: 0;
            display: flex;
            justify-content: center;
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

        button.create-order {
            padding: 10px 20px;
            background-color: #4caf50;
            color: #fff;
            border: none;
            cursor: pointer;
            font-size: 16px;
            border-radius: 5px;
            margin-top: 20px;
        }

        button.create-order:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<div class="navbar">
    <button id="main" onclick="location.href='/semestrovka/main';">Main</button>
    <button id="bids" onclick="location.href='/semestrovka/orders';">My Orders</button>

    <c:choose>
        <%--@elvariable id="userId" type="java.lang.String"--%>
        <c:when test="${!(userId eq null)}">
            <button id="profile" onclick="location.href='/semestrovka/profile';">Profile</button>
            <button id="signout" onclick="location.href='/semestrovka/signout';">Sign Out</button>
        </c:when>
        <c:otherwise>
            <button id="signup" onclick="location.href='/semestrovka/signup-choice';">Sign Up</button>
            <button id="login" onclick="location.href='/semestrovka/login';">Log In</button>
        </c:otherwise>
    </c:choose>
</div>

<% Repairman repairman = (Repairman) request.getSession().getAttribute("repairman"); %>
<h1><%=repairman.getFirstName() + " " + repairman.getLastName()%></h1>

<div class="content">
    <p class="profile-info">
        Rating: ${repairman.ratingSum} (Finished Orders: ${repairman.finishedOrdersCount})
    </p>
    <div>
        <p id="desc"><%=repairman.getDescription()%></p>
    </div>

    <h2>Reviews</h2>
    <c:choose>
        <%--@elvariable id="reviews" type="java.util.List<ru.itis.entities.Review>"--%>
        <c:when test="${reviews.size() == 0}">
            <p>There are no reviews yet</p>
        </c:when>
        <c:otherwise>
            <c:forEach var="review" items="${reviews}">
                <div class="review">
                    <p>${review.customerName}</p>
                    <p>${review.date}</p>
                    <p>${review.rating}</p>
                    <p>${review.text}</p>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    <%--@elvariable id="pageCount" type="java.lang.Integer"--%>
    <c:if test="${pageCount > 1}">
        <ul class="pagination">
            <c:forEach begin="1" end="${pageCount}" var="pageNumber">
                <li>
                    <a href="/semestrovka/repairman?page=${pageNumber}">${pageNumber}</a>
                </li>
            </c:forEach>
        </ul>
    </c:if>

    <button class="create-order" onclick="location.href='/semestrovka/create-order?repairmanId=${repairman.id}';">Create Order</button>
</div>
</body>
</html>
