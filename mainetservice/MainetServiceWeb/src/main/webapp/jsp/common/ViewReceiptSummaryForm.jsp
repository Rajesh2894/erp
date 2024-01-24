<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/common/viewReceipt.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="receipt.title" text=" Receipt Details" />
			</h2>
			<apptags:helpDoc url="ReceiptForm.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form id="ViewRecipt" action="ReceiptForm.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>


				<div class="form-group">
					<label class="control-label  col-sm-2"> <spring:message
							code="receipt.no"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input path="receiptMasBean.rmReceiptNo" id="rmRcptno" class="form-control" maxLength="12"  />
					</div>
					<label class="control-label col-sm-2"> <spring:message
							code="receipt.date"></spring:message>
					</label>

					<div class="col-sm-4">
						<div class="input-group">
							<form:input name="" Class="datepicker form-control" path="receiptMasBean.rmDate" id="rmDate" 
								maxLength="10" /> <label for="rmDatetemp"
								class="input-group-addon"> <i class="fa fa-calendar"></i><span
								class="hide"><spring:message
										code="account.additional.supplemental.auth.icon" text="icon" /></span><input
								type="hidden" id="rmDate"></label>
						</div>
					</div>

				</div>

				<div class="form-group">
					<label class="control-label  col-sm-2"> <spring:message
							code="receipt.amount" text="Receipt Amount" />
					</label>
					<div class="col-sm-4">
					<form:input path="receiptMasBean.rmAmount" id="rmAmount" class="form-control" onkeypress="return hasAmount(event, this, 12, 2)" />
					</div>
					<label class="control-label  col-sm-2"> <spring:message
							code="receipt.name"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input path="receiptMasBean.rmReceivedfrom" id="rmReceivedfrom" class="form-control" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="receipt.appno" text="Application No." /></label>
					<div class="col-sm-4">
						<form:input path="receiptMasBean.apmApplicationId" id="appNo" class="form-control hasNumber"
							maxlength="100" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="receipt.refno" text="Reference No" /></label>
					<div class="col-sm-4">
						<form:input path="receiptMasBean.refId" id="refNo" class="form-control hasNumber"
							maxlength="100" />
					</div>
				</div>


				<div class="form-group">

					<label for="text-1" class="col-sm-2 control-label"><spring:message
							code="receipt.dept" text="Department" /> </label>
					<div class="col-sm-4">

						<form:select path="receiptMasBean.dpDeptId" class="form-control chosen-select-no-results"
							label="Select" id="deptId">
							 <c:choose>
								<c:when test="${command.langId eq 1}">
								<form:option value=""><spring:message code="cfc.report.select" text="Select" /></form:option>
									<c:forEach items="${command.departmentList}" var="dept">
						        		<form:option value="${dept.dpDeptid}" code="${dept.dpDeptcode}">${dept.dpDeptdesc}</form:option>
						        	</c:forEach>
								</c:when>
								<c:otherwise>
								<form:option value=""><spring:message code="cfc.report.select"  text="Select" /></form:option>
									<c:forEach items="${command.departmentList}" var="dept">
							        	<form:option value="${dept.dpDeptid}" code="${dept.dpDeptcode}">${dept.dpNameMar}</form:option>
						         	</c:forEach>
								</c:otherwise>
							</c:choose>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="receipt.loino" text="LOI No" /></label>
					<div class="col-sm-4">
						<form:input path="receiptMasBean.rmLoiNo" id="loiNo" class="form-control"
							maxlength="100" />
					</div>
				</div>

				<div class="text-center margin-bottom-10">

					<button type="button" class="btn btn-success" title="Search"
						onclick="searchForm(this,'ReceiptForm.html','searchForm')">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="receipt.Search" text="Search"></spring:message>
					</button>

					<button type="button" class="btn btn-warning" title="Reset"
						onclick="ResetForm()">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="receipt.Reset" text="Reset"></spring:message>
					</button>

					<button type="button" class="btn btn-danger" id="back"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="receipt.back" text="Back"></spring:message>
					</button>

				</div>


				<div class="table-responsive">
					<table class="table table-bordered table-striped"
						id="receiptDatatables">
						<thead>
							<tr>
								<th scope="col" width="10%" align="center"><spring:message
										code="receipt.dept" text="Depatment" />
								<th scope="col" width="10%" align="center"><spring:message
										code="collection.receipt.number" text="Receipt Number" />
								<th scope="col" width="10%" class="text-center"><spring:message
										code="collection.receipt.date" text="Receipt Date" /></th>
								<th scope="col" width="20%" align="center"><spring:message
										code="collection.receipt.payeename" text="Payee" /></th>
								<th scope="col" width="15%" align="center"><spring:message
										code="collection.receipt.amount" text="Amount" /></th>
								<th scope="col" width="15%" class="text-center"><spring:message
										code="collection.receipt.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.receiptMasBeanList}" var="mstDto">
								<tr>
								    <td class="text-center">${mstDto.deptName}</td>
									<td class="text-center">${mstDto.rmReceiptNo}</td>
									<td class="text-center">${mstDto.rmDatetemp}</td>
									<td class="text-center">${mstDto.rmReceivedfrom}</td>
									<td class="text-center">${mstDto.rmAmount}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-3 btn-sm"
											title="View"
											onclick="viewReceipt(${mstDto.rmRcptid},'V');">
											<i class="fa fa-eye"></i>
										</button>
											<button type="button" class="btn btn-blue-3 btn-sm printClass"
											title="Print"
											onclick="printReceipt(${mstDto.rmRcptid},'P');">
											<i class="fa fa-print"></i>
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
