$(document).ready(function(){
	//$("#mappingForm :input").prop("disabled", true);

	 $('#propertyNo_0').val('');
		$('#usage_0').val('');
		$('#unit_0').val('');
		$('#floor_0').val('');
		$('#totalArea_0').val('');
		
		
		$(".addCF2").click(function(){
			var row=0;
			var errorList = [];
			errorList = validateEstate(errorList);
			 if (errorList.length == 0) {
				           if(checkPropertyType()){
				                errorList = validateProperty(errorList);
								if (errorList.length == 0) {
								        var content = $(this).closest('tr').next().closest('tr').clone();
					    				$(this).closest("tr").after(content);
										var clickedIndex = $(this).parent().parent().index() - 1;	
										content.find("input:text").val('');
										content.find("input:hidden").val('');
										content.find('div.chosen-container').remove();
					    				content.find("select").chosen().trigger("chosen:updated");
					    				content.find("select").val('0');
					    				$('.error-div').hide();
					    				reOrderSequenceForContract();
								}else {
									displayErrorsOnPageView(errorList);
								}
				      }else{
						showAddPropertyValidation(getLocalMessage("rl.common.can.not.add.property"));
				     }	
			 }else{
				 displayErrorsOnPageView(errorList); 
			 }
		
	    });

	 $("#customFields2").on('click','.remCF2',function(){
			if($("#customFields2 tr").length != 2)
				{
					 $(this).parent().parent().remove();
					 reOrderSequenceForContract();
				}
		   else
				{
			   var errorList = [];
			   errorList.push(getLocalMessage("water.additnlowner.deletrw.validtn"));   
			   displayErrorsOnPageView(errorList);
			}
		 });
 });
function showContract(contId,type,showForm){
	 var requestData = 'contId='+contId+'&type='+type+'&showForm='+showForm;
		var ajaxResponse	=	doAjaxLoading('ContractAgreement.html?form', requestData, 'html');
		$('.content').removeClass('ajaxloader');
		$('.content').html(ajaxResponse);
		
		if(type == 'V'){
			 $("#ContractAgreement :input").prop("disabled", true);
			 $('.addCF3').attr('disabled',true);
			 $('.addCF4').attr('disabled',true);
			 $('.addCF5').attr('disabled',true);
			 $('.addCF2').attr('disabled',true);
			 $('.remCF2').attr('disabled',true);
			 $('.remCF3').attr('disabled',true);
			 $('.remCF4').attr('disabled',true);
			 $('.remCF5').attr('disabled',true);
			 $(".backButton").removeProp("disabled");
			 //$('#back').attr('disabled', false);
			 $('#noa_header').show();
		}
		
	} 

function proceedSave(element) {

		var errorList = [];
		var decision =  $("#decision:checked").val();
		var comments = document.getElementById("comments").value;

		if((decision == undefined || decision == '') && (comments == undefined || comments == '') ){
			errorList.push(getLocalMessage('rnl.remark.decision'));
		}else if (decision == undefined || decision == '')
			errorList.push(getLocalMessage('rnl.select.decision'));
		else if (comments == undefined || comments == '')
			errorList.push(getLocalMessage('rnl.enter.remark'));
		
		if (errorList.length > 0) {
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
		} else {
			var divName = '.content';
			var formName = findClosestElementId(element, 'form');
			var theForm = '#' + formName;
			var requestData = __serializeForm(theForm);
			var object = __doAjaxRequest($(theForm).attr('action')
					+ '?save', 'POST', requestData,false, 'json');
			if (object.error != null && object.error != 0) {
				$.each(object.error, function(key, value) {
					$.each(value, function(key, value) {
						if (value != null && value != '') {
							errorList.push(value);
						}
					});
				});
				displayErrorsOnPage(errorList);
			} else {
				if (decision == 'REJECTED') {
					showBoxForApproval(getLocalMessage('rnl.application.rejectedStatus'));
				} else {
					showBoxForApproval(getLocalMessage('rnl.application.acceptStatus'));
				}
			}
		}

	}

	function showBoxForApproval(succesMessage) {
	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed = getLocalMessage('rnl.button.proceed');
	var no = 'No';
	message += '<p class="text-blue-2 text-center padding-15">' + succesMessage
			+ '</p>';

	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
			+ Proceed + '\'  id=\'Proceed\' ' + ' onclick="closeApproval();"/>'
			+ '</div>';
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#Proceed').focus();
	showModalBoxWithoutClose(childDivName);
	}

	function closeApproval() {
	window.location.href = 'AdminHome.html';
	$.fancybox.close();
	}
	
 

