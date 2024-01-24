<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/rnl/master/freezeList.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="rnl.master.freeze.prop.details" text="Freeze Property Details" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form action="PropFreeze.html" class="form-horizontal" name=""
				id="propFreeze">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<ul>
						<li><label id="errorId"></label></li>
					</ul>
				</div>

				<div class="text-center padding-bottom-10">
					<%-- <button type="button" class="btn btn-blue-2" id="addPropForm">
						<i class="fa fa-plus-circle"></i>&nbsp;
						<spring:message code="" text="Add Freeze Property" />
					</button> --%>
					<button class="btn btn-primary add" id="addPropForm" type="button">
						<i class="fa fa-plus-circle padding-right-5"></i>
						<spring:message code="rnl.master.add.freeze.property" text="Add Freeze Property" />
					</button>
				</div>

				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="propFreezeDatatables">
						<thead>
							<tr>
								<th width="5%" align="center"><spring:message code="rnl.master.sr.no"
										text="Sr.No" /></th>
								<th width="10%" align="center"><spring:message code="rnl.master.location"
										text="Location" /></th>
								<th width="12%" align="center"><spring:message code="rnl.master.estate.name"
										text="Estate Name" /></th>
								<th width="18%" align="center"><spring:message code="rnl.master.property.name"
										text="Property Name" /></th>
								<th width="10%" align="center"><spring:message code="rnl.master.from.date"
										text="From Date" /></th>
								<th width="10%" align="center"><spring:message code="rnl.master.to.date"
										text="To Date" /></th>
								<th width="15%" align="center"><spring:message code="rnl.master.shift"
										text="Shift" /></th>
								<th width="15%" align="center"><spring:message code="rnl.master.reason"
										text="Reason" /></th>
								<th width="5%" align="center"><spring:message code="rnl.master.unfreeze"
										text="Unfreeze" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.freezeDTOList}" var="freezeDto"
								varStatus="status">
								<tr>
									<td>${status.count}</td>
									<td>${freezeDto.location}</td>
									<td>${freezeDto.estate}</td>
									<td>${freezeDto.property}</td>
									<td>${freezeDto.fromDate}</td>
									<td>${freezeDto.toDate}</td>
									<td>${freezeDto.shift}</td>
									<td>${freezeDto.reasonOfFreezing}</td>
									<td class="text-center"><a class='btn btn-danger btn-sm'
									title=<spring:message code="rnl.master.delete"  />
										data-toggle='tooltip' data-placement='top'
										onclick="unFreezeProperty(${freezeDto.id})"><i
											class='fa fa-trash-o'></i><span class='hide'>Delete</span></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

			</form:form>
		</div>
	</div>
</div>




<%-- <div class="content animated slideInDown">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="master.estate.form.name" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="master.field.message" /><i
					class="text-red-1">*</i> <spring:message
						code="master.field.mandatory.message" /></span>
			</div>
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div>
			<form:form action="PropFreeze.html" method="POST"
				class="form-horizontal" id="propFreezeMaster"
				name="propFreezeMaster">


				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2" id="addPropForm">
						<i class="fa fa-plus-circle"></i>&nbsp;
						<spring:message code="" text="Add Freeze Property" />
					</button>
				</div>
				<div id="" align="center">
					<table id="propertyGrid"></table>
					<div id="propertyPager"></div>
				</div>
			</form:form>
		</div>
	</div>
</div> --%>