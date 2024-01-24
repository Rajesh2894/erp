<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
    response.setContentType("text/html; charset=utf-8");
%>
<script src="js/rnl/master/freezeForm.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>


<div class="content animated slideInDown">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="rnl.master.freeze.prop" text="Freeze Property"></spring:message>
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="rnl.book.field" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message
						code="master.estate.field.mandatory.message" text="is mandatory"></spring:message></span>
			</div>
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div>
			<form:form method="post" action="PropFreeze.html"
				class="form-horizontal" name="propFreezeForm" id="propFreezeForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="locationId"><spring:message
							code="estate.label.Location" /></label>
					<div class="col-sm-4">
						<form:select path="locId"
							class="chosen-select-no-results form-control" id="locationId">
							<%--D#75710 <form:option value="">
								<spring:message code="selectdropdown" />
							</form:option>
							<c:forEach items="${command.locationList}" var="objArray">
								<form:option value="${objArray[0]}">
									<c:choose>
										<c:when test="${userSession.languageId eq 2}">${objArray[2]}</c:when>
										<c:otherwise>${objArray[1]}</c:otherwise>
									</c:choose>
								</form:option>
							</c:forEach> --%>
							
								<form:option value="">
										<spring:message code="selectdropdown" />
									</form:option>
									<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
									<c:forEach items="${command.locationList}" var="locationList">
										<form:option value="${locationList[0]}" code="">${locationList[1]}</form:option>
									</c:forEach>
									</c:if>
									<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
									<c:forEach items="${command.locationList}" var="locationList">
										<form:option value="${locationList[0]}" code="">${locationList[2]}</form:option>
									</c:forEach>
									</c:if>
						</form:select>
					</div>
					<label for="estateId"
						class="control-label col-sm-2 required-control"><spring:message
							code="estate.label.name" /></label>
					<div class="col-sm-4">
						<form:select path="estateId"
							class="chosen-select-no-results form-control" id="estateId">
							<form:option value="">
								<spring:message code="selectdropdown" />
							</form:option>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="name"><spring:message
							code='rl.property.label.name' /></label>
					<div class="col-sm-4">
						<form:select path="estateBookingDTO.propId"
							class="chosen-select-no-results form-control" id="propId">
							<form:option value="0">
								<spring:message code="selectdropdown" text="Select" />
							</form:option>

						</form:select>
					</div>
				</div>
				<div class="form-group" id="dateDiv">
					<label class="control-label col-sm-2 required-control"
						for="FromDate"><spring:message code="rnl.from.date"
							text="From Date"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="estateBookingDTO.fromDate" type="text"
								id="fromDate" class="lessthancurrdatefrom  form-control"></form:input>
							<span class="input-group-addon"><i class="fa fa-calendar"></i><span
								class="hide"><spring:message code="rnl.from.date"
										text="From Date"></spring:message></span></span>
						</div>
					</div>
					<label class="control-label col-sm-2 required-control" for="ToDate"><spring:message
							code="rnl.to.date" text="To Date"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="estateBookingDTO.toDate" type="text"
								id="toDate" class="lessthancurrdateto form-control"></form:input>
							<span class="input-group-addon"><i class="fa fa-calendar"></i><span
								class="hide"><spring:message code="rnl.to.date"
										text="To Date"></spring:message></span></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="Shift"><spring:message
							code="rnl.master.shift" text="Shift"></spring:message></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="SHF" />
						<form:select path="estateBookingDTO.shiftId"
							class="shift form-control" id="shiftId">
							<form:option value="0">
								<spring:message code="rnl.master.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>
				<%-- <div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="PurposeofBooking"><spring:message
							code="rnl.master.reason" text="Reason of Freezing"></spring:message></label>
					<div class="col-sm-10">
						<form:textarea path="estateBookingDTO.purpose" type="text"
							id="purposeofBooking" class="form-control"></form:textarea>
					</div>
				</div> --%>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"><spring:message
							code="rnl.master.reason" text="Reason of Freezing"></spring:message></label>
					<div class="col-sm-10">
						<form:textarea path="estateBookingDTO.reasonOfFreezing" maxlength="299"
							type="text" id="reasonOfFreezing" class="form-control"></form:textarea>
					</div>
				</div>
				<div class="text-center padding-top-10">

					<button type="button" id="submitProp"
						class="btn btn-success btn-submit">
						<i class="fa fa-sign-out padding-right-5"></i>
						<spring:message code="bt.save" text="" />
					</button>
					<button class="btn btn-warning" type="button" id="resetPropfreeze"
						onclick="resetData(this)">
						<i class="fa fa-undo padding-right-5"></i>
						<spring:message code="bt.clear" text="" />
					</button>

					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style="" onclick="back()"
						id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="bt.backBtn" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
