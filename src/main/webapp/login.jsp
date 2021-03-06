<%@page import="utility.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>User Login</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="assets/library/bootstrap/css/bootstrap.min.css" >
    
    <!-- custom css -->
	<link rel="stylesheet" href="assets/css/login.css">
   
</head>

<body>

    <c:if test="${ role eq 'admin'}" >
        <c:redirect url = "adminDashboard.jsp"/>
    </c:if>
    
    <c:if test="${ role eq 'user' }">
    	<c:redirect url="welcome.jsp"/>
    </c:if>


    <jsp:include page="header.jsp"></jsp:include>
    
    
    
    <div class="wrapper">
        <h1 class="text-center">User-Login</h1>
        <div class="col-md-12">
        	<div class="error">
        		<c:out value= "${ requestScope.error }" />
        	</div>
        	<div class="success">
        		<c:out value="${ requestScope.success }" />
        	</div>
        </div>
        <form action="LoginServlet" method="post" id="login_form">
            <div class="form-group">
                <label for="email">Email address</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="Email" value="${loginEmail }">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Password" value="${loginPass }">
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
	<script src="assets/library/jquery-validation-1.19.3/dist/jquery.validate.js"></script>
   	<script src="assets/js/login.js"></script>
</body>

</html>