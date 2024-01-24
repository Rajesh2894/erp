<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/material_mgmt/service/itemOpeningBalance.js" type="text/javascript"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated ">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="material.item.openingBalnaceForm" text="Item Opening Balnace Form" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand" text="Field with" /> <i
					class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /></span>
			</div>

			<form:form class="form-horizontal" commandName="command" action="ItemOpeningBalance.html" 
				method="POST" name="openBalanceFrm" id="openBalanceFrm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<h4>
					<spring:message code="material.item.openingBalnace" text="Item Opening balance" />
				</h4>
				<form:hidden path="itemOpeningBalanceDto.valueMethodCode" id="valueMethodCode"/>
				<form:hidden path="itemOpeningBalanceDto.isExpiry" id="isExpiry"/>
				<input type="hidden" id="isExipryId">
				<input type="hidden" id="expiryType">
				
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"><spring:message
							code="store.master.name" text="Store Name" /></label>
					<div class="col-sm-4">
						<form:select path="itemOpeningBalanceDto.storeId" id="storeId" 
							class="form-control chosen-select-no-results" disabled="${command.modeType eq 'V'}">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<%-- <form:options items="${command.storeMasterList}" itemLabel="storeName" itemValue="storeId" /> --%>
							<c:forEach items="${command.storeObjectList}" var="storeObject">
								<form:option value="${storeObject[0]}" code="${storeObject[2]}">${storeObject[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="control-label col-sm-2 required-control"><spring:message
							code="material.item.openingDate" text="Opening Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="itemOpeningBalanceDto.openingDate" type="text"
								class="form-control" id="openingDate" disabled="${command.modeType eq 'V'}"/>
							<label class="input-group-addon" for=openingDate><i
								class="fa fa-calendar"></i></label>
						</div>
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"><spring:message
							code="material.item.master.name" text="Item Name" /></label>
					<div class="col-sm-4">
						<form:select id="itemId" path="itemOpeningBalanceDto.itemId"
							cssClass="form-control chosen-select-no-results"
							disabled="${command.modeType ne 'C'}" onchange="getMethodForm(this);">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<form:options items="${command.itemList}" itemLabel="name"
								itemValue="itemId" />
						</form:select>
					</div>
				</div>
				
				<div class="form-group">
					<label for="group" class="control-label col-sm-2"><spring:message
							code="material.item.master.group1" text="Group" /></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control "
							path="itemOpeningBalanceDto.group" id="group" disabled="true" />
					</div>
					<label for="subGroup" class="control-label col-sm-2"><spring:message
							code="material.item.master.subgroup1" text="Sub Group" /></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control "
							path="itemOpeningBalanceDto.subGroup" id="subGroup"
							disabled="true" />
					</div>
				</div>
				
				<div class="form-group">
					<label for="uom" class="control-label col-sm-2"><spring:message
							code="store.master.uom" text="UoM" /></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control "
							path="itemOpeningBalanceDto.uom" id="uom" disabled="true" />
					</div>
					<label for="valueMethod" class="control-label col-sm-2"><spring:message
							code="material.item.master.itemMgdmethod" text="Item Managed Method" /></label>
					<div class="col-sm-4">
						<form:input type="Value Method" class="form-control" id="valueMethod"
							path="itemOpeningBalanceDto.valueMethod" disabled="true" />
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control"><spring:message
							code="material.item.Balnace" text="Opening Balance" /></label>
					<div class="col-sm-4">
						<form:input type="text" cssClass="form-control"  id="openingBalance"
							onkeypress="return hasAmount(event, this, 11, 1)"
							path="itemOpeningBalanceDto.openingBalance"	readonly="${command.modeType eq 'V'}" />
					</div>
					<label class="control-label col-sm-2 required-control"><spring:message
							code="material.item.unitPrice" text='Unit Price (Base UoM)' /></label>
					<div class="col-sm-4">
						<form:input type="text" path="itemOpeningBalanceDto.unitPrice" id="unitPrice"
							onkeypress="return hasAmount(event, this, 10, 2)" 
							cssClass="form-control" readonly="${command.modeType eq 'V'}" />
					</div>
				</div>
				<br>

				<h4>
					<spring:message code="material.item.binLocationFor" text="Bin Location for " />
					<span id="methodName"></span>
				</h4>
				<c:choose>
					<c:when test="${'C' ne command.modeType}">
						<div id="appendtable">
							<jsp:include page="openingBalanceMethod.jsp"></jsp:include>
						</div>
					</c:when>
					<c:otherwise>
						<div id="appendtable"></div>
					</c:otherwise>
				</c:choose>

				<div class="text-center padding-top-10">
					<c:if test="${'V' ne command.modeType}">
						<button type="button" class="btn btn-success btn-submit" id="submit" onclick="proceed(this)">
							<spring:message code="material.management.submit" text="Submit" />
						</button>
						<button type="Reset" class="btn btn-warning" id="resetEstate">
							<spring:message code="material.management.reset" text="Reset" />
						</button>
					</c:if>
					<input type="button" class="btn btn-danger"
						onclick="javascript:openRelatedForm('ItemOpeningBalance.html');"
						value="<spring:message code="material.management.back" text="Back"/>" />
				</div>
			</form:form>
		</div>
	</div>
</div>