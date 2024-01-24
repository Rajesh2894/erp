<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/objection/hearingInspection.js"></script>
<style>
.addColor{
	background-color: #fff !important
}
</style>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
<div class="widget">
	<div class="widget-header">
		<h2>		
			<b><spring:message code="obj.hearingInspectionScheduling"/></b>
		</h2>
		<%-- <div class="additional-btn">
			<apptags:helpDoc url="objectionAddDetails.html"></apptags:helpDoc>
		</div> --%>
	</div>

	<div class="widget-content padding">
		<form:form action="HearingInspection.html"
					class="form-horizontal" name="HearingInspection"
					id="HearingInspection">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>


			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">
				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a1"> <spring:message
								code="obj.objappealissuerdetails" /></a>
					</h4>
					<div id="a1" class="panel-collapse collapse in">
						<div class="panel-body">
		<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="applicantTitle"><spring:message code="obj.title" /></label>
								<c:set var="baseLookupCode" value="TTL" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="objectionDetailsDto.title"
									cssClass="form-control" hasChildLookup="false" hasId="true"
									showAll="false"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" showOnlyLabel="rti.title" disabled="true" />

								<apptags:input labelCode="obj.firstName" cssClass="hasCharacter"
									path="objectionDetailsDto.fName" isMandatory="true" isDisabled="true"></apptags:input>
							</div>

							<div class="form-group">
								<apptags:input labelCode="obj.middleName"
									cssClass="hasCharacter"
									path="objectionDetailsDto.mName" isDisabled="true"></apptags:input>
								<apptags:input labelCode="obj.lastName" cssClass="hasCharacter"
									path="objectionDetailsDto.lName" isMandatory="true" isDisabled="true"></apptags:input>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="applicantinfo.label.gender" /></label>
								<c:set var="baseLookupCode" value="GEN" />
								<apptags:lookupField items="${command.getLevelData('GEN')}"
									path="objectionDetailsDto.gender"
									cssClass="form-control" hasChildLookup="false" hasId="true"
									showAll="false"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" disabled="true" />

								<apptags:input labelCode="obj.mobile1" cssClass="hasMobileNo"
									path="objectionDetailsDto.mobileNo" isMandatory="true" isDisabled="true" ></apptags:input>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label" for="EmailID"><spring:message
										code="obj.emailId" text="Email ID" /></label>
								<div class="col-sm-4">
									<form:input name="" type="email"
										class="form-control mandColorClass" id="EmailID"
										path="objectionDetailsDto.eMail"
										data-rule-required="true" data-rule-email="true" disabled="true"></form:input>
								</div>

				 				<apptags:input labelCode="obj.Aadhar"
									cssClass="form-control hasAadharNo"
									path="objectionDetailsDto.uid" 
									maxlegnth="12" isDisabled="true"></apptags:input>

							</div>
							<div class="form-group">
						<apptags:textArea isMandatory="true" labelCode="obj.address" path="objectionDetailsDto.address" 
						maxlegnth="500" cssClass="preventSpace" isDisabled="true"></apptags:textArea>
							</div>

						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a2"> <spring:message
								code="obj.objappealdetails" /></a>
					</h4>
					<div id="a2" class="panel-collapse collapse in">
						<div class="panel-body">

							<div class="form-group">							
								<apptags:input labelCode="obj.dept" path="objectionDetailsDto.deptName" isDisabled="true"></apptags:input>
								<apptags:input labelCode="obj.serviceName" path="objectionDetailsDto.serviceName" isDisabled="true"></apptags:input>
							</div>
											
							<div class="form-group">
							<c:set var="baseLookupCode" value="OBJ" />
								<apptags:select labelCode="obj.ObjOn"
									items="${command.getLevelData(baseLookupCode)}"
									path="objectionDetailsDto.objectionOn"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" showAll="false" isLookUpItem="true" disabledOption="true"></apptags:select>
									
							</div>
							
<div class="form-group">
			<label class="col-sm-2 control-label required-control" id="refNo"
				for="dept"><spring:message code="obj.ReferenceNo" /></label>
			<div class="col-sm-4">
				<form:input  class="form-control mandColorClass" id="objectionReferenceNumber"
					path="objectionDetailsDto.objectionReferenceNumber"
					data-rule-required="true" disabled="true"></form:input>
			</div>
			
			
			
			<br><br>
			<div class="form-group">
			<label class="col-sm-10 text-red">&nbsp&nbsp&nbsp<spring:message code="obj.note"/></label>
			</div>
			
	</div>			
		<div class="form-group">
			<label class="col-sm-2 control-label " 
				for="billNo"><spring:message code="obj.billNo" text="Bill No"/></label>
			<div class="col-sm-4">
				<form:input class="form-control mandColorClass" id="billNo"
					path="objectionDetailsDto.billNo"
					data-rule-required="true" disabled="true"></form:input>
					
			</div>
	<apptags:input labelCode="obj.billDueDate" path="objectionDetailsDto.billDueDate" isDisabled="true" cssClass="dateClass"></apptags:input>
							
						
	</div>
			<div class="form-group">
			<label class="col-sm-2 control-label "
				for="noticeNo"><spring:message code="obj.noticeNo" text="Notice No"/></label>
			<div class="col-sm-4">
				<form:input class="form-control mandColorClass" id="noticeNo"
					path="objectionDetailsDto.noticeNo"
					data-rule-required="true" disabled="true"></form:input>
			</div>
	</div>			
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="rtiDetails"><spring:message
										code="obj.appealobjdetails" /></label>
								<div class="col-sm-10">
	
									<form:textarea name="" cols="" rows="" class="form-control mandColorClass" id="ObjDetails" path="objectionDetailsDto.objectionDetails" disabled="true"></form:textarea>
								</div>
							</div>

						</div>
					</div>
				</div>

		    <c:if test="${not empty command.documentList}">	
<h4 class="margin-top-0 margin-bottom-10 panel-title"> <a data-toggle="collapse" href="#DocumentUpload"><spring:message code="obj.UploadDocumnet"/></a></h4>
<div id="DocumentUpload">
							<fieldset class="fieldRound">
								<div class="overflow">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<th><label class="tbold"><spring:message
																code="water.serialNo" text="Sr No" /></label></th>
				
												     <th><label class="tbold"><spring:message
																code="water.download"/></label></th>
												</tr>

												<c:forEach items="${command.documentList}" var="lookUp"
													varStatus="lk">
													<tr>
														<td><label>${lk.count}</label></td>
					
														  <td>
														  <c:set var="links" value="${fn:substringBefore(lookUp.attPath, lookUp.attFname)}" />
															<apptags:filedownload filename="${lookUp.attFname}" filePath="${lookUp.attPath}" dmsDocId="${lookUp.dmsDocId}" actionUrl="HearingInspection.html?Download"></apptags:filedownload>
					                                    </td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</fieldset>
							</div>
					 </c:if>   
					 
					   <c:if test="${command.onlyInspecMode eq 'Y'}">							
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a4">Inspection Details</a>
					</h4>
					<div id="a4" class="panel-collapse collapse in">
					<div class="form-group padding-top-10">
						<label class="col-sm-2 control-label required-control" 
							for="billNo">Inspection Authority</label>
						<div class="col-sm-4">				
						<form:select path="inspectionDto.empId" class="form-control mandColorClass" id="empId" disabled="true">
							<form:option value="0" label="select employee"></form:option>
           		  			<c:forEach items="${command.getEmpList()}" var="lookUp" varStatus="key">
           	  						<form:option value="${lookUp.lookUpId}" label="${lookUp.descLangFirst}"></form:option>
							</c:forEach>
					</form:select>
					</div>					<apptags:textArea labelCode="obj.HearingRemark" path="inspectionDto.remark" isMandatory="true" isReadonly="true"></apptags:textArea>
					</div>
					<div class="form-group padding-top-10 ">
						<apptags:radio radioLabel="Owner,Representative" radioValue="O,R" labelCode="obj.Selection" path="inspectionDto.availPerson" disabled="true"></apptags:radio>
						</div>
					<div class="form-group">
					<apptags:input labelCode="obj.NameOfPerson" path="inspectionDto.name" isMandatory="true" isReadonly="true"></apptags:input>
					</div>
						<div class="form-group">
						<apptags:input labelCode="obj.mobile1" cssClass="hasMobileNo"
									path="inspectionDto.mobno" isMandatory="true" isReadonly="true"></apptags:input>
					
													<label class="col-sm-2 control-label" for="EmailID"><spring:message
										code="obj.emailId" text="Email ID" /></label>
								<div class="col-sm-4">
									<form:input  type="email"
										class="form-control mandColorClass" id="EmailID"
										path="inspectionDto.emailid"
										data-rule-required="true" data-rule-email="true" readonly="true"></form:input>
					</div>
					</div>

			</div>	 
					 </c:if>
					 
					 
					   			
				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a3"><spring:message code="obj.Scheduling"/></a>
					</h4>
					<div id="a3" class="panel-collapse collapse in">
						<div class="form-group padding-top-10 ">
						<apptags:radio radioLabel="obj.Hearing,obj.Inspection" radioValue="H,I" labelCode="obj.Selection" path="objectionDetailsDto.schedulingSelection" disabled="${command.onlyInspecMode eq 'Y' ? true : false}"></apptags:radio>
						</div>
						
						<div class="form-group">							
							<label class="col-sm-2 control-label required-control" text="Date "
														for="inspectionDate"><spring:message code="obj.HearingInspectionDateTime"/></label>
							<div class="col-sm-4">
							<div class="input-group"> 
							<form:input path="HearingInspectionDto.insHearDate" class="form-control datetimepicker addColor" id="inspectionDate" readonly="true" data-rule-required="true" placeholder="DD/MM/YYYY HH:MM" autocomplete="off"/>
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>	
							</div>
							</div>
							
						</div>
					</div>
				
				</div>
				
				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="submitInspectionForm(this)">
						<spring:message code="obj.submit"/>
					</button>
					<apptags:resetButton></apptags:resetButton>
				</div>
			</div>
		</form:form>
	</div>
</div>
</div>