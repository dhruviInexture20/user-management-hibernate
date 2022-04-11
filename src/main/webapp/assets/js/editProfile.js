/**
 * 
 */



$(document).ready(function() {
	
	// $("input[name=gender]").attr("disabled", true);
	

	// get user address
	$.ajax({
		url: "GetUserAddressServlet",
		type: "post",
		data:{
			userid : function(){
				console.log($("#userid").val());
				return $("#userid").val();
			},
		},
		success : function(response){
			
			var addressList = jQuery.parseJSON(response);
			console.log(addressList);
			var count = 0;
			console.log("address = "+addressList.length);
			
			$.each(addressList, function(key, address){
				$("#addressID_"+count).val(address.addressid);
				console.log($("#addressID_"+count).val());
				$("#streetAddress_"+count).val(address.streetAddress);
				
				$("#city_"+count).val(address.city);
				$("#postal_code_"+count).val(address.postalCode);
				$("#country_"+count).val(address.country);
				populateStates( "country_"+count , "state_"+count );
				$("#state_"+count).val(address.state);
				count++;
				if(addressList.length > count){
					$("#add-more").click();
				}
			});
		},
		error: function(){
			alert("alert while retriving user data");
		}
	});
});

