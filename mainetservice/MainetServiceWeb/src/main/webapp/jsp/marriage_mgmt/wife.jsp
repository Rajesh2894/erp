<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/marriage_mgmt/wife.js"></script>
<!-- <script type="text/javascript" src="js/mainet/file-upload.js"></script> -->
<script type="text/javascript" src="js/marriage_mgmt/mrm-file-upload.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script type="text/javascript" src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<style>
.fileUpload.fileinput.fileinput-new, .removePhotoW, #removeThumbW {
	display: inline-block;
	width: auto;
}
</style>
<div class="widget-content padding" id="wifeTabForm">
	<form:form action="MarriageRegistration.html" id="wifeFormId"
		method="post" class="form-horizontal">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<div
			class="warning-div error-div alert alert-danger alert-dismissible"
			id="errorDiv"></div>
		<form:hidden path="modeType" id="modeType" />
		<form:hidden path="marriageDTO.marId" id="marId" />
		<form:hidden path="marriageDTO.marDate" id="marDate" />
		<input type="hidden" path="" id="applicationNo" value="${command.marriageDTO.applicationId}" />
		<input type="hidden" path="" id="applicableENV" value="${command.applicableENV}" />
		<input type="hidden" path="" id="husbNriChecked" value="${command.marriageDTO.husbandDTO.nri}" />		

		<div class="panel-group accordion-toggle"
			id="accordion_single_collapse1">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse1" href="#WifeDet"><spring:message
								code="mrm.tab.wifeDetails" /></a>
					</h4>
				</div>
				
				<div class="col-sm-12">
							<form:checkbox
									path="marriageDTO.wifeDTO.nri" id="wifeNriApplicable"  class="margin-top-10 margin-left-10"  value="N"></form:checkbox>					
								<label class="" style="margin-left:34px;margin-top: 9px;margin-bottom: 15px;"><spring:message code="mrm.wifeNRI" text="Is Wife NRI ?"/></label>		
	
						</div>

				<div id="WifeDet" class="panel-collapse collapse in">
					<div class="panel-body">

						<div class="form-group">
							<apptags:input labelCode="mrm.wife.firstNameE"
								path="marriageDTO.wifeDTO.firstNameEng"
								isDisabled="${command.modeType eq 'V'}" isMandatory="true"
								cssClass="hasCharacter form-control" maxlegnth="100"></apptags:input>
							<apptags:input labelCode="mrm.wife.firstNameR"
								path="marriageDTO.wifeDTO.firstNameReg"
								isDisabled="${command.modeType eq 'V'}" isMandatory="true"
								cssClass=" form-control hasNameClass" maxlegnth="100"></apptags:input>
						</div>
						<div class="form-group">
							<apptags:input labelCode="mrm.wife.middleNameE"
								path="marriageDTO.wifeDTO.middleNameEng"
								isDisabled="${command.modeType eq 'V'}" isMandatory="false"
								cssClass="hasCharacter form-control" maxlegnth="100"></apptags:input>
							<apptags:input labelCode="mrm.wife.middleNameR"
								path="marriageDTO.wifeDTO.middleNameReg"
								isDisabled="${command.modeType eq 'V'}" isMandatory="false"
								cssClass="  form-control hasNameClass" maxlegnth="100"></apptags:input>
						</div>
						<div class="form-group">
							<apptags:input labelCode="mrm.wife.lastNameE"
								path="marriageDTO.wifeDTO.lastNameEng"
								isDisabled="${command.modeType eq 'V'}" isMandatory="true"
								cssClass="hasCharacter form-control" maxlegnth="100"></apptags:input>
							<apptags:input labelCode="mrm.wife.lastNameR"
								path="marriageDTO.wifeDTO.lastNameReg"
								isDisabled="${command.modeType eq 'V'}" isMandatory="true"
								cssClass="  form-control hasNameClass" maxlegnth="100"></apptags:input>
						</div>
						<div class="form-group">
							<apptags:input labelCode="mrm.wife.otherName"
								path="marriageDTO.wifeDTO.otherName"
								isDisabled="${command.modeType eq 'V'}" isMandatory="false"
								cssClass="form-control " maxlegnth="100"></apptags:input>
								
							<label class="col-sm-2 control-label required-control uidLabelW" ><spring:message code="mrm.wife.uid" text="UID" /></label>
							<div class="col-sm-4">
								<form:input path="marriageDTO.wifeDTO.uidNo"
										cssClass="form-control" readonly="${command.modeType eq 'V'}"
										maxlength="14" id="uidNo"  />
							</div>
							
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label required-control" for=""><spring:message
									code="mrm.wife.dob" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input class="form-control datepicker" id="wdob"
										path="marriageDTO.wifeDTO.dob" isMandatory="true"
										maxlength="10" disabled="${command.modeType eq 'V'}"></form:input>
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span>
								</div>
							</div>
						</div>
						<div class="form-group">

							<apptags:input labelCode="mrm.wife.year"
								path="marriageDTO.wifeDTO.year" isReadonly="true"
								isMandatory="false" cssClass="form-control hasNumber"
								maxlegnth="3"></apptags:input>
							<apptags:input labelCode="mrm.wife.month"
								path="marriageDTO.wifeDTO.month" isReadonly="true"
								isMandatory="false" cssClass="form-control hasNumber"
								maxlegnth="2"></apptags:input>
						</div>
						<div class="form-group">
							<c:set var="baseLookupCodeRLG" value="RLG" />
							<label class="col-sm-2 control-label required-control "
								for="assetgroup"> <spring:message
									code="mrm.wife.religionByBirth" />
							</label>
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCodeRLG)}"
								path="marriageDTO.wifeDTO.religionBirth"
								disabled="${command.modeType eq 'V'}"
								cssClass="form-control chosen-select-no-results"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="true" />

							<label class="col-sm-2 control-label"
								for="assetgroup"> <spring:message
									code="mrm.wife.otherReligion" />
							</label>
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCodeRLG)}"
								path="marriageDTO.wifeDTO.religionAdopt"
								disabled="${command.modeType eq 'V'}"
								cssClass="form-control chosen-select-no-results"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="false" />
						</div>
						
						<div class="form-group">
							<apptags:lookupFieldSet cssClass="form-control required-control"
								baseLookupCode="CST" hasId="true" pathPrefix="marriageDTO.wifeDTO.wcaste"
								hasLookupAlphaNumericSort="true" disabled="${command.modeType eq 'V'}"
								hasSubLookupAlphaNumericSort="true" showAll="false"
								isMandatory="false" />

						</div>
						
						<div class="form-group">
							<c:set var="baseLookupCodeOCU" value="OCU" />
							<label class="col-sm-2 control-label required-control "
								for="assetgroup"> <spring:message
									code="mrm.wife.occupation" />
							</label>
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCodeOCU)}"
								path="marriageDTO.wifeDTO.occupation"
								disabled="${command.modeType eq 'V'}"
								cssClass="form-control chosen-select-no-results"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="true" />

							<c:set var="baseLookupCodeSTA" value="STA" />
							<label class="col-sm-2 control-label required-control "
								for="assetgroup"> <spring:message
									code="mrm.wife.statusAtMarriageTime" />
							</label>
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCodeSTA)}"
								path="marriageDTO.wifeDTO.statusMarTime"
								disabled="${command.modeType eq 'V'}"
								cssClass="form-control chosen-select-no-results"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="true" />
						</div>

						<div class="form-group">
							<apptags:textArea labelCode="mrm.wife.fullAddress"
								path="marriageDTO.wifeDTO.fullAddrEng"
								isDisabled="${command.modeType eq 'V'}" isMandatory="true"
								maxlegnth="175"></apptags:textArea>
							<apptags:textArea labelCode="mrm.wife.fullAddressR"
								path="marriageDTO.wifeDTO.fullAddrReg"
								isDisabled="${command.modeType eq 'V'}" isMandatory="true"
								maxlegnth="175"></apptags:textArea>
						</div>

					</div>
				</div>
				<%-- <form:hidden path="photoThumbDisp" id="photoThumbDispW" /> --%>
				
			</div>
		</div>

		<c:if test="${command.approvalProcess ne 'Y' }">
			<div class="text-center">
				<c:choose>
					<c:when
						test="${command.modeType eq 'C' || command.modeType eq 'V' || command.modeType eq 'D' }">
						<c:set var="backButtonAction" value="showTab('#husband')" />
					</c:when>
					<c:otherwise>
						<c:set var="backButtonAction" value="backToHomePage()" />
					</c:otherwise>
				</c:choose>
				<c:if
					test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'D' }">
					<button type="button" class="button-input btn btn-success"
						name="button" value="Save" onclick="saveWife(this);" id="save">
						<spring:message code="mrm.button.save&next" />
					</button>
				</c:if>
				<c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
					<button type="Reset" class="btn btn-warning" onclick="resetWife()">
						<spring:message code="mrm.button.reset" text="Reset" />
					</button>
				</c:if>
				<button type="button" class="btn btn-danger" name="button" id="Back"
					value="Back" onclick="${backButtonAction}">
					<spring:message code="mrm.button.back" />
				</button>
				<c:if test="${command.conditionFlag eq 'CLOSE_BT'}">
					<button type="button" class="btn btn-danger" onclick="window.close();">
						<spring:message code="mrm.acknowledgement.close" text="Close"></spring:message>
					</button>
				</c:if>
			</div>
		</c:if>
		
	</form:form>
</div>

<script>
var subDate=moment($('#marDate').val(),"DD/MM/YYYY").subtract(18,'years');
var subFormatDate=moment(subDate).format("DD/MM/YYYY")

//D#127368
var fromDate=moment($('#marDate').val(),"DD/MM/YYYY");
var ageWarning = false;
if('${command.applicableENV}' == 'true'){
	//D#128500 minor age validation
	subDate=moment($('#marDate').val(),"DD/MM/YYYY").subtract(12,'years');
	subFormatDate=moment(subDate).format("DD/MM/YYYY")
	ageWarning = true;
	
}

$('#wdob').datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	maxDate: subFormatDate,
	yearRange: "-200:+200",
	onSelect : function(selected) {
		/* let today = moment($('#marDate').val(),"DD/MM/YYYY");
		today=new Date(today);
		let ageInYears = today.getFullYear() - ui.selectedYear;
		let ageInMonths = "";
		let monthDiff = today.getMonth() - ui.selectedMonth;
		if(monthDiff < 0) {
			ageInYears = ageInYears - 1;
			ageInMonths = monthDiff + 12;
		} else {
			ageInMonths = monthDiff;
		}
		$("#wifeTabForm input[id=year]").val(ageInYears);
		$("#wifeTabForm input[id=month]").val(ageInMonths); */
		if(ageWarning){
			popupAgeWarning();
		}
    	setYearAndMonth(fromDate,selected);
	}
});

$("#wdob").keyup(function(e) {

	if (e.keyCode != 8) {
		if ($(this).val().length == 2) {
			$(this).val($(this).val() + "/");
		} else if ($(this).val().length == 5) {
			$(this).val($(this).val() + "/");
		}
	}
});

//D#126769
$('#wdob').keydown(function(e) {
    var code = e.keyCode || e.which;
    if (code === 9) {  
        e.preventDefault();
        //set year and month
        let wifeDOB=$('#wdob').val();
        let wifemomdob=moment(wifeDOB,"DD/MM/YYYY");
        if((moment(wifeDOB, 'DD/MM/YYYY',true).isValid())){
        	/* let month=wifemomdob.month();
        	let year=wifemomdob.year();
        	
        	let today = moment($('#marDate').val(),"DD/MM/YYYY");
    		today=new Date(today);
    		
        	let ageInYears = today.getFullYear() - year;
    		let ageInMonths = "";
    		let monthDiff = today.getMonth() - month;
    		if(monthDiff < 0) {
    			ageInYears = ageInYears - 1;
    			ageInMonths = monthDiff + 12;
    		} else {
    			ageInMonths = monthDiff;
    		}
    		$("#wifeTabForm input[id=year]").val(ageInYears);
    		$("#wifeTabForm input[id=month]").val(ageInMonths); */
    		
        	if(ageWarning){
    			popupAgeWarning();
    		}
        	setYearAndMonth(fromDate,wifemomdob);
        	
        }
    }
});

	var wifeDOB = $("#wifeTabForm input[id=wdob]").val();
	if (wifeDOB) {
		$("#wifeTabForm input[id=wdob]").val(wifeDOB.split(' ')[0]);
	}
	
	//D#127368
	function setYearAndMonth(fromDate, toDate){
		var year=Number(moment(fromDate,"DD/MM/YYYY").diff(moment(toDate,"DD/MM/YYYY"),'year'));
		var month=Number(moment(fromDate,"DD/MM/YYYY").diff(moment(toDate,"DD/MM/YYYY"),'month'));
		
		$("#wifeTabForm input[id=year]").val(year);
		$("#wifeTabForm input[id=month]").val(month);
		
	}


	//D#127368
	function popupAgeWarning(){
		//POPUP for less than 18 age
		let marDate= moment($('#marDate').val(),"DD/MM/YYYY");
		let dob = $("#wifeTabForm input[id=wdob]").val();
		let year=Number(moment(marDate,"DD/MM/YYYY").diff(moment(dob,"DD/MM/YYYY"),'year'));
		if(year>=18){
			//valid age 
		}else if(year>=12 && year<=17){
			//POPUP Warning MSG age is less than 18 / 21
			let yes = getLocalMessage('mrm.draft.appNo.popup.continue');
			
			let warnMsg= getLocalMessage('mrm.popup.ageWarningWife');
			 
			 message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
			 message	+='<div class=\'text-center padding-bottom-10\'>'+	
			'<input class="btn btn-success" type=\'button\' value=\''+yes+'\'  id=\'yes\' '+ 
			' onclick="warningClose()"/>'+	
			'</div>';
			$(childDivName).addClass('ok-msg').removeClass('warn-msg');
			$(childDivName).html(message);
			$(childDivName).show();
			$('#yes').focus();
			showModalBox(childDivName);
			return false;
			
		}
	}

	function warningClose(){
		$.fancybox.close();
	}
	

	if('${command.marriageDTO.wifeDTO.nri}' == "Y"){
		$("#wifeNriApplicable").prop("checked",true)
		$('#wifeNriApplicable').val("Y");
	}else{
		$("#wifeNriApplicable").prop("checked",false)
		$('#wifeNriApplicable').val("N");
	}
	
</script>