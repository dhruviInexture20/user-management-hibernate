<%@ page import="utility.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="true" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>

<head>
<title>User Registration</title>

<!-- Bootstrap -->
<link href="assets/library/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="assets/css/registration.css" rel="stylesheet">

</head>


<body>
 	
	<section>
		<div class="main container">
		
	 <c:forEach items="${userData.getAddressList() }" var="address">
        <tr>
            <td><c:out value="${address.getState()}"/></td>
            <td> <c:out value="${address.getCity()}"/></td>  
        </tr>
    </c:forEach>
		
		
		
			<h1 class="text-center">User Registration</h1>
			<div class="col-md-12" class="msg">
				<c:out value= "${ errorMsg }" />				
				<c:out value= "${ requestScope.success }" />
				<c:out value= "${ requestScope.error }" />
				
				
			</div>
			<form id="registration_form" 
				action= "<c:if test="${empty userData}">RegistrationServlet </c:if>
						<c:if test="${not empty userData}">EditProfileServlet</c:if>" 
				method="post" enctype="multipart/form-data">
				<div class="col-md-12">
				
					<!-- first Name -->
					<div class="form-group col-md-6">
						<label for="firstname">First Name</label> <input type="text"
							class="form-control" id="firstname" name="firstname"
							placeholder="First Name" 
							required 
							value=<c:out value='${userData.getFname() }'/> >
					</div>

					<!-- Last Name -->
					<div class="form-group col-md-6">
						<label for="lastname">Last Name</label> <input type="text"
							class="form-control" id="lastname" name="lastname"
							placeholder="Last Name" 
							required
							value = <c:out value='${userData.getLname() }'/> >
							
					</div>
				</div>

				<div class="col-md-12">
					<!-- Email -->
					<div class="form-group col-md-6">
						<label for="email">Email</label> 
						<input type="text"
							class="form-control" id="email" name="email" placeholder="Email"
							required
							value = <c:out value='${userData.getEmail() }'/> 
							<c:if test="${not empty userData}">  
   								disabled  
							</c:if>  
							>
					</div>

					<!-- phone -->
					<div class="form-group col-md-6">
						<label for="phone">Phone number</label> <input type="tel"
							class="form-control" name="phone" id="phone"
							placeholder="phone number" pattern="[0-9]{10}" 
							required
							value = <c:out value='${userData.getPhone() }'/> >
					</div>
				</div>


				<div class="col-md-12">
					<!-- password -->
					<div class="form-group col-md-6">
						<label for="password">Password</label> <input type="password"
							class="form-control" id="password" name="password"
							placeholder="Password" 
							required
							value = <c:out value='${userData.getPassword() }'/> >
							
					</div>

					<!-- confirm password -->
					<div class="form-group col-md-6">
						<label for="confirm_password">confirm Password</label> <input
							type="password" class="form-control" id="confirm_password"
							name="confirm_password" placeholder="Confirm Password" 
							required
							value = <c:out value='${userData.getPassword() }'/> >
							
					</div>
				</div>

				<div class="col-md-12">
					<!-- designation -->
					<div class="form-group col-md-6">
						<label for="role_title">User Designation</label> <select
							class="form-control" name="designation" required>
							<option value="">Select designation</option>
							<option value="intern" ${userData.getDesignation() == "intern" ? 'selected="selected"' : ''}>Intern</option>
							<option value="jrDeveloper" ${userData.getDesignation() == "jrDeveloper" ? 'selected="selected"' : ''}>Junior Developer</option>
							<option value="srDeveloper" ${userData.getDesignation() == "srDeveloper" ? 'selected="selected"' : ''}>Senior Developer</option>
							<option value="team-lead" ${userData.getDesignation() == "team-lead" ? 'selected="selected"' : ''}>Team Lead</option>
							<option value="project-manager" ${userData.getDesignation() == "project-manager" ? 'selected="selected"' : ''}>Project Manager</option>
						</select>
					</div>
					<div class="col-md-6">
						<label for="birthdate">Birthday:</label> 
						<input type="date"
							value = <c:out value='${userData.getDob() }'/>
							id="datepicker" name="birthdate" class="form-control" >
					</div>
				</div>

				<div class="col-md-12">
					<!-- gender -->
					<div class="gender-input col-md-12">
						<label>Select your gender</label>
						<div class="radio">
							<label><input type="radio" id="male" value="male"
								name="gender"
								${userData.getGender() eq 'male' ? 'checked' : ''} >
								Male</label>
						</div>
						<div class="radio">
							<label><input type="radio" id="female" value="female"
								name="gender" 
								${userData.getGender() eq 'female' ? 'checked' : ''} >Female</label>
						</div>
					</div>
				</div>

				<div class="col-md-12">
					<div class="form-group col-md-12">
						<label for="profilepic">Choose Your Profile Picture</label> <input
							type="file" id="profilepic" name="profilepic"
							accept=".jpg, .jpeg, .png">
					</div>	
					<div class="holder col-md-12">
						<img id="imgPreview" src="#" alt="profile pic">
					</div>
				</div>
				
				
				<c:set var="count" value="0" scope="page" />
				<c:forEach items="${userData.getAddressList() }" var="address">
				
				<!-- Address  -->
				<div id="main-container">
					<div class="container-item col-md-12">
						<!-- street address -->
						<div class="form-group col-md-12">
							<label class="control-label" for="address_0">Street
								Address</label> <input class="form-control" type="text" id="address_${count }"
								name="address" required 
								value = <c:out value="${address.getStreetAddress() }" />
								>
						</div>
						<br>

						<!-- city -->
						<div class="col-md-12">
							<div class="form-group col-md-6">
								<label class="control-label" for="city_0">City</label> <input
									class="form-control" type="text" id="city_${count }" name="city"
									required
									value = <c:out value="${address.getCity() }" />
									>
							</div>
							
							
							<!-- country -->
							<div class="form-group col-md-6">
								<label class="control-label" for="country_0">Country</label> 
								<select id="country_${count }" name="country" class="form-control" required ></select>
							</div>
						</div>

						<br>

						<div class="col-md-12">
							<!-- state -->
							<div class="form-group col-md-6">
								<label class="control-label" for="state_0">State</label> 
								<select id="state_${count }" name="state" class="form-control" required  ></select>
							</div>

							<div class="form-group col-md-6">
								<label class="control-label" for="postal_code_0">PostalCode</label>
								<input type="text" name="postal_code" id="postal_code_${count }"
									class="form-control" required
									value = <c:out value="${address.getPostalCode() }" />
									>
							</div>
						</div>
						<br>

						<!-- remove item  -->
						<div class="col-md-12">
							<a href="javascript:void(0)" id="remove_0"
								class="remove-item btn btn-danger">Remove</a>
						</div>
						<br>
					</div>
				</div>
				 <c:set var="count" value="${count + 1 }" scope="page" /> 
				</c:forEach>
				
				<c:if test="${empty userData}">  
   				
   				
   				<div id="main-container">
					<div class="container-item col-md-12">
						<!-- street address -->
						<div class="form-group col-md-12">
							<label class="control-label" for="address_0">Street
								Address</label> <input class="form-control" type="text" id="address_${count }"
								name="address" required 
								value = <c:out value="${address.getStreetAddress() }" />
								>
						</div>
						<br>

						<!-- city -->
						<div class="col-md-12">
							<div class="form-group col-md-6">
								<label class="control-label" for="city_0">City</label> <input
									class="form-control" type="text" id="city_${count }" name="city"
									required
									value = <c:out value="${address.getCity() }" />
									>
							</div>
							<!-- country -->
							<div class="form-group col-md-6">
								<label class="control-label" for="country_0">Country</label> 
								<select id="country_${count }" name="country" class="form-control" required ></select>
							</div>
						</div>

						<br>

						<div class="col-md-12">
							<!-- state -->
							<div class="form-group col-md-6">
								<label class="control-label" for="state_0">State</label> 
								<select id="state_${count }" name="state" class="form-control" required></select>
							</div>

							<div class="form-group col-md-6">
								<label class="control-label" for="postal_code_0">PostalCode</label>
								<input type="text" name="postal_code" id="postal_code_${count }"
									class="form-control" required
									value = <c:out value="${address.getPostalCode() }" />
									>
							</div>
						</div>
						<br>

						<!-- remove item  -->
						<div class="col-md-12">
							<a href="javascript:void(0)" id="remove_0"
								class="remove-item btn btn-danger">Remove</a>
						</div>
						<br>
					</div>
				</div>
   				
   			   								  
				</c:if>
				
				<div class="col-md-4">
					<a id="add-more" class="btn btn-info" href="javascript:;">Add
						Another Address</a>
				</div>
				<div class="col-md-4">
					<input type="submit" class="btn btn-success" 
					value= "<c:if test="${empty userData}">Register </c:if>
						<c:if test="${not empty userData}">Save Edits </c:if>
					" >
				</div>
			</form>
			
			<c:if test="${empty userData}">
			<div class="col-md-4">
				<a href="login.jsp" class="btn btn-primary">Goto Login Page</a>
			</div>
			</c:if>
		</div>
	</section>


	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="assets/library/jquery/jquery-3.6.0.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="assets/library/bootstrap/js/bootstrap.min.js"></script>
	<script src="assets/library/clone-field-increment-id/cloneData.js"></script>
	<script
		src="assets/library/jquery-validation-1.19.3/dist/jquery.validate.js"></script>
	<script src="assets/library/List_Country_State-master/countries.js"></script>
	<script src="assets/library/datetimepicker/moment.min.js"></script>
	<script
		src="assets/library/datetimepicker/bootstrap-datetimepicker.min.js"></script>
	<script src="assets/js/registration.js"></script>
</body>

</html>
