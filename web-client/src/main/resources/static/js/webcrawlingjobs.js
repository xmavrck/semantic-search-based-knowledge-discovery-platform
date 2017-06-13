$(document).ready(function() {
	loadAllJobs();
});
function loadAllJobs() {
	$.ajax({
		url : BASE_URL + "url-scrap-job",
		type : 'GET',
		success : function(data) {
			console.log(data);
			renderJobs(data);
		},
		error : function(jqXHR, textStatus, error) {
			console.log(error);
		}
	});
}

function renderJobs(data) {
	var tableHeader = '<tr><th>Sr.No.</th><th>University Name</th><th>URL</th><th>Files Crawled</th><th>File Retrieved</th><th>Links</th></tr>';
	var tableContent = '';
	if (data != null && data.length > 0) {
		for (var index = 0; index < data.length; index++) {
			var job = data[index];
			var link = "";
			if (job.taskStatus == 'completed') {
				link = '<a href="javascript:void(0)" onclick="deleteJob(\''
						+ job.id + '\')">Delete</a>';
			} else {
				link = "Job State:Running";
			}
			var linkToRetrieveUrls=job.noOfUrlsRetrieved;
			if(parseInt(job.noOfUrlsRetrieved)>0){
				linkToRetrieveUrls='<a href="scrappy-jobs/'
				+ job.id + '">'+job.noOfUrlsRetrieved+'</a>';
			}
			tableContent = tableContent + '<tr><td>' + (index + 1)
					+ '</td><td>' + job.university + '</td><td>' + job.rootUrl
					+ '</td><td>' + job.noOfUrlsProcessed + '</td><td>'
					+ linkToRetrieveUrls + '</td><td>' + link + '</td></tr>';
		}
	}
	console.log(tableHeader + "  " + tableContent)
	document.getElementById("tblScrappyJobs").innerHTML = tableHeader
			+ tableContent;
}

function deleteJob(jobId) {
	console.log("deleteJob " + jobId)
	$.ajax({
		url : BASE_URL + "url-scrap-job/" + jobId,
		type : 'DELETE',
		success : function(data) {
			console.log(data);
			window.location.reload();
		},
		error : function(jqXHR, textStatus, error) {
			console.log(error);
		}
	});
}