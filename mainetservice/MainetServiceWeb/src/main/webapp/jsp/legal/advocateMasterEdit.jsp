
<%--################################################################################################################################################################## --%>

<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/legal/advocateMaster.js"></script>
<script src="js/mainet/file-upload.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>
<!-- lgl.advocateMaster.form.heading -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="lgl.advocatemaster" text="Advocate Master" />
			</h2>
			<apptags:helpDoc url="AdvocateMaster.html" />
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">

				<span><spring:message code="legal.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="legal.mand.field"
						text="is mandatory"></spring:message></span>
			</div>
			<form:form action="AdvocateMaster.html" name="AdvocateMaster"
				id="AdvocateMasterForm" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="advocateMasterDTO.advId"
						cssClass="hasNumber form-control" id="advId" />
				<form:hidden path="envFlag" id="envFlag" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<div class="form-group">
					<apptags:input labelCode="lgl.advocate.firstName"
						path="advocateMasterDTO.advFirstNm" isMandatory="true"
						cssClass="hasNameClass form-control hasNoSpace" maxlegnth="100"></apptags:input>
					<apptags:input labelCode="lgl.advocate.middleName"
						path="advocateMasterDTO.advMiddleNm" isMandatory="false"
						cssClass="hasNameClass form-control hasNoSpace" maxlegnth="100"></apptags:input>

				</div>

				<div class="form-group">
					<apptags:input labelCode="lgl.advocate.lastName"
						path="advocateMasterDTO.advLastNm" isMandatory="true"
						cssClass="hasNameClass form-control hasNoSpace" maxlegnth="100"></apptags:input>
					<apptags:input labelCode="lgl.barcouncil.no"
						path="advocateMasterDTO.adv_barCouncilNo" isMandatory="false"
						cssClass=" form-control hasNoSpace" maxlegnth="16"></apptags:input>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 " for="Census"><spring:message
							code="lgl.gender" text="Gender" /></label>
					<c:set var="baseLookupCode" value="GEN" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="advocateMasterDTO.advGen" isMandatory="false"
						cssClass="form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" />

					<%-- <apptags:date fieldclass="lessthancurrdate" labelCode="lgl.dateOfBirth"
							datePath="advocateMasterDTO.advDob" isMandatory="false"
							cssClass="custDate mandColorClass ">
						</apptags:date> --%>

					<label for="text-1" class="control-label col-sm-2"><spring:message
							code="lgl.dateOfBirth" text="Date of Birth" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input class="form-control datepicker" id="advDob"
								path="advocateMasterDTO.advDob" maxlength="10"
								onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')" />
							<label class="input-group-addon" for="trasaction-date-icon30"><i
								class="fa fa-calendar"></i></label>

						</div>
					</div>

				</div>

				<div class="form-group">
					<apptags:input labelCode="lgl.advocate.mobileNumber"
						isMandatory="true" path="advocateMasterDTO.advMobile"
						cssClass="hasMobileNo hasNoSpace " maxlegnth="10"></apptags:input>

					<apptags:input labelCode="lgl.emailid"
						path="advocateMasterDTO.advEmail" isMandatory="true"
						cssClass="hasemailclass hasNoSpace" dataRuleEmail="true">
					</apptags:input>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="pannumber"> <spring:message
							code="lgl.panNumber" /></label>
					<div class="col-sm-4">
						<form:input type="text" name="PAN Number" id="advPanno"
							path="advocateMasterDTO.advPanno"
							cssClass="form-control text-uppercase hasNoSpace" maxLength="10"
							onchange="fnValidatePAN(this)" />
					</div>

					<!-- UID : Aadhar number -->
					<apptags:input labelCode="lgl.aadharNumber"
						path="advocateMasterDTO.advUid" cssClass="hasAadharNo hasNoSpace">
					</apptags:input>

				</div>

				<div class="form-group">
					<%-- 	<apptags:date fieldclass="fromDateClass"
							labelCode="lgl.appointment.startDate"
							datePath="advocateMasterDTO.advAppfromdate"
							isMandatory="true"></apptags:date>

						<apptags:date fieldclass="toDateClass"
							labelCode="lgl.appointment.endDate"
							datePath="advocateMasterDTO.advApptodate">
						</apptags:date> --%>

					<label for="text-1" class="control-label col-sm-2 required-control">
						<spring:message code="lgl.appointment.startDate"
							text="Appointment Start Date" />
					</label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input class="form-control datepicker" id="advAppfromdate"
								path="advocateMasterDTO.advAppfromdate" maxlength="10"
								onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')" />
							<label class="input-group-addon" for="trasaction-date-icon30"><i
								class="fa fa-calendar"></i></label>

						</div>
					</div>
					<label for="text-1" class="control-label col-sm-2">
						<spring:message code="lgl.appointment.endDate"
							text="Appointment End Date" />
					</label></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input class="form-control datepicker" id="advApptodate"
								path="advocateMasterDTO.advApptodate" maxlength="10"
								onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')" />
							<label class="input-group-addon" for="trasaction-date-icon30"><i
								class="fa fa-calendar"></i></label>

						</div>
					</div>


				</div>

				<div class="form-group">

					<apptags:textArea labelCode="lgl.advocate.advocateAddress"
						isMandatory="false" path="advocateMasterDTO.advAddress"
						maxlegnth="200" cssClass="alpaSpecial"></apptags:textArea>

					<apptags:textArea labelCode="lgl.advocate.advocateOfficeAddress"
						isMandatory="true" path="advocateMasterDTO.advOfficeAddress"
						maxlegnth="200" cssClass="alpaSpecial"></apptags:textArea>
				</div>

				<div class="form-group">

					<apptags:input labelCode="lgl.advocate.experience"
						path="advocateMasterDTO.advExperience" maxlegnth="5"
						cssClass="decimal22 hasNoSpace"></apptags:input>

					<%--  <label for="advMaritalstatus"
						class="col-sm-2 control-label"><spring:message
						code="lgl.advocate.marital.status"
						text="Marital Status" /></label>
						 
						<div class="col-sm-4">
						 
						<form:select path="advocateMasterDTO.advMaritalstatus" cssClass="form-control" >  
							<form:option value=''><spring:message
						code="lgl.select"
						text="Select" /></form:option>
                			<form:option value='M'><spring:message code="lgl.advocate.marital.status.married" text="Married" />
                			</form:option>
                			<form:option value='U'><spring:message
						code="lgl.advocate.marital.status.unmarried"
						text="Unmarried" />
                			 </form:option>
            			</form:select>

						</div>  --%>
					<apptags:input labelCode="lgl.advChamberNo"
						path="advocateMasterDTO.advChamberNo"
						cssClass="hasMobileNo hasNoSpace"></apptags:input>


				</div>

				<div class="form-group">

					<%-- <apptags:input labelCode="lgl.advChamberNo" 
							path="advocateMasterDTO.advChamberNo" cssClass="hasMobileNo hasNoSpace"></apptags:input> --%>

					<label for="advFeeType" class="col-sm-2 control-label"><spring:message
							code="lgl.advocate.fee.type" text="Fee Type" /></label>


					<apptags:lookupField items="${command.getLevelData('FET')}"
						path="advocateMasterDTO.advFeeType"
						cssClass="form-control mandColorClass hasNameClass"
						selectOptionLabelCode="Select" hasId="true" isMandatory="true" />

					<label for="text-1" class="col-sm-2 control-label"> <spring:message
							code="lgl.advocate.fee.amount" text="Fee Amount" />
					</label>
					<div class="col-sm-4">
						<form:input cssClass="form-control" id="advFeeAmt"
							path="advocateMasterDTO.advFeeAmt"
							onkeypress="return hasAmount(event,this,12,2)" />
					</div>

				</div>

				<div class="form-group">

					<%-- <apptags:input labelCode="lgl.advocate.fee.amount" path="advocateMasterDTO.advFeeAmt" onBlur="hasAmount(event,this,12,2)"></apptags:input> --%>
					<%-- 		<label for = "text-1" class= "col-sm-2 control-label required-control">Fee Amount</label>
					<div class= "col-sm-4">
						<form:input 
						cssClass ="form-control"
						id ="lnInrate"
						path="advocateMasterDTO.advFeeAmt"
						onkeypress ="return hasAmount(event,this,12,2)"
						/>
					</div> --%>

         <!-- 120797 prefix changed from ADT to AVT bcoz of ADT is used for AuditType in TSCL Proj -->
					<label for="" class="col-sm-2 control-label required-control"><spring:message
							code="lgl.advocate.type" text="Advocate Type" /></label>
					<apptags:lookupField items="${command.getLevelData('AVT')}"
						path="advocateMasterDTO.adv_advocateTypeId"
						cssClass="form-control mandColorClass" selectOptionLabelCode="Select"
						hasId="true" isMandatory="true" />

					<label class="control-label col-sm-2"> <spring:message
							code="lgl.courtnm" text="Court Name" />
					</label>
					<div class="col-sm-4">
						<!-- chosen-select-no-results -->
						<form:select
							class=" mandColorClass form-control chosen-select-no-results"
							path="advocateMasterDTO.adv_courtNameId" id="crtId">
							<form:option value="">
								<spring:message code="" text="Select" />
							</form:option>
							<c:forEach items="${command.courtNameList}" var="data">
								<c:choose>
									<c:when
										test="${userSession.getCurrent().getLanguageId() ne '1'}">
										<form:option value="${data.id}">${data.crtNameReg}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${data.id}">${data.crtName}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>


				</div>

				<div class="form-group">

					<%-- <label class="control-label col-sm-2"> <spring:message
							code="lgl.courtnm" text="Court Name" />
					</label>
					<div class="col-sm-4">
						<!-- chosen-select-no-results -->
						<form:select class=" mandColorClass form-control chosen-select-no-results"
							path="advocateMasterDTO.adv_courtNameId" id="crtId">
							<form:option value="">
								<spring:message code="" text="Select" />
							</form:option>
							<c:forEach items="${command.courtNameList}"
								var="data">
								<form:option value="${data.id}">${data.crtName}</form:option>
							</c:forEach>
						</form:select>
					</div> --%>

					<label class="col-sm-2 control-label"><spring:message
							code="lgl.advocate.status" text="Advocate Status" /><span
						class="mand">*</span> </label>
					<div class="col-sm-4">

						<label class="radio-inline" for="advStatusYes"> <form:radiobutton
								name="advStatus" path="advocateMasterDTO.advStatus"
								checked="checked" value="Y" id="advStatusYes"></form:radiobutton>
							<spring:message code="lgl.yes" text="Yes" />
						</label> <label class="radio-inline" for="advStatusNo"> <form:radiobutton
								name="advStatus" path="advocateMasterDTO.advStatus" value="N"
								id="advStatusNo"></form:radiobutton> <spring:message
								code="lgl.no" text="No" />
						</label>
					</div>

				</div>


				<%-- 	
				<div class="form-group">
				
				
					<label class="control-label col-sm-2 " for="Census"><spring:message code="lgl.gender" text="Gender" /></label>
						<c:set var="baseLookupCode" value="GEN" />
						<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="advocateMasterDTO.advGen"
						isMandatory="false" cssClass="form-control"
						hasId="true" selectOptionLabelCode="selectdropdown" />
					
    			<label class="control-label col-sm-2 " id ="id1" for="Census"><spring:message code="lgl.gender" text="Gender" /></label>
    			<div class ="col-sm-4">
    				<c:set var="baseLookupCode" value="GEN" />
						<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="advocateMasterDTO.advGen"
						isMandatory="true" cssClass="form-control"
						hasId="true" selectOptionLabelCode="selectdropdown" />
    			</div>
					
    				
				</div>
				
				<div class ="form-group">
				
					<apptags:input labelCode="" 
							path="" cssClass=" form-control"></apptags:input>
				</div>
				
				
				 --%>

				<!-- lgl.advocate.proceed -->
				<div class="text-center padding-bottom-10">
					<button type="submit" class="btn btn-success btn-submit"
						onclick="Proceed(this);return false;">
						<spring:message code="lgl.advocate.proceed" text="Proceed"></spring:message>
					</button>
					<c:if test="${command.saveMode ne 'V'}">
						<button type="Reset" class="btn btn-warning"
							onclick="resetAdvocateMaster();" id="resetId">
							<spring:message code="lgl.reset" text="Reset"></spring:message>
						</button>
					</c:if>
					<!-- lgl.cancel -->
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Back"
						onclick="backAdvocateMasterForm();">
						<spring:message code="lgl.back" text="Back"></spring:message>
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
</body>
</html>
