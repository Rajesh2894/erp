$(document).ready(
		function() {

			$("#witnessTable").dataTable({
				"oLanguage" : {
					"sSearch" : ""
				},
				"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
				"iDisplayLength" : 5,
				"bInfo" : true,
				"lengthChange" : true
			});

			//D#122612 Translator	 
			   var langFlag = getLocalMessage('admin.lang.translator.flag');
				if(langFlag ==='Y'){
					$("#witnessTabForm input[id=firstNameEng]").keyup(function(event){
						var no_spl_char;
						no_spl_char = $("#witnessTabForm input[id=firstNameEng]").val().trim();
						if(no_spl_char!=''){
							commonlanguageTranslate(no_spl_char,'witnessTabForm input[id=firstNameReg]',event,'');
						}else{
							$("#witnessTabForm input[id=firstNameReg]").val('');
						}
					});
					
					$("#witnessTabForm input[id=middleNameEng]").keyup(function(event){
						var no_spl_char;
						no_spl_char = $("#witnessTabForm input[id=middleNameEng]").val().trim();
						if(no_spl_char!=''){
							commonlanguageTranslate(no_spl_char,'witnessTabForm input[id=middleNameReg]',event,'');
						}else{
							$("#witnessTabForm input[id=middleNameReg]").val('');
						}
					});
					
					$("#witnessTabForm input[id=lastNameEng]").keyup(function(event){
						var no_spl_char;
						no_spl_char = $("#witnessTabForm input[id=lastNameEng]").val().trim();
						if(no_spl_char!=''){
							commonlanguageTranslate(no_spl_char,'witnessTabForm input[id=lastNameReg]',event,'');
						}else{
							$("#witnessTabForm input[id=lastNameReg]").val('');
						}
					});
					
					$("#witnessTabForm textarea[id=fullAddrEng]").keyup(function(event){
						var no_spl_char;
						no_spl_char = $("#witnessTabForm textarea[id=fullAddrEng]").val().trim();
						if(no_spl_char!=''){
							commonlanguageTranslate(no_spl_char,'witnessTabForm textarea[id=fullAddrReg]',event,'');
						}else{
							$("#witnessTabForm textarea[id=fullAddrReg]").val('');
						}
					});
				}
				
				//D#127376
				//doing below code only when form fill by pressing tab one by one than it create PBLM (mask not work) so adding below like code it work
				$('#witnessTabForm textarea[id=fullAddrEng]').bind('click keyup', function(event) {
					$.mask.definitions['~'] = '[+-]';
					$('#witnessTabForm input[id=uidNo').mask('9999-9999-9999');

				});
				
				$('#witnessTabForm input[id=uidNo').click(function() {
					$.mask.definitions['~'] = '[+-]';
					$('#witnessTabForm input[id=uidNo').mask('9999-9999-9999');

				});
				
				$('.specifyRel').hide();
				if($('#otherRel').val() != ""){
					$('.specifyRel').show();
				}

		});

//D#130755
var rowNumber;
function resetWitnessDet() {
	
	var divName = '#witnessDet';
	var requestData = {
		"resetOption" : "reset"
	}
	var response = __doAjaxRequest('MarriageRegistration.html?showWitnessPage',
			'POST', requestData, false, '', 'html');
	$(divName).html(response);
}


// Add new witness in the witness grid
function addWitnessBT(element) {
	var errorList = [];
	errorList = validateWitnessDetails(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErrAstInfo(errorList);
	} else {
		var divName = '#witnessDet';
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest('MarriageRegistration.html?addWitness',
				'POST', requestData, false, '', 'html');
		$(divName).html(response);
		prepareDateTag();

	}
}

function openActionWitness(srNo, type) {
	var divName = '#witnessDet';
	var requestData = {
		"srNo" : srNo,
		"type" : type
	}
	var response = __doAjaxRequest(
			'MarriageRegistration.html?openActionWitness', 'POST', requestData,
			false, '', 'html');
	$(divName).html(response);
	//D#130755
	rowNumber=srNo;
	$('#witnessInputId').show();
	if (type == "V") {
		$('.addWitBT').hide()
	} else {
		$('.addWitBT').show();
	}
	prepareDateTag();
}

function getChecklistAndCharges(element) {
	var errorList = [];
	// errorList = validateWitnessDetails(errorList);
	//D#108556 here compare noOfWitness at least with NOW prefix no
	let witnessCount = $('#witnessTable tr').length-1;
	let noOfWitness = $('#noOfWitness').val();
	if(noOfWitness > witnessCount ){
		//error at least noOfWitness required
		//errorList.push(getLocalMessage("At least "+noOfWitness+" witness required"));
		if($('#langId').val() == 1){
			
		}else{
			noOfWitness=translateInNumber(noOfWitness,"marathi");
		}
		errorList.push(getLocalMessage("mrm.vldnn.witnessStart") + noOfWitness  + " "+ getLocalMessage("mrm.vldnn.witnessEnd") );
	}
	if (errorList.length == 0) {
		var divName = '#witnessDet';
		var URL = 'MarriageRegistration.html?getChecklistAndCharges';
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var returnData = __doAjaxRequest(URL, 'Post', requestData, false,
				'html');

		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		prepareTags();
		$("#reset").hide();
	} else {
		displayErrorsOnPage(errorList);
	}

}

function saveWitness(element) {
	var errorList = [];
	// errorList = validateWitnessDetails(errorList);
	//D#113931
	let witnessCount = $('#witnessTable tr').length-1;
	let noOfWitness = $('#noOfWitness').val();
	if(noOfWitness > witnessCount ){
		//error at least noOfWitness required
		//errorList.push(getLocalMessage("At least "+noOfWitness+" witness required"));
		if($('#langId').val() == 1){
			
		}else{
			noOfWitness=translateInNumber(noOfWitness,"marathi");
		}
		errorList.push(getLocalMessage("mrm.vldnn.witnessStart") + noOfWitness  + " "+ getLocalMessage("mrm.vldnn.witnessEnd") );
		
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErrAstInfo(errorList);
	} else {
		$.fancybox.close();
		var divName = '.content-page';
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		var object = __doAjaxRequest(
				'MarriageRegistration.html?saveWitnessPage', 'POST',
				requestData, false, 'json');

		if (object.error != null && object.error != 0) {
			$.each(object.error, function(key, value) {
				$.each(value, function(key, value) { 
					if (value != null && value != '') {
						errorList.push(value);
					}
				});
			});
			// displayErrorsOnPage(errorList);
			showErrAstInfo(errorList);
		} else {
			if (object.Success != null && object.Success != undefined ) {
				//showBoxForApproval(getLocalMessage("Your application No." + " "+ object.applicationId + " "+ "has been submitted successfully."));
				showBoxForApproval(object.Success);
				// print acknowledgement
				marriageRegAcknow();

			}
			
		}

	}

}

function showBoxForApproval(succesMessage) {

	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed = getLocalMessage("mrm.proceed");
	var no = 'No';
	message += '<p class="text-blue-2 text-center padding-15">' + succesMessage
			+ '</p>';

	var payableFlag = $("#payableFlagAppl").val();
	if (payableFlag == 'Y') {
		var redirectUrl = 'MarriageRegistration.html?PrintReport';
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input type=\'button\' value=\'' + 'Proceed'
				+ '\'  id=\'btnNo\' class=\'btn btn-success\'    '
				+ ' onclick="closebox(\'' + errMsgDiv + '\',\'' + redirectUrl
				+ '\')"/>' + '</div>';

	} else {
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
				+ Proceed + '\'  id=\'Proceed\' '
				+ ' onclick="closeApproval();"/>' + '</div>';

	}

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

function marriageRegAcknow(element) {debugger;
	var URL = 'MarriageRegistration.html?printMarriageRegAckw';
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);

	/*var title = 'Marriage Registration Acknowlegement';*/
		var appId = $($.parseHTML(returnData)).find("#applicationId").html();

	var title = appId;

	prepareTags();
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
			.write('<link href="assets/css/print.css" media="print" rel="stylesheet" type="text/css"/>')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery-1.10.2.min.js"></script>')
	printWindow.document
			.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}

function validateWitnessDetails(errorList) {
	let firstNameE = $("#witnessTabForm input[id=firstNameEng]").val();
	let firstNameR = $("#witnessTabForm input[id=firstNameReg]").val();
	let lastNameE = $("#witnessTabForm input[id=lastNameEng]").val();
	let lastNameR = $("#witnessTabForm input[id=lastNameReg]").val();
	let witnessAddrE = $("#witnessTabForm textarea[id=fullAddrEng]").val();
	let witnessAddrR = $("#witnessTabForm textarea[id=fullAddrReg]").val();
	let occupation = $("#witnessTabForm select[id=occupation]").find(":selected").val();
	//let relation = $("#witnessTabForm input[id=relation]").val();
	let relation = $("#witnessTabForm select[id=relation]").find(":selected").val();
	let uid = $("#witnessTabForm input[id=uidNo]").val();
	let hUid = $("#husbandFormId").find("#uidNo").val();
	let wifeid = $("#wifeFormId").find("#uidNo").val();

	if (firstNameE == "" || firstNameE == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.witnessFirstNameE'));
	}
	if (firstNameR == '' || firstNameR == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.witnessFirstNameR'));
	}
	if (lastNameE == "" || lastNameE == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.witnessLastNameE'));
	}
	if (lastNameR == "" || lastNameR == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.witnessLastNameR'));
	}
	if (witnessAddrE == "" || witnessAddrE == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.witnessFullAddrE'));
	}
	if (witnessAddrR == "" || witnessAddrR == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.witnessFullAddrR'));
	}
	if (occupation == "" || occupation == "0" || occupation == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.witnessOccupation'));
	}
	
	if (relation == "" || relation == "0" || relation == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.witnessRelation'));
	}
	if (uid == "" || uid == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.witnessUID'));
	}
	else if(uid.length < 12) {
		errorList.push(getLocalMessage('mrm.vldnn.witnessUID.len'));
	}
    if(hUid === uid || wifeid == uid) {
		errorList.push(getLocalMessage("mrm.duplicate.UI"));
	}
	
	// D#129241 check here duplicate AADHAR NO
	$("tr.witnessList").each(function(i) {
		let rowCount=i+1;
        var witUID=$("#witUID_"+rowCount).text();
        //make rowNumber global variable D#130755
        if(witUID == uid && rowNumber!= rowCount){
        	errorList.push(getLocalMessage('mrm.witness.duplicateUI'));
        }
	});
	
	//validate specify relation based on other
	let relationCode=$('#relation').find(':selected').attr('code');
	if(relationCode=="OT" && $('#otherRel').val()==""){
		//enable the input box 
		errorList.push(getLocalMessage('mrm.valid.specifyRel'));
	}
	
	
	return errorList;
}

function saveMarriageCertificateAfterEdit(element) {
	/*return saveOrUpdateForm(element, "Application Approved Succesfully!",
		'AdminHome.html', 'saveAfterMarriageCertificate');*/
	
	var requestData = element;
	var divName = '.content-page'
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var ajaxResponse = __doAjaxRequest('MarriageRegistration.html?saveMarriageCertificateAfterEdit', 'POST',
			requestData, false, '', 'html');
	
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();



}


//function for convert in regional LANG.

function translateInNumber(input, target) {
  var systems = {
          devanagari: 2406, tamil: 3046, marathi: 2406,  gujarati: 2790
      },
      zero = 48, // ASCII code for  zero
      nine = 57, // ASCII code for  nine
      offset = (systems[target.toLowerCase()] || zero) - zero,
      output = input.toString().split(""),
      i, l = output.length, cc;

  for (i = 0; i < l; i++) {
      cc = output[i].charCodeAt(0);
      if (cc >= zero && cc <= nine) {
          output[i] = String.fromCharCode(cc + offset);
      }
  }
  return output.join("");
}


function checkRelation(obj){
	let code=$(obj).find(':selected').attr('code');
	if(code=="OT"){
		//enable the input box 
		$('.specifyRel').show();
	}else{
		$('.specifyRel').hide();
		$('#otherRel').val("");
	}
}      
