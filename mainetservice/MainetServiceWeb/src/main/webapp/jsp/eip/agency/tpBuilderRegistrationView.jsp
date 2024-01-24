<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" src="js/rti/offlinePay.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/tp/builderRegistration.js"></script>

	<div class="widget-header">
		         <%-- <h4><spring:message code="tp.builderDet"></spring:message></h4> --%>
     </div>
	 <div class="widget-content padding" id="testID">
		
					<%-- <div class="mand-label">
						<spring:message code="MandatoryMsg" />
					</div> --%>
					<!--Form Area Starts Here-->


					<%-- <div class="regheader">
						<spring:message code="tp.FirmDetails" />
					</div> --%>
					<div class="form-group">
							<label class="col-sm-2 control-label required-control">AgencyName</label>
                            <div class="col-sm-4">
							<form:input path="" cssClass="form-control" readonly="true" />
							<!-- <span class="mand">*</span> -->
						    </div>
							<label>Type of Agency</label>
							<div class="col-sm-4">
								<c:set var="baseLookupCode" value="TYF" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="" showOnlyLabel=""
									selectOptionLabelCode="tp.selTypeOfFirm" hasId="true" />
						    </div>		
					</div>
					<div class="form-group">
							<label class="col-sm-2 control-label required-control" ><spring:message code="eip.citizen.reg.dateOfBirth" /></label>
							<div class="col-sm-4">
								<apptags:dateField fieldclass="datepicker"
									datePath="" isMandatory="true"
									cssClass="form-control cal " readonly="true" />
							<!-- <span class="mand">*</span> -->
							</div>
							<%-- <label class="col-sm-2 control-label required-control"><spring:message code="eip.nationality" /></label>
							<div class="col-sm-4">
							 	<c:set var="baseLookupCode" value="TRY" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode,1)}"
									path="licenseMaster.licNationality" showOnlyLabel=""
									selectOptionLabelCode="tp.SelectNationality" hasId="true" />
						 	</div> --%>
					</div>
					<div class="form-group">
							<label class="col-sm-2 control-label required-control">Registration No.</label>
							<div class="col-sm-4">
							<form:input path="" cssClass="form-control" readonly="true" />
							<!-- <span class="mand">*</span> -->
							</div>
							<label class="col-sm-2 control-label required-control">PanNo.</label>
							<div class="col-sm-4">
								<form:input path=""
									cssClass="form-control" readonly="true" />
							<!-- <span class="mand">*</span> -->
							</div>
					</div>

					<div class="form-group">
							<label class="col-sm-2 control-label required-control">Tax:</label>
							<div class="col-sm-4">
							<form:input path="" cssClass="form-control" readonly="true" />
							<!-- <span class="mand">*</span> -->
							</div>
							
							<label class="col-sm-2 control-label required-control">Vat No:</label>
							<div class="col-sm-4">
							<form:input path=""
								cssClass="form-control" readonly="true" />
							<!-- <span class="mand">*</span> -->
							</div>
					</div>
					<div class="form-group">
						
							<label class="col-sm-2 control-label required-control">Applied For</label>
							<div class="col-sm-4">
							<c:set var="baseLookupCode" value="CLS" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="" showOnlyLabel=""
								selectOptionLabelCode="tp.SelectCategory " hasId="true" />

							</div>
							<label class="col-sm-2 control-label required-control">Adddress</label>
							<div class="col-sm-4">
							<form:textarea path=""
								cssClass="texboxcase form-control" maxlength="500" readonly="true" />
							<!-- <span class="mand">*</span> -->
							</div>
					</div>
					<div class="form-group">
							<label class="col-sm-2 control-label required-control">Telephone No.</label>
							<div class="col-sm-4">
							<form:input path=""
								cssClass="form-control" maxlength="11" readonly="true" />
							<!-- <span class="mand">*</span> -->
						</div>
							<label class="col-sm-2 control-label required-control">Mobile No.</label>
							<div class="col-sm-4">
							<form:input path="" cssClass="form-control" maxlength="10" readonly="true" />
							<!-- <span class="mand">*</span> -->
						</div>
					</div>
					<div class="form-group">
							<label class="col-sm-2 control-label required-control">Email Id:</label>
							<div class="col-sm-4">
								<form:input path="" cssClass="form-control" readonly="true" />
							<!-- <span class="mand">*</span> -->
							</div>
					</div>

					<div class="widget-header">
						Partner Name
					</div>
					
					<div class="clear padding_5"></div>
					<%-- <c:if test="${command.partnerDetail.size() ne 0 }"> --%>
					<table class="table table-hover table-bordered table-striped" id="secondParty${status.index}">
						<tbody>
							<tr>
								<th>Title <span class="mand2">*</span></th>
								<th>First Name<span class="mand2">*</span></th>
								<th>Middle Name</th>
								<th>Last Name<span class="mand2">*</span></th>
							</tr>
							<%-- <c:forEach items="${command.partnerDetail}" var="in"
								varStatus="status">
								<tr class="tr_clone" id="partnerDetail${status.index}">
									<td><c:set var="baseLookupCode" value="TTL" /> <apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="partnerDetail[${status.index}].licTitle"
											cssClass="select_title disablefield"
											selectOptionLabelCode="tp.Title" hasId="true"
											isMandatory="true" tabIndex="1" showOnlyLabel="true" /></td>
									<td><form:input
											path="partnerDetail[${status.index}].licFname"
											cssClass="input2 mandClassColor disablefield" hasId="true"
											readonly="true" /></td>
									<td><form:input
											path="partnerDetail[${status.index}].licMname"
											cssClass="input2 disablefield" hasId="true" readonly="true" /></td>
									<td><form:input
											path="partnerDetail[${status.index}].licLname"
											cssClass="input2 mandClassColor disablefield" hasId="true"
											readonly="true" /></td>
								</tr>
							</c:forEach> --%>
						</tbody>
					</table>
					<%-- </c:if> --%>
				</div>
<script>
	$("#licFirmType").prop('disabled', 'disabled').addClass("disablefield");
	$("#licNationality").prop('disabled', 'disabled').addClass("disablefield");
	$("#licTechperClass").prop('disabled', 'disabled').addClass("disablefield");
	$("#licTitle").prop('disabled', 'disabled').addClass("disablefield");
</script>







