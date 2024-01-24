<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/marriage_mgmt/husband.js"></script>
<!-- <script type="text/javascript" src="js/mainet/file-upload.js"></script> -->
<script type="text/javascript" src="js/marriage_mgmt/mrm-file-upload.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script type="text/javascript" src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<style>
.fileUpload.fileinput.fileinput-new, .removePhoto, #removeThumb {
	display: inline-block;
	width: auto;
}
</style>
<div class="widget-content padding" id="husbandTabForm">
	<form:form action="MarriageRegistration.html" id="husbandFormId"
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
		<input type="hidden" path="" id="wifeNriChecked" value="${command.marriageDTO.wifeDTO.nri}" />
		

		<div class="panel-group accordion-toggle"
			id="accordion_single_collapse1">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse1" href="#HusbandDet"><spring:message
								code="mrm.tab.husbandDetails" /></a>
					</h4>
				</div>
				
				
					<div class="col-sm-12">
							<form:checkbox
									path="marriageDTO.husbandDTO.nri" id="husNriApplicable"  class="margin-top-10 margin-left-10"  value="N"></form:checkbox>					
								<label class="" style="margin-left:34px;margin-top: 9px;margin-bottom: 15px;"><spring:message code="mrm.husNRI" text="Is Husband is NRI ?"/></label>		
	
						</div>

				<div id="HusbandDet" class="panel-collapse collapse in">
					<div class="panel-body">

						<div class="form-group">
							<apptags:input labelCode="mrm.husband.firstNameE"
								path="marriageDTO.husbandDTO.firstNameEng"
								isDisabled="${command.modeType eq 'V'}" isMandatory="true"
								cssClass="hasCharacter form-control" maxlegnth="100"></apptags:input>
							<apptags:input labelCode="mrm.husband.firstNameR"
								path="marriageDTO.husbandDTO.firstNameReg"
								isDisabled="${command.modeType eq 'V'}" isMandatory="true"
								cssClass="form-control hasNameClass" maxlegnth="100"></apptags:input>

						</div>
						<div class="form-group">
							<apptags:input labelCode="mrm.husband.middleNameE"
								path="marriageDTO.husbandDTO.middleNameEng"
								isDisabled="${command.modeType eq 'V'}" isMandatory="false"
								cssClass="hasCharacter form-control" maxlegnth="100"></apptags:input>
							<apptags:input labelCode="mrm.husband.middleNameR"
								path="marriageDTO.husbandDTO.middleNameReg"
								isDisabled="${command.modeType eq 'V'}" isMandatory="false"
								cssClass="form-control hasNameClass" maxlegnth="100"></apptags:input>
						</div>
						<div class="form-group">
							<apptags:input labelCode="mrm.husband.lastNameE"
								path="marriageDTO.husbandDTO.lastNameEng"
								isDisabled="${command.modeType eq 'V'}" isMandatory="true"
								cssClass="hasCharacter form-control" maxlegnth="100"></apptags:input>
							<apptags:input labelCode="mrm.husband.lastNameR"
								path="marriageDTO.husbandDTO.lastNameReg"
								isDisabled="${command.modeType eq 'V'}" isMandatory="true"
								cssClass=" form-control hasNameClass" maxlegnth="100"></apptags:input>
						</div>
						<div class="form-group">
							<apptags:input labelCode="mrm.husband.otherName"
								path="marriageDTO.husbandDTO.otherName"
								isDisabled="${command.modeType eq 'V'}" isMandatory="false"
								cssClass="hasCharacter form-control" maxlegnth="100"></apptags:input>
								
						<label class="col-sm-2 control-label required-control uidLabel" ><spring:message code="mrm.husband.uid" text="UID" /></label>
						<div class="col-sm-4">
							<form:input path="marriageDTO.husbandDTO.uidNo"
									cssClass="form-control" readonly="${command.modeType eq 'V'}"
									maxlength="14" id="uidNo"  />
						</div>
						</div>
						<div class="form-group">
							<apptags:date fieldclass="datepicker" labelCode="mrm.husband.dob"
								isDisabled="${command.modeType eq 'V'}" isMandatory="true"
								datePath="marriageDTO.husbandDTO.dob"></apptags:date>

						</div>
						<div class="form-group">
							<apptags:input labelCode="mrm.husband.year"
								path="marriageDTO.husbandDTO.year" isReadonly="true"
								isMandatory="false" cssClass="form-control hasNumber"
								maxlegnth="3"></apptags:input>
							<apptags:input labelCode="mrm.husband.month"
								path="marriageDTO.husbandDTO.month" isReadonly="true"
								isMandatory="false" cssClass="form-control hasNumber"
								maxlegnth="2"></apptags:input>
						</div>
						<div class="form-group">
							<c:set var="baseLookupCodeRLG" value="RLG" />
							<label class="col-sm-2 control-label required-control "
								for="assetgroup"> <spring:message
									code="mrm.husband.religionByBirth" />
							</label>
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCodeRLG)}"
								path="marriageDTO.husbandDTO.religionBirth"
								disabled="${command.modeType eq 'V'}"
								cssClass="form-control chosen-select-no-results"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="true" />

							<label class="col-sm-2 control-label"
								for="assetgroup"> <spring:message
									code="mrm.husband.otherReligion" />
							</label>
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCodeRLG)}"
								path="marriageDTO.husbandDTO.religionAdopt"
								disabled="${command.modeType eq 'V'}"
								cssClass="form-control chosen-select-no-results"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="false" />
						</div>
						
						<div class="form-group">
							<apptags:lookupFieldSet cssClass="form-control required-control"
								baseLookupCode="CST" hasId="true" pathPrefix="marriageDTO.husbandDTO.caste"
								hasLookupAlphaNumericSort="true" disabled="${command.modeType eq 'V'}"
								hasSubLookupAlphaNumericSort="true" showAll="false"
								isMandatory="false" />

						</div>
						
						
						
						<div class="form-group">
						<%-- 	<c:set var="baseLookupCodeOCU" value="OCU" /> --%>
							<label class="col-sm-2 control-label required-control "
								for="assetgroup"> <spring:message
									code="mrm.husband.occupation" />
							</label>
						<%-- 	<apptags:lookupField
								items="${command.getLevelData(baseLookupCodeOCU)}"
								path="marriageDTO.husbandDTO.occupation"
								disabled="${command.modeType eq 'V'}"
								cssClass="form-control chosen-select-no-results"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="true" /> --%>
                            <!-- to hide House wife option in case of Husband  -->
							<div class="col-sm-4">
								<c:set var="baseLookupCode" value="OCU" />
								<form:select path="marriageDTO.husbandDTO.occupation" disabled="${command.modeType eq 'V'}"
									class="form-control chosen-select-no-results" id="occupation">
									<form:option value="0">
										<spring:message code="" text="Select" />
									</form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">
										<c:if test="${lookUp.lookUpCode ne 'NON'}">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:if>
									</c:forEach>
								</form:select>
							</div>

							<c:set var="baseLookupCodeSTA" value="STA" />
							<label class="col-sm-2 control-label required-control "
								for="assetgroup"> <spring:message
									code="mrm.husband.statusAtMarriageTime" />
							</label>
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCodeSTA)}"
								path="marriageDTO.husbandDTO.statusMarTime"
								disabled="${command.modeType eq 'V'}"
								cssClass="form-control chosen-select-no-results"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="true" />
						</div>

						<div class="form-group">
							<apptags:textArea labelCode="mrm.husband.fullAddress"
								path="marriageDTO.husbandDTO.fullAddrEng"
								isDisabled="${command.modeType eq 'V'}" isMandatory="true"
								maxlegnth="175"></apptags:textArea>
							<apptags:textArea labelCode="mrm.husband.fullAddressR"
								path="marriageDTO.husbandDTO.fullAddrReg"
								isDisabled="${command.modeType eq 'V'}" isMandatory="true"
								maxlegnth="175"></apptags:textArea>
						</div>

					</div>
				</div>
				<%-- <form:hidden path="photoThumbDisp" id="photoThumbDisp" /> --%>
				
				
			</div>
		</div>

		<c:if test="${command.approvalProcess ne 'Y' }">
			<div class="text-center">
				<c:choose>
					<c:when
						test="${command.modeType eq 'C' || command.modeType eq 'V' || command.modeType eq 'D' }">
						<c:set var="backButtonAction" value="showTab('#marriage')" />
					</c:when>
					<c:otherwise>
						<c:set var="backButtonAction" value="backToHomePage()" />
					</c:otherwise>
				</c:choose>
				<c:if
					test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'D' }">
					<button type="button" class="button-input btn btn-success"
						name="button" value="Save" onclick="saveHusband(this);" id="save">
						<spring:message code="mrm.button.save&next" />
					</button>
				</c:if>
				<c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
					<button type="Reset" class="btn btn-warning"
						onclick="resetHusband()">
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
//D#126789
var subDate=moment($('#marDate').val(),"DD/MM/YYYY").subtract(21,'years');
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




$('#dob').datepicker({
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
		$("#husbandTabForm input[id=year]").val(ageInYears);
		$("#husbandTabForm input[id=month]").val(ageInMonths); */
		//D#127368 check here POPUP for valid age in case of SKDCL
		if(ageWarning){
			popupAgeWarning();
		}
    	setYearAndMonth(fromDate,selected);
	}
});

$("#dob").keyup(function(e) {
	if (e.keyCode != 8) {
		if ($(this).val().length == 2) {
			$(this).val($(this).val() + "/");
		} else if ($(this).val().length == 5) {
			$(this).val($(this).val() + "/");
		}
	}
});


//D#126769	
$('#dob').keydown(function(e) {
    var code = e.keyCode || e.which;
    if (code === 9) {  
        e.preventDefault();
        
        //set year and month
        let husbandDOB=$('#dob').val();
        let hsbmomdob=moment(husbandDOB,"DD/MM/YYYY");
        if((moment(husbandDOB, 'DD/MM/YYYY',true).isValid())){
        	
        	
        	/* 
        	let month=hsbmomdob.month();
        	let year=hsbmomdob.year();
        	
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
    		$("#husbandTabForm input[id=year]").val(ageInYears);
    		$("#husbandTabForm input[id=month]").val(ageInMonths); */
    		
        	if(ageWarning){
    			popupAgeWarning();
    		}
        	setYearAndMonth(fromDate,hsbmomdob);
        	
        }
    }
});

var husbandDOB = $("#husbandTabForm input[id=dob]").val();
if (husbandDOB) {
	$("#husbandTabForm input[id=dob]").val(husbandDOB.split(' ')[0]);
}

//D#127368
function setYearAndMonth(fromDate, toDate){
	var year=Number(moment(fromDate,"DD/MM/YYYY").diff(moment(toDate,"DD/MM/YYYY"),'year'));
	var month=Number(moment(fromDate,"DD/MM/YYYY").diff(moment(toDate,"DD/MM/YYYY"),'month'));
	
	$("#husbandTabForm input[id=year]").val(year);
	$("#husbandTabForm input[id=month]").val(month);
	
}


//D#127368
function popupAgeWarning(){
	//POPUP for less than 21 age
	let marDate= moment($('#marDate').val(),"DD/MM/YYYY");
	let dob = $("#husbandTabForm input[id=dob]").val();
	let year=Number(moment(marDate,"DD/MM/YYYY").diff(moment(dob,"DD/MM/YYYY"),'year'));
	if(year>=21){
		//valid age 
	}else if(year>=12 && year<=17){
		//POPUP Warning MSG age is less than 18 / 21
		let yes = getLocalMessage('mrm.draft.appNo.popup.continue');
		
		let warnMsg= getLocalMessage('mrm.popup.ageWarningHus');
		 
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

if('${command.marriageDTO.husbandDTO.nri}' == "Y"){
	$("#husNriApplicable").prop("checked",true)
	$('#husNriApplicable').val("Y");
}else{
	$("#husNriApplicable").prop("checked",false)
	$('#husNriApplicable').val("N");
}




</script>