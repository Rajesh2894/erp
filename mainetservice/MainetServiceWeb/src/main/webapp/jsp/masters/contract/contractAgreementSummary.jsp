<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="../assets/libs/jqueryui/jquery-ui-datepicker.css"
	rel="stylesheet" type="text/css">

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/masters/contract/contractAgreement.js"></script>
<script src="js/masters/contract/contractAgreementSummary.js"></script>

<script>
$( document ).ready(function() {

	$(".datepicker").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		maxDate: '-0d',
		changeYear: true,
	});
	
	});	
</script>


<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated slideInDown">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="master.contract.summary"
					text="Contract Summary"></spring:message>
			</h2>
			<apptags:helpDoc url="ContractAgreement.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="rnl.book.field" text="Field with"></spring:message>
					<strong class="text-red-1">*</strong> <spring:message
						code="master.estate.field.mandatory.message" text="is mandatory"></spring:message></span>
			</div>
			<form:form action="ContractAgreement.html"
				class="form-horizontal form" name="ContractAgreementSummary"
				id="ContractAgreement">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="error-div warning-div error-div alert alert-danger alert-dismissible"></div>
				<div class="form-group">
					<label class="control-label col-sm-2" for="ContractNo"><spring:message
							code="master.contract.no" text="Agreement No."></spring:message></label>
					<div class="col-sm-4">
						<form:input path="" type="text" class="form-control hasNumberWithFSlash"
							id="contractNo" maxlength="10" />
					</div>
					<label class="control-label col-sm-2" for="ContractDate"><spring:message
							code="master.contract.date" text="Agreement Date"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" type="text" class="form-control datepicker"
								id="contractDate" placeholder="dd/mm/yyyy" maxlength="10"/>
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>

				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="ViewClosedContracts"><spring:message
							code="agreement.status" text="Status"></spring:message></label>
					<div class="col-sm-4">

						<form:select path=""
							cssClass="form-control chosen-select-no-results"
							id="viewClosedContracts">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<form:option value="Y">
								<spring:message code='agreement.status.yes' />
							</form:option>
							<form:option value="N">
								<spring:message code='agreement.status.no' />
							</form:option>
							<form:option value="D">
								<spring:message code='agreement.status.draft' />
							</form:option>
						</form:select>

					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" id="btnsearch"
						class="btn btn-blue-2 searchData" onclick="searchContract();">
						<strong class="fa fa-search"></strong>
						<spring:message code="search.data" text="Search"></spring:message>
					</button>
					<button class="btn btn-warning  reset" type="reset"
						onclick="window.location.href='ContractAgreement.html'">
						<i class="button-input"></i>
						<spring:message code="reset.msg" />
					</button>
					<%-- <form:input path="" id="addEstateLink" value="Add Agreement"
						class="btn btn-blue-2" type="BUTTON" /> --%>
					<button class="btn btn-success add" id="addEstateLink"
						type="button">
						<i class="button-input"></i>
						<spring:message code="contract.add.agreement" text="" />
					</button>

				</div>


				<div class="table-responsive">
					<table class="table table-bordered table-striped contractAgreementSummaryTable" id="datatables">
						<thead>
							<tr>
								<%-- <th scope="col" width="10%" align="center"><spring:message
										code="" text="Cont ID" /></th> --%>
								<th scope="col" width="10%" align="center"><spring:message
										code="contract.label.tenderNo" text="Tender No." /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="contract.label.LOA" text="LOA No." /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="master.contract.no" text="Agreement No." /></th>
								<th scope="col" width="8%" align="center"><spring:message
										code="master.contract.entered.Date" text="Agreement Entered Date" /></th>
								<th scope="col" width="15%" align="center"><spring:message
										code="master.department" text="Department" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="contract.label.contractor.name" text="Contractor Name" /></th>
								<th scope="col" width="8%" align="center"><spring:message
										code="contract.label.contractFromDate"
										text="Agreement From Date" /></th>
								<th scope="col" width="8%" class="text-center"><spring:message
										code="contract.label.contractToDate" text="Agreement To Date" /></th>
								<th scope="col" width="10%" class="text-center"><spring:message
										code="estate.grid.column.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.summaryDTOList}" var="mstDto">
								<tr class="text-center">
									<%-- <td hidden>${mstDto.contId}</td> --%>
									<td>${mstDto.contTndNo}</td>
									<td>${mstDto.contLoaNo}</td>
									<td>${mstDto.contNo}</td>
									<td>
										<fmt:parseDate pattern="dd/mm/yyyy" value="${mstDto.contDate}" var="date" />
										<fmt:formatDate type="date" var="formatedDate" value="${date}" pattern="yyyymmdd" />
										<span style="display:none"> ${formatedDate} </span>
										<c:out value="${mstDto.contDate}"></c:out>
									</td>
									<td>${mstDto.contDept}</td>
									<td>${mstDto.contp2Name}</td>
									<td>
										<fmt:parseDate pattern="dd/mm/yyyy" value="${mstDto.contFromDate}" var="date" />
										<fmt:formatDate type="date" var="formatedDate" value="${date}" pattern="yyyymmdd" />
										<span style="display:none"> ${formatedDate} </span>
										<c:out value="${mstDto.contFromDate}"></c:out>
									</td>
									<td>
										<fmt:parseDate pattern="dd/mm/yyyy" value="${mstDto.contToDate}" var="date" />
										<fmt:formatDate type="date" var="formatedDate" value="${date}" pattern="yyyymmdd" />
										<span style="display:none"> ${formatedDate} </span>
										<c:out value="${mstDto.contToDate}"></c:out>
									</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onclick="showContract(${mstDto.contId},'V');"
											title="<spring:message code="contract.master.view.contract"></spring:message>">
											<i class="fa fa-eye"></i>
										</button>

										<button type="button" class="btn btn-warning btn-sm"
											onclick="showContract(${mstDto.contId},'E');"
											title="<spring:message code="contract.master.edit.contract"></spring:message>">
											<i class="fa fa-pencil"></i>
										</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>
