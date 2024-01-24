var smUrl="PensionSchemeMaster.html";
$(document)
.ready(
		function() {
$("#createSchemeMaster").click(
							function() {
								
								var requestData = {
									"type" : "C"
								}
								
								var ajaxResponse = __doAjaxRequest(smUrl
										+ '?showPensionSchemeForm', 'POST', requestData, false,
										'html');
								
								$('.pagediv').html(ajaxResponse);
								prepareDateTag();
							});

$("#schemeMstHome").dataTable(
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

		

$("#searchSMH")
.click(
		function() {
			
			var errorList = [];
			
			
			var serviceId = $('#serviceId').val();
			
			
			if (serviceId >= 1)
			{
				var requestData = 'serviceId='+ serviceId;
				var table = $('#schemeMstHome')
						.DataTable();
				table.rows().remove().draw();
				$(".warning-div").hide();
				
				var ajaxResponse = __doAjaxRequest(
						smUrl
								+ '?filterServices',
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
												       index+1,
													obj.schemeCode,
													obj.schemeName,
													'<td class="text-center">'
															+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:90px;" onClick="viewSMH(\''
															+ obj.id
															+ '\',\''+obj.orgId+'\')" title="View pension Scheme Master"><i class="fa fa-eye"></i></button>'
															+ '<button type="button" class="btn btn-success btn-sm margin-right-10" onClick="editSMH(\''
															+ obj.id
															+ '\',\''+obj.orgId+'\')"  title="Edit pension Scheme Master"><i class="fa fa-pencil"></i></button>'
															+ '</td>' ]);
								});
				table.rows.add(result);
				table.draw();
			} else {
				errorList
						.push(getLocalMessage('social.select.security.schemeName.record'));
				displayErrorsOnPage(errorList);
			}
		});

});

function editSMH(id,orgId) {
	
	var requestData = {
			"id":id,
			"orgId":orgId,
			"type":"E",
			
	};
	
	
	var response = __doAjaxRequest(smUrl + '?editForm', 'POST', requestData,
			false, 'html');
	
	$('.pagediv').removeClass('ajaxloader');
	$('.pagediv').html(response);
	$("#addCF").attr("disabled",true);
	$(".remCF ").attr("disabled",true);
	$('input:checkbox[class^="checkboxClass"]').each(function() {
		$('input:checkbox[class^="checkboxClass"]').prop('checked', true);
	});
	$(".checkboxClass ").attr("disabled",true);
	var rowCount = $('#criteriatableId tr').length;
	for (let i = 0; i < rowCount - 1; i++) {
		$("#pensionSchmDto\\.pensioneligibilityList" + i + "\\.criteriaId").prop("disabled", false).trigger("chosen:updated");
		$("#pensionSchmDto\\.pensioneligibilityList" + i + "\\.rangeFrom").prop("disabled",false);
		$("#pensionSchmDto\\.pensioneligibilityList" + i + "\\.rangeTo").prop("disabled",false);
		let factorName=$("#pensionSchmDto\\.pensioneligibilityList" + i + "\\.factorApplicableDesc").val();
		removeDisable(i, factorName)
	}
	prepareDateTag();
}

function viewSMH(id,orgId) {
	
	var requestData = {
			"id":id,
			"orgId":orgId,
			"type":"V"
	};
	
	var response = __doAjaxRequest(smUrl + '?editForm', 'POST', requestData,
			false, 'html');
	
	$('.pagediv').removeClass('ajaxloader');
	$('.pagediv').html(response);
	$("#addCF").attr("disabled",true);
	$(".remCF ").attr("disabled",true);
	$(".checkboxClass ").attr("disabled",true);
	$(".deleteDetails ").attr("disabled",true);
	
	prepareDateTag();
}

		