var areUniversitiesLoaded = false, areResearchAreasLoaded = false;
var searchByProjectDescription = false;
$(document).ready(function() {
	searchButton(false);
	initialize();
	onClickEvents();
});

function initialize() {
	$('#advancedSearchBlock').hide();
	$('#tabsResults').hide();
	$('#mydiv').hide();
	getAllUniversities();
	getAllResearchAreas();
	$("#researchAreas").select2({
		placeholder : "Loading research areas",
		allowClear : true,
		// match strings that begins with (instead of contains):
		matcher : function(term, text) {
			return text.toUpperCase().indexOf(term.toUpperCase()) == 0;
		}
	});
	$("#universities").select2({
		placeholder : "Loading universities",
		allowClear : true,

		// match strings that begins with (instead of contains):
		matcher : function(term, text) {
			return text.toUpperCase().indexOf(term.toUpperCase()) == 0;
		}
	});
	$('#results').hide();
	$('#universityNames').hide();
	$('#sectionLinks').hide();
}
function onClickEvents() {
	$('#btnAdvancedSearchQuery').click(
			function() {
				$('#tabsResults').hide();
				$('#results').hide();
				$('#universityNames').hide();
				$('#sectionLinks').hide();
				if (searchByProjectDescription == false) {
					if ($('#inputAdvancedSearchQuery').val() == ''
							&& ($('#researchAreas').val() == null || $(
									'#researchAreas').val().length == 0)
							&& ($('#universities').val() == null || $(
									'#universities').val() == 0)) {
						alert("Choose at least one option to search");
					} else {
						$("#links").empty();
						$('#mydiv').show();
						search();
					}
				}else{
					if ($('#projectDescription').val().trim() == ''){
						alert("Please enter your project description");
					}else{
						$("#links").empty();
						$('#mydiv').show();
						search();
					}
				}
			});

	$('#rbNormalSearch').click(function() {
		searchByProjectDescription = false;
		$('#normalSearchBlock').show();
		$('#advancedSearchBlock').hide();
	});

	$('#rbAdvancedSearch').click(function() {
		searchByProjectDescription = true;
		$('#normalSearchBlock').hide();
		$('#advancedSearchBlock').show();
	});

}
function getAllUniversities() {
	$.ajax({
		url : BASE_URL + "sparql/universities",
		type : 'GET',
		success : function(data) {

			$("#universities").select2({
				placeholder : "Select universities",
				allowClear : true,

				// match strings that begins with (instead of contains):
				matcher : function(term, text) {
					return text.toUpperCase().indexOf(term.toUpperCase()) == 0;
				}
			});

			$('#universities').empty()

			for (var index = 0; index < data.length; index++) {
				$('#universities').append(
						'<option value="' + data[index] + '">' + data[index]
								+ '</option>');
			}
			areUniversitiesLoaded = true;
			searchButton(true)
		},
		error : function(jqXHR, textStatus, error) {
			console.log(error);
		}
	});
}
function getAllResearchAreas() {
	$
			.ajax({
				url : BASE_URL + "sparql/researchAreas",
				type : 'GET',
				success : function(data) {

					$("#researchAreas").select2(
							{
								placeholder : "Select research areas",
								allowClear : true,

								// match strings that begins with (instead of
								// contains):
								matcher : function(term, text) {
									return text.toUpperCase().indexOf(
											term.toUpperCase()) == 0;
								}
							});
					$('#researchAreas').empty()
					for (var index = 0; index < data.length; index++) {

						var rs = data[index].split("#");

						$('#researchAreas').append(
								'<option value="' + rs[0] + '">' + rs[1]
										+ '</option>');
					}
					areResearchAreasLoaded = true;
					searchButton(true)
				},
				error : function(jqXHR, textStatus, error) {
					console.log(error);
				}
			});
}
function searchButton(isEnabled) {
	if (isEnabled) {
		if (areResearchAreasLoaded == true && areUniversitiesLoaded == true) {
			$("#btnAdvancedSearchQuery").prop('value', 'Search');
			$('#btnAdvancedSearchQuery').prop('disabled', false);
		}
	} else {
		$("#btnAdvancedSearchQuery").prop('value',
				'Loading Research Areas and Universities...');
		$('#btnAdvancedSearchQuery').prop('disabled', true);
	}
}
function search() {
	if (searchByProjectDescription == false) {
		normalSearch();
	} else {
		projectDescriptionSearch();
	}
}
function projectDescriptionSearch() {
	var searchQuery = {
		"projectDescription" : $('#projectDescription').val()
	};
	$.ajax({
		url : BASE_URL + "sparql/search",
		type : 'POST',
		contentType : 'application/json',
		data : JSON.stringify(searchQuery),
		success : function(data) {
			$('#mydiv').hide();
			console.log(data);
			try{
				renderLinks(data);	
			}catch(e){
				$('#mydiv').hide();
				$('#tabsResults').show();
				// alert("Some internal error occured");
				$("#links")
						.append("<tr><th width='100%'>No result found</th></tr>");
				$('#divSearch').css("margin-top", "150px")
				$('#divSearch').css("margin-bottom", "150px")
				$('#universityNames').hide();
				$('#results').html("Sorry, No results(s) found");
			}
			
		},
		error : function(jqXHR, textStatus, error) {
			$('#mydiv').hide();
			$('#tabsResults').show();
			// alert("Some internal error occured");
			$("#links")
					.append("<tr><th width='100%'>No result found</th></tr>");
			$('#divSearch').css("margin-top", "150px")
			$('#divSearch').css("margin-bottom", "150px")
			$('#universityNames').hide();
			$('#results').html("Sorry, No results(s) found");
			console.log(error);
		}
	});
}
function normalSearch() {
	var keywordBased = "";
	if (document.getElementById("isKeywordbased").checked) {
		keywordBased = "true";
	} else {
		keywordBased = "false";
	}
	var searchQuery = {
		"researchAreas" : $('#researchAreas').val(),
		"universities" : $('#universities').val(),
		"text" : $('#inputAdvancedSearchQuery').val(),
		"keywordBased" : keywordBased
	};
	$.ajax({
		url : BASE_URL + "sparql/search",
		type : 'POST',
		contentType : 'application/json',
		data : JSON.stringify(searchQuery),
		success : function(data) {
			$('#mydiv').hide();
			console.log(data);
			renderLinks(data);
		},
		error : function(jqXHR, textStatus, error) {
			$('#mydiv').hide();
			$('#tabsResults').show();
			// alert("Some internal error occured");
			$("#links")
					.append("<tr><th width='100%'>No result found</th></tr>");
			$('#divSearch').css("margin-top", "150px")
			$('#divSearch').css("margin-bottom", "150px")
			$('#universityNames').hide();
			$('#results').html("Sorry, No results(s) found");
			console.log(error);
		}
	});
}
function renderLinks(data) {
	$("#links").empty();
	$('#universityNames').empty();
	$('#tabsResults').show();
	if (data != null) {
		$('#universityNames').show();
		firstTable(data);
		secondTable(data);
		$('#divSearch').css("margin-top", "0px")
		$('#divSearch').css("margin-bottom", "0px")

		$('#results').html(data.length + " results(s) found");
		$('#sectionLinks').show();
	} else {
		$("#links").append("<tr><th width='100%'>No result found</th></tr>");
		$('#divSearch').css("margin-top", "150px")
		$('#divSearch').css("margin-bottom", "150px")
		$('#universityNames').hide();
		$('#results').html("Sorry, No results(s) found");
	}

}
function secondTable(data) {
	$("#universityNames")
			.append(
					"<tr><th>Research Area</th><th>University Name</th><th>No of People in Same Research Area</th><th>No of Publications in same Department</th></tr>");
	for (var index = 0; index < data.aggregates.length; index++) {
		var doc = data.aggregates[index];
		$("#universityNames").append(
				'<tr><td>' + doc.researchArea + '</td><td>' + doc.university
						+ '</td><td>' + doc.peopleCount + '</td><td>'
						+ doc.publicationCount + '</td></tr>');
	}
}
function firstTable(data) {
	$("#links").append(
			"<tr><th></th><th width='100%' colspan='4'>" + data.results.length
					+ " doc(s) retrieved</th></tr>");
	$("#links").append("<tr><th>Sr. No.</th><th colspan='4'>Results</th></tr>");
	for (var index = 0; index < data.results.length; index++) {
		var doc = data.results[index];
		var researchArea = doc.data.researchArea.split(" ");
		var keywords = doc.data.Keywords;
		var email = doc.data.EmailAddress;
		var noOfPublications = doc.data.ResearchPublications;

		if (noOfPublications == 'undefined' || noOfPublications == ""
				|| noOfPublications == undefined || noOfPublications == 'na'
				|| noOfPublications == 'NA') {
			noOfPublications = 0;
		} else {
			noOfPublications = noOfPublications.split("<br/>").length;
		}

		if (email == 'undefined' || email == "" || email == undefined
				|| email == 'na' || email == 'NA') {
			email = "NA";
		}

		if (keywords == 'undefined' || keywords == undefined) {
			keywords = "NA";
		}

		var name = doc.data.Name;
		if (name == 'undefined' || name == undefined) {
			name = "NA";
		}

		$('#links')
				.append(
						'<tr class="blue"><th>'
								+ (index + 1)
								+ '</th><th>Email</th><th>Name</th><th>Url</th><th>University Name</th></tr>');
		$('#links').append(
				'<tr><td></td><td>' + email + '</td><td>' + name
						+ '</td><td><a href="' + doc.url + '" target="_blank">'
						+ doc.url + '</a></td><td>' + doc.data.UniversityName
						+ '</td></tr>');

		$('#links')
				.append(
						'<tr><td></td><th colspan="4" style="background-color:#e0e0e0">Research Area</th></tr>');
		$('#links').append(
				'<tr><td></td><td colspan="4">' + doc.data.researchArea
						+ '</td></tr>');

		$('#links')
				.append(
						'<tr><td></td><th colspan="4" style="background-color:#e0e0e0">No Of Publications</th></tr>');
		$('#links').append(
				'<tr><td></td><td colspan="4">' + noOfPublications
						+ '</td></tr>');

		var otherRelatedTerms = '';
		for (var i = 0; i < doc.otherRelatedAreas.length; i++) {
			otherRelatedTerms = otherRelatedTerms + " , "
					+ doc.otherRelatedAreas[i];
		}

		$('#links')
				.append(
						'<tr><td></td><th colspan="4" style="background-color:#e0e0e0">Related Interest Area:</th></tr>');
		$('#links').append(
				'<tr><td></td><td colspan="4">' + otherRelatedTerms
						+ '</td></tr>');

	}
}
