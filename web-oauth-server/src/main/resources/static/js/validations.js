var ALPHABETIC_EX = new RegExp(/^[A-Z]+$/i);
var EMAIL_EX = new RegExp(
		/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
var PASSWORD_EX = new RegExp(
		/^((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})/);
var NUMERIC_EX = new RegExp(/^\d+$/);
function isName(name) {
	if (name.trim() == '')
		return "Please fill out this field";
	if (!name.match(ALPHABETIC_EX)) {
		return "Name should contain only alphabets.";
	} else {
		return null;
	}
}
function isEmail(email) {
	if (email.trim() == '')
		return "Please fill out this field";
	if (!email.match(EMAIL_EX))
		return "Please enter a valid email address";
	else
		return null;
}
function isMobile(mobile) {
	if (mobile.trim() == '')
		return "Please fill out this field";
	if (!mobile.match(NUMERIC_EX)) {
		return "Mobile Number should contains only numbers.";
	}
	if (mobile.length > 15 || mobile.length < 10)
		return "Mobile Number should be 10-15 digits.";
	else
		return null;
}
function isPassword(password) {
	if (password.trim() == '')
		return "Please fill out this field";
	if (!password.match(PASSWORD_EX)) {
		return getPasswordError(password);
	} else {
		return null;
	}
}
function getPasswordError(password) {
	var alphaLower = false, alphaUpper = false, numeric = false, specialSymbol = false;
	for (i = 0; i < password.length; i++) {
		var ch = password.charAt(i);
		if (!alphaLower) {
			if (ch >= 'a' && ch <= 'z') {
				alphaLower = true;
			}
		}
		if (!alphaUpper) {
			if (ch >= 'A' && ch <= 'Z') {
				alphaUpper = true;
			}
		}
		if (!numeric) {
			if (ch >= '0' && ch <= '9') {
				numeric = true;
			}
		}
		if (!specialSymbol) {
			if (ch == '@' || ch == '#' || ch == '$' || ch == '%' || ch == '*') {
				specialSymbol = true;
			}
		}
	}
	var msg = 'Password should contain at least ';
	if (!alphaLower) {
		msg += 'one lowercase letter,';
	}
	if (!alphaUpper) {
		msg += 'one upper letter,';
	}
	if (!numeric) {
		msg += 'one digit[0-9],';
	}
	if (!specialSymbol) {
		msg += 'one special symbol ( @,#,$,%,* ),';
	}
	if (password.length < 6 || password.length > 20) {
		if (msg == 'Password should contain at least ') {
			msg = 'Password must be of 6-20 chracters';
		} else {
			msg += 'must be of 6-20 chracters';
		}
	}
	if ((msg.length - 1) == msg.lastIndexOf(',')) {
		msg = msg.substring(0, msg.length - 1);
	}
	return msg;
}

