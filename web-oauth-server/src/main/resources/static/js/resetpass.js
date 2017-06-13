var RESET_ERROR_BOXES = [ "#resetNewPasswordError", "#resetConfPasswordError" ];
$(document).ready(
		function() {
			$("#resetNewPassword").keyup(function() {
				removeAndClearPopUpClass('#resetNewPasswordError', DOWN);
				validateResetPassword(false);
			});
			$("#resetNewPassword").click(
					function() {
						validateResetPassword(false);
						if ($("#resetNewPasswordError").html() != '') {
							onclickShowPopUp('#resetNewPasswordError',
									RESET_ERROR_BOXES, DOWN);
						} else {
							removeAndClearPopUpClass('#resetNewPasswordError',
									DOWN);
						}
					});
			$("#resetNewPassword").blur(function() {
				removePopUpClass('#resetNewPasswordError', DOWN);
				validateResetPassword(false);
			});
			$("#resetConfPassword").keyup(function(e) {
				if (e.keyCode == 13) {
					submitForm();
				} else {
					removeAndClearPopUpClass('#resetConfPasswordError', DOWN);
					validateResetCPassword(false);
				}
			});
			$("#resetConfPassword").click(
					function() {
						validateResetCPassword(false);
						if ($("#resetConfPasswordError").html() != '') {
							onclickShowPopUp('#resetConfPasswordError',
									RESET_ERROR_BOXES, DOWN);
						} else {
							removeAndClearPopUpClass('#resetConfPasswordError',
									DOWN);
						}
					});
			$("#resetConfPassword").blur(function() {
				removePopUpClass('#resetConfPasswordError', DOWN);
				validateResetCPassword(false);
			});
			$('#submitResetForm').click(function() {
				var pass = validateResetPassword(true);
				var cPass = validateResetCPassword(true);
				if (pass && cPass) {
					$('#frmResetPass').submit();
				} else {
					showPopUp(RESET_ERROR_BOXES, DOWN);
				}
			});
		});

function validateResetPassword(isFormSubmit) {
	var password = $("#resetNewPassword").val();
	var res = isPassword(password);
	if (res != null) {
		$("#resetNewPassword");
		if (isFormSubmit) {
			$('#resetNewPasswordError').html(res);
		}
		return false;
	} else {
		$("#resetNewPassword").css('border', '');
		return true;
	}
}
function validateResetCPassword(isFormSubmit) {
	var password = $("#resetNewPassword").val();
	var cPassword = $("#resetConfPassword").val();
	if (password != cPassword) {
		$("#resetConfPassword");
		if (isFormSubmit) {
			$('#resetConfPasswordError').html(
					'Password and Confirm Password should match');
		}
		return false;
	} else {
		$("#resetConfPassword").css('border', '');
		return true;
	}
}

