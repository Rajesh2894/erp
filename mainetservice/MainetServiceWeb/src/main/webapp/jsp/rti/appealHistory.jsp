
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/rti/appealHistory.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<style>
.sectionSeperator {
	border-bottom: 1px solid #123456;
	border-top: 1px solid #123456;
}
</style>


<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong>Appeal History</strong>
			</h2>

		</div>

		<div class="widget-content padding">


			<form:form action="AppealHistory.html"
				class="form-horizontal form" name="appealHistory"
				id="appealHistory">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- 	<div class="accordion-toggle "> -->
				<div class="table-responsive clear" id="PropDetails">
				
					<table id="datatables" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th width="2%">Sr.No</th>
								<th width="15%"><spring:message code=""
										text="Application No" /></th>
								<th width="15%"><spring:message code="" text="Department" /></th>
								<th width="15%"><spring:message code=""
										text="Information forward to Department" /></th>
								<th width="10%"><spring:message code=""
										text="Information received from department" /></th>
								<th width="5%"><spring:message code="" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="rtiAppList"
								items="${command.getRtiList()}" varStatus="status">

								<tr>

									<td align="center"><label class="margin-left-20">${status.index+1}</label>
									</td>
									<td class="text-center">${rtiAppList.apmApplicationId}</td>
									<td class="text-center">${rtiAppList.departmentName}</td>
									<td class="text-center"><fmt:formatDate
											value="${rtiAppList.rtiDeptidFdate}"
											pattern="dd-MM-yyyy " /></td>
											
									<%-- <td class="text-center"><c:if test="${empty rtiAppList.rtiDeciRecDate}"> --%>
											
									<td class="text-center"><fmt:formatDate value="${rtiAppList.rtiDeciRecDate}" pattern = "dd-MM-yyyy" /></td>
									<td class="text-center">
									
									<button type="button" class="btn btn-blue-2 btn-sm"
												onClick="viewRtiForm(${rtiAppList.apmApplicationId})" title="View">
												<i class="fa fa-eye"></i>
									</button>
									<c:if test="${empty rtiAppList.rtiDeciRecDate}">
										<button class="btn btn-primary btn-sm" title='View RTI form'
											type="button"
											onclick="editRtiForm(${rtiAppList.apmApplicationId})">
											<i class='fa fa-file-text-o' aria-hidden='true'></i>
										</button>
										
									</td>
									</c:if>
									<%-- <td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												onClick="viewRtiForm(${rtiAppList.apmApplicationId})" title="View">
												<i class="fa fa-eye"></i>
											</button>
										</td> --%>
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
