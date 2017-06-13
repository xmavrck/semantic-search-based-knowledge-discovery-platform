var SIGNUP_ERROR_BOXES = [ "#signupFirstNameError", "#signupLastNameError",
		"#signupEmailIdError", "#signupMobileNumberError", "#signupAcceptError" ];

var BASE_URL = "/oauth";
$(document).ready(
		function() {
			$("#signupFirstName").keyup(function() {
				removeAndClearPopUpClass('#signupFirstNameError', DOWN);
				validateFName(false);
			});
			$("#signupFirstName").blur(function() {
				removePopUpClass('#signupFirstNameError', DOWN);
				validateFName(false);
			});
			$("#signupFirstName").change(function() {
				console.log("value change");
			});
			$("#signupLastName").keyup(function() {
				removeAndClearPopUpClass('#signupLastNameError', DOWN);
				validateLName(false);
			});

			$("#signupLastName").blur(function() {
				removePopUpClass('#signupLastNameError', DOWN);
				validateLName(false);
			});

			$("#signupEmailId").keyup(function() {
				removeAndClearPopUpClass('#signupEmailIdError', DOWN);
				validateEmail(false);
			});

			$("#signupEmailId").blur(function() {
				removePopUpClass('#signupEmailIdError', DOWN);
				validateEmail(false);
			});

			$("#signupMobileNumber").keyup(function() {
				removeAndClearPopUpClass('#signupMobileNumberError', DOWN);
				validateMobile(false);
			});
			$("#signupMobileNumber").blur(function() {
				removePopUpClass('#signupMobileNumberError', DOWN);
				validateMobile(false);
			});
			$("#chkAccept").click(function() {
				removePopUpClass('#signupAcceptError', DOWN);
			});
			$('#signUpSubmitEmployee').click(
					function() {
						console.log("1")
						var fName = validateFName(true);
						var lName = validateLName(true);
						var email = validateEmail(true);
						var mobile = validateMobile(true);
						if (fName && lName && email && mobile) {
							console.log("3")
							if (validateCheckBox()) {
								console.log("2")
								$('#frmSignUpEmployee').submit();
							} else {
								console.log("4")
								onclickShowPopUp('#signupAcceptError',
										SIGNUP_ERROR_BOXES, DOWN);
							}
						} else {
							console.log("5")
							showPopUp(SIGNUP_ERROR_BOXES, DOWN);
						}
					});
		});

function validateCheckBox() {
	if ($('#chkAccept').is(':checked')) {
		return true;
	} else {
		$('#signupAcceptError').html('Please accept the terms and conditions');
		return false;
	}
}
function validateFName(isFormSubmit) {
	var fName = $("#signupFirstName").val();
	var res = isName(fName);
	if (res != null) {
		$("#signupFirstName");
		if (isFormSubmit) {
			$('#signupFirstNameError').html(res);
		}
		return false;
	} else {
		$("#signupFirstName").css('border', '');
		return true;
	}
}
function validateLName(isFormSubmit) {
	var lName = $("#signupLastName").val();
	var res = isName(lName);
	if (res != null) {
		$("#signupLastName");
		if (isFormSubmit) {
			$('#signupLastNameError').html(res);
		}
		return false;
	} else {
		$("#signupLastName").css('border', '');
		return true;
	}
}
function validateEmail(isFormSubmit) {

	var email = $("#signupEmailId").val();
	var res = isEmail(email);
	if (res != null) {
		$("#signupEmailId");
		if (isFormSubmit) {
			$('#signupEmailIdError').html(res);
		}
		return false;
	} else {
		if (!isFormSubmit) {
			$("#signupEmailId").css('border', '');
			$.get(BASE_URL + "/signup/check-email?emailId=" + email, function(
					data, status) {
				if (data.error != null) {
					$('#signupEmailIdError').html(data.error);
					$("#signupEmailId");
					return false;
				}
				return true;
			});
		} else {
			if ($('#signupEmailIdError').html() != '') {
				return false;
			} else {
				return true;
			}
		}
	}
}

function validateMobile(isFormSubmit) {

	var mobile = $("#signupMobileNumber").val();
	var res = isMobile(mobile);
	if (res != null) {
		$("#signupMobileNumber");
		if (isFormSubmit) {
			$('#signupMobileNumberError').html(res);
		}
		return false;
	} else {
		if (!isFormSubmit) {
			$("#signupMobileNumber").css('border', '');
			$.get(BASE_URL + "/signup/check-mobile?mobileNumber=" + mobile,
					function(data, status) {
						if (data.error != null) {
							$("#signupMobileNumber");
							$('#signupMobileNumberError').html(data.error);
							return false;
						}
						return true;
					});
		} else {
			if ($('#signupMobileNumberError').html() != '') {
				return false;
			} else {
				return true;
			}
		}
	}
}