/**
 * 
 */

$(document).ready(function () {

	var activeUserid;
	var table =
		$("#myTable").DataTable({
			//serverSide: true,
			ajax: {
				type: 'POST',
				url: 'GetAllUsers',
				//dataType : 'JSON',
				error: function (error) {
					alert("Error = " + error.toString());
				},
			},
			columns: [
				{ "data": 'userid' },
				{ "data": 'fname' },
				{ "data": 'lname' },
				{ "data": 'email' },
				{ "data": 'phone' },
				{ "data": 'gender' },
				{ "data": 'dob' },
				{ "data": 'designation' },
				{ "defaultContent": "<button class='btn btn-primary editUser' >Edit</button> " },
				{ "defaultContent": "<button class='btn btn-danger deleteUser'>Delete</button> " },
				{ "defaultContent": "<a href='EditUserByAdminServlet?userid="+activeUserid+"' class='btn btn-primary edituser'>Edit User</a>"},
			],
		});

	$("#myTable tbody").on("click", "tr", function () {

	    var row_data = table.row(this).data(); // get the row data
		activeUserid = row_data["userid"];
		console.log(activeUserid);
		$("#hidden_userid").val(activeUserid);

	});

	$("#myTable ").on("click", ".deleteUser", function () {
		console.log("delete");
	
		$.ajax({
			url: "DeleteUserServlet",
			type: "post",
			data:{
				userid: activeUserid
			},
			success: function () {
				alert("successs");

			},
			error: function () {
				alert("fail");
			}
	
		});
	});


	$("#myTable ").on("click", ".editUser", function () {
		console.log("edit");
		$("#submit").click();


		// $.ajax({
		// 	url: "EditUserByAdminServlet",
		// 	type: "post",
		// 	data:{
		// 		userid: activeUserid
		// 	},
		// 	success: function(){
		// 		console.log("go to registration page");

		// 	}
	
		// });
	});

});
