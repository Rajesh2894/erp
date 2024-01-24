$(document).ready(function() {
	
	$('.subpage').each(function() {
		// this wrapped in jQuery will give us the current .letter-q div
		$(this).append('<span class="Pagecount"></span>');
	});

	var numItems = $('.page').length;
	$("span.Pagecount").text("" + numItems + "");

});
/* ********Print as PDF ******** */
function ExportPdf() {
	kendo.drawing.drawDOM("#myCanvas", {
		forcePageBreak : ".page-break",
		paperSize : "A4",
		// margin: { top: "0cm", bottom: "0cm" },
		scale : 0.7,
		height : 700,
		template : $("#page-template").html(),
		keepTogether : ".prevent-split"
	}).then(function(group) {
		kendo.drawing.pdf.saveAs(group, "FORM F.pdf")
	});
}

/* ********Print as Word Doc ******** */
function exportHTML() {
	var header = "<html xmlns:o='urn:schemas-microsoft-com:office:office' "
			+ "xmlns:w='urn:schemas-microsoft-com:office:word' "
			+ "xmlns='http://www.w3.org/TR/REC-html40'>"
			+ "<head><meta charset='utf-8'><title>Export HTML to Word Document with JavaScript</title></head><body>";
	var footer = "</body></html>";
	var sourceHTML = header + document.getElementById("myCanvas").innerHTML
			+ footer;

	var source = 'data:application/vnd.ms-word;charset=utf-8,'
			+ encodeURIComponent(sourceHTML);
	var fileDownload = document.createElement("a");
	document.body.appendChild(fileDownload);
	fileDownload.href = source;
	fileDownload.download = 'FORM F.doc';
	fileDownload.click();
	document.body.removeChild(fileDownload);
}

/** ******* Scroll To Top ******** */
// When the user scrolls down 20px from the top of the document, show the
// button
window.onscroll = function() {
	scrollFunction()
};

function scrollFunction() {
	if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
		document.getElementById("myBtn").style.display = "block";
	} else {
		document.getElementById("myBtn").style.display = "none";
	}
}
 

function backTenderPage() {
	
	var divName = '.content-page';
	var url = "TenderInitiation.html?showCurrentTenderViewForm";
	var requestData = {};
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	if ($("#mode").val() == 'V') {
		$("#TenderInitiation :input").prop("disabled", true);
		$("select").prop("disabled", true).trigger("chosen:updated");
		$(".workClass").prop("disabled", false);
		$('.viewEstimate').prop("disabled", false);
		$('.viewNIT').prop("disabled", false);
		$(".viewFormA").prop("disabled", false);
		$(".viewPQR").prop("disabled", false);
		$(".viewFormF").prop("disabled", false);
		$("#button-Cancel").prop("disabled", false);
	}
}