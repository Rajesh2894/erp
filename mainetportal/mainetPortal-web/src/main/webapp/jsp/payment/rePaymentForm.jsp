<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script>
	function saveForm(element) {
		var succMsg = getLocalMessage('eip.citizen.repayment.msg');

		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'Y')

			return saveOrUpdateForm(element, succMsg,
					'RePaymentForm.html?redirectToPay', 'saveform');
		else
			return saveOrUpdateForm(element, succMsg,
					'RePaymentForm.html?PrintReport', 'saveform');
	}
</script>
<style>
#offLineLabel {
	display: none;
}
</style>
<ol class="breadcrumb">
	<li><a href="CitizenHome.html"><i class="fa fa-home"></i></a></li>
	<li><spring:message code="form.loi.home" text="Home"/></li>
	<li class="active"><spring:message code="form.loi.rep.fail.trans" text="Payment Of Failed Transactions"/></li>
</ol>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="form.loi.rep.failtrans"
					text="Repayment Of Failed Transaction"/>
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="form.loi.field" text="Field with" />
					<i class="text-red-1">*</i> <spring:message code="form.loi.mand"
						text="is mandatory" /> </span>
			</div>
			<form:form method="POST" action="RePaymentForm.html"
				name="frmRePaymentForm" id="frmRePaymentForm"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="accordion-toggle">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 id="" class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#App-details">
									<spring:message code="form.loi.applicant.details"
										text="Application Details"/>
								</a>
							</h4>
						</div>
						<div class="panel-collapse collapse in" id="App-details">
							<div class="panel-body">

								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="form.loi.serv.name" text="Service Name" /></label>
									<div class="col-sm-4">
										<form:input path="dto.serviceName" type="text"
											class="form-control" readonly="true" />
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="form.loi.app.no" text="Application Number" /> </label>
									<div class="col-sm-4">
										<form:input path="dto.refId" type="text" class="form-control"
											readonly="true" />
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 id="" class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"
									href="#Applicant-details"> <spring:message
										code="form.loi.app.detail" text="Applicant Details" /></a>
							</h4>
						</div>
						<div class="panel-collapse collapse in" id="Applicant-details">
							<div class="panel-body">

								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="app.mob.no" text="Applicant Mobile No" /></label>
									<div class="col-sm-4">
										<form:input path="dto.phoneNo" type="text"
											class="form-control" readonly="true" />
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="form.loi.mailid" text="Applicant EmailId" /></label>
									<div class="col-sm-4">
										<form:input path="dto.email" type="text" class="form-control"
											readonly="true" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="loi.form.appName" text="Applicant Name" /></label>
									<div class="col-sm-4">
										<form:input path="dto.payeeName" type="text"
											class="form-control" readonly="true" />
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 id="" class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"
									href="#LoiFees-details"><spring:message
										code="loi.form.trns.amt" text="Transaction Amount" /> </a>
							</h4>
						</div>
						<div class="panel-collapse collapse in" id="LoiFees-details">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="loi.form.tot.amt" text="Total Amount" /> </label>
									<div class="col-sm-4">
										<form:input path="dto.amount" value="${dto.amount}"
											cssClass="form-control text-right" readonly="true" />
									</div>
								</div>
							</div>
						</div>
					</div>

				</div>

				<div id="onlineOffline">
					<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
				</div>

				<div class="text-center padding-bottom-20">
					<input type="button" class="btn btn-success"
						onclick="return saveForm(this);"
						value="<spring:message code="citizen.editProfile.submit" text="Submit" />" />
					<apptags:backButton url="CitizenHome.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>
