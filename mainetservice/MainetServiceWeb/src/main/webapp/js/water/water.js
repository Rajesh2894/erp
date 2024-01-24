var contactNoConsumer;
var uidNo;
var roadName;
var ownerTitle;
var flatType;

var kdmcPropertyType;
var kdmcPropertyBalance;
var housetTypeCode="";

function getWardData(val, param) {
	var data = {

		"ward" : $("#divid").val()
	};

	var URL = 'WaterConnectionHome.html?' + param;

	var ajaxResponse2 = __doAjaxRequest(URL, 'POST', data, false, 'json');

	$('#ward').html("");
	$('#ward')
	.append($("<option></option>")
	.attr("value",0)
	.text(getLocalMessage("--Select--")));

	var temp = [];
	$.each(ajaxResponse2, function(key, value) {
	   temp.push({v:value, k: key});
	});
	temp = temp.sort(function (a,b) {
	  return a.v.toLowerCase()  > b.v.toLowerCase() ? 1 : a.v.toLowerCase() < b.v.toLowerCase() ? -1 : 0; 

	});
	$.each(temp, function(key, obj) {
		 
		 if(obj.k     != '0'){
		 $('#ward')
		      .append($("<option></option>")
		    	       .attr("value", obj.k).text(obj.v));
		
		 }});
}
 
	 
	 
function getZoneData(val, param) {

	var data = {

		"zone" : $("#ward").val()
	};

	var URL = 'WaterConnectionHome.html?' + param;

	var ajaxResponse2 = __doAjaxRequest(URL, 'POST', data, false, 'json');

	$('#zone').html("");
	$('#zone').append(
			$("<option></option>").attr("value", 0).text("--Select--"));

	$.each(ajaxResponse2, function(key, value) {

		$('#zone').append(
				$("<option></option>").attr("value", value).text(value));

	});
}

//maha
function getZoneDataM(val, param) {

	var data = {

		"zoneM" : $("#wardM").val()
	};

	var URL = 'WaterConnectionHome.html?' + param;

	var ajaxResponse2 = __doAjaxRequest(URL, 'POST', data, false, 'json');

	$('#zoneM').html("");
	$('#zoneM').append(
			$("<option></option>").attr("value", 0).text("--Select--"));

	$.each(ajaxResponse2, function(key, value) {

		$('#zoneM').append(
				$("<option></option>").attr("value", key).text(value));

	});
}


function getPrimiseType() {
	var data = {

		"trafficId" : $("#tariffCatK").val(),
		"serviceType" : $("#getCheckList").val()
		
	};

	var URL = 'WaterConnectionHome.html?' + 'getPrimiseType';

	var ajaxResponse2 = __doAjaxRequest(URL, 'POST', data, false, 'json');

	$('#tariffSubCat').html("");
	$('#tariffSubCat').append(
			$("<option></option>").attr("value", 0).text("--Select--"));

	$.each(ajaxResponse2, function(key, value) {

		$('#tariffSubCat').append(
				$("<option></option>").attr("value", key).text(value));

	});
	
	if($("#tariffCatK").find("option:selected").attr('code')=='C'){
		
		$('#dto\\.noOfFamilyK').val(0);
		$('#dto\\.noOfUserK').val(0);
		
	}else{
		$('#dto\\.noOfFamilyK').val('');
		$('#dto\\.noOfUserK').val('');
	}
}

function premiseTypeForKD1(val, param) {
	var data = {

		"tariffSubCatprimises" : $("#tariffSubCat").val()
		
	};

	var URL = 'WaterConnectionHome.html?' + param;

	var ajaxResponse2 = __doAjaxRequest(URL, 'POST', data, false, 'json');

	$('#premiseTypeForKD').html("");
	$('#premiseTypeForKD').append(
			$("<option></option>").attr("value", 0).text("--Select--"));

	$.each(ajaxResponse2, function(key, value) {

		$('#premiseTypeForKD').append(
				$("<option></option>").attr("value", key).text(value));

	});
	
	
}



function getPropertyDetils(propertyNo) {
	
	$("#errorDivId").hide();
	$('.error-div').hide();
	$("#alertMsgBox").hide();
	
	$("#dto\\.csCcDate").val("");
	$("#ccDateId").hide();

	$("#ccReasonKdmcId").val("");
	$("#ccReasonId").hide();

	$("#ccSApprovedById").val(0);
	$("#approvedById").hide();
	
	var errorList = [];
	
	if($.trim(propertyNo) == ""){
		return false;		
	}
	
	if($("#getCheckList").val() == "0"){
		
		errorList.push(getLocalMessage("water.validn.serviceSubtype"));
		showWaterError(errorList);
		$("#propertyNo").val("");
		return false;
	}
	
	var ccaprroved=null;
	var ccDate=null;
	var ccReason=null;
	
	if($("#dto.dateK").val()!=undefined)
		ccDate=$("#dto.dateK").val();

	var data = {

		"propertyNo" : $("#propertyNo").val(),
		"ccApproved" : null,
		"ccDate" :  ccDate,
		"ccReason" : null
		
	};

	var URL = 'WaterConnectionHome.html?' + 'getPropertyNo';

	var returnData1 = __doAjaxRequest(URL, 'POST', data, false);
	
	if(returnData1 == "exception"){
		
		$("#propertyNo").val("");
		var URL = $("form").attr('action');	            
        _openPopUpForm(URL,"showErrorPage");		
		
		throw "error in ajax response";	
		
	}
	
	var errorMsg = returnData1.errorMsg;//returnData1.split("@@#")[0];
	var balanceMsg = returnData1.balanceMsg;//returnData1.split("@@#")[1];
	var aletMsg = returnData1.alertMsg;//returnData1.split("@@#")[2];
	var firstName = returnData1.firstName;//returnData1.split("@@#")[3];
	var middleName = returnData1.middleName;//returnData1.split("@@#")[4];
	var lastName = returnData1.lastName;//returnData1.split("@@#")[5];
	var houseNo = returnData1.houseNo;//returnData1.split("@@#")[6];
	var buildingName = returnData1.buildingName;//returnData1.split("@@#")[7];
	kdmcPropertyBalance = returnData1.propertyBalance;//returnData1.split("@@#")[8];
	kdmcPropertyType = returnData1.propertyType;//returnData1.split("@@#")[9];
	var housingComplxn = returnData1.housimgComplxn;//returnData1.split("@@#")[10];
	var roadName = returnData1.roadname;//returnData1.split("@@#")[11];
	var lanArea = returnData1.lanArea;//returnData1.split("@@#")[12];
	var city = returnData1.city;//returnData1.split("@@#")[13];
	
	if(errorMsg == "noError"){
		
		if(aletMsg != "noAlert"){
			// show proper alert msg
			$("#alertMsgBox").val(aletMsg);
			$("#alertMsgBox").show();
			
		}
		
		if(balanceMsg != "noBalMsg"){			
			// show proper balance msg
			balConfirm(balanceMsg, returnData1);
			
		}
		if(balanceMsg == "noBalMsg"){
			if(firstName == "null" || firstName == "noFirstName"){
				$("#firstNamepo").val("");
			}else{
				$("#firstNamepo").val(firstName);
			}
			
			if(middleName == "null" || middleName == "noMiddleName"){
				$("#fatherOrHusbandNamepo").val("");
			}else{
				$("#fatherOrHusbandNamepo").val(middleName);
			}
			
			if(lastName == "null" || lastName == "noLastName"){
				$("#lastNamepo").val("");
			}else{
				$("#lastNamepo").val(lastName);
			}
			
			/*if(houseNo == "null" || houseNo == "noHouseNo"){
				$("#pOhouseNoM").val("");
			}else{
				$("#pOhouseNoM").val(houseNo);
			}*/
			
			if(buildingName == "null" || buildingName == "noBuildingName"){
				$("#pObuldingNameM").val("");
			}else{
				$("#pObuldingNameM").val(buildingName);
			}
			if(housingComplxn == "null" || housingComplxn == "noHousingComplxn"){
				$("#pOhouseNoM").val("");
			}else{
				$("#pOhouseNoM").val(housingComplxn);
			}
			if(roadName == "null" || roadName == "noRoadName"){
				$("#pOroadNameM").val("");
			}else{
				$("#pOroadNameM").val(roadName);
			}
			if(lanArea == "null" || lanArea == "noLanArea"){
				$("#pOLanArea").val("");
			}else{
				$("#pOLanArea").val(lanArea);
			}
			if(city == "null" || city == "noCityName"){
				$("#pOcityName").val("");
			}else{
				$("#pOcityName").val(city);
			}
		}
		
		
	}else{
		
		// show error msg
		$("#errorDivId").hide();
		$('.error-div').hide();
		
		errorList.push(errorMsg);
		showWaterError(errorList);
		$("#propertyNo").val("");
		
	}	

}

/*function getPropertyDetilsforMh(propertyNo) {
	
	if($.trim(propertyNo) == ""){
		return false;		
	}
	
	var data = {

		"propertyNo" : $("#propertyNoMH").val()
		
	};

	var URL = 'WaterConnectionHome.html?' + 'getPropertyNoMH';

	var returnData1 = __doAjaxRequest(URL, 'POST', data, false);
	
	var new1 = returnData1.substring(0, returnData1.length - 1);

	var abc = returnData1;
	abc = returnData1.substring(0, 0);
	namesArray=returnData1.substring(0, 1 - 1)
	+ returnData1.substring(1, returnData1.length);
	
	namesArray=namesArray.substring(0, namesArray.length - 2);
	
	var namesArray = namesArray.split(",");
	var item, items = [];
	item = {};
	
	item.returnData1 = namesArray[0];
	
	$("#pDTitle").val(item.returnData1)
	
	item.returnData1 = namesArray[1];
	
	$("#firstNamepo").val(item.returnData1)

	item.returnData1 = namesArray[2];
	$("#fatherOrHusbandNamepo").val(item.returnData1);

	item.returnData1 = namesArray[3];
	$("#lastNamepo").val(item.returnData1);

	item.returnData1 = namesArray[4];
	$("#pObuldingNameM").val(item.returnData1);
	
	item.returnData1 = namesArray[10];
	flatType=item.returnData1;
	alert(flatType);
	if($.trim(flatType)=="I")
	$("#flatMvald").addClass("mandClassColor mand");
	
	if(item.returnData1==" W"){
		
		$("#flatNoM").hide();
	}
	else{
		$("#flatNoM").show();
	}
	$("#flatPrstnId").val(namesArray[16]);
}*/

function getFlatValid(){
	
	if($("#flatMvald").val()=="" || $("#flatPrstnId").val()=='')
		return false;
	
	var data = {
			"propertyNo" : $("#flatMvald").val(),
			"flatPrstnId" : $("#flatPrstnId").val()
		};
	
	var URL = 'WaterConnectionHome.html?' + 'getFlatNoValMH';

	var returnData1 = __doAjaxRequest(URL, 'POST', data, false);
	
	var errMsg = returnData1.split(",");
	
	var errorList=[];
	if(errMsg[0]=="-1"){
		
		$('.error-div').hide();
		var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" align="right" onclick="closeErrBox()" width="32"/></div><ul><li><i class="fa fa-exclamation-circle"></i> &nbsp;'+errMsg[1]+'</li></ul>';
		$(".error-div").html(errMsg);
		$('.error-div').show();
		$("html, body").animate({ scrollTop: 0 }, "slow");

		return false;
		
		$("#flatMvald").val("");
		$("#flatPrstnId").val("");		
		
	}else if(errMsg[0]=="exception"){
		
		$("#flatMvald").val("");
		$("#flatPrstnId").val("");
		
		var URL = $("form").attr('action');	            
        _openPopUpForm(URL,"showErrorPage");		
		
		throw "error in ajax response";	
		
	}else{
		$(".error-div").html('');
		$('.error-div').hide();
	}
	
}

$(document).ready(function(){

	/*$("#cOTitle").val("0");
	$("#pDTitle").val("0");*/

	
	$("#mandProp").hide();
	if($("#codCcgId1").val()==0)
	$("#connectionTypeM").val("0");
	$("#sizeService").hide();
	$("#dateService").hide();
	$("#ServiceSlum").hide();
	
	
	   // document.getElementById('testNameHidden').disabled = true;
	
	$('#hasSameAdd').click(function(){
		
		if($("#propertyNoMH").val()==""){
		var errMsg ;
		
		var propValidMsg = getLocalMessage("water.propNo.errMsg");
		var errorList=[];
			$('.error-div').hide();
			var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" align="right" onclick="closeErrBox()" width="32"/></div><ul><li><i class="fa fa-exclamation-circle"></i> &nbsp;'+propValidMsg+'</li></ul>';
			$(".error-div").html(errMsg);
			$('.error-div').show();
			$("html, body").animate({ scrollTop: 0 }, "slow");

			return false;
		}
		
		
		if(document.getElementById("hasSameAdd").checked){
		
			
			
		if($("#propertyNoMH").val()!=""){
			
			$("#cOTitle").val(ownerTitle);
			
			$("#firstNameco").val($("#firstNamepo").val());

			//$("#fatherOrHusbandNameco").val($("#fatherOrHusbandName").val());
			$('.fatherOrHusbandNameco').val($('.middleName').val());
			$('.orgNameConsumer').val($('.pOorgnameM').val());
			$('.cOcontactNo').val(contactNoConsumer);
			$('.cOconsumerAdharNoM').val(uidNo);
			$('.cORoadNameM').val(roadName);
			
			$("#lastNameco").val($("#lastNamepo").val());

			$("#cOBulidNameM").val($("#pObuldingNameM").val());
			
			$("#csaddM").val($("#csOaddM").val());
			$("#cOHouseNo").val($("#pOhouseNoM").val());
			
		}
	}else{
		
		$("#cOTitle").val('0');
		
		$('.orgNameConsumer').val('');
		$("#firstNameco").val("");
		$('.cOcontactNo').val('');
		$('.cOconsumerAdharNoM').val('');
		$('.cORoadNameM').val('');
		$("#fatherOrHusbandNameco").val("");

		$("#lastNameco").val("");

		$("#cOBulidNameM").val("");
		
		// for billing address
		$('#hasSameAddBA').prop('checked',false);
		$("#bABulidName").val("");
		$(".bARoadNameM").val('');
		$(".bAcontactNoM").val('');
		$("#csBaddM").val('');
		$("#bAorgNameM").val('');
		$("#bAHouseNoM").val('');
		
		$("#bABulidName").removeClass("disablefield").attr("readonly", false);
		$(".bARoadNameM").removeClass("disablefield").attr("readonly", false);
		$(".bAcontactNoM").removeClass("disablefield").attr("readonly", false);
		$("#csBaddM").removeClass("disablefield").attr("readonly", false);
		$("#bAorgNameM").removeClass("disablefield").attr("readonly", false);
		$("#bAHouseNoM").removeClass("disablefield").attr("readonly", false);
		
		$("#copyToBillingAddressId").val("N");		
		
	}
		});
	
	
	$('#hasSameAddBA').click(function(){	
		
		if(document.getElementById("hasSameAddBA").checked && (document.getElementById("hasSameAdd").checked && $("#csaddM").val() =="")){
			
				var propValidMsg = getLocalMessage("water.enter.connOwner.address");
			
				$('.error-div').hide();
				var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" align="right" onclick="closeErrBox()" width="32"/></div><ul><li><i class="fa fa-exclamation-circle"></i> &nbsp;'+propValidMsg+'</li></ul>';
				$(".error-div").html(errMsg);
				$('.error-div').show();
				$("html, body").animate({ scrollTop: 0 }, "slow");

				return false;
			
		}
		
		if(document.getElementById("hasSameAddBA").checked && (document.getElementById("hasSameAdd").checked || $("#csaddM").val()!="")){

			$("#bABulidName").val($("#pObuldingNameM").val());
			$(".bARoadNameM").val($(".cORoadNameM").val());
			$(".bAcontactNoM").val(contactNoConsumer);
			
			$("#bABulidName").val($("#cOBulidNameM").val());	
			$("#csBaddM").val($("#csaddM").val());	
			
			$("#bAHouseNoM").val($("#cOHouseNo").val());
			
			$("#bABulidName").addClass("disablefield").attr("readonly", true);
			$(".bARoadNameM").addClass("disablefield").attr("readonly", true);
			$(".bAcontactNoM").addClass("disablefield").attr("readonly", true);
			$("#csBaddM").addClass("disablefield").attr("readonly", true);
			$("#bAorgNameM").addClass("disablefield").attr("readonly", true);
			$("#bAHouseNoM").addClass("disablefield").attr("readonly", true);
			
			$("#copyToBillingAddressId").val("Y");
		
	}else if(document.getElementById("hasSameAddBA").checked &&  $("#csaddM").val() ==""){
		
		var propValidMsg = getLocalMessage("water.enter.connOwner.address");
		
		$('.error-div').hide();
		var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" align="right" onclick="closeErrBox()" width="32"/></div><ul><li><i class="fa fa-exclamation-circle"></i> &nbsp;'+propValidMsg+'</li></ul>';
		$(".error-div").html(errMsg);
		$('.error-div').show();
		$("html, body").animate({ scrollTop: 0 }, "slow");

		return false;
	
	}else{

		$('#hasSameAddBA').prop('checked',false);
		$("#bABulidName").val("");
		$(".bARoadNameM").val('');
		$(".bAcontactNoM").val('');
		$("#csBaddM").val('');
		$("#bAorgNameM").val('');
		$("#bAHouseNoM").val('');
		
		$("#bABulidName").removeClass("disablefield").attr("readonly", false);
		$(".bARoadNameM").removeClass("disablefield").attr("readonly", false);
		$(".bAcontactNoM").removeClass("disablefield").attr("readonly", false);
		$("#csBaddM").removeClass("disablefield").attr("readonly", false);
		$("#bAorgNameM").removeClass("disablefield").attr("readonly", false);
		$("#bAHouseNoM").removeClass("disablefield").attr("readonly", false);
		
		$("#copyToBillingAddressId").val("N");
		
	}
		
		/*if(document.getElementById("hasSameAddBA").checked &&  $("#csaddM").val() ==""){
			
			var propValidMsg = getLocalMessage("Enter Connection Owner Address");
		
			$('.error-div').hide();
			var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/></div><ul><li><i class="fa fa-exclamation-circle"></i> &nbsp;'+propValidMsg+'</li></ul>';
			$(".error-div").html(errMsg);
			$('.error-div').show();
			$("html, body").animate({ scrollTop: 0 }, "slow");

			return false;
		
		}*/
		
		});
	
});
//function premiseTypeForMH(val, param) {
	$(document).ready(function(){
	$('#premiseTypeForMH1').click(function(){
	var Type1=null;
	var Type2=null;
	var Type3=null;
	var Type4=null;
	var Type5=null;
	
	if($("#codTrfId"+1).val() != 0 && $("#codTrfId"+1).val() != undefined ) {
		var Type1=$('#codTrfId1').val();
	}
	
	if ($("#codTrfId"+2).val() != 0 && $("#codTrfId"+2).val() != undefined ) {
		var Type2=$('#codIdAmt2').val();
	}
	
	if ($("#codTrfId"+3).val() != 0 && $("#codTrfId"+3).val() != undefined ) {
		var Type3=$('#codIdAmt3').val();
	}
	
	if ($("#codTrfId"+4).val() != 0 && $("#codTrfId"+4).val() != undefined ) {
		var Type4=$('#codIdAmt4').val();		
	}
	
	if ($("#codTrfId"+5).val() != 0 && $("#codTrfId"+5).val() != undefined ) {
		var Type5=$('#codTrfId').val();
	}

		var data = {
			"codTrfId1" :Type1,
			"codTrfId2" :Type2,
			"codTrfId3" :Type3,
			"codTrfId4" :Type4,
			"codTrfId5" :Type5,
		};
	
	var URL = 'WaterConnectionHome.html?' + "premiseTypeForMH";

	var ajaxResponse2 = __doAjaxRequest(URL, 'POST', data, false, 'json');

	$('#premiseTypeForMH').html("");
	$('#zone').append(
			$("<option></option>").attr("value", 0).text("--Select--"));

	$.each(ajaxResponse2, function(key, value) {

		$('#premiseTypeForMH').append(
				$("<option></option>").attr("value", key).text(value));

	});
	
	});
	});
//}

var cnt = 1;
var arr =[];
$(document).ready(function(){
$("#advPayScheduleTab").on(
		"click", '.addButton',
		function(e) {
			var errorList = [];	
			 $('.appendableClass').each(function(i) {
				row = i;
				 var lcOldccn = $.trim($("#lcOldccn"+i).val());
				
				var lcOldccnsize = $.trim($("#lcOldccnsize"+i).val());
				
				var lcOldtaps = $.trim($("#lcOldtaps"+i).val());
				
				 var csIdn = $.trim($("#csIdn"+i).val());
				
				 if( lcOldccn == ""){
					
					errorList.push(getLocalMessage('water.connEmptyErr'));	
					
				}
				 /* if(lcOldccnsize ==""){
					
					errorList.push(getLocalMessage('connection Size No must not be empty'));	
					
				} */
				 /* if(lcOldtaps==""){
					
					errorList.push(getLocalMessage('No of Taps No must not be empty'));
				} */ 

				
			}); 
			
			if(errorList.length > 0){
				
				showWaterError(errorList);
				
				errorList = [];
				arr=[];
				return false;
			} 
			arr=[];
			
			e.preventDefault();
				
				$(".datepicker").datepicker("destroy");
				
				var content = $(this).closest('tr').clone();
				
				$(this).closest("tr").after(content);		
				
				
				content.find("input:text:eq(0)").attr("value", "");
				content.find("input:text:eq(1)").attr("value", "");
				content.find("input:text:eq(2)").attr("value", "");
				
				
				content.find("input:text:eq(0)").attr("id",
						"lcOldccn" + (row + 1)); 
				
				content.find("input:text:eq(0)").attr("onblur",
						"prCheckConnection(this.value,"+ (row + 1)+")");
				
			 	content.find("input:text:eq(1)").attr("id",
						"linkDetails" + (row + 1));
			 	
			 	content.find("input:text:eq(2)").attr("id",
						"lcOldtaps" + (row + 1));
			 	
			 	content.find("input:hidden:eq(0)").attr("id",
						"csIdn" + (row + 1));
			 	
			 	
			 	content.find('.delButton').attr("id", "delButton"+ (row+1));
			 	content.find('.addButton').attr("id", "addButton"+ (row+1));
			 	
				
			    content.find("input:text:eq(0)").attr(
								"name",
								"linkDetails[" + (row + 1)
										+ "].lcOldccn");
					
				content.find("input:text:eq(1)").attr(
						"name",
						"linkDetails[" + (row + 1)
								+ "].lcOldccnsize");	
				
				content.find("input:text:eq(2)").attr(
						"name",
						"linkDetails[" + (row + 1)
								+ "].lcOldtaps");
				
				content.find("input:hidden:eq(0)").attr(
						"name",
						"linkDetails[" + (row + 1)
								+ "].csIdn").attr("value", "");
				 
				reOrderTableIdSequence();
			
				
		});


$("#advPayScheduleTab").on("click", '.delButton', function(e) {
	
	var totalTaxAmt = 0;
	var countRows = -1;
	var delId = -1;
	$('.appendableClass').each(function(i) {
		
		if ( $(this).closest('tr').is(':visible') ) {
				countRows = countRows + 1;			
		}
		
		
	});
	
	row = countRows;
	
	if (row != 0) {
		
		$(this).parent().parent().remove();
		
		row--;
		reOrderTableIdSequence();
		
	}
	e.preventDefault();
});	


function reOrderTableIdSequence() {

	
	$('.appendableClass').each(function(i) {
		
		$(this).find("input:text:eq(0)").attr("id", "lcOldccn"+ i);
		$(this).find("input:text:eq(1)").attr("id", "lcOldccnsize" + i);
		$(this).find("input:text:eq(2)").attr("id", "lcOldtaps" + i);
		$(this).find("input:hidden:eq(0)").attr("id", "csIdn" + i);
		
		$(this).find("input:button:eq(4)").attr("id", "delButton"+ i);
		
		$(this).parents('tr').find('.delButton').attr("id", "delButton"+ i);
		$(this).parents('tr').find('.addButton').attr("id", "addButton"+ i);
		
		$(this).find("input:text:eq(0)").attr("name", "linkDetails[" + i + "].lcOldccn");
		$(this).find("input:text:eq(1)").attr("name", "linkDetails[" + i	+ "].lcOldccnsize");
		$(this).find("input:text:eq(2)").attr("name", "linkDetails[" + i	+ "].lcOldtaps");
		$(this).find("input:hidden:eq(0)").attr("name", "linkDetails[" + i	+ "].csIdn");
		
		$(this).find("input:text:eq(0)").attr("onblur",
				"prCheckConnection(this.value,"+ i +")");
		
		
	});
	
}


//for Maharashtra after server side error 

	if($("#dataBaseType").val()=="M"){
		
		if($("#hasSameAdd").val()=="Y"){
			
			$('#hasSameAdd').prop('checked',true);
		
		}
	
	
	}





});


function saveForm(element) {
	
	var errorList = [];
	/* if ($.trim(marriageYear) == "" && $.trim(marriageRegNo) == "") {
		errorList.push(getLocalMessage("marriage.form.year.not.empty"));// getLocalMessage('adv.agencycontract.pleaseselecttax.error')
		errorList.push("<li></li> ");
		errorList.push(getLocalMessage("marriage.form.registration.number.not.empty"));
		$(".error-div").html(errorList);
		$("#errorDivId").show(); */
	
	if($("#dataBaseType").val()=="K"){
		
		if($.trim($("#applicantTitle").val()) == "0"){
			errorList.push(getLocalMessage("water.validation.ApplicantNameTitle"));	
		}
		
		if($.trim($("#firstName").val()) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantFirstName"));	
		}
		
		if($.trim($("#lastName").val()) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantLastName"));		
		}
		if($.trim($("#gender").val()) == "0" || $.trim($("#gender").val()) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantGender"));	
		}
		
		if($.trim($("#buildingName").val()) == ""){
			
			errorList.push(getLocalMessage('water.validation.ApplicantBuldingName'));		
		}
			
		if($.trim($("#blockName").val()) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantBlockName"));	
		}	
		
		if($.trim($("#villageTownSub").val()) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantcityVill"));
		}
		/*
		if($.trim($("#pinCode").val()) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantnterPincode"));	
		}
		
		if($.trim(isNaN($("#pinCode").val())) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantInvalidPincode"));	
		}
		
		if($.trim($("#emailId").val()) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantEmail"));	
		}*/
		
		/*if($.trim($("#mobileNo").val()) == ""){
			errorList.push(getLocalMessage("water.validation.Applicantentermobileno"));	
		}
		
		if($.trim(isNaN($("mobileNo").val())) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantInvalidmobile"));	
		}
		*/
		
		
		var mobileNo=$("#mobileNo").val();
		if($.trim($("#mobileNo").val()) == ""){
			errorList.push(getLocalMessage("water.validation.Applicantentermobileno"));	
		}
		
		else if($.trim(isNaN($("mobileNo").val())) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantInvalidmobile"));	
		}
		else if(mobileNo.length<=10){
			 var count=0;
			if(mobileNo.length<10){
				if(mobileNo.length==""){
					errorList.push(getLocalMessage("water.validation.ApplicantInvalidmobile"));
				}else{
					errorList.push(getLocalMessage("water.validation.ApplicantInvalidmobile"));
				}
			}
			
			for(var i=0; i<mobileNo.length; i++){
				
				var eachMobileNo=mobileNo.charAt(i);
				if(eachMobileNo==0){
					count++;
				}
			}
		}
		
		var pinCode=$("#pinCode").val();
		if(pinCode.length<=6){
			 var count=0;
			if(pinCode.length<6){
				if(pinCode.length==""){
					errorList.push(getLocalMessage("water.validation.ApplicantnterPincode"));
				}else{
					errorList.push(getLocalMessage("citizen.login.reg.pincode.length.error"));
				}
			}
			
			for(var i=0; i<pinCode.length; i++){
				
				var eachPinCode=pinCode.charAt(i);
				if(eachPinCode==0){
					count++;
				}
			}
		}
		
		
		if($.trim($("#emailId").val()) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantEmail"));	
		}else{
		
		var x = $.trim($("#emailId").val());
	    var atpos = x.indexOf("@");
	    var dotpos = x.lastIndexOf(".");
	    if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length) {
	    	errorList.push(getLocalMessage("emailId.invalid"));	
	    }
		}
		
		
		if($.trim($("#getCheckList").val()) == "0"){
			errorList.push(getLocalMessage("water.validn.serviceSubtype"));	
		}
		
		if($.trim($("#divid").val()) == "0"){
			errorList.push(getLocalMessage("water.validn.selectDivision"));	
		}
		
		if($.trim($("#ward").val()) == "0"){
			errorList.push(getLocalMessage("water.validn.selectWard"));	
		}
		
		/* if($.trim($("zone").val()) == "0"){
			errorList.push("Please select Zone");		
		} */
		
		/* if($.trim($("#pDTitle").val()) == "0"){
			errorList.push("Please select Property Owner Title");	
			errorList.push("<li></li> ");
		} */
		
		/* if($.trim($("#firstNamepo").val()) == ""){
			errorList.push("Please enter Property Owner First Name");
			errorList.push("<li></li> ");
		} */
		
		/* if($.trim($("#lastNamepo").val()) == ""){
			errorList.push("Please enter Property Owner Last Name");	
			errorList.push("<li></li> ");
		} */
		
		var serviceSubType = $("#getCheckList").find("option:selected").attr('code');
		if(serviceSubType=="NRUN")
		if(serviceSubType !="" && serviceSubType=="NRUN" && housetTypeCode==""){
			errorList.push(getLocalMessage("water.houseTypevValidn"));
		}else if(housetTypeCode!="" && housetTypeCode!="Hut" && housetTypeCode!="Slum" && $.trim($("#propertyNo").val())==""){
				errorList.push(getLocalMessage("water.propNoValidn"));	
			}
		
		/*if(housetTypeCode!="" && housetTypeCode!="Hut" && housetTypeCode!="Slum" && $.trim($("#propertyNo").val())==""){
			errorList.push(getLocalMessage("water.propNoValidn"));	
		}*/
		
		var pinCode=$.trim($("#pinCodeKDMC").val());
		
		/*if($.trim($("#pinCodeKDMC").val()) == ""){
			errorList.push(getLocalMessage("water.picodeValidtn"));	
		}
		else */
		if($.trim($("#pinCodeKDMC").val()) != ""){
			if($.trim($("#pinCodeKDMC").val()) == 0){
				errorList.push(getLocalMessage("water.invalidPincode"));	
			}
			else if(pinCode.length<6){	
					
						errorList.push(getLocalMessage("citizen.login.reg.pincode.length.error"));
				}
		}
		
		
		if($.trim($("#cOTitle").val()) == "0"){
			errorList.push(getLocalMessage("water.connOwner.titleErr"));	
		} 
		
		if($.trim($("#firstNameco").val()) == ""){
			errorList.push(getLocalMessage("water.connOwner.firstNameErr"));	
		}
		
		/*if($.trim($("#lastNameco").val()) == ""){
			errorList.push(getLocalMessage("water.connOwner.lastNameErr"));		
			errorList.push("<li></li> ");
		}*/
		
		if($.trim($("#cityK").val()) == "0"){
			errorList.push(getLocalMessage("water.selectCity.validn"));
		}
		
		if($.trim($("#talukaK").val()) == "0"){
			errorList.push(getLocalMessage("water.selectTaluka.validn"));	
		}
		
		if($.trim($("#districtK").val()) == "0"){
			errorList.push(getLocalMessage("water.selectDistrict.validn"));	
		}
		
		if(serviceSubType!="NRUN" &&($("#houseTypeHidden").val() == "0" ||$("#houseTypeHidden").val()=="")){
			
			errorList.push(getLocalMessage("water.selectHouse.validn"));	
		}
		
		
		if($.trim($("#tariffCatK").val()) == "0"){
			errorList.push(getLocalMessage("water.selectTarrifCatg.validn"));	
		}
		
		/* if($.trim($("#tariffSubCat").val()) == "0"){
			errorList.push("Please select Tariff Group");	
			errorList.push("<li></li> ");
		}
		
		if($.trim($("#premiseTypeForKD").val()) == "0"){
			errorList.push("Please select Premise Type");	
			errorList.push("<li></li> ");
		} */
		
		var noOffamily = $('#dto\\.noOfFamilyK').val();//$(getElemId("dto.noOfFamilyK")).val();
		var noOfUsers = $('#dto\\.noOfUserK').val();//$(getElemId("dto.noOfUserK")).val();	
		
		if($("#tariffCatK").find("option:selected").attr('code') == "D"){	
			
				
			
			if($.trim(noOffamily) == ""){
				errorList.push(getLocalMessage("water.noOfFamily.validn"));	
			}
			
			if($.trim(noOfUsers) == ""){
				errorList.push(getLocalMessage("water.noOfUsers.validn"));		
			}
			
			if($.trim(noOffamily) == "0"){
				
				errorList.push(getLocalMessage("water.validtnOnFamilyNo"));	
			}
			
			if($.trim(noOfUsers) == "0"){
				
				errorList.push(getLocalMessage("water.validtnOnUserNo"));	
			}
		}else{
			
			if($.trim(noOffamily) == ""){
				errorList.push(getLocalMessage("water.noOfFamily.validn"));	
			}
			if($.trim(noOfUsers) == ""){
				errorList.push(getLocalMessage("water.noOfUsers.validn"));		
			}
		}	
		
		
		
		var serviceSubType = $("#getCheckList").find("option:selected").attr('code');
		
		 var propertyNum = $.trim($("#propertyNo").val());
		 
		var serviceId = $("#getCheckList").val();
		
		if(serviceId!=0)
		if(serviceSubType != "NTEMP" && serviceSubType != "NM" && serviceSubType != "NSLUM" && serviceSubType!="NRUN"){
			
			if($.trim($.trim(propertyNum)) == ""){
				errorList.push(getLocalMessage("water.propNo.errMsg"));
			}
		
		}	 
		
		
		
		var connectionSize = $.trim($(getElemId("dto.sizeK")).val());
		var connectionDate = $.trim($(getElemId("dto.dateK")).val());
		
		
		//alert("Service sub type:"+serviceSubType);
		
		
		if(serviceSubType == "NRUN"){
			
			if(connectionSize == ""){
				errorList.push(getLocalMessage("water.unauthConnSize.validn"));
			}
			
			if(connectionDate == ""){
				errorList.push(getLocalMessage("water.unauthConnDt.validn"));
			}
		
		}
		
		if((kdmcPropertyType == "LND") && (propertyNum != "") && (kdmcPropertyBalance > 0) ){
			
		
			if($('#dto\\.csCcDate').val() == ""){
				errorList.push(getLocalMessage("water.constComppDt.validn"));
			}
			
			if($("#ccReasonKdmcId").val() == ""){
				errorList.push(getLocalMessage("water.constCompReaason.validn"));
			}
			
			if($("#ccSApprovedById").val() == "0"){
				errorList.push(getLocalMessage("water.apprvdBy.err"));
			}
		}
		
		
		
		var code=$("#getCheckList").find('option:selected').attr('code');
		
		if(code == "NSLUM"){
			if($.trim($("#SlumNo").val()) == ""){
				errorList.push(getLocalMessage("water.slumErr"));
			}
		}
		
		// validation for file upload
		
		var fileUploadedStatus =  __doAjaxRequest("WaterConnectionHome.html?checkForMandatoryFilesUploaded", 'post', {}, false);
		
		if(fileUploadedStatus=="N")
			errorList.push(getLocalMessage("water.fileuplaod.validtnMsg"));
		
	}// end kdmc validation
	
	if($("#dataBaseType").val()=="M"){
		
		
		if($.trim($("#applicantTitle").val()) == "0"){
			errorList.push(getLocalMessage("water.validation.ApplicantNameTitle"));	
		}
		
		if($.trim($("#firstName").val()) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantFirstName"));	
		}
		
		if($.trim($("#lastName").val()) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantLastName"));		
		}
		if($.trim($("#gender").val()) == "0" || $.trim($("#gender").val()) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantGender"));	
		}
		
		if($.trim($("#buildingName").val()) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantBuldingName"));		
		}
			
		if($.trim($("#blockName").val()) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantBlockName"));	
		}	
		
		if($.trim($("#villageTownSub").val()) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantcityVill"));
		}
		
		/*if($.trim($("#pinCode").val()) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantnterPincode"));	
			errorList.push("<li></li> ");
		}
		
		if($.trim(isNaN($("#pinCode").val())) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantInvalidPincode"));	
			errorList.push("<li></li> ");
		}*/
		var pinCode=$("#pinCode").val();
		if(pinCode.length<=6){
			 var count=0;
			if(pinCode.length<6){
				if(pinCode.length==""){
					errorList.push(getLocalMessage("water.validation.ApplicantnterPincode"));
				}else{
					errorList.push(getLocalMessage("citizen.login.reg.pincode.length.error"));
				}
				
			}
			
			for(var i=0; i<pinCode.length; i++){
				
				var eachPinCode=pinCode.charAt(i);
				if(eachPinCode==0){
					count++;
				}
				
			}
			
			/*if(count==6){
				errorList.push(getLocalMessage("citizen.login.reg.pincode.digits.error"));
				errorList.push("<li></li> ");
			}*/
			
			
		}
		
		
		if($.trim($("#emailId").val()) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantEmail"));	
		}else{
		
		var x = $.trim($("#emailId").val());
	    var atpos = x.indexOf("@");
	    var dotpos = x.lastIndexOf(".");
	    if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length) {
	    	errorList.push(getLocalMessage("emailId.invalid"));	
	    }
		}
		
		/*if($.trim($("#mobileNo").val()) == ""){
			errorList.push(getLocalMessage("water.validation.Applicantentermobileno"));	
		}
		
		if($.trim(isNaN($("mobileNo").val())) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantInvalidmobile"));	
		}*/
		
		var mobileNo=$("#mobileNo").val();
		if($.trim($("#mobileNo").val()) == ""){
			errorList.push(getLocalMessage("water.validation.Applicantentermobileno"));	
		}
		
		else if($.trim(isNaN($("mobileNo").val())) == ""){
			errorList.push(getLocalMessage("water.validation.ApplicantInvalidmobile"));	
		}
		else if(mobileNo.length<=10){
			 var count=0;
			if(mobileNo.length<10){
				if(mobileNo.length==""){
					errorList.push(getLocalMessage("water.validation.ApplicantInvalidmobile"));
				}else{
					errorList.push(getLocalMessage("water.validation.ApplicantInvalidmobile"));
				}
			}
			
			for(var i=0; i<mobileNo.length; i++){
				
				var eachMobileNo=mobileNo.charAt(i);
				if(eachMobileNo==0){
					count++;
				}
			}
		}
	
		
		if($.trim($("#codDwzId1").val()) == "" || $.trim($("#codDwzId1").val()) == 0){
			errorList.push(getLocalMessage('water.zoneName.validn'));	
		}	
		
		if($("#codDwzId2").val() == "" || $("#codDwzId2").val() == 0){
			errorList.push(getLocalMessage("water.wardName.validn"));	
		}
		
		// CODE
		if($.trim(flatType)=="I"){
			if($("#flatMvald").val() == ""){
				errorList.push(getLocalMessage("water.flatNo.validn"));	
			}
		}
		
		if($("#propertyNoMH").val() == ""){
			if($("#flatMvald").val() != ""){
				errorList.push(getLocalMessage("water.propNo.errMsg"));	
			}
		}
		
		
	/* 	if( $("#flatPrstnId").val()!=="")
		
		"propertyNo" : $("#flatMvald").val(),
			"flatPrstnId" : $("#flatPrstnId").val() */
		
		
		
		
		if(document.getElementById("hasSameAdd").checked){
			
			if($("#propertyNoMH").val() == ""){
					errorList.push(getLocalMessage("water.propOwner.validnMsg"));	
			}
			
			
			/*if($.trim($("#cOTitle").val()) == "0" || $("#cOTitle").val()==""){
				errorList.push("Please select Connection Owner Title");	
				errorList.push("<li></li> ");
			}
			
			if($.trim($("#firstNameco").val()) == ""){
				errorList.push("Please enter Coonection Owner First Name");	
				errorList.push("<li></li> ");
			}
			
			if($.trim($("#lastNameco").val()) == ""){
				errorList.push("Please enter Coonection Owner Last Name");		
				errorList.push("<li></li> ");
			}*/
			
		}
		else{
			
			if($.trim($("#cOTitle").val()) == "0" || $("#cOTitle").val()==""){
				errorList.push(getLocalMessage("water.connOwner.titleErr"));	
			}
			
			if($.trim($("#firstNameco").val()) == ""){
				errorList.push(getLocalMessage("water.connOwner.firstNameErr"));	
			}
			
			if($.trim($("#lastNameco").val()) == ""){
				errorList.push(getLocalMessage("water.connOwner.lastNameErr"));		
			}
			
			if($.trim($("#csaddM").val()) == ""){
				errorList.push(getLocalMessage("water.connLocAddr.validn"));	
			}
		}
		
		
		var aadharNumber=$.trim($(".cOconsumerAdharNoM").val());
	
		
			if(aadharNumber.length !=0 && aadharNumber.length<12){
					errorList.push(getLocalMessage("water.aadhaRNo.validn"));
				}
		
		
		if(!document.getElementById("hasSameAddBA").checked){
			if($.trim($("#csBaddM").val()) == ""){
				errorList.push(getLocalMessage("water.billLocAddr.validn"));	
			}
		}
		
		
		if($.trim($("#codCcgId1").val()) == "" || $.trim($("#codCcgId1").val()) ==0){
			errorList.push(getLocalMessage("water.consumer.typeErr"));	
		}
		
		if($.trim($("#codTrfId1").val()) == "" || $.trim($("#codTrfId1").val()) ==0){
			errorList.push(getLocalMessage("water.tariffCatg.validn"));	
		}
		
		if($.trim($("#premiseTypeForMH").val()) == "" || $.trim($("#premiseTypeForMH").val()) ==0){
			errorList.push(getLocalMessage("water.premiseType.validn"));	
		}
		
		var fileUploadedStatus =  __doAjaxRequest("WaterConnectionHome.html?checkForMandatoryFilesUploaded", 'post', {}, false);
		
		if(fileUploadedStatus=="N")
			errorList.push(getLocalMessage("UpLoad Mandatory Documents"));
		
		
	}
	
	//var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/></div><ul><li>'+errMsg[1]+'</li></ul>';
	if(errorList.length != 0){		
		
		$('.error-div').hide();
		
		var errMsg = '<ul>';

		$.each(errorList, function(index) {
			errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
		});

		errMsg += '</ul>';
	    var err = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" align="right" onclick="closeErrBox()" width="32"/></div>'+errMsg;
		$(".error-div").html(err);
		$('.error-div').show();
		$("html, body").animate({ scrollTop: 0 }, "slow");
		return false;
	}
	
	
	//var trackid= $("#appleTrackID").val();
	
	// if(trackid==null || trackid=='')
	//	{
		//return saveOrUpdateForm(element,"Your application saved Successfully!", 'AdminHome.html', 'saveform');	
		
		
		 var selectedZone = $("#zone").val();
			
			if(selectedZone != 0){
				$("#zone").val($("#zone").text());
			}
			//alert("zone ="+$("#zone").val());
			var selectedElectoralWard = $("#electrolWardKdmc").val();
			
			
			

			//validating Property Assessment date against service sub type = NUA95 
			var serviceSubTypeCodeTest = $("#getCheckList").find('option:selected').attr('code');
			
			var propertyNoTest = $.trim($("#propertyNo").val()); 
			
			if(serviceSubTypeCodeTest == "NUA95" && propertyNoTest != ""){				
				
				var requestData = {
						"serviceId" 			:			$("#getCheckList").val(),
						"propertyNo"			:			propertyNoTest
				};
				var serviceValidationStatus = __doAjaxRequest("WaterConnectionHome.html?serviceIdValidation", 'post', requestData, false);
				
				if(serviceValidationStatus == "exception"){
					
					var URL = $("form").attr('action');	            
			        _openPopUpForm(URL,"showErrorPage");		
					
					throw "error in ajax response";
				}
				
				var ccDateId ;
				var ccReasonId ;
				var approvedById ;
				
				if($('#ccDateId').is(':visible')){
					ccDateId = $(getElemId("dto.csCcDate").val());
				}else{
					ccDateId = null;
				}
				
				if($('#ccReasonId').is(':visible')){
					ccReasonId = $("#ccReasonKdmcId").val();
				}else{
					ccReasonId = null;
				}
				
				if($('#approvedById').is(':visible')){
					approvedById =  $("#ccSApprovedById").val();
				}else{
					approvedById = null;
				}
				
				
				
				
				requestData1 = {
						"serviceId" 			:			$("#getCheckList").val(),
						"propertyNo"			:			propertyNoTest,
						"ccDateId"				:			ccDateId,
						"ccReasonId"			:			ccReasonId,
						"approvedById"			:			approvedById
				};
				var vPmLegal =  __doAjaxRequest("WaterConnectionHome.html?vPmLegal", 'post', requestData1, false);
				//alert('vPmLegal ='+vPmLegal);
				
				if(vPmLegal == "exception"){
					
					var URL = $("form").attr('action');	            
			        _openPopUpForm(URL,"showErrorPage");		
					
					throw "error in ajax response";
				}
				
				/*if(vPmLegal == "error"){
					
					var URL = $("form").attr('action');	            
			        _openPopUpForm(URL,"showErrorPage");		
					
					throw "error in ajax response";
				}*/
				
				if(serviceValidationStatus == "N"){
					
					if(vPmLegal == "N"){
						assesmentDate = $('#dto\\.assesmentDate').val();
						if($.trim(assesmentDate) == "") {
							
							$('.error-div').hide();							
							$("#errorDivId").hide();
							
							errorList.push(getLocalMessage("water.assDtValidn"));
							showWaterError(errorList);
							return false;
						}else if(dateFormat($.trim(assesmentDate)) <= dateFormat("31/03/1995")){
							$('.error-div').hide();							
							$("#errorDivId").hide();
							
							errorList.push(getLocalMessage("water.assErr"));
							showWaterError(errorList);
							return false;
						}
					}
				} 
			}
			
			var succMsg = getLocalMessage('water.succMsg');
			
			if(selectedElectoralWard != 0){
				$("#electrolWardKdmc").val($("#electrolWardKdmc").text());
			}
			
			errorList = [];
			$('.error-div').hide();		
			$("#errorDivId").hide();
			
			if($("#dataBaseType").val()=="M"){
				

				// check for no. of taps
				
				var noOfTapsMHForTbExcludecolumn =  __doAjaxRequest("WaterConnectionHome.html?noOfTapsMHForTbExcludecolumn", 'post', {}, false);	
				
				if(noOfTapsMHForTbExcludecolumn == -1){
					
					var URL = $("form").attr('action');	            
		            _openPopUpForm(URL,"showErrorPage");					
					
					throw "error in ajax response";
				}
				
				var noOfTapsonForm = $('#dto\\.noOfTapsM').val();
				
				if(noOfTapsMHForTbExcludecolumn != 0 && $.trim(noOfTapsonForm) == ""){
					
					errorList.push(getLocalMessage("water.tapsValidtn"));
					
				}
				
				if(noOfTapsMHForTbExcludecolumn != 0 && $.trim(noOfTapsonForm) == 0){
					
					errorList.push(getLocalMessage("water.tapsSizeValidtn"));
					
				}
				
				var noOfUsersMHForTbExcludecolumn =  __doAjaxRequest("WaterConnectionHome.html?noOfUsersMHForTbExcludecolumn", 'post', {}, false);	
				
				if(noOfUsersMHForTbExcludecolumn == -1){
					
					var URL = $("form").attr('action');	            
		            _openPopUpForm(URL,"showErrorPage");					
					
					throw "error in ajax response";					
				}
				
				var noOfUsersonForm = $('#dto\\.noOfUserM').val();
				
				if(noOfUsersMHForTbExcludecolumn != 0 && $.trim(noOfUsersonForm) == ""){
					
					errorList.push(getLocalMessage("water.userNoValidtn"));
					
				}
				
				if(noOfUsersMHForTbExcludecolumn != 0 && $.trim(noOfUsersonForm) == 0){
					
					errorList.push(getLocalMessage("water.userNoSizeValidtn"));
					
				}
				
				if(errorList.length != 0){		
					
					$('.error-div').hide();
					
					var errMsg = '<ul>';

					$.each(errorList, function(index) {
						errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
					});

					errMsg += '</ul>';
					showWaterError(errMsg);
					var errMsg = '<ul>';
					return false;
				}
				

				var appliWithAmount =  __doAjaxRequest("WaterConnectionHome.html?getApplicationIdWithChargeAmount", 'post', {}, false);
				if(appliWithAmount == "exception"){
					
					//alert(appliWithAmount);
					
					var URL = $("form").attr('action');	            
		            _openPopUpForm(URL,"showErrorPage");
					
					
					throw "error in ajax response";
					
				}
				//alert("appliWithAmount ="+appliWithAmount);
				var chargableFlag = appliWithAmount.split(",")[2];
				
				if(chargableFlag=='Y'){

				var result = saveOrUpdateForm(element, succMsg,'WaterConnectionHome.html', 'saveWaterForm');
				if(result == false){
					
					var appliWithAmount =  __doAjaxRequest("WaterConnectionHome.html?getApplicationIdWithChargeAmount", 'post', {}, false);
					
					var apllicationId = appliWithAmount.split(",")[1];
					var payAmount = appliWithAmount.split(",")[0];
					
					if(payAmount != 0 && payAmount != 0.0 && payAmount != 'null'){
						
						// disable all form fields
						$("#WaterConnectionHome :input").attr("disabled", true);
						$("#submitAndPayButtonId").attr("disabled", false);
						$("#cancelButtonId").attr("disabled", false);
						
						// checkbox enable disable decision
						var copyToConnectionOwnerDetailId = $("#copyToConnectionOwnerDetailId").val();
						if(copyToConnectionOwnerDetailId == "Y"){
							
							$("#hasSameAdd").attr("checked", true);
							
							$("#cOTitle").addClass("disablefield").attr("readonly", true);
							$("#firstNameco").addClass("disablefield").attr("readonly", true);
							$("#fatherOrHusbandNameco").addClass("disablefield").attr("readonly", true);
							$("#lastNameco").addClass("disablefield").attr("readonly", true);
							$('.orgNameConsumer').addClass("disablefield").attr("readonly", true);
							$("#csaddM").addClass("disablefield").attr("readonly", true);
							$("#cOBulidNameM").addClass("disablefield").attr("readonly", true);
							$(".cORoadNameM").addClass("disablefield").attr("readonly", true);
							$(".cOcontactNo").addClass("disablefield").attr("readonly", true);
							$("#cOorgNameM").addClass("disablefield").attr("readonly", true);
							$("#cOHouseNo").addClass("disablefield").attr("readonly", true);
							
							if($("#propertyNoMH").val()!=""){
								
								$("#cOTitle").val(ownerTitle);
								
								$("#firstNameco").val($("#firstNamepo").val());

								//$("#fatherOrHusbandNameco").val($("#fatherOrHusbandName").val());
								$('.fatherOrHusbandNameco').val($('.middleName').val());
								$('.orgNameConsumer').val($('.pOorgnameM').val());
								$('.cOcontactNo').val(contactNoConsumer);
								$('.cOconsumerAdharNoM').val(uidNo);
								$('.cORoadNameM').val(roadName);
								
								$("#lastNameco").val($("#lastNamepo").val());

								$("#cOBulidNameM").val($("#pObuldingNameM").val());
								
								$("#csaddM").val($("#csOaddM").val());
								$("#cOHouseNo").val($("#pOhouseNoM").val());
								
							}
						}
						
						var copyToBillingAddressId = $("#copyToBillingAddressId").val();
						
						if(copyToBillingAddressId == "Y"){
							
							$("#hasSameAddBA").attr("checked", true);
							
							$("#bABulidName").val($("#pObuldingNameM").val());
							$(".bARoadNameM").val($(".cORoadNameM").val());
							$(".bAcontactNoM").val(contactNoConsumer);
							
							$("#bABulidName").val($("#cOBulidNameM").val());	
							$("#csBaddM").val($("#csaddM").val());	
							
							$("#bAHouseNoM").val($("#cOHouseNo").val());
							
							$("#bABulidName").addClass("disablefield").attr("readonly", true);
							$(".bARoadNameM").addClass("disablefield").attr("readonly", true);
							$(".bAcontactNoM").addClass("disablefield").attr("readonly", true);
							$("#csBaddM").addClass("disablefield").attr("readonly", true);
							$("#bAorgNameM").addClass("disablefield").attr("readonly", true);
							$("#bAHouseNoM").addClass("disablefield").attr("readonly", true);
						}
						
						$("#applicationId").val(apllicationId);
						$("#chargeAmountToPay").val(payAmount);
						$("#certificateDetail").show();
						
						return false;
					}
				}
				}
				else{
					
					var trackid= $("#appleTrackID").val();
					
					if(trackid==null || trackid==''){
						
						return saveOrUpdateForm(element, succMsg,
								'WaterConnectionHome.html', 'saveWaterForm');
						
					}else{
						
						errorList = [];
						$('.error-div').hide();		
						$("#errorDivId").hide();
						
						var	formName =	findClosestElementId(element, 'form');
						var theForm	=	'#'+formName;
						var requestData = {};
						 var selectedZone = $("#zone").val();
							
							if(selectedZone != 0){
								$("#zone").val($("#zone").text());
							}
						requestData = __serializeForm(theForm);
						var returnData=__doAjaxRequestForSave("WaterConnectionHome.html?saveWaterForm", 'post',requestData, false,'',element);
						if ($.isPlainObject(returnData))
						{
							var message = returnData.command.message;
							var hasError = returnData.command.hasValidationError;
							if (!message) {
								message = getLocalMessage('water.succMsg');
							}
							
							if(message && !hasError)
								{
								   		var	errMsgDiv		=	'.msg-dialog-box';
								   		var messagesdata='';
								   		
								   		var cls = getLocalMessage('eip.page.process');
								   		
								   			messagesdata	+='<p>'+ message+'</p>';
								   			messagesdata	+='<div class=\'btn_fld clear padding_10\'>'+	
								   			'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'css_btn \'    '+ 
								   			' onclick="sessionClosedWindow()"/>'+	
								   			'</div>';
								   	    	 
								   			$(errMsgDiv).html(messagesdata);
								   			$(errMsgDiv).show();
								   			$('#btnNo').focus();
								   		  
								   	  showModalBoxWithoutClose(errMsgDiv);
								   		
								   		
								}
							else if(hasError)
							{
								$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');	
							}
							else
								return returnData;
						}
						else if (typeof(returnData) === "string")
						{
							$(formDivName).html(returnData);	
							prepareTags();
						}
						else 
						{
							alert("Invalid datatype received : " + returnData);
						}
						
						
						
						}
						
					
										
					/*return saveOrUpdateForm(element, succMsg,
							'WaterConnectionHome.html', 'saveWaterForm');*/
				}
				
			}else{
				

				
				var trackid= $("#appleTrackID").val();
				
				if(trackid==null || trackid==''){
					
					return saveOrUpdateForm(element, succMsg,
							'WaterConnectionHome.html', 'saveWaterForm');
					
				}else{
					
					errorList = [];
					$('.error-div').hide();		
					$("#errorDivId").hide();
					
					var	formName =	findClosestElementId(element, 'form');
					var theForm	=	'#'+formName;
					var requestData = {};
					 var selectedZone = $("#zone").val();
						
						if(selectedZone != 0){
							$("#zone").val($("#zone").text());
						}
					requestData = __serializeForm(theForm);
					var returnData=__doAjaxRequestForSave("WaterConnectionHome.html?saveWaterForm", 'post',requestData, false,'',element);
					if ($.isPlainObject(returnData))
					{
						var message = returnData.command.message;
						var hasError = returnData.command.hasValidationError;
						if (!message) {
							message = getLocalMessage('water.succMsg');
						}
						
						if(message && !hasError)
							{
							   		var	errMsgDiv		=	'.msg-dialog-box';
							   		var messagesdata='';
							   		
							   		var cls = getLocalMessage('eip.page.process');
							   		
							   			messagesdata	+='<p>'+ message+'</p>';
							   			messagesdata	+='<div class=\'btn_fld clear padding_10\'>'+	
							   			'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'css_btn \'    '+ 
							   			' onclick="sessionClosedWindow()"/>'+	
							   			'</div>';
							   	    	 
							   			$(errMsgDiv).html(messagesdata);
							   			$(errMsgDiv).show();
							   			$('#btnNo').focus();
							   		  
							   	  showModalBoxWithoutClose(errMsgDiv);
							   		
							   		
							}
						else if(hasError)
						{
							$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');	
						}
						else
							return returnData;
					}
					else if (typeof(returnData) === "string")
					{
						$(formDivName).html(returnData);	
						prepareTags();
					}
					else 
					{
						alert("Invalid datatype received : " + returnData);
					}
					
					
					
					}
				
				/*return saveOrUpdateForm(element, succMsg,
						'WaterConnectionHome.html', 'saveWaterForm');*/
			}
			
			
		
	//	}
	/*else
		{ 
		
		errorList = [];
		$('.error-div').hide();		
		$("#errorDivId").hide();
		
		var	formName =	findClosestElementId(element, 'form');
		var theForm	=	'#'+formName;
		var requestData = {};
		 var selectedZone = $("#zone").val();
			
			if(selectedZone != 0){
				$("#zone").val($("#zone").text());
			}
		requestData = __serializeForm(theForm);
		var returnData=__doAjaxRequestForSave("WaterConnectionHome.html?saveWaterForm", 'post',requestData, false,'',element);
		if ($.isPlainObject(returnData))
		{
			var message = returnData.command.message;
			var hasError = returnData.command.hasValidationError;
			if (!message) {
				message = getLocalMessage('water.succMsg');
			}
			
			if(message && !hasError)
				{
				   		var	errMsgDiv		=	'.msg-dialog-box';
				   		var messagesdata='';
				   		
				   		var cls = getLocalMessage('eip.page.process');
				   		
				   			messagesdata	+='<p>'+ message+'</p>';
				   			messagesdata	+='<div class=\'btn_fld clear padding_10\'>'+	
				   			'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'css_btn \'    '+ 
				   			' onclick="sessionClosedWindow()"/>'+	
				   			'</div>';
				   	    	 
				   			$(errMsgDiv).html(messagesdata);
				   			$(errMsgDiv).show();
				   			$('#btnNo').focus();
				   		  
				   	  showModalBoxWithoutClose(errMsgDiv);
				   		
				   		
				}
			else if(hasError)
			{
				$('.error-div').html('<h2>ddddddddddddddddddddddddddddddd</h2>');	
			}
			else
				return returnData;
		}
		else if (typeof(returnData) === "string")
		{
			$(formDivName).html(returnData);	
			prepareTags();
		}
		else 
		{
			alert("Invalid datatype received : " + returnData);
		}
		
		
		
		}*/

	
	

}


function getCheckList1(obj,param) {
	var data = {

		"getCheckList" : $("#getCheckList").val()
	};
	
	

	var URL = 'WaterConnectionHome.html?' + param;

	var ajaxResponse2 = __doAjaxRequest(URL, 'POST', data, false);

	$("#fileUpload").html(ajaxResponse2);
	if($("#getCheckList").val()!=0)
		$("#fileUploadRegId").show();
	else
		$("#fileUploadRegId").hide();
	
	var data1 = {

			"trafficId" : $("#tariffCatK").val(),
			"serviceType" : $("#getCheckList").val()
			
		};

		var URL = 'WaterConnectionHome.html?' + 'getPrimiseType';

		var ajaxResponse = __doAjaxRequest(URL, 'POST', data1, false, 'json');

		$('#tariffSubCat').html("");
		$('#zone').append(
				$("<option></option>").attr("value", 0).text("--Select--"));

		$.each(ajaxResponse, function(key, value) {

			$('#tariffSubCat').append(
					$("<option></option>").attr("value", key).text(value));

		});
		
		
		var code=$(obj).find('option:selected').attr('code');
		if(code=='NRUN'){
			$("#sizeService").show();
			$("#dateService").show();
			$("#nrunShow").show();
			$("#nrunHide").hide();
			$("#houseTypeHidden").val("0");
			housetTypeCode="";
		}
		else{
			$("#sizeService").hide();
			$("#dateService").hide();
			$("#nrunShow").hide();
			$("#nrunHide").show();
		}
		
		if(code=='NSLUM'){
			$("#propService").hide();
			$("#ServiceSlum").show();
		}
		else{
			$("#propService").show();
			$("#ServiceSlum").hide();
		}
		
		var serviceId = $("#getCheckList").val();
		if(serviceId!=0)
			if(code != "NTEMP" && code != "NM" && code != "NSLUM" ){
				$("#propertyNo").addClass("mandClassColor");
				$("#mandProp").show();
			}else{
				$("#propertyNo").removeClass("mandClassColor");
				$("#mandProp").hide();
			}	 
}

function prCheckConnection(connectionNum, count){
	
	$('.error-div').hide();
	$("#errorDivId").hide();
	var errorList = [];
	
	var connectionDuplicateFound = 0;
	
	if($.trim(connectionNum) == ""){
		
		$("#lcOldccn"+count).val("");
		$("#lcOldccnsize"+count).val("");
		$("#lcOldtaps"+count).val("");
		$("#csIdn"+count).val("");

		
		return false;
	}
	
	$('.appendableClass').each(function(i) {
		//alert(i);
		 var lcOldccn = $.trim($("#lcOldccn"+i).val());
		 if(i != count){
			 if(lcOldccn == connectionNum){
				// alert("i ="+i+" -- lcOldccn ="+lcOldccn+" -- connectionNum ="+connectionNum);
				 connectionDuplicateFound = connectionDuplicateFound + 1;				
			 }
		 }
	});
	
	if(connectionDuplicateFound > 0){
		
		errorList.push(getLocalMessage("water.duplicateConnValidtn"));
		
		$("#lcOldccn"+count).val("");
		$("#lcOldccnsize"+count).val("");
		$("#lcOldtaps"+count).val("");
		$("#csIdn"+count).val("");
		
		showWaterError(errorList);
		return false;
	}	
	
		var data = {

			"connectionNo" : connectionNum
		};
		
		/*// Check for connection number used by some other application id or not
		
		var conNumCheckURL = 'WaterConnectionHome.html?connectionNoInUsed';
		
		if(conNumCheckURL > 0){
			errorList.push("Connection No. <b>"+ connectionNum + "</b> is already specified in other application");
			
			$("#lcOldccn"+count).val("");
			$("#lcOldccnsize"+count).val("");
			$("#lcOldtaps"+count).val("");
			
			$(".error-div").html(errorList);
			$(".error-div").show();
			$("html, body").animate({ scrollTop: 0 }, "slow");
			return false;
		}*/

		var URL = 'WaterConnectionHome.html?prCheckConnection';

		var result = __doAjaxRequest(URL, 'POST', data, false, 'json');
		
		if(result != "null" || result != ""){
			
			if(result.split(",")[0] == "success"){
				$("#lcOldccnsize"+count).val(result.split(",")[1]);
				$("#lcOldtaps"+count).val(result.split(",")[2]);
				$("#csIdn"+count).val(result.split(",")[3]);
				errorList = [];
				//$(".error-div").html(errorList);
				$("#errorDivId").hide();
				
			}else if(result.split(",")[0] == "fail"){
			
				errorList.push(result.split(",")[1]);
				
				$("#lcOldccn"+count).val("");
				$("#lcOldccnsize"+count).val("");
				$("#lcOldtaps"+count).val("");
				$("#errorDivId").hide();
				$("#csIdn"+count).val("");
				showWaterError(errorList);
				return false;				
				
			}else{

				$("#lcOldccn"+count).val("");
				$("#lcOldccnsize"+count).val("");
				$("#lcOldtaps"+count).val("");
				$("#errorDivId").hide();
				$("#csIdn"+count).val("");
				var URL = $("form").attr('action');	            
		        _openPopUpForm(URL,"showErrorPage");		
				
				throw "error in ajax response";
				
			}
		}
		
		
		
}

function setPconsumerCHKValue(){

	if($("#hasSameAdd").attr("checked") && $("#propertyNoMH").val()!=""){

		$("#hasSameAdd").val("Y");
		
		
		$("#copyToConnectionOwnerDetailId").val("Y");
		
		$("#cOTitle").addClass("disablefield").attr("readonly", true);
		$("#firstNameco").addClass("disablefield").attr("readonly", true);

		$("#cOTitle").addClass("disablefield").attr("readonly", true);		
		$("#firstNameco").addClass("disablefield").prop("readonly", true);

		$("#fatherOrHusbandNameco").addClass("disablefield").attr("readonly", true);
		$("#lastNameco").addClass("disablefield").attr("readonly", true);
		$('.orgNameConsumer').addClass("disablefield").attr("readonly", true);
		$("#csaddM").addClass("disablefield").attr("readonly", true);
		$("#cOBulidNameM").addClass("disablefield").attr("readonly", true);
		$(".cORoadNameM").addClass("disablefield").attr("readonly", true);
		$(".cOcontactNo").addClass("disablefield").attr("readonly", true);
		$("#cOorgNameM").addClass("disablefield").attr("readonly", true);
		$("#cOHouseNo").addClass("disablefield").attr("readonly", true);
		
		
	}else{
		$("#hasSameAdd").val("N");
		
		$("#copyToConnectionOwnerDetailId").val("N");
		
		$("#cOTitle").removeClass("disablefield").attr("readonly", false);
		$("#firstNameco").removeClass("disablefield").attr("readonly", false);
		$("#fatherOrHusbandNameco").removeClass("disablefield").attr("readonly", false);
		$("#lastNameco").removeClass("disablefield").attr("readonly", false);
		$('.orgNameConsumer').removeClass("disablefield").attr("readonly", false);
		$("#csaddM").removeClass("disablefield").attr("readonly", false);
		$("#cOBulidNameM").removeClass("disablefield").attr("readonly", false);
		$(".cORoadNameM").removeClass("disablefield").attr("readonly", false);
		$(".cOcontactNo").removeClass("disablefield").attr("readonly", false);
		$("#cOorgNameM").removeClass("disablefield").attr("readonly", false);
		$("#cOHouseNo").removeClass("disablefield").attr("readonly", false);
		
		$("#csaddM").val('');
		$("#cOorgNameM").val('');
		$("#cOHouseNo").val('');
		
	}
	
}

function sessionClosedWindow(){

	var result = __doAjaxRequest('rtsServicesAccess.html?sessionClosed', 'POST', '', false);
	window.close();
}

function checkForSlumName(slumName){
	
	$('.error-div').hide();
	$("#errorDivId").hide();
	
	var errorList = [];
	
		var data = {

			"slumName" : slumName
		};

		var URL = 'WaterConnectionHome.html?checkForSlumName';

		var result = __doAjaxRequest(URL, 'POST', data, false, 'json');
		
		if(result == "fail"){
			
			errorList.push(getLocalMessage("water.slumErr.validn"));
			var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" align="right" onclick="closeErrBox()" width="32"/></div><ul><li><i class="fa fa-exclamation-circle"></i> &nbsp;'+errorList+'</li></ul>';
			$(".error-div").html(errMsg);			
			$(".error-div").show();
			$("html, body").animate({ scrollTop: 0 }, "slow");
			$("#SlumNo").val("");
			return false;
		}
		
		if(result == "exception"){
			
			var URL = $("form").attr('action');	            
	        _openPopUpForm(URL,"showErrorPage");		
			
			throw "error in ajax response";
			
		}
		
}

function balConfirm(msg, returnData1) {
	 alertify.confirm(msg,function (e) {
		if (e) {
			  // Add three column for CsCcDate, CsCCReason, CsCcAppBy
			  $("#ccDateId").show();
			  $("#ccReasonId").show();
			  $("#approvedById").show();			  
			  	
				var firstName = returnData1.split("@@#")[3];
				var middleName = returnData1.split("@@#")[4];
				var lastName = returnData1.split("@@#")[5];
				var houseNo = returnData1.split("@@#")[6];
				var buildingName = returnData1.split("@@#")[7];
				
				if(firstName == "null" || firstName == "noFirstName"){
					$("#firstNamepo").val("");
				}else{
					$("#firstNamepo").val(firstName);
				}
				
				if(middleName == "null" || middleName == "noMiddleName"){
					$("#fatherOrHusbandName").val("");
				}else{
					$("#fatherOrHusbandName").val(middleName);
				}
				
				if(lastName == "null" || lastName == "noLastName"){
					$("#lastNamepo").val("");
				}else{
					$("#lastNamepo").val(lastName);
				}
				
				if(houseNo == "null" || houseNo == "noHouseNo"){
					$("#prtyhouseNoId").val("");
				}else{
					$("#prtyhouseNoId").val(houseNo);
				}
				
				if(buildingName == "null" || buildingName == "noBuildingName"){
					$("#pObuldingNameM").val("");
				}else{
					$("#pObuldingNameM").val(buildingName);
				}
			  
     }else{
       	$("#propertyNo").val("");
   	  return false;
	   }
	});
	}



function getPropertyDetilsforMh(propertyNo) {
	if($.trim(propertyNo) == ""){
		$('#dto\\.assesmentDate').val('');
		$('.constructionPerNo').val('');
		$('.occupationCertNo').val('');
		$('#pDTitle').val('0');
		$('#firstNamepo').val('');
		$('.middleName').val('');
		$('#lastNamepo').val('');
		$('.pOorgnameM').val('');
		$('#pObuldingNameM').val('');
		$('.pOhouseNoM').val('');
		
		$('#flatMvald').val('');
		
		if($("#hasSameAdd").attr("checked")){
		$('#cOTitle').val('');
		$('#firstNameco').val('');
		$('#fatherOrHusbandNameco').val('');
		$('#lastNameco').val('');
		$('.orgNameConsumer').val('');
		$('.cOconsumerAdharNoM').val('');
		$('#cOBulidNameM').val('');
		$('.cORoadNameM').val('');
		$('.cOcontactNo').val('');
		$('#bABulidName').val('');
		$("#csaddM").val('');
		$("#cOorgNameM").val('');
		}
		
		
		$('#hasSameAdd').prop('checked',false);
		$('#hasSameAddBA').prop('checked',false);
		
		$("#cOTitle").removeClass("disablefield").attr("readonly", false);
		$("#firstNameco").removeClass("disablefield").attr("readonly", false);
		$("#fatherOrHusbandNameco").removeClass("disablefield").attr("readonly", false);
		$("#lastNameco").removeClass("disablefield").attr("readonly", false);
		$('.orgNameConsumer').removeClass("disablefield").attr("readonly", false);
		$("#csaddM").removeClass("disablefield").attr("readonly", false);
		$("#cOBulidNameM").removeClass("disablefield").attr("readonly", false);
		$(".cORoadNameM").removeClass("disablefield").attr("readonly", false);
		$(".cOcontactNo").removeClass("disablefield").attr("readonly", false);
		$("#cOorgNameM").removeClass("disablefield").attr("readonly", false);
		
		
		if(document.getElementById("hasSameAddBA").checked){
		$("#bABulidName").val("");
		$(".bARoadNameM").val('');
		$(".bAcontactNoM").val('');
		$("#csBaddM").val('');
		$("#bAorgNameM").val('');
		$('.bARoadNameM').val('');
		$('.bAcontactNoM').val('');
		$("#bAHouseNoM").val('');
		
		}
		
		$("#bABulidName").removeClass("disablefield").attr("readonly", false);
		$(".bARoadNameM").removeClass("disablefield").attr("readonly", false);
		$(".bAcontactNoM").removeClass("disablefield").attr("readonly", false);
		$("#csBaddM").removeClass("disablefield").attr("readonly", false);
		$("#bAorgNameM").removeClass("disablefield").attr("readonly", false);
		$("#bAHouseNoM").removeClass("disablefield").attr("readonly", false);
		
		$("#flatNoM").show();
		$("#csOaddM").val('');
		
		return false;		
	}
	
	var data = {

		"propertyNo" : $("#propertyNoMH").val()
		
	};

	var URL = 'WaterConnectionHome.html?' + 'getPropertyNoMH';

	var returnData1 = __doAjaxRequest(URL, 'POST', data, false);
	
	
	var errMsg = returnData1.split(",");
	var errorList=[];
	if(errMsg[0]=="-1"){	
		
		$('.error-div').hide();
		var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" align="right" onclick="closeErrBox()" width="32"/></div><ul><li><i class="fa fa-exclamation-circle"></i> &nbsp;'+errMsg[1]+'</li></ul>';
		$(".error-div").html(errMsg);
		$('.error-div').show();
		$("#propertyNoMH").val('');
		$("html, body").animate({ scrollTop: 0 }, "slow");

		return false;
		
	}else if(errMsg[0]=="exception"){
		
		$("#propertyNoMH").val("");
		var URL = $("form").attr('action');	            
        _openPopUpForm(URL,"showErrorPage");		
		
		throw "error in ajax response";		
	}else{
		$(".error-div").html('');
		$('.error-div').hide();
	}
	
	
	var new1 = returnData1.substring(0, returnData1.length - 1);

	var abc = returnData1;
	abc = returnData1.substring(0, 0);
	namesArray=returnData1.substring(0, 1 - 1)
	+ returnData1.substring(1, returnData1.length);
	
	namesArray=namesArray.substring(0, namesArray.length - 2);
	
	var namesArray = namesArray.split(",");
	//alert(namesArray);
	var item, items = [];
	item = {};
	
	//[O_TITLE,O_FIRST_NAME,O_SEC_NAME,O_LAST_NAME,O_ADDRESS,O_ORG_NAME,O_PROP_TYPE,O_CONSTRUCTION_PER_NO,O_CITY_SURVAY_NO,O_BUL_NAME,O_BILL_METHOD,O_ASSNDATE,O_HOUSE_NO
	//	O_STREETNM,O_CONTACTNO,O_UID,O_PM_PRMSTID,O_COUNTRY_ID,O_STATE_ID,]
	item.returnData1 = namesArray[0]; //title
	ownerTitle=namesArray[0];
	contactNoConsumer=namesArray[14];
	uidNo=namesArray[15];
	roadName=namesArray[13];
	if ($.trim(contactNoConsumer) == 'null') {
		contactNoConsumer='';
	}else{
		$("#pOcontactNoM").val(contactNoConsumer);
	}
	if ($.trim(uidNo) == 'null') {
		uidNo='';
	}
	if ($.trim(roadName) == 'null') {
		roadName='';
	}else{
		$("#pOroadNameM").val(roadName);
	}
	
	
	$("#pDTitle").val(item.returnData1);
	
	$('#pdtitle').val(item.returnData1);
	
	item.returnData1 = namesArray[1];//firstName
	if ($.trim(item.returnData1) =='null') {
		$("#firstNamepo").val('');
	} else {
		$("#firstNamepo").val(item.returnData1);
	}

	item.returnData1 = namesArray[2];//middleName
	var middleName=$.trim(item.returnData1);
	if ($.trim(item.returnData1) =='null') {
		$(".middleName").val('');
	} else {
		$(".middleName").val(middleName);
	}
	
	item.returnData1 = namesArray[3];//lastName
	if ($.trim(item.returnData1) =='null') {
		$("#lastNamepo").val('');
	} else {
		$("#lastNamepo").val(item.returnData1);
	}
	
	
	item.returnData1 = namesArray[4];//Location Address
	if ($.trim(item.returnData1) =='null') {
		$("#csOaddM").val('');
	} else {
		$("#csOaddM").val(item.returnData1);
	}
	
	item.returnData1 = namesArray[5];//orgName
	if ($.trim(item.returnData1) =='null') {
		$(".pOorgnameM").val('');
	} else {
		$(".pOorgnameM").val(item.returnData1);
	}

	item.returnData1 = namesArray[7];//construction No
	if ($.trim(item.returnData1) =='null') {
		$(".constructionPerNo").val('');
	} else {
		$(".constructionPerNo").val(item.returnData1);
	}
	
	item.returnData1 = namesArray[8];//construction No
	if ($.trim(item.returnData1) =='null') {
		$(".occupationCertNo").val('');
	} else {
		$(".occupationCertNo").val(item.returnData1);
	}

	item.returnData1 = namesArray[9];//address
	if ($.trim(item.returnData1) =='null') {
		$("#pObuldingNameM").val('');
	} else {
		$("#pObuldingNameM").val(item.returnData1);
	}
	
	item.returnData1 = namesArray[11];//address
	if ($.trim(item.returnData1) =='null') {
		$(".assesmentDate").val('');
	} else {
		$(".assesmentDate").val(item.returnData1);
	}
	

	item.returnData1 = namesArray[12];//house No
	if ($.trim(item.returnData1) =='null') {
		$(".pOhouseNoM").val('');
	} else {
		$(".pOhouseNoM").val(item.returnData1);
	}
	
	item.returnData1 = namesArray[10];//Bill Method	
	
	
	$("#billMethodMh").val($.trim(item.returnData1));

	flatType=item.returnData1;
	if($.trim(flatType)=="I"){
	$("#flatMvald").addClass("mandClassColor mand");
	}
	else{
		$("#flatMvald").removeClass("mandClassColor mand");
	}
	
	if(item.returnData1==" W"){
		$("#flatNoM").hide();
		$("#flatMvald").val("");
		$("#flatNoteId").hide();
	}
	else{
		$("#flatNoM").show();
		$("#flatNoteId").show();
	}
	$("#flatPrstnId").val(namesArray[16]);
	
	$("#contryM").val(namesArray[19]);
	$("#stateM").val(namesArray[20]);
	$("#districtM").val(namesArray[21]);
	$("#talukaM").val(namesArray[22]);
	$("#cityM").val(namesArray[23]);
	
	
	
}

function dateFormat(value) {
	var date = value;
	var chunks = date.split('/');
	var formatDate = chunks[1] + '/' + chunks[0] + '/' + chunks[2];
	var newDate = new Date(formatDate);
	return newDate;

}

jQuery(function($) {
	
	$('#dto\\.assesmentDate').datepicker({
		
	}).on("change", function() {
		
		$('.error-div').hide();
		$("#errorDivId").hide();
		var errorList = [];
		
		  if($.trim($('#dto\\.assesmentDate').val()) == ""){
			  return false;
		  }
		  
		  var serviceSubTypeCodeTest = $("#getCheckList").find('option:selected').attr('code');
		
		  if(serviceSubTypeCodeTest == "NUA95")
		  { 
		  if($.trim($("#propertyNo").val()) == "" ){
			  
			  errorList.push(getLocalMessage("water.propNoValidn"));
			  $('#dto\\.assesmentDate').val("");
			  var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" align="right" onclick="closeErrBox()" width="32"/></div><ul><li><i class="fa fa-exclamation-circle"></i> &nbsp;'+errorList+'</li></ul>';
				$(".error-div").html(errMsg);
				$('.error-div').show();
			  $("html, body").animate({ scrollTop: 0 }, "slow");
				
				return false;
			  
		  }
		  
		  	var data = {
					"propertyNo" 	:	$("#propertyNo").val(),
					"serviceId"	 	:	$("#getCheckList").val(),
					"assesmentDate" : 	$('#dto\\.assesmentDate').val()
				};

		  	var URL = 'WaterConnectionHome.html?' + 'checkAssesmentDate';

			var returnData1 = __doAjaxRequest(URL, 'POST', data, false);
			
			if(returnData1 == "exception"){
				
				var URL = $("form").attr('action');	            
		        _openPopUpForm(URL,"showErrorPage");		
				
				throw "error in ajax response";
			}
			
			if(returnData1 != "empty" && returnData1 != "exception"){
				 $('#dto\\.assesmentDate').val("");
				errorList.push(returnData1);
				var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" align="right" onclick="closeErrBox()" width="32"/></div><ul><li><i class="fa fa-exclamation-circle"></i> &nbsp;'+errorList+'</li></ul>';
				$(".error-div").html(errMsg);
				$('.error-div').show();
				  $("html, body").animate({ scrollTop: 0 }, "slow");
					
					return false;
			}
		  }

	});
	
	
$('#dto\\.csCcDate').datepicker({
		
	}).on("change", function() {
	
		$('.error-div').hide();
		$("#errorDivId").hide();
		var errorList = [];
		
		  if($.trim($('#dto\\.csCcDate').val()) == ""){
			  return false;
		  }
		  
		  if($.trim($("#propertyNo").val()) == "" ){
			  
			  errorList.push(getLocalMessage("water.propNoValidn"));
			  
			  $('#dto\\.csCcDate').val("");
			  var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" align="right" onclick="closeErrBox()" width="32"/></div><ul><li><i class="fa fa-exclamation-circle"></i> &nbsp;'+errorList+'</li></ul>';
				$(".error-div").html(errMsg);
				$('.error-div').show();
			  $("html, body").animate({ scrollTop: 0 }, "slow");
				
				return false;
			  
		  }
	  
		  	var data = {
					"propertyNo" 	:	$("#propertyNo").val(),
					"constructionCompletiontDate" : 	$('#dto\\.csCcDate').val()
				};
	
		  	var URL = 'WaterConnectionHome.html?' + 'checkconstructionCompletiontDate';
	
			var returnData1 = __doAjaxRequest(URL, 'POST', data, false);
			
			if(returnData1 == "exception"){
				
				var URL = $("form").attr('action');	            
		        _openPopUpForm(URL,"showErrorPage");		
				
				throw "error in ajax response";
			}
			
			if(returnData1 != "empty" && returnData1 != "exception"){
				errorList.push(returnData1);
				$('#dto\\.csCcDate').val("");
				 var errMsg = '<div class="closeme"><img alt="Close" title="Close" src="css/images/close.png" align="right" onclick="closeErrBox()" width="32"/></div><ul><li><i class="fa fa-exclamation-circle"></i> &nbsp;'+errorList+'</li></ul>';
					$(".error-div").html(errMsg);
					$('.error-div').show();
				  $("html, body").animate({ scrollTop: 0 }, "slow");
					
					return false;
			}
		
	});
	
});



/*$('#dto\\.csCcDate').datepicker({
			
			onSelect : function(date) {
		
				$('.error-div').hide();
				$("#errorDivId").hide();
				var errorList = [];
				
				  if($.trim($('#dto\\.csCcDate').val()) == ""){
					  return false;
				  }
				  
				  if($.trim($("#propertyNo").val() == "" )){
					  
					  errorList.push("Please enter Property No.");
					  $(".error-div").html(errorList);
					  $("#errorDivId").show();
					  $("html, body").animate({ scrollTop: 0 }, "slow");
						
						return false;
					  
				  }
			  
				  	var data = {
							"propertyNo" 	:	$("#propertyNo").val(),
							"constructionCompletiontDate" : 	$('#dto\\.csCcDate').val()
						};
			
				  	var URL = 'WaterConnectionHome.html?' + 'checkconstructionCompletiontDate';
			
					var returnData1 = __doAjaxRequest(URL, 'POST', data, false);
					
					if(returnData1 != "empty"){
						errorList.push(returnData1);
						  $(".error-div").html(errorList);
						  $("#errorDivId").show();
						  $("html, body").animate({ scrollTop: 0 }, "slow");
							
							return false;
					}
			}
});*/



var formObject;
function saveFormWaterMhPayment(obj) {

	var askMessage;

	$('#submitAndPayButtonId').prop("disabled", true);
	formObject = obj;

	var onlineCheck = getLocalMessage('ulb.payment_gateway.available');

	if (onlineCheck == "Y") {
		askMessage = getLocalMessage('continue.forpayment');
	} else {
		askMessage = getLocalMessage('continue.forchallan');
	}

	var message = '';
	message += '<p style=\'text-align:center;margin: 5px;\'>' + askMessage
			+ '</p>';
	message += '<p style=\'text-align:center;margin: 5px;\'>'
			+ '<br/><input type=\'button\' value=\'' + "Proceed"
			+ '\'  id=\'btnNo3\' class=\' button css_btn \''
			+ 'onclick="saveOrUpdateFormPopupMsg()"/> ';

	$(errMsgDiv).removeClass('ok-msg').addClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();

	$('#btnNo3').focus();
	showModalBoxWithoutClose(errMsgDiv);

}

function saveOrUpdateFormPopupMsg(){
	return saveOrUpdateForm(formObject, '','WaterConnectionHome.html','redirectToPay');
}


function goBack(){
	window.location.href="AdminHome.html";
}

function closeErrBox(){
	$('.error-div').hide();
}


function resetFormAndLoadURl(){
	
	divName = ".widget";
	redirectUrl = "WaterConnectionHome.html";
	
	if(typeof redirectUrl!== undefined && redirectUrl!=='undefined' && redirectUrl!=null && (redirectUrl!=''))
	{	
		try
		{
		 var lastIndex = redirectUrl.lastIndexOf("?");
		 
	     var lastchar = redirectUrl.substring(lastIndex	+	1);	
	     
	   
	   
		if(lastchar == 'PrintReport' || lastchar == 'PrintChallanOnline' || lastchar == 'PrintCounterReceipt')
			{
				 window.open(redirectUrl,'_blank');			
				 window.location.href='AdminHome.html';
			}else if(lastchar == 'PrintULBCounterReceipt' ){
				 window.open(redirectUrl,'_blank');			
				 window.location.href='ChallanAtULBCounter.html';
				
			}
		else
			//window.location.href=redirectUrl;
			$("#postMethodFormSuccess").prop('action', '');
			$("#postMethodFormSuccess").prop('action', redirectUrl);
			$("#postMethodFormSuccess").submit();
		}
		catch(e)
		{
			//window.location.href=redirectUrl;
			$("#postMethodFormSuccess").prop('action', '');
			$("#postMethodFormSuccess").prop('action', redirectUrl);
			$("#postMethodFormSuccess").submit();
		}
	}
	
	disposeModalBox();
	
	return false;
}

function clearErrorDiv() {
	
	resetFormAndLoadURl();
	
	/*cleareFile(this);
	$('.error-div').hide();
	$("#buildingName").val("");
	$("#roadName").val("");
	$("#areaName").val("");
	$("#blockName").val(""); 
	$("#housingComplexName").val(""); 
    $("#wing").val(""); 
    $("#floorNo").val(""); 
    $("#villageTownSub").val(""); 
    $("#phone1").val(""); 
    $("#phone2").val(""); 
    $("#contactPersonName").val(""); 
    $("#emailId").val(""); 
    $("#codDwzId1").val("0"); 
    $("#codDwzId2").val("0");
    $("#electrolWardM").val("0");
    $("#propertyNoMH").val("");
	$("#flatMvald").val("");
	$("input[name='dto.assesmentDate']").val('');
	$("input[name='dto.constructionPerNo']").val('');
	$("input[name='dto.occupationCertNo']").val('');
	$("#pDTitle").val("0"); 
	$("#firstNamepo").val(""); 
    $("#fatherOrHusbandNamepo").val(""); 
    $("#lastNamepo").val(""); 
    $("input[name='dto.pOorgnameM']").val('');
    $("#csOaddM").val(""); 
    $("#pObuldingNameM").val(""); 
    $("#pOhouseNoM").val(""); 
    if($("#hasSameAdd").val()=="Y"){
		
		$('#hasSameAdd').prop('checked',false);
	
	}
    $("#cOTitle").val("0");
	$("#firstNameco").val("");
	$("#fatherOrHusbandNameco").val("");
	$("#lastNameco").val(""); 
	$(".orgNameConsumer").val(""); 
	$("input[name='dto.cOconsumerAdharNoM']").val('');
    $("#csaddM").val(""); 
    $("#cOBulidNameM").val(""); 
    $("#cORoadNameM").val(""); 
    $("input[name='dto.cOcontactNo']").val('');
    $("#contactPersonName").val(""); 
    $("#cOorgNameM").val(""); 
   
    if($("#hasSameAddBA").val()=="Y"){
		
		$('#hasSameAddBA').prop('checked',false);
	
	}
	$("#bABulidName").val("");
	$("#csBaddM").val("");
	$("input[name='dto.bARoadNameM']").val('');
	$("input[name='dto.bAcontactNoM']").val('');
    $("#bAorgNameM").val("");
    $("#connectionTypeM").val("0"); 
    $("#codCcgId1").val("0"); 
    $("#codTrfId1").val("0"); 
    $("#premiseTypeForMH").val("0"); 
    $("input[name='dto.noOfUserM']").val('');
    $("input[name='dto.noOfTapsM']").val('');*/
  }

function cleareFile(obj)
{
	var action = "WaterConnectionHome.html";
	var url	=	action+'?cleareFiles';
   var count= __doAjaxRequest(url,'post',{},false);
   
   for(var i = 0; i < count; i++)
	   {
	  $("#file_list_"+i).find("*").remove(); 
	   }
    $("#file_list_"+i).hide();
	fileUploadMultipleValidationList();
}


function setHouseType(obj){
	var obj1 =$(obj).find('option:selected').val();
	
	housetTypeCode=obj.options[ obj.selectedIndex ].text;
	
	$("#houseTypeHidden").val(obj1);
	
	

}
function setelEctrolWardMhHidden(obj){
	var obj1 =$(obj).find('option:selected').val();
	
	var electrolWardMhHidden=obj.options[ obj.selectedIndex ].text;
	
	$("#electrolWardMhHidden").val(electrolWardMhHidden);
	
	
}
function setelEctrolWardKdHidden(obj){
	var obj1 =$(obj).find('option:selected').val();
	
	var electrolWardkdHidden=obj.options[ obj.selectedIndex ].text;
	
	$("#electrolWardkdHidden").val(electrolWardkdHidden);
	
	
}

$( document ).ready(function() {
	
	if($('#mode').val() == 'R') {
	
		$('input[type=text]').prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#applicantTitle").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#title").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#gender").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");

		$("#getCheckList").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		//$("#divid").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#ward").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#electrolWardKdmc").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#zone").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#cOTitle").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#cityK").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#talukaK").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#districtK").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		
		$(".houseTypeR").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#houseTypeK").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#tariffCatK").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#tariffSubCat").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#premiseTypeForKD").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#codDwzId1").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#codDwzId2").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#codCcgId1").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#codTrfId1").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#ccnType").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		$("#ccSApprovedById").prop('disabled',true).removeClass("mandClassColor").addClass("disablefield");
		
		$('.mand').hide();
		$('.mand-label').hide();
		var code = $("#getCheckList").find("option:selected").attr('code');
		if(code=='NRUN'){
			$("#sizeService").show();
			$("#dateService").show();
			$("#nrunShow").show();
			$("#nrunHide").hide();
			$("#houseTypeHidden").val("0");
			housetTypeCode="";
		
		}
		else{
			$("#sizeService").hide();
			$("#dateService").hide();
			$("#nrunShow").hide();
			$("#nrunHide").show();
			
		}
		
		if(code=='NSLUM'){
			$("#propService").hide();
			$("#ServiceSlum").show();
			
		}
		else{
			$("#propService").show();
			$("#ServiceSlum").hide();
		}
		if($("#ccReasonKdmcId").val()!=""){			
			 $("#ccDateId").show();
			  $("#ccReasonId").show();
			  $("#approvedById").show();
			 if( $("#dto.csCcDate").val()!=undefined  && $("#dto.csCcDate").val()!="")
			  $("input[name='dto.csCcDate']").val($("input[name='dto.csCcDate']").val().substring(0, 10));
		}
		 	
	  }

});


$(document).ready(function(){
	
	 $('.widget').keypress(function(event){
		    
		    if (event.keyCode == 10 || event.keyCode == 13) 
		        event.preventDefault();
		    
	});
	 
	if($("#serviceLiveflag").val()=='N'){
		
		$("#propertyNoMH").addClass("disablefield").attr("disabled", true);
		$("#flatMvald").addClass("disablefield").attr("disabled", true);
		$("#lcOldccn0").addClass("disablefield").attr("disabled", true);
		$("#hasSameAdd").addClass("disablefield").attr("disabled", true);		
		
	}
	
	var myDate = new Date(); 
	$("#dto.assesmentDate").datepicker({
        dateFormat: 'dd/mm/yy',
		maxDate: myDate,
		changeMonth: true,
		changeYear: true
		
	});
	if($("input[name='dto.assesmentDate']").val()!="" && $("input[name='dto.assesmentDate']").val()!=undefined)
	{
	var lastchar1 = $("input[name='dto.assesmentDate']").val().substring(0, 10);
	$("input[name='dto.assesmentDate']").val(lastchar1);
	}
	
	$("#dto.assesmentDateforUthK").datepicker({
        dateFormat: 'dd/mm/yy',
		maxDate: myDate,
		changeMonth: true,
		changeYear: true
		
	});
	
	if($("input[name='dto.assesmentDateforUthK']").val()!="" && $("input[name='dto.assesmentDateforUthK']").val()!=undefined)
	{
	var lastchar2 = $("input[name='dto.assesmentDateforUthK']").val().substring(0, 10);
	$("input[name='dto.assesmentDateforUthK']").val(lastchar2);
	}
	
	
	$("#dto.dateK").datepicker({
        dateFormat: 'dd/mm/yy',
		maxDate: myDate,
		changeMonth: true,
		changeYear: true
		
	});
	
	if($("input[name='dto.dateK']").val()!="" && $("input[name='dto.dateK']").val()!=undefined)
	{
	var lastchar = $("input[name='dto.dateK']").val().substring(0, 10);
	$("input[name='dto.dateK']").val(lastchar);
	}
	
	if($("#dataBaseType").val()=="M"){
		
		$("#csaddM").blur(function (){
			
			if($.trim($("#csaddM").val()) == ""){
				$("#hasSameAddBA").attr("checked", false);
				
				$("#bABulidName").val("");
				$(".bARoadNameM").val("");
				$(".bAcontactNoM").val("");
				
				$("#bABulidName").val("");	
				$("#csBaddM").val("");	
				
				$("#bAHouseNoM").val("");
				
				$("#bABulidName").removeClass("disablefield").attr("readonly", false);
				$(".bARoadNameM").removeClass("disablefield").attr("readonly", false);
				$(".bAcontactNoM").removeClass("disablefield").attr("readonly", false);
				$("#csBaddM").removeClass("disablefield").attr("readonly", false);
				$("#bAorgNameM").removeClass("disablefield").attr("readonly", false);
				$("#bAHouseNoM").removeClass("disablefield").attr("readonly", false);
				
				$("#copyToBillingAddressId").val("N");
			}
			
		});
	}	
	
});


/*function copyToConnectionOwnerDetail(){

	if($("#propertyNoMH").val()!=""){
		
		$("#cOTitle").val(ownerTitle);
		
		$("#firstNameco").val($("#firstNamepo").val());

		//$("#fatherOrHusbandNameco").val($("#fatherOrHusbandName").val());
		$('.fatherOrHusbandNameco').val($('.middleName').val());
		$('.orgNameConsumer').val($('.pOorgnameM').val());
		$('.cOcontactNo').val(contactNoConsumer);
		$('.cOconsumerAdharNoM').val(uidNo);
		$('.cORoadNameM').val(roadName);
		
		$("#lastNameco").val($("#lastNamepo").val());

		$("#cOBulidNameM").val($("#pObuldingNameM").val());
		
		$("#csaddM").val($("#csOaddM").val());
		$("#cOHouseNo").val($("#pOhouseNoM").val());
		
	}
}*/




		function showWaterError(errorList){
			$('.error-div').hide();
			var errMsg = '<div class="closeme">	<img alt="Close" title="Close" src="css/images/close.png" align="right"  onclick="closeErrBox()" width="32"/></div><ul><li><i class="fa fa-exclamation-circle"></i> &nbsp;<i class="fa fa-exclamation-circle"></i> &nbsp;'+errorList+'</li></ul>';
			$(".error-div").html(errMsg);
			$('.error-div').show();
			$("html, body").animate({ scrollTop: 0 }, "slow");
		}
		
		
		function checkDecimal(obj)
		{
			var regx	=   /^\d{0,4}(\.\d{0,2})?$/;
			
			var size	=	obj.value;

			if(!regx.test(size))
			{
				size = size.substring(0, size.length-1);
				
				$(obj).val(size);	
			} 
			
		}

function downloadFile(filePath,action)
{
	var my_form=document.createElement('FORM');
	my_form.name='myForm';
	my_form.method='POST';
	my_form.action=action;
	my_form.target='_blank';
	
	var	my_tb=document.createElement('INPUT');
	my_tb.type='HIDDEN';
	my_tb.name='downloadLink';
	my_tb.value=filePath;
	my_form.appendChild(my_tb); 
	
	document.body.appendChild(my_form);
	my_form.submit();
}



function check_decimal_digit(e, obj, intsize, deczize) {
	var keycode;

	if (window.event)
		keycode = window.event.keyCode;
	else if (e) {
		keycode = e.which;
	} else {
		return true;
	}

	var fieldval = (obj.value), dots = fieldval.split(".").length;

	if (keycode == 46) {
		return dots <= 1;
	}
	if (keycode == 8 || keycode == 9 || keycode == 46 || keycode == 13) {
		// back space, tab, delete, enter 
		return true;
	}
	if ((keycode >= 32 && keycode <= 45) || keycode == 47
			|| (keycode >= 58 && keycode <= 127)) {
		return false;
	}
	if (fieldval == "0" && keycode == 48) {
		return false;
	}
	if (fieldval.indexOf(".") != -1) {
		if (keycode == 46) {
			return false;
		}
		var splitfield = fieldval.split(".");
		if (splitfield[1].length >= deczize && keycode != 8 && keycode != 0)
			return false;
	} else if (fieldval.length >= intsize && keycode != 46) {
		return false;
	} else {
		return true;
	}
}

function saveAndPayFordashboard(element)
{
	
	
	return saveOrUpdateForm(element, '','AdminHome.html','redirectToPay');
}

/**
 * 
 */
function saveChangeOfOwnerShip(element) { 
	
	var errorList = [];
	errorList = validateChangeOfOwnershipFormData(errorList);
	if (errorList.length == 0) {
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'Y') {
			
			 return saveOrUpdateForm(element,"Your application for change Of ownership saved successfully!", 'ChangeOfOwnership.html?redirectToPay', 'saveform');
			}
			else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'N')
				{
				 return saveOrUpdateForm(element,"Your application for change Of ownership saved successfully!", 'ChangeOfOwnership.html?PrintReport', 'saveform');
				}
	} else {
		displayErrorsOnPage(errorList);
	}
	
	
}

/**
 * this function being used to edit application at scrutiny level
 * @param element
 * @returns
 */
function editChangeOfOwnershipInfo(element) {
	var errorList = [];
	errorList = validateChangeOfOwnershipFormData(errorList);
	
	if (errorList.length == 0) {
		
		return saveOrUpdateForm(element,"Application for change of ownership has updated succesfully!", '', 'saveform');
	}
}

$(function(){
	$('#editBtn').click(function(){
		/*$('#searchConnection').attr('disabled',false);*/
		$("#submitBtn").attr('disabled',false);
		$('input[type=text]').attr('disabled',false);
		$('select').attr("disabled", false);
		$('#editBtn').attr('disabled',true);
		$('#scrutinyBtn').attr('disabled',true);
		$('.trfClass').attr('disabled',true);
		/*// disable applicant info
		$('#applicantTitle').attr('disabled', true);
		$('#firstName').attr('disabled', true);
		$('#middleName').attr('disabled', true);
		$('#lastName').attr('disabled', true);
		$('#mobileNo').attr('disabled', true);
		$('#emailId').attr('disabled', true);
		$('#flatNo').attr('disabled', true);
		$('#buildingName').attr('disabled', true);
		$('#roadName').attr('disabled', true);
		$('#areaName').attr('disabled', true);
		$('#pinCode').attr('disabled', true);
		$('#adharNo').attr('disabled', true);
		$('#dwzid1').attr('disabled', true);
		$('#dwzid2').attr('disabled', true);*/
		$('#remark').attr('disabled',false);
		// disable old owner section
		$('#conNum').attr('disabled',true);
		$('#conName').attr('disabled',true);
		$('#oldAdharNo').attr('disabled',true);
		$('#conSize').attr('disabled',true);
		$('#conSize').attr('disabled',true);
		
		
	});
});

/*$(function(){
	$('#submitBtn').click(function(){
		
	    $(this).val('Please wait ...')
	      .attr('disabled','disabled');
	    $('#changeOfOwnerForm').submit();
		
	    return false;
		
		
	});
});*/

function editChangeOfOwnershipByDept(element) {
	
	var errorList = [];
	errorList = validateOwnershipDataOnEdit(errorList);
	if (errorList.length == 0) {
		var response = saveOrUpdateForm(element,"Application for change of ownership has Edited succesfully!", '', 'saveform');
		$('#editBtn').attr('disabled',false);
		$("#submitBtn").attr('disabled',true);
		$('#scrutinyBtn').attr('disabled',false);
		//disabling form fields
		$('input[type=text]').attr('disabled',true);
		$('input[type=textarea]').attr('disabled',true);
		$('#remark').attr('disabled',true);
		$('select').attr("disabled", true);
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateOwnershipDataOnEdit(errorList) {
	
	var applicantTitle= $.trim($('#applicantTitle').val());
	var firstName= $.trim($('#firstName').val());
	var lastName= $.trim($('#lastName').val());
	var gender= $.trim($('#gender').val());
	var applicantMobileNo= $.trim($('#mobileNo').val());
	var applicantBlockName= $.trim($('#blockName').val());
	var applicantVillTownCity= $.trim($('#villTownCity').val());
	var applicantPinCode= $.trim($('#pinCode').val());
	var applicantPovertyLineId= $.trim($('#povertyLineId').val());
	var chNewTitle= $.trim($('#chNewTitle').val());
	var chNewName= $.trim($('#ownerDTO\\.chNewName').val());
	var chNewLname= $.trim($('#ownerDTO\\.chNewLname').val()); 
	var newGender= $.trim($('#newGender').val()); 
	
	if(applicantTitle =="" || applicantTitle =='0' || applicantTitle == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantNameTitle'));
	 }
	 if(firstName =="" || firstName == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantFirstName'));
	 }
	 if(lastName == "" || lastName == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantLastName'));
	 }
	 if(gender == "" || gender =='0' || gender == undefined ){
		 errorList.push('Gender must be selected.');
	 }
	 if(applicantMobileNo == "" || applicantMobileNo == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
	 }
	 if(applicantBlockName == "" || applicantBlockName == undefined){
		 errorList.push('Block name cannot be empty.');
	 }
	 if(applicantVillTownCity == "" || applicantVillTownCity == undefined){
		 errorList.push('Village/Town/City cannot be empty.');
	 }
	 if(applicantPinCode == "" || applicantPinCode == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantPinCode'));
	 }
	 if(applicantPovertyLineId == "" || applicantPovertyLineId =='0'|| applicantPovertyLineId == undefined){
		 errorList.push('Is below poverty line must be selected');
	 } else {
		 if (povertyLineId == 'Y') {
			 var bplNo= $.trim($('#bplNo').val());
			 if (bplNo == '' || bplNo == undefined) {
				 errorList.push(getLocalMessage('water.validation.bplnocantempty'));
			 }
		 }
	 }
	 if(chNewTitle == "" || chNewTitle =='0' || chNewTitle == undefined){
		 errorList.push('New owner title must be selected.');
	 }
	 if(chNewName == "" || chNewName == undefined){
		 errorList.push('New owner first name cannot be empty.');
	 }
	 if(chNewLname == "" || chNewLname == undefined){
		 errorList.push('New owner last name cannot be empty.');
	 }
	 if(newGender == "" || newGender =='0' || newGender == undefined){
		 errorList.push('New owner gender must be selected.');
	 }
	 
	 return errorList;
	
}

/**
 * function being used to open form in edit mode
 * @param element
 */
function editForm(element)
{
	$("#submitdiv").show();
	$('input[type=text]').attr('disabled',false);
	$('select').attr("disabled", false);
	
}



function validateChangeOfOwnershipFormData(errorList) {
	
	var applicantTitle= $.trim($('#applicantTitle').val());
	var firstName= $.trim($('#firstName').val());
	var lastName= $.trim($('#lastName').val());
	var applicantMobileNo= $.trim($('#mobileNo').val());
	var applicantAreaName= $.trim($('#areaName').val());
	var applicantPinCode= $.trim($('#pinCode').val());
	var applicantAdharNo= $.trim($('#adharNo').val());
	var conNum= $.trim($('#conNum').val());
	var cooNotitle= $.trim($('#cooNotitle').val());
	var cooNoname= $.trim($('#changeOwnerMaster\\.cooNoname').val()); 
	var cooNolname= $.trim($('#changeOwnerMaster\\.cooNolname').val()); 
	
	var payMode= $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val();

	 if(applicantTitle =="" || applicantTitle =='0' || applicantTitle == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantNameTitle'));
	 }
	 if(firstName =="" || firstName == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantFirstName'));
	 }
	 if(lastName == "" || lastName == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantLastName'));
	 }
	 if(applicantMobileNo == "" || applicantMobileNo == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
	 }
	 if(applicantAreaName == "" || applicantAreaName == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantarea'));
	 }
	 if(applicantPinCode == "" || applicantPinCode == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantPinCode'));
	 }
	 if(applicantAdharNo == "" || applicantAdharNo == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantAdharNo'));
	 }
	 if(conNum == "" || conNum == undefined){
		 errorList.push(getLocalMessage('water.validation.connectionno'));
	 }
	 if(cooNotitle == "" || cooNotitle =='0' || cooNotitle == undefined){
		 errorList.push(getLocalMessage('water.validation.ctitle'));
	 }
	 if(cooNoname == "" || cooNoname == undefined){
		 errorList.push(getLocalMessage('water.validation.ownerfirstname'));
	 }
	 if(cooNolname == "" || cooNolname == undefined){
		 errorList.push(getLocalMessage('water.validation.ownerlastname'));
	 }
	 if(payMode == "" || payMode == undefined){
		 errorList.push(getLocalMessage('water.validation.paymode'));
	 }
	 
	return errorList;	

}


function displayErrorsOnPage(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';

	errMsg += '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';

	$('.error-div').html(errMsg);
	$(".error-div").show();
	$('html,body').animate({ scrollTop: 0 }, 'slow');
	return false;
}


function closeOutErrBox(){
	$('.error-div').hide();
}


/**
 * additional owner event
 */
$(function(){
	
	$("#customFields").on('click','.addCF',function(i){
		
		var row=0;
		var errorList = [];
		errorList = validateAdditionalOwners(errorList);
		
		if (errorList.length == 0) {
			if (errorList.length == 0 ) {
				 var romm=0;
				var content = $(this).closest('tr').clone();
				$(this).closest("tr").after(content);
				var clickedIndex = $(this).parent().parent().index() - 1;	
				content.find("input:text").val('');
				content.find("select").val('0');
		
				
				reOrderTableIdSequence();
			}else {
				displayErrorsOnPage(errorList);
			}
			
		}else {
			displayErrorsOnPage(errorList);
		}
	
});
	
$("#customFields").on('click','.remCF',function(){
		
		if($("#customFields tr").length != 2)
			{
				 $(this).parent().parent().remove();					
				 reOrderTableIdSequence();
			}
	   else
			{
				alert("You cannot delete first row");
			}
		
 });
});

/**
 * validate additional owners details
 * @param errorList
 */
function validateAdditionalOwners(errorList) {
	
	errorList = validateApplicantInfo(errorList);
	errorList = validateOldOwnerInfo(errorList);
	errorList = validateNewOwnerInfo(errorList);
//	alert('errorlist='+errorList.length);
	if (errorList.length == 0) {
		$('.appendableClass').each(function(i) {
			row=i+1;
			errorList = validateAdditionalOwnerTableData(errorList,i);
			
		});
	}
	
	return errorList;
}

/**
 * 
 */
function reOrderTableIdSequence() {

	
	$('.appendableClass').each(function(i) {

//		$(this).find("td:eq(0)").attr("id", "srNoId_"+i);
		$(this).find("select:eq(0)").attr("id", "caoNewTitle_"+i);
		$(this).find("input:text:eq(0)").attr("id", "caoNewFName_"+i);
		$(this).find("input:text:eq(1)").attr("id", "caoNewMName_"+i);
		$(this).find("input:text:eq(2)").attr("id", "caoNewLName_"+i);
		$(this).find("select:eq(1)").attr("id", "caoNewGender_"+i);
		$(this).find("input:text:eq(3)").attr("id", "caoNewUID_"+i);
		$("#srNoId_"+i).text(i+1);

		$(this).find("select:eq(0)").attr("name","additionalOwners["+i+"].caoNewTitle");
		$(this).find("input:text:eq(0)").attr("name","additionalOwners["+i+"].caoNewFName");	
		$(this).find("input:text:eq(1)").attr("name","additionalOwners["+i+"].caoNewMName");	
		$(this).find("input:text:eq(2)").attr("name","additionalOwners["+i+"].caoNewLName");	
		$(this).find("select:eq(1)").attr("name","additionalOwners["+i+"].caoNewGender");
		$(this).find("input:text:eq(3)").attr("name","additionalOwners["+i+"].caoNewUID");	
		
				
	});
	
}

/**
 * validate applicant info
 * @param errorList
 * @returns
 */
function validateApplicantInfo(errorList) {
	
	var applicantTitle= $.trim($('#applicantTitle').val());
	var firstName= $.trim($('#firstName').val());
	var lastName= $.trim($('#lastName').val());
	var gender= $.trim($('#gender').val());
	var applicantMobileNo= $.trim($('#mobileNo').val());
	var applicantAreaName= $.trim($('#areaName').val());
	var blockName= $.trim($('#blockName').val());
	var villTownCity= $.trim($('#villTownCity').val());
	var applicantPinCode= $.trim($('#pinCode').val());
	var applicantAdharNo= $.trim($('#adharNo').val());
	var povertyLineId= $.trim($('#povertyLineId').val());
	
	if(applicantTitle =="" || applicantTitle =='0' || applicantTitle == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantNameTitle'));
	 }
	 if(firstName =="" || firstName == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantFirstName'));
	 }
	 if(lastName == "" || lastName == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantLastName'));
	 }
	 if(gender == "" || gender =='0' || gender == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantGender'));
	 }
	 if(applicantMobileNo == "" || applicantMobileNo == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
	 }
	 if(applicantAreaName == "" || applicantAreaName == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantarea'));
	 }
	 if(blockName == "" || blockName == undefined){
		 errorList.push(getLocalMessage('water.validation.ApplicantBlockName'));
	 }
	 if(villTownCity == "" || villTownCity == undefined){
		 errorList.push(getLocalMessage('water.validation.ApplicantcityVill'));
	 }
	 if(applicantPinCode == "" || applicantPinCode == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantPinCode'));
	 }
	 if(povertyLineId == "" || povertyLineId =='0'|| povertyLineId == undefined){
		 errorList.push(getLocalMessage('water.validation.isabovepovertyline'));
	 } else {
		 if (povertyLineId == 'Y') {
			 var bplNo= $.trim($('#bplNo').val());
			 if (bplNo == '' || bplNo == undefined) {
				 errorList.push(getLocalMessage('water.validation.bplnocantempty'));
			 }
		 }
	 }
	 
	 return errorList;
}

/**
 * to validate New Owner Information
 * @param errorList
 * @returns
 */
function validateNewOwnerInfo(errorList) {
	
	var cooNotitle= $.trim($('#cooNotitle').val());
	var cooNoname= $.trim($('#changeOwnerMaster\\.cooNoname').val()); 
	var cooNolname= $.trim($('#changeOwnerMaster\\.cooNolname').val()); 
	
	if(cooNotitle == "" || cooNotitle =='0' || cooNotitle == undefined){
		 errorList.push(getLocalMessage('water.validation.ctitle'));
	 }
	 if(cooNoname == "" || cooNoname == undefined){
		 errorList.push(getLocalMessage('water.validation.ownerfirstname'));
	 }
	 if(cooNolname == "" || cooNolname == undefined){
		 errorList.push(getLocalMessage('water.validation.ownerlastname'));
	 } 
	 
	return errorList;
}



$(function(){
	$('#executionOwnerSaveBtn').click(function(){
		
		return saveOrUpdateForm(this,"Change of owner execution done successfuly.", '', 'saveform');
	});
});


