var intervalId;
$(document).ready(function() {
	var accountPath = window.location.pathname;
	console.log(accountPath);
	var jobId = accountPath.substring(accountPath.lastIndexOf('/') + 1);
	findJobById(jobId);
	findByTaskId(jobId);
//	intervalId = setInterval(function() {
//		findJobById(jobId);
//		findByTaskId(jobId);
//	}, 20000);
});
function findJobById(jobId) {
	$.ajax({
		url : BASE_URL + "url-scrap-job/" + jobId,
		type : 'GET',
		success : function(data) {
			console.log(data);
			renderJobById(data);
		},
		error : function(jqXHR, textStatus, error) {
			console.log(error);
		}
	});
}
function findByTaskId(taskId) {
	$.ajax({
		url : BASE_URL + "url-history/findByTaskId?taskId=" + taskId,
		type : 'GET',
		success : function(data) {
			console.log(data);
			renderUrls(data);
		},
		error : function(jqXHR, textStatus, error) {
			console.log(error);
		}
	});
}
function renderJobById(data) {
	if (data != null) {
		$('#spanRootUrl').html(data.rootUrl);
	}
}
function renderUrls(data) {
	var tableContent = "";
	document.getElementById("tblScrapyJobUrl").innerHTML = ''
	for (var index = 0;index <data.length; index++) {
		var urlHistory = data[index];
		var date = new Date(urlHistory.dateAdded).toString();
		var tr = document.createElement('tr');
		var td1 = document.createElement('td');
		
		var td3 = document.createElement('td');

		var text1 = document.createTextNode((index + 1));
		
		var text3 = document.createTextNode(urlHistory.url);

		td1.appendChild(text1);
		
		td3.appendChild(text3);
		
		tr.appendChild(td1);
		tr.appendChild(td3);
		
		document.getElementById("tblScrapyJobUrl").appendChild(tr);
	}
}