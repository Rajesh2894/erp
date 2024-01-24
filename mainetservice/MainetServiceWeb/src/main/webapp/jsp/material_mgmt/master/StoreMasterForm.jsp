<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/material_mgmt/master/StoreMaster.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<style>
.input-group-field {
	display: table-cell;
	width: 114px;
}
</style>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="store.master.heading" text="Store Master" />
			</h2>
			<apptags:helpDoc url="StoreMaster.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /> </span>
			</div>
			<form:form action="StoreMaster.html" name="StoreMasterForm"
				id="StoreMasterId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				
				<form:hidden path="saveMode" id="saveMode" />
					
				<h4>
					<spring:message code="store.master.details" text="Store Details" />
				</h4>
				<div class="form-group">
					<apptags:input labelCode="store.master.code"
						path="storeMasterDTO.storeCode" isMandatory="true"
						cssClass="form control mandcolour" isReadonly="true">
					</apptags:input>
					<apptags:input labelCode="store.master.name" maxlegnth="100"
						path="storeMasterDTO.storeName" isMandatory="true"
						cssClass="form control mandcolour hasCharacterNumbers">
					</apptags:input>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control "
						for="Location"><spring:message
							code="store.master.location" text="Location" /></label>
					<div class="col-sm-4">
						<form:select path="storeMasterDTO.location"
							onchange="getStoreIncharge()"
							cssClass="form-control chosen-select-no-results mandColorClass"
							id="locId" data-rule-required="true">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${command.locList}" var="lookUp">
								<form:option value="${lookUp.locId}" code="">${lookUp.locNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<apptags:input labelCode="store.master.address"
						path="storeMasterDTO.address" isMandatory="false"
						cssClass="form control mandcolour" isReadonly="false">
					</apptags:input>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control "
						for="Store Incharge"><spring:message
							code="store.master.store.incharge" text="Store Incharge" /></label>
					<div class="col-sm-4">
						<form:select path="storeMasterDTO.storeIncharge"
							cssClass="form-control  chosen-select-no-results mandColorClass"
							id="storeInchargeId" data-rule-required="true">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${command.inchargeList}" var="lookup">
								<form:option value="${lookup[3]}"> ${lookup[0]} </form:option>
							</c:forEach>
						</form:select>
					</div>

					<c:if test="${command.saveMode ne 'A'}">
						<label class="control-label col-sm-2 required-control "
							for="StoreStatus"><spring:message
								code="store.master.status" text="Status" /></label>
						<div class="col-sm-4">
							<form:select path="storeMasterDTO.status"
								cssClass="form-control   mandColorClass" id="statusId"
								data-rule-required="true">
								<form:option value="">
									<spring:message code="material.management.select" text="Select" />
								</form:option>
								<form:option value="Y">
									<spring:message code="material.management.active" text="Active" />
								</form:option>
								<form:option value="N">
									<spring:message code="material.management.inActive" text="Inactive" />
								</form:option> 
							</form:select>
						</div>
					</c:if>
				</div>

				<h4>
					<spring:message code="store.master.item.group.details"
						text="Item Group Details" />
				</h4>
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="UnitDetail">
						<div class=" clear padding-10">
							<c:set var="d" value="0" scope="page" />
							<table id="unitDetailTable"
								class="table table-striped table-bordered appendableClass unitDetails">
								<thead>
									<tr>
										<th width="10%"><spring:message code="store.master.srno" text="Sr.No." /></th>
										<th><spring:message code="store.master.item.group.name" text="Item Group Name" /></th>
										<c:if test="${command.saveMode ne 'V'}">
											<th width="20%"><spring:message code="material.management.action" text="Action" /></span></th>
										</c:if>
									</tr>
								</thead>
								<tbody>
									<tr class="firstUnitRow">
										<td><form:input path="" id="sNo${d}" value="${d + 1}" readonly="true" 
													cssClass="form-control text-center" /></td>
										<td>
											<form:select path="storeMasterDTO.storeGrMappingDtoList[${d}].itemGroupId"
												cssClass="form-control" id="itemGroupId${d}" data-rule-required="true">
												<form:option value="">
													<spring:message code="material.management.select" text="Select" />
												</form:option>
												<c:forEach items="${command.lookupList}" var="lookUp">
													<form:option value="${lookUp.lookUpId}" code="">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</td>
										<c:if test="${command.saveMode ne 'V'}">
											<td class="text-center"><a href="javascript:void(0);"  id="addUnitRow"
													title="<spring:message code="material.management.add" text="Add" />"
													class="addPurReq btn btn-success btn-sm unit"><i class="fa fa-plus-circle"></i></a> 
												<a href="javascript:void(0);" title="<spring:message code="material.management.delete" text="Delete" />"
													class="btn btn-danger btn-sm delete" id="deleteUnitRow"
													onclick="deleteEntry($(this),'removedIds');"> <i class="fa fa-trash"></i></a>
											</td>
										</c:if>
									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveStoreMasterForm(this);">
							<spring:message code="material.management.submit" text="Submit"></spring:message>
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'A'}">
						<button type="button" class="btn btn-warning"
							onclick="resetStoreMasterAdd();">
							<spring:message code="material.management.reset" text="Reset"></spring:message>
						</button>					
					</c:if>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backStoreMasterForm();" id="button-Cancel">
						<spring:message code="material.management.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
