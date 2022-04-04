/**
 * 
 */
$(document).ready(function() {
	
	populateCountries("country_0", "state_0");

	//var date = new Date().toISOString().slice(0, 10);
	// $("#datepicker").datetimepicker({
		// format: 'yyyy-mm-dd'
		//endDate : new Date() ,
		//maxDate: date,
	// });
	
//	$("#email").blur(function(){
//		console.log("emaillll");	
//	});

	$("#profilepic").change(function() {
		const file = this.files[0];
		if (file) {
			let reader = new FileReader();
			reader.onload = function(event) {
				$("#imgPreview")
					.attr("src", event.target.result);
			};
			reader.readAsDataURL(file);
		}
	});

	i = 1;
	$('#add-more').cloneData({
		mainContainerId: 'main-container',
		cloneContainer: 'container-item',
		removeButtonClass: 'remove-item',
		maxLimit: 0,
		minLimit: 1,
		removeConfirm: true,
		removeConfirmMessage: 'Are you sure want to delete?',
		afterRender: function() {
			console.log("country_" + i, "state_" + i);
			populateCountries("country_" + i, "state_" + i);
			i++;
		},
		afterRemove: function() {
			i--;
			var numItems = $(".container-item").length;
			if (numItems <= 1) {
				$(".remove-item").hide();
			}
		},
		beforeRender: function() {
			var numItemsAfterAdd = $(".container-item").length;
			if(numItemsAfterAdd >= 1){
				$(".remove-item").show();
			}
		},
	});

	$.validator.addMethod("passwordFormatCheck", function(value, element) {
		//return this.optional(element) || /^(?=.*\d)(?=.*[A-Z])(?=.*\W).*$/i.test(value);
		return this.optional(element) || /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/i.test(value);
	}, 'Password must contain one capital letter,one numerical and one special character');

	$("#registration_form").validate({
		rules: {
			firstname: "required",
			lastname: "required",

			password: {
				required: true,
				passwordFormatCheck: true,
			},
			confirm_password: {
				required: true,
				equalTo: "#password"
			},
			email: {
				required: true,
				email: true,
				"remote": {
					url: 'CheckEmailAvailability',
					type: "post",
					data : {
						email : function(){
							return $("#email").val();
						}
					}
				} 
			},
			phone: {
				required: true,
				number: true,
			},
			postal_code: {
				required: true,
				number: true,
			}
			// gender: "required",

		},
		messages: {
			firstname: "Please enter your firstname",
			lastname: "Please enter your lastname",
			password: {
				required: "Please provide a password",
			},
			confirm_password: {
				required: "Please provide a password",
				equalTo: "Please enter the same password as above"
			},
			email: {
				required: "Please provide an email",
				email: "Please enter a valid email address",
				remote: "The corresponding email already exists"
			},
		}
	});
	
	
});
