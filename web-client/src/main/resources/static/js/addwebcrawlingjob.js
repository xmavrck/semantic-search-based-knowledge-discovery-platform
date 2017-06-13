$(document).ready(function() {
	$('#submitWebCrawlingJobForm').click(function() {
		createJob();
	})
});
function createJob() {
	if ($('#rootUrl').val() == '' || !validateUrl($('#rootUrl').val())) {
		$('#error').html('Please fill a valid url');
		return;
	}
	if ($('#rootUrl').val() == '' || !validateUrl($('#rootUrl').val())) {
		$('#error').html('Please fill a valid url');
		return;
	}
	var jobData = {
		"rootUrl" : $('#rootUrl').val(),
		"university":$('#university').val()
	};
	$.ajax({
		url : BASE_URL + "url-scrap-job",
		type : 'POST',
		contentType : 'application/json',
		data : JSON.stringify(jobData),
		dataType : 'json',
		success : function(data) {
			console.log(data);
			window.location = 'scrappy-jobs';
		},
		error : function(jqXHR, textStatus, error) {
			console.log(error);
		}
	});
}
function validateUrl(url) {
	var re = /^(http[s]?:\/\/){0,1}(www\.){0,1}[a-zA-Z0-9\.\-]+\.[a-zA-Z]{2,5}[\.]{0,1}/;
	if (!re.test(url)) {
		return false;
	}
	return true;
}