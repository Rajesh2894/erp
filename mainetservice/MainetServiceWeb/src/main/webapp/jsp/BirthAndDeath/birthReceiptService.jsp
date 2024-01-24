<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<script type="text/javascript" src="js/birthAndDeath/birthCorrection.js"></script>
<script type="text/javascript"
	src="js/birthAndDeath/IssuenceBirthCertificate.js"></script>
<script>

	$(document).ready(function() {
	   /*  var end = new Date();
	    end.setFullYear(2016);
	    $("#brDob").datepicker({
	        dateFormat : 'dd/mm/yy',
	        changeMonth : true,
	         changeYear: true,
	        yearRange: "-200:+200",
	        maxDate : new Date(end.getFullYear(), 11, 31)
	    }); */
		$("#rmDatetemp").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			minDate : disableBeforeDate,
			maxDate : today,
			changeYear : true
		});
		$("#rmDatetemp").datepicker('setDate', new Date());

		$("#rmDatetemp").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});
	    
	    $("#birthDate").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			minDate : disableBeforeDate,
			maxDate : today,
			changeYear : true
		});
		$("#birthDate").datepicker('setDate', new Date());

		$("#birthDate").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});
		
		$("#deathDate").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			minDate : disableBeforeDate,
			maxDate : today,
			changeYear : true
		});
		$("#deathDate").datepicker('setDate', new Date());

		$("#deathDate").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});
	    
	});

	

</script>


<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="Issuance.birth.Receipt"
						text="Issuance of Birth Certificate" />
				</h2>

				<apptags:helpDoc url="IssuanceBirthCertificate.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding" id="ashish4">
				<form:form id="birthReceiptDataid"
					action="IssuanceBirthCertificate.html" method="POST"
					class="form-horizontal" name="birthReceiptDataFormId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<form:hidden path="" cssClass="hasNumber form-control" id="brId" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv" style="display: none;"></div>

					<div class="form-group">

						<apptags:input labelCode="accounts.receipt.receipt_no"
							path="birthReceiptData.rmRcptno"
							cssClass="hasNumber form-control" maxlegnth="20">
						</apptags:input>

						<apptags:date fieldclass="datepicker"
							labelCode="accounts.receipt.receipt_date"
							datePath="birthReceiptData.rmDate" isMandatory=""
							cssClass="custDate mandColorClass date">
						</apptags:date>

					</div>

					<div class="form-group" align="center">
						<spring:message code="birth.death.or" text="OR" />
					</div>

					<div class="form-group">

						<apptags:date fieldclass="datepicker"
							labelCode="BirthRegistrationDTO.brDob"
							datePath="birthReceiptData.birthDate" isMandatory=""
							cssClass="custDate mandColorClass date">
						</apptags:date>

						<apptags:date fieldclass="datepicker"
							labelCode="TbDeathregDTO.drDod"
							datePath="birthReceiptData.deathDate" isMandatory=""
							cssClass="custDate mandColorClass date">
						</apptags:date>

					</div>

					<div class="form-group" align="center">
						<spring:message code="birth.death.or" text="OR" />
					</div>
					<div class="form-group">
						<apptags:input labelCode="BirthRegistrationDTO.brChildName"
							path="birthReceiptData.brChildName" isMandatory=""
							cssClass="hasNameClass form-control" maxlegnth="400">
						</apptags:input>

						<apptags:input labelCode="TbDeathregDTO.drDeceasedname"
							path="birthReceiptData.deathName" isMandatory=""
							cssClass="hasNameClass form-control" maxlegnth="400">
						</apptags:input>

					</div>

					<div class="text-center">
						<button type="button" class="btn btn-blue-3" title="Search"
							onclick="searchBndReceiptData(this)">
							<spring:message code="BirthRegDto.search" />
						</button>
						<button type="button" class="btn btn-warning btn-yellow-2"
							title="Submit"
							onclick="window.location.href ='IssuanceBirthCertificate.html?birthReceiptService'">
							<spring:message code="BirthRegDto.reset" />
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>

					<div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="BirthReceiptDataTable">
							<thead>
								<tr>
									<th width="5%" align="center"><spring:message
											code="BirthRegDto.srNo" text="Sr.No" /></th>
									<th width="10%" align="center"><spring:message
											code="BirthRegistrationDTO.receiptNo" text="Receipt Number" /></th>
									<th width="10%" align="center"><spring:message
											code="BirthRegistrationDTO.receiptDate" text="Receipt Date" /></th>
									<th width="10%" align="center"><spring:message
											code="BirthRegistrationDTO.payerName" text="Payer Name" /></th>
									<th width="10%" align="center"><spring:message
											code="BirthRegistrationDTO.receiptAmount"
											text="Receipt Amount" /></th>

								</tr>
							</thead>
							<tbody>

								<c:forEach items="${registrationDetail}" var="birth"
									varStatus="item">

									<tr>
										<td class="text-center">${item.count}</td>
										<td>${birth.rmRcptno}</td>
										<td>${birth.rmDate}</td>
										<td>${birth.rmReceivedfrom}</td>
										<td>${birth.rmAmount}</td>

									</tr>
								</c:forEach>
							</tbody>

						</table>
					</div>

				</form:form>
			</div>
		</div>
	</div>
</div>
