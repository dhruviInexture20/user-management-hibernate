/**
 * 
 */

$(function() {

	$("#myTable").DataTable({
		//serverSide: true,
		ajax: {
			type: 'POST',
			url: 'GetAllUsers',
			//dataType : 'JSON',
			error : function(error){
				alert("Error = " + error.toString());
			},
		},
    	columns: [
        { "data": 'userid' },
        { "data": 'fname' },
        { "data": 'lname' },
        { "data": 'email' },
        { "data": 'phone' },
		{ "data": 'gender'},
		{ "data": 'dob'},
		{ "data": 'designation'},
		{ "defaultContent": "<button class='btn btn-primary' id='editUser'>Edit</button> " },
		{ "defaultContent": "<button class='btn btn-danger' id='deleteUser'>Delete</button> " }
		],

});
});