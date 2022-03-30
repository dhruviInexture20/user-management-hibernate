<%@page import="utility.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>User Login</title>

    <!-- Bootstrap -->
    <link href="assets/library/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .wrapper {
            width: 500px;
            margin: 20px auto;
            border: 1px solid black;
            border-radius: 10px;
            padding: 10px;
            box-shadow: 15px 15px 5px grey;
        }
        .links{
            margin: 10px 0px 15px 0px;
        }
        .links a {
        	padding-right: 20px;
        }
    </style>
</head>

<body>
    
    <div class="wrapper">
        <h1 class="text-center">User Login</h1>
        <div class="col-md-12">
        	<c:out value= "<%= ServletUtility.getErrorMessage(request, response) %>" />	
            <c:out value= "<%= ServletUtility.getSuccessMessage(request) %>" />
        </div>
        <form action="LoginServlet" method="post">
            <div class="form-group">
                <label for="email">Email address</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="Email">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Password">
            </div>
            <input type="submit" class="btn btn-primary" value="Login">
        </form>
        <div class="links">
        <a href="registration.jsp">Don't have an account?</a>
        <a href="forgetpassword.jsp">Forget Password?</a>
        </div>
    </div>
    
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="assets/library/jquery/jquery-3.6.0.min.js"></script>
    <script src="assets/library/bootstrap/js/bootstrap.min.js"></script>
   	<script src="assets/js/login.js"></script>
</body>

</html>