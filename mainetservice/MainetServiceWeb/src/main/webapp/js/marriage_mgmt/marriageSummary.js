var mrmURL = 'MarriageRegistration.html';
$(document).ready(
		function() {

			$("#searchMarriage").dataTable({
				"oLanguage" : {
					"sSearch" : ""
				},
				"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
				"iDisplayLength" : 5,
				"bInfo" : true,
				"lengthChange" : true
			});
			
			$("#marDate").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				maxDate : new Date()
			});

			$("#marDate").keyup(function(e) {
				if (e.keyCode != 8) {
					if ($(this).val().length == 2) {
						$(this).val($(this).val() + "/");
					} else if ($(this).val().length == 5) {
						$(this).val($(this).val() + "/");
					}
				}
			});
			
			$("#appDate").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				maxDate : new Date()
			});

			$("#appDate").keyup(function(e) {
				if (e.keyCode != 8) {
					if ($(this).val().length == 2) {
						$(this).val($(this).val() + "/");
					} else if ($(this).val().length == 5) {
						$(this).val($(this).val() + "/");
					}
				}
			});
			
			$('#searchMRM').click(function() {
				let errorList = [];
				let marDate = $('#marDate').val();
				let appDate = $('#appDate').val();
				let husbandId = $('#husbandId').val();
				let status = $('#status').val();
				if (status !=''|| husbandId != ''|| marDate != ''|| appDate != '') {
					var requestData = '&marDate='+ marDate + '&appDate='+ appDate
								+ '&husbandId='+ husbandId
								+ '&status=' + status;
					var table = $('#searchMarriage').DataTable();
					table.rows().remove().draw();
					$(".warning-div").hide();
					var ajaxResponse = __doAjaxRequest('MarriageRegistration.html?searchMarriageData','POST', requestData, false,'json');
					if (ajaxResponse.length == 0) {
						errorList.push(getLocalMessage("mrm.validation.grid.nodatafound"));
						displayErrorsOnPage(errorList);
						return false;
					}
					var result = [];
						$.each(ajaxResponse,function(index) {
							var obj = ajaxResponse[index];
								let marId = obj.marId;
								let applicationId = obj.applicationId;
								let applicantName = obj.applicantName;
								let husbandName = obj.husbandDTO.firstNameEng +" " + obj.husbandDTO.lastNameEng ;
								
								let marDate = new Date(obj.marDate);
								let month =  marDate.getMonth() + 1;
							    let marFormateDate = marDate.getDate() + "-" + month + "-" + marDate.getFullYear();
							    let appDate = new Date(obj.appDate);
							    let appMonth =appDate.getMonth() + 1;
							    let appFormatDate=  appDate.getDate() + "-" + appMonth + "-" + appDate.getFullYear();
							    let status = obj.status;
							    let serialNo = obj.serialNo;
							    
								result.push([ 
									/*'<div align="center">'+ (index + 1) + '</div>',*/
									'<div align="center">'+applicationId + '</div>',
									'<div align="center">'+applicantName + '</div>',
									'<div align="center">'+husbandName + '</div>',
									'<div align="center">'+marFormateDate + '</div>',
									'<div align="center">'+appFormatDate + '</div>',
									'<div align="center">'+ (status== "D" ? "DRAFT": status)  + '</div>',
									'<div class="text-center">'
									+(status == "D" ? '<button type="button"  class="btn btn-danger btn-sm margin-right-5 " onClick="draftMRM(\''
											+ obj.marId
											+ '\')" title="normal Draft"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button>': '')
									+ '<button type="button"  class="btn btn-blue-1 btn-sm margin-right-5 " onClick="viewMRM(\''
									+ marId
									+ '\')" title="View"><i class="fa fa-eye"></i></button>'
									+(serialNo!=""  && serialNo != "NA" ? '<button type="button"  class="btn btn-danger btn-sm btn-sm margin-right-5 " onClick="rePrintCertificate(\''
									+ marId
									+ '\')" title="normal Draft"><i class="fa fa-print" aria-hidden="true"></i></button>': '')
									
							     	+ '</div>' ]);
								});
						table.rows.add(result);
						table.draw();
					
					} else {
						errorList.push(getLocalMessage("mrm.validation.select.any.field"));
						displayErrorsOnPage(errorList);
					}
				});

			$("#AddMarriageRegisration").click(
					function() {
						var requestData = {
							"type" : "C"
						}
						var ajaxResponse = __doAjaxRequest(
								'MarriageRegistration.html?form', 'POST',
								requestData, false, 'html');
						$('.content-page').html(ajaxResponse);
						prepareDateTag();
			});

		});

function closeErrBox() {
	$('.warning-div').addClass('hide');
}

function resetMRM() {

	var saveMode = $('#saveMode').val();
	if (saveMode == 'C') {
		var searchURL = "AssetSearch.html";
		var response = __doAjaxRequest(searchURL + '?searchAsset', 'POST', {},
				false, 'html');
		var dialogId = $('#searchAssetPage').parent().attr('id');
		$('#' + dialogId).html(response);
	} else {
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', cdmURL);
		$("#postMethodForm").submit();
	}
}

function draftMRM(marId) {
	var requestData = 'marId=' + marId + '&type=D';
	var response = __doAjaxRequest(mrmURL + '?form', 'POST', requestData,
			false, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(response);
	prepareDateTag();
}

function viewMRM(marId) {
	var saveMode = $('#saveMode').val();
	var requestData = 'marId=' + marId + '&type=V';
	var response = __doAjaxRequest(mrmURL + '?form', 'POST', requestData,
			false, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(response);
	prepareDateTag();
}

function rePrintCertificate(marId) {
	var requestData = 'marId=' + marId+ '&printType=R';
	var response = __doAjaxRequest('MarriageCertificateGeneration.html?printCertificate', 'POST', requestData,
			false, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(response);
	prepareDateTag();
}


function displayErrorsOnPage(errorList) {
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$(".warning-div").html(errMsg);
	$(".warning-div").removeClass('hide')
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	$(".warning-div").show();
	errorList = [];
	return false;
}

function triggerDatatable() {
	$("#dtCdmForm").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 10, 20, 30, -1 ], [ 10, 20, 30, "All" ] ],
		"iDisplayLength" : 10,
		"bInfo" : true,
		"lengthChange" : true,
		"scrollCollapse" : true,
		"bSort" : false
	}).fnPageChange('last');
}
