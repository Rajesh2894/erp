
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
<script type="text/javascript" src="js/rti/SecondApealRtiApplication.js"></script>
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
				<strong><spring:message code="rti.second.appeal" text="Second Apeal Rti Application"/></strong>
			</h2>

		</div>

		<div class="widget-content padding">


			<form:form action="SecondApealRtiApplication.html"
				class="form-horizontal form" name="SecondApealRtiApplication"
				id="SecondApealRtiApplication">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- 	<div class="accordion-toggle "> -->
				<div class="table-responsive clear" id="PropDetails">
					<table id="datatables" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th width="2%"><spring:message code="rti.srno" text="Sr.No"/></th>
								<th width="15%"><spring:message code="rti.applicationNO"
										text="Application No" /></th>
								<th width="15%"><spring:message code="rti.date.time"
										text="Date and Time" /></th>
								<th width="15%"><spring:message code="rti.second.apl.dept" text="Department" /></th>
								<th width="10%"><spring:message code="rti.second.appl.desc"
										text="Description/service" /></th>
								<th width="5%"><spring:message code="rti.hearingAction" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="rtiAppList"
								items="${command.getRtiApplicationList()}" varStatus="status">

								<tr>

									<td align="center"><label class="margin-left-20">${status.index+1}</label>
									</td>
									<td class="text-center">${rtiAppList.apmApplicationId}</td>
									<td class="text-center"><fmt:formatDate
											value="${rtiAppList.apmApplicationDate}"
											pattern="dd-MM-yyyy " /></td>
									<td class="text-center">${rtiAppList.departmentName}</td>
									<td class="text-center">${rtiAppList.inwAuthorityDept}</td>
									<td class="text-center">
										<button class="btn btn-primary btn-sm" title='View RTI form'
											type="button"
											onclick="viewRtiForm(${rtiAppList.apmApplicationId})">
											<i class='fa fa-file-text-o' aria-hidden='true'></i>
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
