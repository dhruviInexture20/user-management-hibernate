<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Dashboard</title>

<link href="assets/library/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet" type="text/css" href="assets/library/DataTables/datatables.css">
<!-- <link rel="stylesheet" type="text/css" href="assets/library/DataTables/DataTables-1.11.4/css/dataTables.bootstrap.min.css"> -->
<link rel="stylesheet" href="assets/css/adminDashboard.css">

</head>
<body>
	
	<div id="wrapper" class="container">
		
        <h2 class="text-center">User Detail</h2>
        <table id="myTable" class="table table-striped table-bordered" style="width:100%">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>FirstName</th>
                    <th>LastName</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Gender</th>
                    <th>DOB</th>
                    <th>Designation</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
        </table>
    </div>

	<script src="assets/library/jquery/jquery-3.6.0.min.js"></script>
    <script src="assets/library/bootstrap/js/bootstrap.min.js"></script>
    <!-- <script src="assets/library/DataTables/DataTables-1.11.4/js/jquery.dataTables.min.js"></script>  -->
	<script src="assets/library/DataTables/datatables.js"></script>
	<script src="assets/library/DataTables/DataTables-1.11.4/js/dataTables.bootstrap.min.js"></script>
	<script src="assets/js/adminDashboard.js"></script>
	
</body>
</html>