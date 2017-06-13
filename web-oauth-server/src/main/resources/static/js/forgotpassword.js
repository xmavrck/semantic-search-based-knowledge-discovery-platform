var FORGOT_PASS_ERROR_BOXES = [ "#forgotEmailIdError" ];
var disableKeyUp = false;
$(document).ready(
		function() {
			$("#forgotEmailId").keyup(function() {
				if (!disableKeyUp) {
					removeAndClearPopUpClass('#forgotEmailIdError', RIGHT);
					validateForgotEmail(false);
				}else{
					disableKeyUp=false;
				}
			});
			$("#forgotEmailId").click(
					function() {
						validateForgotEmail(false);
						if ($("#forgotEmailIdError").html() != '') {
							onclickShowPopUp('#forgotEmailIdError',
									FORGOT_PASS_ERROR_BOXES, RIGHT);
						} else {
							removeAndClearPopUpClass('#forgotEmailIdError',
									RIGHT);
						}
					});
			$("#forgotEmailId").blur(function() {
				removePopUpClass('#forgotEmailIdError', RIGHT);
				validateForgotEmail(false);
			});
			$('#submitForgotPass').click(function() {
				var email = validateForgotEmail(true);
				if (email) {
					$('#frmForgot').submit();
				} else {
					showPopUp(FORGOT_PASS_ERROR_BOXES, RIGHT);
				}
			});
		});
function validateForgot() {
	var email = validateForgotEmail(true);
	if (email) {
		return true;
	} else {
		disableKeyUp=true;
		showPopUp(FORGOT_PASS_ERROR_BOXES, RIGHT);
		return false;
	}
}
function validateForgotEmail(isFormSubmit) {
	var email = $("#forgotEmailId").val();
	var res = isEmail(email);
	if (res != null) {
		$("#forgotEmailId");
		if (isFormSubmit) {
			$('#forgotEmailIdError').html(res);
		}
		return false;
	} else {
		$("#forgotEmailId").css('border', '');
		$('#forgotEmailIdError').html('');
		return true;
	}
}

