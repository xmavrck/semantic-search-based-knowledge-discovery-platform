$(document).ready(function() {
	$('#submitChangePassword').click(function() {
		var oldPassword = validateOldPassword(true);
		var pass = validatePassword(true);
		var cPass = validateCPassword(true);
		if (pass && cPass) {
			changePassword($("#oldPassword").val(), $("#newPassword").val());
		}
	});
});
function changePassword(oldPassword, newPassword) {
	var user = {
		"id" : getCookie('s_a_id'),
		"oldPassword" : oldPassword,
		"password" : newPassword
	}
	$
			.ajax({
				url : "/api/users/" + getCookie('s_a_id'),
				type : 'PATCH',
				data : JSON.stringify(user),
				dataType : 'json',
				contentType : 'application/json',
				success : function(data) {
					console.log(data);
					resetFields();
					if (data.code == 200) {
						$("#error").html(
								"Your password has been changed successfully");
					} else {
						$("#error")
								.html(
										"Sorry,Some internal error occured.Please try again later.")
					}

				},
				error : function(jqXHR, textStatus, error) {
					console.log(error);
					resetFields();
					$("#error")
							.html(
									"Your old password is not correct.Please try again.");
				}
			});
}
function resetFields() {
	$('#oldPassword').val('')
	$('#newPassword').val('')
	$('#confirmPassword').val('')
}
function getCookie(name) {
	var value = "; " + document.cookie;
	var parts = value.split("; " + name + "=");
	if (parts.length == 2)
		return parts.pop().split(";").shift();
}
function validateOldPassword(isFormSubmit) {
	var password = $("#oldPassword").val();
	var res = null;
	if (password.trim() == '') {
		res = 'Please fill out this field';
	}
	if (res != null) {
		$("#newPassword");
		if (isFormSubmit) {
			$('#oldPasswordError').html(res);
		}
		return false;
	} else {
		$("#newPassword").css('border', '');
		return true;
	}
}
function validatePassword(isFormSubmit) {
	var password = $("#newPassword").val();
	var res = isPassword(password);
	if (res != null) {
		$("#newPassword");
		if (isFormSubmit) {
			$('#newPasswordError').html(res);
		}
		return false;
	} else {
		$("#newPassword").css('border', '');
		return true;
	}
}
function validateCPassword(isFormSubmit) {
	var password = $("#newPassword").val();
	var cPassword = $("#confirmPassword").val();
	if (password != cPassword) {
		$("#confirmPassword");
		if (isFormSubmit) {
			$('#confirmPasswordError').html(
					'Password and Confirm Password should match');
		}
		return false;
	} else {
		$("#confirmPassword").css('border', '');
		return true;
	}
}