var ComplaintStatusUrl = "GrievanceComplaintStatus.html";
$(document)
		.ready(
				function() {
				$("#id_complaintList").dataTable({
					"oLanguage" : {
						"sSearch" : ""
					},
					"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
					"iDisplayLength" : 5,
					"bInfo" : true,
					"lengthChange" : true
				});

									
					$("#fromDate").datepicker(
							{
								dateFormat : 'dd/mm/yy',
								changeMonth : true,
								changeYear : true,
								onClose : function(selectedDate) {
									$("#toDate").datepicker("option", "minDate", selectedDate);
								}
							});

					$("#toDate").datepicker(
							{
								dateFormat : 'dd/mm/yy',
								changeMonth : true,
								changeYear : true,
								onClose : function(selectedDate) {
									$("#fromDate").datepicker("option", "maxDate", selectedDate);
								}
							}); 
		
                    $('#departmentComplaint')
							.change(function() {
										if ($('#departmentComplaint').val() > 0) {
											var requestData = {
												"deptId" : $(
														'#departmentComplaint')
														.val(),
												"orgId" : null
											}
											var response = __doAjaxRequest(
													'GrievanceComplaintStatus.html?grievanceComplaintTypes',
													'post', requestData, false,'html');

											if (response == "") {
												$('#id_complaintType_div')
														.html('');
												$('#id_complaintType_div')
														.hide();
											} else {
												$('#id_complaintType_div')
														.html(response);
												$('#id_complaintType_div')
														.show();
											}
										}
									});

                    
                    $(".reset").click(function() {
						$("#postMethodForm").prop('action', '');
						$("#postMethodForm").prop('action', ComplaintStatusUrl);
						$("#postMethodForm").submit();

					});
				});

function searchAndViewComplaint(){
	showloader(true);
	setTimeout(function () {
		$("#errorDiv").hide();
		fromDate = $("#fromDate").val();
		toDate= $("#toDate").val();	
		departmentComplaint= $("#departmentComplaint").val();	
		complaintType=$("#complaintType").val();
		searchString = $(".searchString").val();	
		status=$("#status").val();
		
		var errorList=[];
		if(fromDate == "" && toDate == "" && departmentComplaint == "0" && complaintType == "0" && status == "" && searchString == ""){
			errorList.push(getLocalMessage('check.status.search.criteria'));	
		}
		if(fromDate !== "" && toDate == ""){
			errorList.push(getLocalMessage('care.reports.select.toDate'));	
		}
		if(toDate !== "" && fromDate == ""){
			errorList.push(getLocalMessage('care.reports.select.fromDate'));
		}
		
		if(errorList.length>0){
			displayErrorsOnPage(errorList);
		}else{
			var divName = '.content-page';
			var requestData = {
					"fromDate":fromDate,
					"toDate":toDate,
					"departmentComplaint":departmentComplaint,
					"complaintType":complaintType,	
					"searchString":searchString,
					"status":status							
			};
			var ajaxResponse = doAjaxLoading('GrievanceComplaintStatus.html?searchGrievance', requestData, 'html');	
		  $(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
		}
		showloader(false);
	},2000);
}


function viewComplaintDetails(searchString) {	
	$("#errorDiv").hide();
	searchString = (searchString== null)?$(".searchString").val():searchString;
	var errorList=[];
	if(searchString == undefined || searchString == null || searchString == ""){
		errorList.push(getLocalMessage('care.validator.searchString'));	
	}
	if(errorList.length>0){
		displayErrorsOnPage(errorList);
	}else{
		var divName = '.content-page';
		var requestData = {
				"searchString":searchString,
		};
		var ajaxResponse = doAjaxLoading('GrievanceComplaintStatus.html?viewComplaintDetails', requestData, 'html',
				divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function printContent(el){
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}

function resetApplication(obj) {	
	$('input[type=text]').val('');
	$('select').val('0').trigger('chosen:updated');
	$(".alert-danger").hide();
	resetForm(obj);
}
//D#127219
function viewReopenComplaint(searchString){
	//D#131629 1st check here complaint is valid for reopen or not
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('GrievanceDepartmentReopen.html?validComplForReopen', {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	
	var tempDiv = $('<div id="tempDiv">' + ajaxResponse + '</div>');
	var errorsPresent = tempDiv.find('#validationerror_errorslist');
	if(!errorsPresent || errorsPresent == undefined || errorsPresent.length == 0) {
		
		$("#errorDiv").hide();
		searchString = (searchString== null)?$(".searchString").val():searchString;
		var errorList=[];
		if(searchString == undefined || searchString == null || searchString == ""){
			errorList.push(getLocalMessage('care.validator.searchString'));	
		}
		if(errorList.length>0){
			displayErrorsOnPage(errorList);
		}else{
			var divName = '.content-page';
			var requestData = {
					"searchString":searchString,
					"hitFrom":"GrievanceComplaintStatus.html"
			};
			var ajaxResponse = doAjaxLoading('GrievanceDepartmentReopen.html?searchGrievance', requestData, 'html',
					divName);
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
		}
		
	} else {
		$(divName).html(ajaxResponse);
		prepareDateTag();
	}
	
	
	
}