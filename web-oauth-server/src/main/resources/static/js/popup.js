var BORDER_CSS = '1px solid #f64141';
var RIGHT = 'right';
var DOWN = 'down';

function removePopUpClass(popUpId, direction) {
	if (direction == DOWN) {
		$(popUpId).removeClass('error_box');
	} else if (direction == RIGHT) {
		$(popUpId).removeClass('error_boxRight');
	}
	$(popUpId).hide();
}

function removeAndClearPopUpClass(popUpId, direction) {
	if (direction == DOWN) {
		$(popUpId).removeClass('error_box');
	} else if (direction == RIGHT) {
		$(popUpId).removeClass('error_boxRight');
	}
	$(popUpId).hide();
	$(popUpId).html('');
}

function onclickShowPopUp(popUpId, ERROR_BOXES, direction) {
	hideAll(ERROR_BOXES);
	removeAll(ERROR_BOXES);
	$(popUpId).show();
	if (direction == DOWN) {
		$(popUpId).addClass('error_box');
	} else if (direction == RIGHT) {
		$(popUpId).addClass('error_boxRight');
	}

}
function showPopUp(ERROR_BOXES, direction) {
	hideAll(ERROR_BOXES);
	removeAll(ERROR_BOXES, direction);
	for (i = 0; i < ERROR_BOXES.length; i++) {
		if ($(ERROR_BOXES[i]).html() != '') {
			var data = $(ERROR_BOXES[i]).html();
			$(ERROR_BOXES[i]).html('');
			if (direction == DOWN) {
				$(ERROR_BOXES[i]).addClass('error_box');
			} else if (direction == RIGHT) {
				$(ERROR_BOXES[i]).addClass('error_boxRight');
			}
			$(ERROR_BOXES[i]).show();
			$(ERROR_BOXES[i]).html(data);
			$(ERROR_BOXES[i]).focus();
			break;
		}
	}
}
function removeAll(ERROR_BOXES, direction) {
	for (i = 0; i < ERROR_BOXES.length; i++) {
		if (direction == DOWN) {
			$(ERROR_BOXES[i]).removeClass('error_box');
		} else if (direction == RIGHT) {
			$(ERROR_BOXES[i]).removeClass('error_boxRight');
		}
	}
}
function hideAll(ERROR_BOXES) {
	for (i = 0; i < ERROR_BOXES.length; i++) {
		$(ERROR_BOXES[i]).hide();
	}
}

