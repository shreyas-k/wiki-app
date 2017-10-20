function getRandomWikiUrl() {
	$.getJSON("https://en.wikipedia.org/w/api.php?action=query&generator=random&grnnamespace=0&prop=extracts&explaintext&exintro=&format=json&callback=?", function (data) {
		$.each(data.query.pages, function (k, v) {
			$.getJSON('https://en.wikipedia.org/w/api.php?action=query&prop=info&pageids=' + v.pageid + '&inprop=url&format=json&callback=?', function (url) {
				$.each(url.query.pages, function (key, page) {
					$('#input_url').val(page.fullurl);
				});
			});
		});
	});
}

function startLoader() {
	if(document.getElementById("err_btn")) {
		document.getElementById("err_btn").click();
	}
	if(document.getElementById("steps_wiz")) {
		$("#steps_wiz").hide();
	}
	$('#load_spinner').show();
}

function stopLoader() {
	$('#load_spinner').hide();
}