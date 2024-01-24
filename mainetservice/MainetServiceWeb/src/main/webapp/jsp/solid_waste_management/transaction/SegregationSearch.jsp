<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/Segregation.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="swm.segsum"
						text="Segregation Summary" /></strong>
						<apptags:helpDoc url="Segregation.html"></apptags:helpDoc>
			</h2>
		</div>
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="Segregation.html" commandName="command"
				class="form-horizontal form" name="Segregation" id="id_Segregation">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- End Validation include tag -->
				<div class="form-group">
					<label class="col-sm-2 control-label "
						for="Desposal Site"><spring:message code="swm.dsplsite" /></label>
					<div class="col-sm-4">
						<form:select path="segregationDto.deId"
							class="form-control  " label="Select" id="deId">
							 <form:option value="0"><spring:message code="solid.waste.select" text="select"/></form:option>
								<c:forEach items="${command.mrfMasterList}" var="lookUp">
												<form:option value="${lookUp.mrfId}" code="">${lookUp.mrfPlantName}</form:option>
											</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<apptags:date fieldclass="fromDateClass"
						labelCode="swm.fromDate"
						datePath="segregationDto.fromDate" 
						cssClass="fromDate "  readonly="true">
					</apptags:date>
					<apptags:date fieldclass="toDateClass"
						labelCode="swm.toDate"
						datePath="segregationDto.toDate" isMandatory="true"
						cssClass="toDate"  readonly="true">
					</apptags:date>
				</div>
				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2 search" onclick="searchSegregation('Segregation.html','SearchSegregation')">
					<i class="fa fa-search"></i>
						<spring:message code="solid.waste.search" text="Search" />
					</button>
					<button type="submit" class="button-input btn btn-success"
						onclick="openaddSegregation('Segregation.html', 'AddSegregation') "
						name="button-Add" style="" id="button-submit">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="swm.seg" text="Segregation" />
					</button>
				</div>
				<!-- End button -->
			<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="id_segregationTable">
							<thead>
								<tr>
									<th width="8%"><spring:message code="swm.tripNo" text="Transaction No." /></th>
									<th><spring:message code="swm.date" text="Date" /></th>
									<th><spring:message code="swm.wastetype" text="Waste Type" /></th>
									<th><spring:message code="swm.ttlvolume" text="Total Volume" /></th>									
									<th width="10%"><spring:message code="swm.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.tbSwWastesegDets}" var="seg" varStatus="loop">
									<tr>
										<td align="center" width="8%">${seg.grId}</td>
										<td align="center"><fmt:formatDate pattern="dd/MM/yyyy" value="${seg.grDate}"/></td>										
										<td align="center">${command.getHierarchicalLookUpObject(seg.tbSwWastesegDets[0].codWast1).getLookUpDesc()}</td>
										<td align="center">${seg.totalVol} <spring:message
																	code="swm.kilo" text="Kgs" /></td>										
										<td class="text-center" width="10%">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View " onclick="modifySegregation(${seg.grId},'Segregation.html','ViewSegregation','V')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit " onclick="modifySegregation(${seg.grId},'Segregation.html','EditSegregation','E')">
												<i class="fa fa-pencil"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>





