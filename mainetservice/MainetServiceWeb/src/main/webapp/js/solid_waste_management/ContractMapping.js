var dcount=0;
$(document).ready(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			maxDate : '-0d',
			changeYear : true,
		});
	});
/** ******************Add Function*********** */


function addContractMappingForm(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	reOrderUnitTabIdSequence('.firstUnitRow');
}

/** ******************ViewFunction*********** */
function getViewContractMappingForm(formUrl, actionParam, contId) {
	
	
	if (actionParam == 'V') {
		var type = 'V';
		showContractForm(formUrl, contId, type);
		$("#ContractAgreement :input").prop("disabled", true);
		/*$('.addCF3').attr('disabled', true);
		$('.addCF4').attr('disabled', true);
		$('.addCF5').attr('disabled', true);
		$('.addCF2').attr('disabled', true);
		$('.remCF2').attr('disabled', true);
		$('.remCF3').attr('disabled', true);
		$('.remCF4').attr('disabled', true);
		$('.remCF5').attr('disabled', true);
		$('.uploadbtn').attr('disabled', true);
		$(".btn-danger").removeProp("disabled");*/
		$(".backButton").removeProp("disabled");
		
	}
	if(actionParam == 'A'){
		var type = 'V';
		var divName = '.content-page';
		var url = "ContractMapping.html?contractMappingForm";
		var requestData = __serializeForm("#ContractMappingForm");
		var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
				'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
		showContractForm(formUrl, contId, type);
		
	}
}
function showContractForm(formUrl, contId, type) {
	
	var showForm = "SWM";
	var requestData = 'contId=' + contId + '&type=' + type + '&showForm='+ showForm;
	var ajaxResponse = doAjaxLoading(formUrl + '?form', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$('.content').html(ajaxResponse);
}


function getViewNotMapped(formUrl, actionParam, contId) {
	
var requestData = 'contId=' + contId;
var divName = '.content-page';
var ajaxResponse = doAjaxLoading(formUrl + '?'+ actionParam, requestData, 'html',divName);
reOrderUnitTabIdSequence('.firstUnitRow');
$(divName).removeClass('ajaxloader');
$(divName).html(ajaxResponse);
prepareTags();
}



/** ******************DeleteFunction*********** */
function deleteContractMapping(formUrl, actionParam, popId) {
	
	if (actionParam == "deletePopulationMaster") {
		showConfirmBoxForDelete(popId, actionParam);
	}
}


function showConfirmBoxForDelete(popId, actionParam) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger padding-5\">'
			+ getLocalMessage('Do you want to delete?') + '</h4>';
	message += '<div class=\'text-center padding-bottom-18\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + popId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}


function proceedForDelete(popId) {
	$.fancybox.close();
	var requestData = 'popId=' + popId;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('PopulationMaster.html?'+ 'deletePopulationMaster', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}



/** **************Search************************ */
function SearchContractNo(contid) {
	
	var data = {
		"contid" : contid
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('ContractMapping.html?searchContractMapping', data, 'html', divName);
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	reOrderUnitTabIdSequence('.firstUnitRow');
}

function search() {
	
	var contractNo = $("#contractNo").val();
	var contDate = $("#contDate").val();
	var data = {
		"contractNo" : contractNo,
		"contDate" : contDate
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('ContractMapping.html?filter', data,'html', divName);
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

/** ***************Save***************************** */
function saveContractMapping(element) {
	
	var errorList = [];
	errorList = validateUnitDetailTable(errorList);
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}
	else{
	var ContractNo = $("#ContractNo").val();
	if (ContractNo == "" || ContractNo == undefined || ContractNo == '0') {
		errorList.push(getLocalMessage('vehicle.master.validation.contractNo'));
		displayErrorsPage(errorList);
	}else {
		return saveOrUpdateForm(element,getLocalMessage('swm.contractMapping.add.success'),'ContractMapping.html', 'saveform');
		reOrderUnitTabIdSequence('.firstUnitRow');
      }
	}	
}
function displayErrorsPage(errorList) {
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
}

/** ************************** */

function backcontractMapping() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'ContractMapping.html');
	$("#postMethodForm").submit();
}

function resetContractMapping() {
	
	$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();

}

/** ******************DataTable Function*********** */
$(document).ready(function() {
					var table = $('.table-striped1').DataTable(
							{
								"oLanguage" : {
									"sSearch" : ""
								},
								"aLengthMenu" : [ [ 5, 10, 15, -1 ],
										[ 5, 10, 15, "All" ] ],
								"iDisplayLength" : 5,
								"bInfo" : true,
								"lengthChange" : true,
								"bPaginate" : true,
								"bFilter" : true
							});
					     $('input#popEst').keypress(
									function(e) {
										var errorList = [];
										if (this.selectionStart == 0
												&& (e.which == 48 || e.which == 46)) {
											errorList.push(getLocalMessage('swm.population.enter.greater.zero'));
											return false;
										}
									});

				         });

function validateContractMapping(errorList) {
	
	var popYear = $("#popYear").val();
	if (popYear == "0" || popYear == null) {
		errorList.push(getLocalMessage('swm.census.year.select'));
	}
	var codDwzid1 = $("#codDwzid1").val();
	if (codDwzid1 == "0" || codDwzid1 == null) {
		errorList.push(getLocalMessage('swm.select.ward'));
	}
	var codDwzid2 = $("#codDwzid2").val();
	if (codDwzid2 == "0" || codDwzid2 == null) {
		errorList.push(getLocalMessage('swm.select.zone'));
	}
	return errorList;
}
/** ***************************************************** */

$(function() {
	/* To add new Row into table */
	$("#unitDetailTable").on('click', '.addCF', function() {
		var errorList = [];
		errorList = validateUnitDetailTable(errorList);
		if (errorList.length == 0) {
			var content = $("#unitDetailTable").find('tr:eq(1)').clone();
			$("#unitDetailTable").append(content);
			content.find("input:text").val('');
			content.find("select").val('0');
			content.find("input:hidden:eq(0)").val('0');
			$('.error-div').hide();
			reOrderUnitTabIdSequence('.firstUnitRow'); // reorder id and Path
			// Addition of new Row
		} else {
			displayErrorsPage(errorList);
		}
	});
});
function reOrderUnitTabIdSequence(firstRow) {
	if(dcount == 0)
	dcount = $("select[id^=" + "codWard" + "]").length;
	$(firstRow).each(
			function(i) {
				
				var utp = i;
				if (i > 0) {
					utp = i * 6;
				}
				// IDs
				$(this).find("select:eq(0)").attr("id", "codWard" + utp);
				$(this).find("select:eq(1)").attr("id", "codWard" + (utp + 1));
				$(this).find("select:eq(2)").attr("id", "codWard" + (utp + 2));
				$(this).find("select:eq(3)").attr("id", "codWard" + (utp + 3));
				$(this).find("select:eq(4)").attr("id", "codWard" + (utp + 4));
				
				$(this).find("select:eq("+dcount+")").attr("id", "mapTaskId" + i);
				$(this).find("select:eq("+(dcount+1)+")").attr("id", "wasteType" + i);
				$(this).find("input:text:eq(0)").attr("id", "garbageId" + i);
				$(this).find("select:eq("+(dcount+2)+")").attr("id", "garbageUnit" + i)
				$(this).find("select:eq("+(dcount+3)+")").attr("id", "beatId" + i)
				$(this).find("input:text:eq(1)").attr("id", "empCount" + i);
				$("#empCount"+ i).blur(function () { 
				    this.value = this.value.replace(/[^0-9]/g,'');
				  
				});
				$(this).find("input:text:eq(2)").attr("id", "vehicleCount" + i);
				$("#vehicleCount"+ i).blur(function () { 
				    this.value = this.value.replace(/[^0-9]/g,'');
				  
				});
				// names
				$(this).find("select:eq(0)").attr("name","VendorContractMappingList[" + i + "].codWard1");
				$(this).find("select:eq(1)").attr("name","VendorContractMappingList[" + i + "].codWard2");
				$(this).find("select:eq(2)").attr("name","VendorContractMappingList[" + i + "].codWard3");
				$(this).find("select:eq(3)").attr("name","VendorContractMappingList[" + i + "].codWard4");
				$(this).find("select:eq(4)").attr("name","VendorContractMappingList[" + i + "].codWard5");
				$(this).find("select:eq("+dcount+")").attr("name","VendorContractMappingList[" + i + "].mapTaskId");
				$(this).find("select:eq("+(dcount+1)+")").attr("name","VendorContractMappingList[" + i + "].mapWastetype");
				$(this).find("input:text:eq(0)").attr("name","VendorContractMappingList[" + i + "].mapGarbage");
				$(this).find("select:eq("+(dcount+2)+")").attr("name","VendorContractMappingList[" + i + "].mapGarbageUnit");
				$(this).find("select:eq("+(dcount+3)+")").attr("name","VendorContractMappingList[" + i + "].beatId");
				$(this).find("input:text:eq(1)").attr("name","VendorContractMappingList[" + i + "].empCount");
				$(this).find("input:text:eq(2)").attr("name","VendorContractMappingList[" + i + "].vehicleCount");
				

			});
}

function validateUnitDetailTable(errorList) {
	var rowCount = $('#unitDetailTable tr').length;
	var k=0;
	var ward = [],zone = [],task = [],route = [];
	$('.firstUnitRow')
			.each(function(i) {
						
						if (rowCount <= 1) {
							var codWard1 = $("#codWard" + i).val();
							var codWard2 = $("#codWard" + i).val();
							var mapTaskId = $("#mapTaskId" + i).val();
							var wasteType = $("#wasteType" + i).val();
							var garbageId = $("#garbageId" + i).val();
							var garbageUnit = $("#garbageUnit" + i).val();
							var beatId = $("#beatId" + i).val();
							var empCount = $("#empCount" + i).val();
							var vehicleCount = $("#vehicleCount" + i).val();
							var level = 1;
						} else {
							var utp = i;
							utp = i * 6;
							var codWard1 = $("#codWard" + utp).val();
							var codWard2 = $("#codWard" + (utp + 1)).val();
							var mapTaskId = $("#mapTaskId" + i).val();
							var wasteType = $("#wasteType" + i).val();
							var garbageId = $("#garbageId" + i).val();
							var garbageUnit = $("#garbageUnit" + i).val();
							var beatId = $("#beatId" + i).val();
							var empCount = $("#empCount" + i).val();
							var vehicleCount = $("#vehicleCount" + i).val();
							ward.push(codWard1);
							zone.push(codWard2);
							task.push(mapTaskId);
							route.push(beatId);
							var level = i + 1;
						}
						if (codWard1 == '' || codWard1 == undefined
								|| codWard1 == '0') {
							errorList.push(getLocalMessage('population.validation.ward')+ " " + level);
						}
						if (mapTaskId == '' || mapTaskId == undefined || mapTaskId == '0') {
							errorList.push(getLocalMessage('swm.select.task.srNo')+ " " + level);
						}
						if (wasteType == '' || wasteType == undefined || wasteType == '0') {
							errorList.push(getLocalMessage('swm.select.wasteType.srNo')+ " " + level);
						}
						if (garbageId == '' || garbageId == undefined || garbageId == '0') {
							errorList.push(getLocalMessage('swm.enter.garbage.srNo')+ " " + level);
						}
						if (garbageId < 0) {
							errorList.push(getLocalMessage('swm.enter.garbage.greater.zero.srNo')+ " " + level);
						}
						if (garbageUnit == '' || garbageUnit == undefined
								|| garbageUnit == '0') {
							errorList.push(getLocalMessage('swm.enter.garbage.unit.srNo')+ " " + level);
						}
						if (beatId == '' || beatId == undefined
								|| beatId == '0') {
							errorList.push(getLocalMessage('swm.beatNo.select.srNo')+ " " + level);
						}
						if (empCount == '' || empCount == undefined || empCount == '0') {
							errorList.push(getLocalMessage('swm.enter.employee.count.srNo')+ " " + level);
						}
						if (vehicleCount == '' || vehicleCount == undefined || vehicleCount == '0') {
							errorList.push(getLocalMessage('swm.enter.vehicle.count.srNo')+ " " + level);
						}
					});
	var i = 0;
	var j = 1;
	var k = 1;
	var count = 0;
	for (i = 0; i <= ward.length; i++) {
		for (j = k; j <= ward.length; j++) {
			if (ward[i] == ward[j] && zone[i] == zone[j] && task[i] == task[j] && route[i] == route[j]) {
				if (count == 0) {
					errorList.push(getLocalMessage("swm.check.combination.present")+ (j + 1));
				}
				count++;
			}
		}
		k++;
	}
	return errorList;
}

/** **********Start Print Report Function*********** */


function printContractMappint(formUrl, actionParam,contid) {
	
	var codWard1 = $("#codWard").val();
	var data = {
			"contid" : contid
		      };
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

/** **********End Print Report Function*********** */
function deleteEntry(obj, ids) {
	var totalWeight = 0;
	deleteTableRow('firstUnitRow', obj, ids);
	$('#firstUnitRow').DataTable().destroy();
	triggerTable();
}


function resetContract() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'ContractMapping.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}

// Print Div
function PrintDiv(title) {
	var divContents = document.getElementById("receipt").innerHTML;
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery-1.10.2.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button> <button id="btnExport" type="button" class="btn btn-blue-2 hidden-print"><i class="fa fa-file-excel-o"></i> Download</button> <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}



