$(document).ready(function() {
	loadAllEmployees();
});
function loadAllEmployees() {
	$.ajax({
		url : "/api/users?role=employee",
		type : 'GET',
		success : function(data) {
			renderEmployees(data);
		},
		error : function(jqXHR, textStatus, error) {
			console.log(error);
		}
	});
}
function renderEmployees(data) {
	var tableHeader = "<tr><th>Sr.No.</th><th>Name</th><th>Phone</th><th>Email</th><th>Date Created</th><th>Status</th>"
			+ "<th>Links</th></tr>";
	var tableContent = "";
	if (data != null && data.content != null && data.content.length > 0) {
		for (var index = 0; index < data.content.length; index++) {
			var user = data.content[index];
			var status;
			if (user.isEnabled) {
				status = 'Active';
			} else {
				status = 'InActive';
			}
			
			tableContent = tableContent
					+ "<tr><td>"
					+ (index + 1)
					+ "</td><td>"
					+ (user.firstName + " " + user.lastName)
					+ "</td><td>"
					+ user.mobileNumber
					+ "</td><td>"
					+ user.emailId
					+ "</td><td>"
					+ new Date(user.dateAdded).toString().substring(0, 24)
					+ '</td><td>'
					+ status
					+ '</td>'
					+ '<td><a href="javascript:void(0)" onclick="changeStatus(\''+user.id+'\',\''+user.isEnabled+'\')">Change Status</a></td></tr>';
		}
	}
	document.getElementById("tblEmployees").innerHTML = tableHeader
			+ tableContent;
}
function changeStatus(id,isEnabled) {
	var user=null;
	console.log("status  "+isEnabled);
	if(isEnabled==true || isEnabled=='true'){
		user={"id":id,"isEnabled":false};
	}else{
		user={"id":id,"isEnabled":true};
	}
	$.ajax({
		url : "/api/users/" + id,
		type : 'PATCH',
		data : JSON.stringify(user),
		dataType : 'json',
		contentType : 'application/json',
		success : function(data) {
			window.location = '/dashboard/admin/employees'
		},
		error : function(jqXHR, textStatus, error) {
			console.log(error);
		}
	});
}