/********************Add Function************/
function addpopulationForm(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	reOrderUnitTabIdSequence('.firstUnitRow');
}

/********************EditFunction************/
function getPopulationmasterData(popId) {
	var data = {
		"popId":popId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('PopulationMaster.html?'+'editPopulationMaster',data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	reOrderUnitTabIdSequence('.firstUnitRow');
}


/********************ViewFunction************/
function getViewPopulationmasterData(popId) {
	var data = {
		"popId":popId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('PopulationMaster.html?'+'viewPopulationMaster', data, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
/********************DeleteFunction************/

function deletePopulationmasterData(formUrl, actionParam,popId) {
	
if (actionParam == "deletePopulationMaster") {
		showConfirmBoxForDelete(popId, actionParam);
	}		
}


function showConfirmBoxForDelete(popId, actionParam) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger padding-5\">'+ getLocalMessage("swm.cnfrmdelete") +'</h4>';
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
/********************SearchFunction************/
function searchPopulationMaster() {
	
		var data = {
				"yearCpdId":replaceZero($('#popYear').val()),
			};	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('PopulationMaster.html?searchPopulationMaster', data, 'html',divName);
	$('.content').removeClass('ajaxloader');		
	$(divName).html(ajaxResponse);
	prepareTags();

}
/********************ReplaceZero Function************/
function replaceZero(value){
	return value != 0 ? value : undefined;
}
/********************Save PopulationMaster With Validation************/
function savePopulationMasterForm(element){
	var errorList = [];
	var popYear=$("#popYear").val();
	if (popYear == "0" || popYear == null ) {
		errorList.push(getLocalMessage("population.validation.popYear"));
	}
	errorList = validateUnitDetailTable(errorList)
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
	}else if (errorList.length == 0) {
		var status = saveOrUpdateForm(element,getLocalMessage('population.master.sucesss'), 'PopulationMaster.html', 'saveform');
		reOrderUnitTabIdSequence('.firstUnitRow'); 
		return status;
    }
}
function displayErrorsPage(errorList) {
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
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

/********************BackButton Function************/
function backPopulationMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'PopulationMaster.html');
	$("#postMethodForm").submit();
}

function resetPopulation() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'PopulationMaster.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}


/********************DataTable  Function************/
$(document).ready(function(){
	var table = $('.pmId').DataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
	    "iDisplayLength" : 5, 
	    "bInfo" : true,
	    "lengthChange": true,
	    "bPaginate": true,
	    "bFilter": true,
	    "ordering":  false,
	    "order": [[ 1, "desc" ]]
	    });
	$('input#popEst').keypress(function(e){ 
		var errorList = [];
		 if (this.selectionStart == 0 && (e.which == 48 || e.which == 46 )){
			 errorList.push(getLocalMessage("population.validation.popYearsize"));
		      return false;
		   }
	});
});	

function validatePopulationMaster(errorList) {
	
	var popYear=$("#popYear").val();
	if (popYear == "0" || popYear == null ) {
		errorList.push(getLocalMessage("population.validation.popYear"));
	}
	var codDwzid1 = $("#codDwzid1").val();
	if (codDwzid1 == "0" || codDwzid1 == null || codDwzid1 != undefined) {
		errorList.push(getLocalMessage("population.validation.ward"));
	}
	var codDwzid2 = $("#codDwzid2").val();
	if (codDwzid2 == "0" || codDwzid2 == null || codDwzid2 != undefined) {
		errorList.push(getLocalMessage("population.validation.zone"));
	}
	var codDwzid3 = $("#codDwzid3").val();
	if (codDwzid3 == "0" || codDwzid3 == null || codDwzid3 != undefined) {
		errorList.push(getLocalMessage("population.validation.zone"));
	}
	var codDwzid4 = $("#codDwzid4").val();
	if (codDwzid4 == "0" || codDwzid4 == null || codDwzid4 != undefined) {
		errorList.push(getLocalMessage("population.validation.zone"));
	}
	var codDwzid5 = $("#codDwzid5").val();
	if (codDwzid5 == "0" || codDwzid5 == null || codDwzid5 != undefined) {
		errorList.push(getLocalMessage("population.validation.zone"));
	}
	return errorList;
}

function uploadExcelFile() {
	
	var errorList = [];
	var fileName = $("#excelFilePath").val().replace(/C:\\fakepath\\/i, '');
	if (fileName == null || fileName == "") {
		errorList.push(getLocalMessage("excel.upload.vldn.error"));
		displayErrorsPage(errorList);
		
	}else{
		$("#filePath").val(fileName);
		var requestData = $.param($('#wmsMaterialMaster').serializeArray())
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading('PopulationMaster.html?'+ "loadExcelData", requestData, 'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);	
		if($("#validationerror_errorslist").size()==0){
		showSaveResultBox1("PopulationMaster.html");
		}
	}

	
	prepareTags();	
}
function exportExcelData() {
	window.location.href = 'PopulationMaster.html?exportRateExcelData';
}
function ResetForm(){
			
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('PopulationMaster.html?AddPopulationMaster', {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();	
	prepareTags();
	reOrderUnitTabIdSequence('.firstUnitRow'); 
}

function showSaveResultBox1(redirectUrl) {	
		var messageText = getLocalMessage("swm.population.excel.upload.success.message");
		var message='';
		var cls = getLocalMessage('eip.page.process');			
		 message	+='<p class="text-blue-2 text-center padding-15">'+ messageText+'</p>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-success\'    '+ 
		' onclick="closebox(\''+errMsgDiv+'\',\''+redirectUrl+'\')"/>'+	
		'</div>';		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBox(errMsgDiv);		
		return false;
	}


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
			reOrderUnitTabIdSequence('.firstUnitRow');
			hasNumber();
		} else {
			displayErrorsPage(errorList);
		}
	});
});
function reOrderUnitTabIdSequence(firstRow) {
	var saveMode=$("#saveMode").val();
	$(firstRow).each(
			function(i) {
				
				var utp = i;
				if (i > 0) {
					utp = i * 6;
				}
				// IDs
				$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
				$(this).find("select:eq(0)").attr("id", "codDwzid" + utp);
				$(this).find("select:eq(1)").attr("id", "codDwzid" + (utp + 1));
				$(this).find("select:eq(2)").attr("id", "codDwzid" + (utp + 2));
				$(this).find("select:eq(3)").attr("id", "codDwzid" + (utp + 3));
				$(this).find("select:eq(4)").attr("id", "codDwzid" + (utp + 4));
				$(this).find("input:text:eq(1)").attr("id", "popEstId" + i);
				$(this).find("input:text:eq(1)").attr("class", "form-control mandColorClass hasNumber");				
			
				// names
				$(this).find("input:text:eq(0)").val(i+1);
				$(this).find("select:eq(0)").attr("name","populationslist[" + i+ "].codDwzid1");
				$(this).find("select:eq(1)").attr("name","populationslist[" + i+ "].codDwzid2");
				$(this).find("select:eq(2)").attr("name","populationslist[" + i+ "].codDwzid3");
				$(this).find("select:eq(3)").attr("name","populationslist[" + i+ "].codDwzid4");
				$(this).find("select:eq(4)").attr("name","populationslist[" + i+ "].codDwzid5");
				$(this).find("input:text:eq(1)").attr("name","populationslist[" + i+ "].popEst");
				if(saveMode != undefined && saveMode=="E"){
				$(this).find("input:text:eq(2)").attr("name","populationslist[" + i+ "].delete").val('D');
				}
			});
}



function validateUnitDetailTable(errorList) {
	var rowCount = $('#unitDetailTable tr').length;
	var ward = [];
	$('.firstUnitRow')
			.each(function(i) {
						
						if (rowCount <= 2) {
							var codWard1 = $("#codDwzid" + i).val();
							var codWard2 = $("#codDwzid" + (i+1)).val();
							var codWard3 = $("#codDwzid" + (i+2)).val();
							var codWard4 = $("#codDwzid" + (i+3)).val();
							var codWard5 = $("#codDwzid" + (i+4)).val();
							var popcount = $("#popEstId" + i).val();
							var level = 1;
						} else {
							var utp = i;
							utp = i * 6;
							var codWard1 = $("#codDwzid" + utp).val();
							var codWard2 = $("#codDwzid" + (utp + 1)).val();
							var codWard3 = $("#codDwzid" + (utp + 2)).val();
							var codWard4 = $("#codDwzid" + (utp + 3)).val();
							var codWard5 = $("#codDwzid" + (utp + 4)).val();
							var popcount = $("#popEstId" + i).val();
							if (codWard5 != undefined) {
								ward.push(codWard5)
							} else {
								if (codWard4 != undefined) {
									ward.push(codWard4)
								} else if (codWard3 != undefined){
									ward.push(codWard3)
								}else if (codWard2 != undefined){
									ward.push(codWard2)
								}else if (codWard1 != undefined){
									ward.push(codWard1)
								}
								
							}
							var level = i + 1;
						}
						if(codWard1!=undefined){
							if (codWard1 == '' || codWard1 == "0" || codWard1 == null) {
								errorList.push($('#unitDetailTable > thead > tr > th:nth-child(2)').text() + " " + getLocalMessage("valid.not.select") + " " + level);
							}	
						}
						if(codWard2!= undefined ){
							if (codWard2 == '' ||  codWard2=="0" || codWard2 == null) {
								errorList.push($('#unitDetailTable > thead > tr > th:nth-child(3)').text() + " " +getLocalMessage("valid.not.select") + " " + level);
							}
						}
						if(codWard3!= undefined ){
							if (codWard3 == '' ||  codWard3=="0" || codWard3 == null) {
								errorList.push($('#unitDetailTable > thead > tr > th:nth-child(4)').text() + " " +getLocalMessage("valid.not.select") + " " + level);
							}
						}
						if(codWard4!= undefined ){
							if (codWard4 == '' ||  codWard4=="0" || codWard4 == null) {
								errorList.push($('#unitDetailTable > thead > tr > th:nth-child(5)').text() + " " +getLocalMessage("valid.not.select") + " " + level);
							}
						}
						if(codWard5!= undefined ){
							if (codWard5 == '' ||  codWard5=="0" || codWard5 == null) {
								errorList.push($('#unitDetailTable > thead > tr > th:nth-child(6)').text() + " " +getLocalMessage("valid.not.select") + " " + level);
							}
						}
						if (popcount == '' || popcount == undefined || popcount=="0") {
							errorList.push(getLocalMessage("population.validation.popYearsize") + " " + level);
						}
					});
	var i = 0;
	var j = 1;
	var k = 1;
	var count = 0;
	for (i = 0; i <= ward.length; i++) {
		for (j = k; j <= ward.length; j++) {
			if (ward[i] == ward[j]) {
				if (count == 0) {
					errorList.push(getLocalMessage("PopulationMasterDTO.master.exists")+ (j + 1));
				}
				count++;
			}
		}
		k++;
	}
	return errorList;
}
function deleteEntry(obj, ids) {
	deleteTableRow('firstUnitRow', obj, ids);
	$('#firstUnitRow').DataTable().destroy();	
	
	reOrderUnitTabIdSequence('.firstUnitRow'); 
}

function hasNumber() {
	$('.hasNumber').on('input', function() {
		this.value = this.value.replace(/[^0-9]/g, '');
	});
}


