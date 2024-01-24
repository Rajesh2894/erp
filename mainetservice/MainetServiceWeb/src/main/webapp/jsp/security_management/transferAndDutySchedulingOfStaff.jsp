<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script>
<script type="text/javascript"
	src="js/security_management/transferOfStaff.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="TransferSchedulingOfStaffDTO.header.label"
						text="Transfer And Duty Scheduling Of Staff" /></strong>
			</h2>
		</div>
		<div class="widget-content padding">
		<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand" /><i
					class="text-red-1">* </i> <spring:message code="leadlift.master.ismand" />
				</span>
			</div>
			<!-- End mand-label -->
			<form:form action="TransferAndDutyScheduling.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmDutySchedulingForm" id="frmDutySchedulingForm">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for=""> <spring:message
							code="TransferSchedulingOfStaffDTO.empTypeId"
							text="Employee Type" />
					</label>
					<c:set var="baseLookupCode" value="EMT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="transferSchedulingOfStaffDTO.empTypeId"
						cssClass="mandColorClass form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" />

					<label class="col-sm-2 control-label required-control"> <spring:message
							code="TransferSchedulingOfStaffDTO.vendorId" text="Agency Name" />
					</label>
					<div class="col-sm-4">
						<form:select id="vendorId"
							path="transferSchedulingOfStaffDTO.vendorId"
							cssClass="form-control vmVendorid " data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select " />
							</form:option>
							<c:forEach items="${command.vendorList}" var="Vendor">
								<form:option value="${Vendor.vmVendorid}"
									code="${Vendor.vmVendorcode}_ ${Vendor.vmVendorname}">${Vendor.vmVendorcode}-${Vendor.vmVendorname}
								</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<%-- <label class="control-label col-sm-2 " for=""> <spring:message
							code="TransferSchedulingOfStaffDTO.cpdShiftId"
							text="Current Shift" />
					</label>
					<c:set var="baseLookupCode" value="SHT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="transferSchedulingOfStaffDTO.cpdShiftId"
						cssClass="mandColorClass form-control" hasId="true"
						selectOptionLabelCode="selectdropdown" isMandatory="true" /> --%>
						
						<label class="control-label col-sm-2 " for=""> <spring:message
							code="TransferSchedulingOfStaffDTO.cpdShiftId" text="Current Shift" /></label>
					<div class="col-sm-4">
						<form:select id="cpdShiftId" path="TransferSchedulingOfStaffDTO.cpdShiftId"
							cssClass="form-control chosen-select-no-results "
							data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.lookup}" var="lookup">
								<form:option value="${lookup.lookUpId}">${lookup.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label col-sm-2 " for="location"> <spring:message
							code="TransferSchedulingOfStaffDTO.current.location" text="Current Location" /></label>
					<div class="col-sm-4">
						<form:select id="locId" path="transferSchedulingOfStaffDTO.locId"
							cssClass="form-control chosen-select-no-results "
							data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.location}" var="location">
								<form:option value="${location.locId}">${location.locNameEng}-${location.locArea}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>


				<div class="text-center clear padding-10">
					<button type="button" id="search" class="btn btn-blue-2"
						onclick="SearchStaff(this);" title='<spring:message code="TransferSchedulingOfStaffDTO.form.search" text="Search" />'>
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="TransferSchedulingOfStaffDTO.form.search"
							text="Search" />
					</button>

					<button type="button" id="reset"
						onclick="window.location.href='TransferAndDutyScheduling.html'"
						class="btn btn-warning" title='<spring:message code="TransferSchedulingOfStaffDTO.form.reset" text="Reset" />'>
						<spring:message code="TransferSchedulingOfStaffDTO.form.reset"
							text="Reset" />
					</button>

					<button type="button" id="back" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style="" title='<spring:message code="TransferSchedulingOfStaffDTO.form.back" text="Back" />'
						onclick="window.location.href='AdminHome.html'" id="button-Cancel">
						<spring:message code="TransferSchedulingOfStaffDTO.form.back" text="Back" />
					</button>
				</div>


				<c:set var="e" value="0" scope="page" />
				<div class="table-responsive">
					<table class="table table-striped table-bordered" id="frmStaffTbl">
						<thead>
							<tr>
								<th width="3%"class="text-center"><spring:message code="TransferSchedulingOfStaffDTO.sr.no"
										text="Sr No" /></th>
								<th width="4%" class="text-center"><spring:message code="TransferSchedulingOfStaffDTO.name" text="Name" /></th>
								<th width="4%" class="text-center"><spring:message code="TransferSchedulingOfStaffDTO.id" text="ID" /></th>
								<th width="8%" class="text-center"><spring:message code="TransferSchedulingOfStaffDTO.type.of.employee"
										text="Type Of Employee" /></th>
								<th width="8%" class="text-center"><spring:message code="TransferSchedulingOfStaffDTO.weekly.off"
										text="Weekly Off" /></th>
								<th width="4%" class="text-center"><spring:message code="TransferSchedulingOfStaffDTO.new.scheduled.from.date"
										text="New Scheduled From Date" /><span class="mand">*</span></th>
								<th width="4%" class="text-center"><spring:message code="TransferSchedulingOfStaffDTO.new.scheduled.to.date"
										text="New Scheduled To Date" /><span class="mand">*</span></th>
								<th width="8%" class="text-center"><spring:message code="TransferSchedulingOfStaffDTO.shift"
										text="Shift" /><span class="mand">*</span></th>
								<th width="4%" class="text-center"><spring:message code="TransferSchedulingOfStaffDTO.location"
										text="Location" /><span class="mand">*</span></th>
								<th width="5%" class="text-center" height="20px"><spring:message code="TransferSchedulingOfStaffDTO.select.all" text="Select All" />
									<br /> <input type="checkbox"
									style="margin-left: -10px; position: relative;"
									class="checkbox-inline" id="selectall" />
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.transferSchedulingOfStaffDTOList}" var="member"
								varStatus="status">
								<tr>
									<%-- <td class="text-center">${status.count}</td> --%>
									<td><form:input path=""
											cssClass="form-control mandColorClass" id="sequence${e}"
											value="${e+1}" disabled="true" /></td>

									<td class="text-center"><form:input
											path="transferSchedulingOfStaffDTOList[${e}].contStaffName"
											value="${member.contStaffName}" disabled="true"
											readonly="true" id="contStaffName" /></td>

									<td class="text-center"><form:input
											path="transferSchedulingOfStaffDTOList[${e}].contStaffIdNo"
											value="${member.contStaffIdNo}" disabled="true"
											readonly="true" /></td>

									<td class="text-center"><form:select id="empTypeId"
											path="transferSchedulingOfStaffDTOList[${e}].empTypeId"
											cssClass="form-control" hasId="true" disabled="true"
											data-rule-required="false">
											<c:set var="baseLookupCode" value="EMT" />
											<form:option value="0">
												<spring:message code="Select" text="Select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>

									<td class="text-center"><form:select id="dayPrefixId"
											path="transferSchedulingOfStaffDTOList[${e}].dayPrefixId"
											cssClass="form-control" hasId="true" readonly="true"
											disabled="true" data-rule-required="false">
											<c:set var="baseLookupCode" value="DAY" />
											<form:option value="0">
												<spring:message code="Select" text="Select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>

									<td class="text-center"><form:input
											cssClass="form-control mandColorClass datepicker"
											isMandatory="true"
											id="contStaffSchFrom${e}"
											path="transferSchedulingOfStaffDTOList[${e}].contStaffSchFrom" 
											placeholder='DD/MM/YYYY'></form:input></td>

									<td class="text-center"><form:input
											isMandatory="true"
											cssClass="form-control mandColorClass datepicker"
											id="contStaffSchTo${e}"
											path="transferSchedulingOfStaffDTOList[${e}].contStaffSchTo"
											placeholder='DD/MM/YYYY'></form:input></td>

									<td class="text-center"><form:select id="cpdShiftId${e}"
											isMandatory="true"
											path="transferSchedulingOfStaffDTOList[${e}].cpdShiftId"
											cssClass="form-control" hasId="true"
											data-rule-required="false">
											<%-- <c:set var="baseLookupCode" value="SHT" /> --%>
											<form:option value="0">
												<spring:message code="Select" text="Select" />
											</form:option>
											<c:forEach items="${command.lookup}" var="lookup">
																<form:option value="${lookup.lookUpId}"
																	code="${lookup.lookUpCode}">${lookup.lookUpDesc}</form:option>
															</c:forEach>
										</form:select></td>
										
										

									<td class="text-center"><form:select id="location${e}"
											path="transferSchedulingOfStaffDTOList[${e}].locId"
											cssClass="form-control " data-rule-required="true">
											<form:option value="0">
												<spring:message code="Select" text="Select" />
											</form:option>
											<c:forEach items="${command.location}" var="location">
												<form:option value="${location.locId}">${location.locNameEng}-${location.locArea}</form:option>
											</c:forEach>
										</form:select></td>


									<td class="text-center"><form:checkbox
											id="memberSelected${e}"
											path="transferSchedulingOfStaffDTOList[${e}].memberSelected"
											class="case" value="N" /></td>
								</tr>
								<c:set var="e" value="${e + 1}" scope="page" />
							</c:forEach>
						</tbody>
					</table>
				</div>

				<c:if test="${command.mode eq 'Y'}">
					<div class="form-group hideThisOne">
						<label class="control-label col-sm-2 required-control" > <spring:message
								code="TransferSchedulingOfStaffDTO.reasonTransfer"
								text="Reason Of Transfer" ></spring:message>
						</label>
						<div class="col-sm-4">
							<form:textarea id="reasonTransfer"
								path="transferSchedulingOfStaffDTO.reasonTransfer" isMandatory="true" data-rule-required="true"
								class="form-control mandColorClass" maxLength="500" />
						</div>

						<label class="control-label col-sm-2 "> <spring:message
								code="TransferSchedulingOfStaffDTO.remarks" text="Remarks"></spring:message>
						</label>
						<div class="col-sm-4">
							<form:textarea id="remarks"
								path="transferSchedulingOfStaffDTO.remarks"
								class="form-control mandColorClass" maxLength="500" />
						</div>
					</div>

					<div class="text-center hideThisOne clear padding-10">
						<button type="button" id="saveBtn"
							class="btn btn-success btn-submit" onclick="saveStaff(this)"
							value="Save">
							<i class="fa fa-floppy-o" aria-hidden="true"></i>
							<spring:message code="TransferSchedulingOfStaffDTO.form.save" />
						</button>

						<button type="button" id="reset" onclick="clearDataTable()"
							class="btn btn-warning" title="Reset ">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
							<spring:message code="TransferSchedulingOfStaffDTO.form.reset"
								text="Reset" />
						</button>
					</div>
				</c:if>
			</form:form>
		</div>

	</div>
</div>


