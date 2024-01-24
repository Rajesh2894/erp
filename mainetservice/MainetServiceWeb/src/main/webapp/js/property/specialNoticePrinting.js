$(document).ready(function() {
					
					
						showSingleMultiple();
				
						$('#datatables').DataTable({
				    		"oLanguage": { "sSearch": "" } ,
				    		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
				    	    "iDisplayLength" : 5, 
				    	    "bInfo" : true,
				    	    "lengthChange": true,
				    	    "bPaginate": true,
				    	    "bFilter": true
				    	    });
						
						$('#selectall').click(function(event) {
							if (this.checked) {
								$('.checkall').each(function() {
									this.checked = true;
								});
							} else {
								$('.checkall').each(function() {
									this.checked = false;

								});
							}
						});
						
						
				});

function searchProperty() {
	var errorList = [];
	errorList=validateFormDetails(errorList); 
	if (errorList.length == 0) {

		var formAction	=	$('#SpecialNoticePrint').attr('action');
		var url=formAction+'?proceed';
		$('#SpecialNoticePrint').attr('action', url);
		$('#SpecialNoticePrint').submit();

			}
	else{
		 showErrorOnPage(errorList);
	}
}

function validateFormDetails(errorList){
	if($("input[name='specialNotGenSearchDto.specNotSearchType']:checked").val()=="SM"){	
		if ($("#propertyNo").val() == "" && $("#oldPropertyNo").val() == "") {
			errorList.push(getLocalMessage("property.changeInAss"));
		}		
	}

	if($("input[name='specialNotGenSearchDto.specNotSearchType']:checked").val()=="AL"){
		var word1=$("#assWard1").val();
		var location=$("#locId").val();
		var usage=$("#assdUsagetype1").val();

		if((location=='0' || location == undefined) && (word1=='0' || word1 == undefined ) && (usage=='0' || usage == undefined)){
			  errorList.push(getLocalMessage('property.selectLoc')); 
		 }	
	}
	return errorList;
}

function printSpecialNotice(element){
	$('#datatables').DataTable().destroy();
	
	
	var someObj={};
	someObj.checked=[];
	
	$("input:checkbox").each(function(){
	    var $this = $(this);

	    if($this.is(":checked")){
	    	someObj.checked.push($this.attr("id"));
	    }else{
	        
	    }
	});
	
	    var checkedValue = someObj.checked.join();
	    
	    $("#SpecialNoticePrint :input[value!='on']").serialize(); 
	    
	    var requestData = "checkedValue="+checkedValue;
		var url = "SpecialNoticePrinting.html?printSpecialNotice";
		var ajaxResponse = __doAjaxRequest(url, 'post',requestData , false);

		var divName = formDivName;
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	
	
}

function showSingleMultiple(){
	
	$('#selectall').prop('checked', true);
	$('.checkall').each(function() {
		this.checked = true;
	});
	
	if($("input[name='specialNotGenSearchDto.specNotSearchType']:checked").val()=="SM"){
		$('.PropDetail').show();
		/*$('.propLable').show();*/
		$('.wardZone').hide();
		$('.Loc').hide();
		$('.usageType').hide();
		$('.searchBtn').show();
		$('.sectionSeperator').hide();
		
		$('#assWard1').val('');
		$('#assWard2').val('');
		$('#locId').val('');

	}
	else if($("input[name='specialNotGenSearchDto.specNotSearchType']:checked").val()=="AL"){
		$('.PropDetail').hide();
		$('.wardZone').show();
		$('.Loc').show();
		$('.usageType').show();
		$('.searchBtn').show();
		$('.sectionSeperator').show();
		$('.PropDetail').val('');

		$('#propertyNo').val('');
		$('#oldPropertyNo').val('');
	}else{
		$('.PropDetail').hide();
		$('.wardZone').hide();
		$('.Loc').hide();
		/*$('.propLable').hide();*/
		$('.usageType').hide();
		$('.searchBtn').hide();
		$('.sectionSeperator').hide();
	}
	
}
$('#genNotCheck').change(function () {
	if($('#genNotCheck').prop('checked',true))
	{
	$("#checkValue").val("Y"); 
	}
	else if($('#genNotCheck').prop('checked', true))
	{
	$("#checkValue").val("N"); 
	}
});

function resetFormDetails(resetBtn) {
	var data = {};
	var URL = 'SpecialNoticePrinting.html?resetFormData';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	$(".content-page").html(returnData);
}

function showErrorOnPage(errorList){
	var errMsg = '<ul>';
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);
	$("#errorDiv").show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
	return false;
}