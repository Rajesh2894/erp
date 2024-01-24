var cdmURL = "AssetSearch.html";
var astURL = "AssetRegistration.html";
var transURL = "AssetTransfer.html";
var retireURL = "AssetRetirement.html"
var revaluationURL = "AssetRevaluation.html"
var UPLOADURL = "AssetRegisterUpload.html";
var IT_Asset_UPLOADURL = "ITAssetRegisterUpload.html";
$(document)
		.ready(
				function() {

					/*
					 * $('#').validate({ onkeyup : function(element) {
					 * this.element(element); console.log('onkeyup fired'); },
					 * onfocusout : function(element) { this.element(element);
					 * console.log('onfocusout fired'); } });
					 */

					$("#searchAssetHome").dataTable(
							{
								order: [[0, 'desc']],
								"oLanguage" : {
									"sSearch" : ""
								},
								"aLengthMenu" : [ [ 5, 10, 15, -1 ],
										[ 5, 10, 15, "All" ] ],
								"iDisplayLength" : 5,
								"bInfo" : true,
								"lengthChange" : true
							});
					var mode = $("#modeType").val();
					if (mode == 'V' || mode == 'E') {
						triggerDatatable();
					}
					
					//T#92465 set cdmUrl based on moduleDept
					cdmURL=$("#moduleDeptUrl").val();
					
					/* Specific asset search */
					
					$("#searchAST").click(function() {		
						
										var errorList = [];
										var saveMode =  $('#saveMode').val();
										var moduleCode = $('#moduleDeptCode').val()
										var deptId = $('#deptId').val();
										var assetClass1 = $('#assetClass1').val();
										var assetClass2 = $('#assetClass2').val();
										//D#73717
										var astSerialNo = $('#astSerialNo').val();
										if(astSerialNo != undefined){
											astSerialNo =astSerialNo.trim();
										}
										//var costCenter = $('#costCenterId').val();
										var assetStatusId = $('#assetStatusId').val();
										var acquisitionMethodId = $('#acquisitionMethodId').val();
										var employeeId = $('#employeeId').val();
										var locationId = $('#locationId').val();
										var astAppNo = $('#astAppNo').val();
										if(astAppNo != undefined){
											astAppNo =astAppNo.trim();
										}
										
										
										var roadName = $('#roadName').val();
										var pincode=  $('#pincode').val();
										
										if (deptId == undefined || deptId == '')
											deptId = 0;

										if (assetClass1 == undefined
												|| assetClass1 == "0")
											assetClass1 = '';
										if (assetClass2 == undefined
												|| assetClass2 == "0")
											assetClass2 = '';
										if (astSerialNo == undefined)
											astSerialNo = "";
										if (astAppNo == undefined)
											astAppNo = "";
										if (roadName == undefined)
											roadName = "";
										if (pincode == undefined)
											pincode = "";

										/*if (costCenter == undefined)
											costCenter = 0;*/
										if (assetStatusId == undefined)
											assetStatusId = 0;
										if (acquisitionMethodId == undefined)
											acquisitionMethodId = 0;
										if (employeeId == undefined)
											employeeId = 0;
										

										if (locationId == undefined
												|| locationId == "")
											locationId = 0;

										if (deptId != 0 || assetClass1 != ''
												|| assetClass2 != ''
												|| astSerialNo != 0
												|| assetStatusId != 0
												|| acquisitionMethodId != 0
												|| employeeId != 0
												|| locationId != 0
												|| astAppNo !=0 ) {

											var requestData = 'deptId='
													+ deptId + '&assetClass1='
													+ assetClass1
													+ '&assetClass2='
													+ assetClass2 + '&astSerialNo='
													+ astSerialNo + '&assetStatusId='
													+ assetStatusId + '&acquisitionMethodId='
													+ acquisitionMethodId + '&employeeId='
													+ employeeId
													+ '&locationId='
													+ locationId
													+ '&astAppNo='
													+ astAppNo
													+ '&roadName='
													+ roadName
													+ '&pincode='
													+ pincode;
											;

											var table = $('#searchAssetHome')
													.DataTable();
											table.rows().remove().draw();
											$(".warning-div").hide();
											var ajaxResponse = __doAjaxRequest(
													cdmURL + '?filterAsset',
													'POST', requestData, false,
													'json');
											console
													.log('-----------'
															+ JSON
																	.stringify(ajaxResponse));
											let moduleDeptCode=$('#moduleDeptCode').val();
											var result = [];
											$
													.each(
															ajaxResponse,
															function(index) {
																var obj = ajaxResponse[index];
																var disStr	=	"";
																if(obj.appovalStatus == null || obj.appovalStatus == '' || obj.appovalStatus=='P'|| obj.assetStatus == 'DSP' || obj.appovalStatus=='R' || obj.appovalStatus=='D' ) {
																	disStr	=	'disabled="true"';
																}
																//D#85564 get asset status code
																var assetStatusCode = $("#assetStatusId").find("option:selected").attr('code');
																var checkboxTag = '<td class="text-center">'
																	/*+ '<input type="checkbox" class="margin-left-20" id="astSearchDTO.searchCheck${count.index}" value="'+obj.astId+'" />'*/
																	+ '<input type="checkbox" class="margin-left-20 selectedRow" value="'+obj.astId+'" />'/* id="astSearchDTO.searchCheck${count.index}"*/ 
																	+ '</td>';
																if(assetStatusCode != 'A'){
																	checkboxTag='<td class="text-center">'
																		+ '</td>';
																}
																if(saveMode == 'C'){
																	if(moduleDeptCode == 'AST'){
																		result
																		.push([obj.astId,
																		       checkboxTag,
																				obj.astAppNo,
																				obj.astCode,
																				obj.assetClass2Desc,
																				obj.assetClass1Desc,
																				/*obj.costCenterDesc,*/
																				obj.location,
																				obj.deptName,
																				obj.assetStatusDesc,
																				'<td class="text-center">'
																						+ '<button type="button"  class="btn btn-blue-1 btn-sm margin-right-10" onClick="viewAST(\''
																						+ obj.astId
																						+ '\')" title="View Asset"><i class="fa fa-eye"></i></button>'
																						+ '</td>' ]);
																	}else{
																		result
																		.push([checkboxTag,
																				obj.astId,
																				obj.astAppNo,
																				obj.assetModelIdentifier,
																				obj.serialNo,
																				obj.astCode,
																				obj.assetClass2Desc,
																				obj.assetStatusDesc,
																				'<td class="text-center">'
																						+ '<button type="button"  class="btn btn-blue-1 btn-sm margin-right-10" onClick="viewAST(\''
																						+ obj.astId
																						+ '\')" title="View Asset"><i class="fa fa-eye"></i></button>'
																						+ '</td>' ]);
																	}
																	
																} else{
																	if(moduleDeptCode == 'AST'){
																		result
																		.push([
																			obj.astId,
																			obj.astAppNo,
																			obj.astCode,
																			obj.assetClass2Desc,
																			obj.assetClass1Desc,
																			/*obj.costCenterDesc,*/
																			obj.location,
																			obj.deptName,
																			obj.assetStatusDesc,
																			'<td class="text-center">'
																			 
																		    +(obj.appovalStatus == "D" ? '<button type="button"  class="btn btn-danger btn-sm margin-right-10" onClick="draftAST(\''
																			+ obj.astId
																			+ '\')" title="normal Draft"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button>': '')
																		    
																			+ '<button type="button"  class="btn btn-blue-1 btn-sm margin-right-10" onClick="viewAST(\''
																			+ obj.astId
																			+ '\')" title="View Asset"><i class="fa fa-eye"></i></button>'
																			+ '<button type="button" '+ disStr +' class="btn btn-success btn-sm margin-right-10" onClick="editAST(\''
																			+ obj.astId
																			+ '\')"  title="Edit Asset"><i class="fa fa-pencil"></i></button>'
																			
																			+(moduleDeptCode == "AST" ? (obj.assetClass1Code == "IMO" ? '<button type="button" disabled="true" class="btn btn-primary btn-sm margin-right-10" onClick="transferAST(\''
																			+ obj.astId
																			+ '\')"  title="Transfer Asset"><i class="fa fa-exchange"></i></button>': '<button type="button" '+ disStr +' class="btn btn-primary btn-sm margin-right-10" onClick="transferAST(\''
																			+ obj.astId
																			+ '\')"  title="Transfer Asset"><i class="fa fa-exchange"></i></button>'):'')
																			
																			+ '<button type="button" '+ disStr +' class="btn btn-primary btn-sm margin-right-10" onClick="retireAST(\''
																			+ obj.astId
																			+ '\')"  title="Retire Asset"><i class="fa fa-sort-amount-asc"></i></button>'
																			
																			+ (moduleDeptCode == "AST" ? '<button type="button" '+ disStr +' class="btn btn-primary btn-sm margin-right-10" onClick="revaluateAST(\''
																			+ obj.astId
																			+ '\')"  title="Revaluate Asset"><i class="fa fa-recycle"></i></button>' : '')
																			
																			+(obj.depriChecked == "N" ? '<button type="button" disabled="true" class="btn btn-primary btn-sm margin-right-10" onClick="depreciationAST(\''
																			+ obj.astId
																			+ '\')"  title="Asset Depreciation Report"><i class="fa fa-area-chart"></i></button>': '<button type="button" '+ disStr +' class="btn btn-primary btn-sm margin-right-10" onClick="depreciationAST(\''
																			+ obj.astId
																			+ '\')"  title="Asset Depreciation Report"><i class="fa fa-area-chart"></i></button>')
																			+ '</td>' ]);
																	}else{
																		result
																		.push([
																			obj.astId,
																			obj.astAppNo,
																			obj.assetModelIdentifier,
																			obj.serialNo,
																			obj.astCode,
																			obj.assetClass2Desc,
																			obj.assetStatusDesc,
																			'<td class="text-center">'
																			 
																		    +(obj.appovalStatus == "D" ? '<button type="button"  class="btn btn-danger btn-sm margin-right-10" onClick="draftAST(\''
																			+ obj.astId
																			+ '\')" title="normal Draft"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button>': '')
																		    
																			+ '<button type="button"  class="btn btn-blue-1 btn-sm margin-right-10" onClick="viewAST(\''
																			+ obj.astId
																			+ '\')" title="View Asset"><i class="fa fa-eye"></i></button>'
																			+ '<button type="button" '+ disStr +' class="btn btn-success btn-sm margin-right-10" onClick="editAST(\''
																			+ obj.astId
																			+ '\')"  title="Edit Asset"><i class="fa fa-pencil"></i></button>'
																			
																			+(moduleDeptCode == "AST" ? (obj.assetClass1Code == "IMO" ? '<button type="button" disabled="true" class="btn btn-primary btn-sm margin-right-10" onClick="transferAST(\''
																			+ obj.astId
																			+ '\')"  title="Transfer Asset"><i class="fa fa-exchange"></i></button>': '<button type="button" '+ disStr +' class="btn btn-primary btn-sm margin-right-10" onClick="transferAST(\''
																			+ obj.astId
																			+ '\')"  title="Transfer Asset"><i class="fa fa-exchange"></i></button>'):'')
																			
																			/*+ '<button type="button" '+ disStr +' class="btn btn-primary btn-sm margin-right-10" onClick="retireAST(\''
																			+ obj.astId
																			+ '\')"  title="Retire Asset"><i class="fa fa-sort-amount-asc"></i></button>'*/
																			
																			+ (moduleDeptCode == "AST" ? '<button type="button" '+ disStr +' class="btn btn-primary btn-sm margin-right-10" onClick="revaluateAST(\''
																			+ obj.astId
																			+ '\')"  title="Revaluate Asset"><i class="fa fa-recycle"></i></button>' : '')
																			
																			/*+(obj.depriChecked == "N" ? '<button type="button" disabled="true" class="btn btn-primary btn-sm margin-right-10" onClick="depreciationAST(\''
																			+ obj.astId
																			+ '\')"  title="Asset Depreciation Report"><i class="fa fa-area-chart"></i></button>': '<button type="button" '+ disStr +' class="btn btn-primary btn-sm margin-right-10" onClick="depreciationAST(\''
																			+ obj.astId
																			+ '\')"  title="Asset Depreciation Report"><i class="fa fa-area-chart"></i></button>')*/
																			+ '</td>' ]);	
																	}
																	
																}
															});
											table.rows.add(result);
											table.draw();
										} else {
											errorList
													.push(getLocalMessage('asset.select.record'));
											displayErrorsOnPage(errorList);
										}
									});

					$("#AddAssetRegisration").click(
							function() {
								var requestData = {
									"type" : "C"
								}
								var ajaxResponse = __doAjaxRequest(astURL
										+ '?form', 'POST', requestData, false,
										'html');
								$('.content-page').html(ajaxResponse);
								prepareDateTag();
							});

				});

function showErr(errorList) {

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
	errorList = [];
}

function closeErrBox() {
	$('.warning-div').addClass('hide');
}

//T#85539
function changeAssetTypeOrClass(){
	
	let astTypeCode = $('#assetClass2 option:selected').attr('code');
	if(astTypeCode== 'B'){
		$("#buildPropId").show();
	}else{
		$("#buildPropId").hide();
	}
	
}

function resetAST() {
	var saveMode =  $('#saveMode').val();
	if (saveMode == 'C'){
		var searchURL= "AssetSearch.html";
		var response = __doAjaxRequest(searchURL + '?searchAsset', 'POST', {},
				false, 'html');
		var dialogId = $('#searchAssetPage').parent().attr('id');
		$('#'+dialogId).html(response);
	}else{
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', cdmURL);
	$("#postMethodForm").submit();
}
}

function resetCdmForm() {
	$("#name").val("");
	$("#cdmAstCls").val("0");
	$("#cdmFreq").val("0");
	$("#cdmDepreKey").val("0");
	$("#accountCode").val("0");
	$("#cdmFreq").val("");
	$("#remark").val("");
	$('.warning-div').addClass('hide');
}

//D#34059
function draftAST(assetId) {
	var requestData = 'assetId=' + assetId + '&type=D';
	var response = __doAjaxRequest(astURL + '?form', 'POST', requestData,
			false, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(response);
	prepareDateTag();
}

function editAST(assetId) {
	var requestData = 'assetId=' + assetId + '&type=E';
	var response = __doAjaxRequest(astURL + '?form', 'POST', requestData,
			false, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(response);
	prepareDateTag();
}

function viewAST(assetId) {
	
	var saveMode =  $('#saveMode').val();
	var requestData = 'assetId=' + assetId + '&type=V'+ '&saveMode='+saveMode;
	var response = __doAjaxRequest(astURL + '?form', 'POST', requestData,
			false, 'html');
	if(saveMode == 'C'){
		var dialogId = $('#searchAssetPage').parent().attr('id');
		$('#'+dialogId).removeClass('ajaxloader');
		$('#'+dialogId).html(response);	
	}else{
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(response);
	}	
	prepareDateTag();
}

function transferAST(assetId) {
	
	var requestData = 'assetId=' + assetId;
	var response = __doAjaxRequest(transURL + '?form', 'POST', requestData,
			false, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(response);
	prepareDateTag();
}

function retireAST(assetId) {
	
	var requestData = 'assetId=' + assetId;
	var ajaxResponse = __doAjaxRequest(retireURL + '?form', 'POST', requestData,
			false, 'html');
	/*$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(response);
	prepareDateTag();*/
	
	
	 if(ajaxResponse != false){
	    	var hiddenVal = $(ajaxResponse).find('#taxMasErrId').val();
	    	if (hiddenVal != '' && hiddenVal!=null) {
	    		showConfirmBoxAstRetire(hiddenVal);
}
	    	else
	    		{
	    		$('.content-page').removeClass('ajaxloader');
	    		$('.content-page').html(ajaxResponse);
	    		prepareDateTag();
	    		}
	    		}
	 }   	

function revaluateAST(assetId) {
	
	var requestData = 'assetId=' + assetId;
	var response = __doAjaxRequest(revaluationURL + '?form', 'POST', requestData,
			false, 'html');
	$('.content-page').removeClass('ajaxloader');
	$('.content-page').html(response);
	prepareDateTag();
}

function depreciationAST(assetId) {
	
	var depreciationURL = "DepreciationReport.html";
	var requestData = 'assetId=' + assetId;
	var response = __doAjaxRequest(depreciationURL + '?getDetailsByAssetId', 'POST', requestData,
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

function BackCDM() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', cdmURL);
	$("#postMethodForm").submit();
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
//this for excel upload 
function ExportImport() {
		
		var pgName = $('#atype').val()
		if(pgName == 'IAST'){
			window.location.href=IT_Asset_UPLOADURL
			//window.location.href=UPLOADURL
		}else{
			window.location.href=UPLOADURL
		}
		
	}

function BarcodePage(element) {
	
	    var requestData ={}
		var ajaxResponse = __doAjaxRequest(cdmURL+'?viewBarcode', 'POST', requestData, false,'html');
	    var tempDiv = $('<div id="tempDiv">' + ajaxResponse + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');
		$('.content-page').html(ajaxResponse);	
		$(".tableDiv").hide();

	
}
function PrintDiv(title) {
	var divContents = document.getElementById("receipt").innerHTML;
	var printWindow = window.open('','_blank');
	printWindow.document.write('<html><head><title>'+title+'</title>');
	printWindow.document.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document.write('<script src="js/mainet/ui/jquery-1.10.2.min.js"></script>')
	printWindow.document.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>') 
	printWindow.document.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button> <button id="btnExport" type="button" class="btn btn-blue-2 hidden-print"><i class="fa fa-file-excel-o"></i> Download</button> <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}

function showConfirmBoxAstRetire(sucessMsg) {
	
    var errMsgDiv = '.msg-dialog-box';
    var message = '';
    var cls = 'Ok';
    message += '<h4 class=\"text-center text-blue-2 padding-12\">'+sucessMsg+'</h4>';
	 message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedAstRetire()"/>' + '</div>';

	 $(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	 $(errMsgDiv).html(message);
	 $(errMsgDiv).show();
	 $('#btnNo').focus();
	$("#validationDiv :input").prop("disabled", true); 
	
	 showModalBoxWithoutClose(errMsgDiv);

}

function proceedAstRetire() {
	
	$.fancybox.close();
}