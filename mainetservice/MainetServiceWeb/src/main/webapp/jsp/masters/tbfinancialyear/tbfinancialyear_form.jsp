<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/script-library.js"></script>
<script type="text/javascript" src="js/masters/tbfinancialyear/tbfinancialyearForm.js"></script>
<c:url value="${mode}" var="form_mode" />
<div class="widget">
	<div class="widget-header">
		<h2 title="Financial Year">
			<spring:message code="finyear.form.caption" text="Financial Year Master"/>
		</h2>
		 <apptags:helpDoc url="Financialyear.html" helpDocRefURL="Financialyear.html"></apptags:helpDoc>
	</div>
	<div class="widget-content padding">
		<div class="mand-label clearfix">
		<span><spring:message code="contract.breadcrumb.fieldwith"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="common.master.mandatory" text="is mandatory" /> </span>
	</div>
		<form:form method="post" action="Financialyear.html"
			name="finyeardetail" id="finyeardetail" class="form-horizontal"
			modelAttribute="tbFinancialyear">


			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>
			<jsp:include page="/jsp/tiles/validationerror.jsp" />

			<input type="hidden" value="${form_mode}" id="mode" />
			<div id="editOrViewRecord">
				<form:hidden path="editOrView" value="${editOrView}" id="modevalue" />
				<form:hidden path="faYear" />

				<c:if test="${form_mode =='create'}">
					<div class="form-group">

						<label class="col-sm-2 control-label required-control"
							for="fromDate"><spring:message
								code="finyear.form.fromDate"/></label>
						<div class="col-sm-4">
							<div class="input-group">
						      <form:input path="fromDate" cssClass="mandColorClass form-control" id="fromdate" 
						      	placeholder="DD/MM/YYYY" data-rule-required="true" readonly="true"/>
						      <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						    </div>
						</div>

						<label class="col-sm-2 control-label required-control"
							for="toDate"><spring:message code="finyear.form.toDate" /></label>
						<div class="col-sm-4">
							<div class="input-group">
						      <form:input path="toDate" cssClass="form-control mandColorClass" id="todate" placeholder="DD/MM/YYYY" 
						      	data-rule-required="true" readonly="true"/>
						      <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
						    </div>
						</div>
					</div>
				</c:if>

				<c:if test="${form_mode == 'update'}">
				<h4><spring:message
								code="master.finYr.detail" text="Details" /></h4>
					<div class="form-group">

						<label class="col-sm-2 control-label required-control"
							for="fromDate"><spring:message
								code="finyear.form.fromDate" /></label>
						<div class="col-sm-4">
							<form:input path="fromDate" cssClass="form-control"
								readonly="true" id="fromdate"
								placeholder="DD/MM/YYYY" data-rule-required="true" />
						</div>

						<label class="col-sm-2 control-label required-control "
							for="toDate"><spring:message code="finyear.form.toDate" /></label>
						<div class="col-sm-4">
							<form:input path="toDate" cssClass="form-control" id="todate"
								readonly="true" placeholder="DD/MM/YYYY"
								data-rule-required="true" />
						</div>
						</div>
						<div class="form-group">
						<label class="col-sm-2 control-label required-control"
							for="status"><spring:message code="finyear.form.status" /></label>

						<div class="col-sm-4">

							<form:select path="financialyearOrgMap[${0}].yaTypeCpdId"
								id="status" cssClass="form-control" onchange="showMonthDiv()">
								<form:option value="0">Select</form:option>

								<c:forEach items="${yeartype}" var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>

							</form:select>
							<form:hidden path="financialyearOrgMap[${0}].mapId" id="mapId"/>
						</div>
					</div>
					<input type="hidden" value="${yearList}" id="yearList">

					<input type="hidden" value="${startMonth}" id="actualStartMonth">
					<input type="hidden" value="${endMonth}" id="actualEndMonth">
					<div id="monthstatus-change-div">
					<h4><spring:message
								code="master.finYr.monDetail" text="Month Details" /></h4>
					<div class="form-group">
						<label class="col-sm-2 control-label" for="startMonth"
							id="startMonth-lbl"> <spring:message
								code="finyear.form.startmonth" /></label>
						<div class="col-sm-4">
							<form:select path="financialyearOrgMap[${0}].faFromMonth"
								cssClass="form-control" id="startMonth">
								<form:option value=""><spring:message
								code="master.selectDropDwn" text="Select" /></form:option>
								<c:forEach items="${monthList}" var="lookUp">
									<form:option value="${lookUp.lookUpCode}"
										code="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>

						</div>


						<label class="col-sm-2 control-label" for="endMonth"
							id="endMonth-lbl"> <spring:message
								code="finyear.form.endmonth" /></label>
						<div class="col-sm-4">
							<form:select path="financialyearOrgMap[${0}].faToMonth"
								cssClass="form-control" id="endMonth">
								<form:option value=""><spring:message
								code="master.selectDropDwn" text="Select" /></form:option>
								<c:forEach items="${monthListAll}" var="lookUp">
									<form:option value="${lookUp.lookUpCode}"
										code="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>

						</div>
						</div>
						<div class="form-group">
						<label class="col-sm-2 control-label" for="monthStatus"
							id="monthStatus-lbl"><spring:message
								code="finyear.form.monthstatus" /></label>
						<div class="col-sm-4">
							<form:select path="financialyearOrgMap[${0}].faMonthStatus"
								id="monthStatus" cssClass="form-control">
								<form:option value="0"><spring:message
								code="master.selectDropDwn" text="Select" /></form:option>
								<c:forEach items="${monthtype}" var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>


					</div></div>
				</c:if>

			</div>
			<div class="text-center padding-bottom-20" id="divSubmit">
				<input type="button"  class="btn btn-success btn-submit"
					value="<spring:message code="master.save" text="Save"/>" onClick="return submitFinancialYearMaster(this)"
					id="submit">
				<input type="button" class="btn btn-warning" value="<spring:message code="reset.msg"/>" 
				    onclick="this.form.reset();resetFinYear();">
				<button type="button" title="Back" name="back"
					onClick="window.location.href='Financialyear.html'"
					class="btn btn-danger hidden-print"><spring:message code="back.msg" text="Back"/></button>
			</div>

		</form:form>
	</div>
	</div>
