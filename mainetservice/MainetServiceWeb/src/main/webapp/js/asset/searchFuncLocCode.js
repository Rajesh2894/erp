var cdmURL = "AssetFunctionalLocation.html";
$(document)
		.ready(
				function() {
/*					$('#').validate({
						onkeyup : function(element) {
							this.element(element);
							console.log('onkeyup fired');
						},
						onfocusout : function(element) {
							this.element(element);
							console.log('onfocusout fired');
						}
					});*/

					$("#dtFLCHome").dataTable(
							{
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

					$("#searchFLC")
							.click(
									function() {
										
										var errorList = [];
										/*var groupName = $('#name').val();
										var accountCode = $('#accountCode')
												.val();
										var assetClass = $('#assetClass').val();
										var frequency = $('#frequency').val();*/
										
										var funcLocCode = $('#funcLocationCode').val();
										var description = $('#description').val();
										
										if(funcLocCode == undefined)
											funcLocCode	=	"";
										
										if(description == undefined)
											description	=	"";
										
										if (funcLocCode != '' || description != '')
										{
											var requestData = 'funcLocCode='+ funcLocCode	+ '&description='+ description;
											var table = $('#dtFLCHome')
													.DataTable();
											table.rows().remove().draw();
											$(".warning-div").hide();
											
											var ajaxResponse = __doAjaxRequest(
													cdmURL
															+ '?filterFuncCode',
													'POST', requestData, false,
													'json');
											
											var result = [];
											$
													.each(
															ajaxResponse,
															function(index) {
																var obj = ajaxResponse[index];
																result
																		.push([
																				obj.funcLocationCode,
																				obj.description,
																				obj.parentId,
																				obj.unitDesc,
																				'<td class="text-center">'
																						+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:90px;" onClick="viewFLC(\''
																						+ obj.funcLocationId
																						+ '\')" title="View Chart Of Depreciation"><i class="fa fa-eye"></i></button>'
																						+ '<button type="button" class="btn btn-success btn-sm margin-right-10" onClick="editFLC(\''
																						+ obj.funcLocationId
																						+ '\')"  title="Edit Chart Of Depreciation"><i class="fa fa-pencil"></i></button>'
																						+ '</td>' ]);
															});
											table.rows.add(result);
											table.draw();
										} else {
											errorList
													.push(getLocalMessage('asset.select.record'));
											displayErrorsOnPage(errorList);
										}
									});

					$("#createFunLocCode").click(
							function() {
								
								var requestData = {
									"type" : "C"
								}
								
								var ajaxResponse = __doAjaxRequest(cdmURL
										+ '?form', 'POST', requestData, false,
										'html');
								
								$('.pagediv').html(ajaxResponse);
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

function resetCDM() {
	$("input:reset");
	$("select:reset");
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

function editFLC(funcLocId) {
	
	var requestData = 'funcLocId=' + funcLocId + '&type=E';
	
	var response = __doAjaxRequest(cdmURL + '?form', 'POST', requestData,
			false, 'html');
	
	$('.pagediv').removeClass('ajaxloader');
	$('.pagediv').html(response);
	prepareDateTag();
}

function viewFLC(funcLocId) {
	
	var requestData = 'funcLocId=' + funcLocId + '&type=V';
	
	var response = __doAjaxRequest(cdmURL + '?form', 'POST', requestData,
			false, 'html');
	$('.pagediv').removeClass('ajaxloader');
	
	$('.pagediv').html(response);
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