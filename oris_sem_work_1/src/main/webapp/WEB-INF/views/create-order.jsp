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
            max-width: 400px;
            margin: 20px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        input[type="number"],
        textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
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

        span {
            color: darkred;
        }
    </style>
    <title>Create Order</title>
</head>
<body>
<div class="navbar">
    <button id="main" onclick="location.href='/semestrovka/main';">Main</button>
    <button id="bids" onclick="location.href='/semestrovka/orders';">My Orders</button>
    <button id="signup" onclick="location.href='/semestrovka/signup-choice';">Sign Up</button>
    <button id="login" onclick="location.href='/semestrovka/login';">Log In</button>
</div>

<h1>Create Order</h1>

<form action="/semestrovka/create-order" method="post">
    <label for="price">Price:</label>
    <input id="price" type="number" name="price" placeholder="Price..." oninput="validatePrice()" required>
    <span id="priceMessage"></span><br>

    <label for="description">Description:</label>
    <textarea id="description" name="description" rows="4" placeholder="Description..." required>Hi! I want to order your services.</textarea><br>

    <input type="submit" value="Create Order">
</form>
</body>
<script>
    function validatePrice() {
        const price = document.getElementById('price').value;
        const priceMessage = document.getElementById('priceMessage');
        const priceValid = price >= 10;

        priceMessage.textContent = priceValid ? " "
            : "The price must be at least 10";
        priceMessage.style.color = priceValid ? "green" : "darkred";
    }
</script>
</html>
